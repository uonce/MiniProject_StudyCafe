package dao;

import dto.EntryLogDTO;
import dto.MemberDTO;
import dto.RatePlanDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

public class EntryLogDAO {
    // 입실 - 로그 생성
    public int insertEntryLog(EntryLogDTO entry) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO ENTRY_LOG (seat_id, pass_id, entry_status, start_time, expected_end_time) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, entry.getSeatId());
            pstmt.setInt(2, entry.getPassId());
            pstmt.setString(3, entry.getEntryStatus());
            pstmt.setObject(4, entry.getStartTime());
            pstmt.setObject(5, entry.getExpectedEndTime());

            return pstmt.executeUpdate(); // 성공 시 1 반환

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            // 리소스 정리 작업
            DBManager.releaseConnection(pstmt, conn);
        }
    }

    // 퇴실 - 생성된 로그에 퇴실 처리
    // entryLog에서 퇴실시간이 비어있는 것(입실 중인 것) 중에 member_id-pass_id selected 찾기
    public EntryLogDTO findActiveEntry(int memberId) {
        String sql = """
                SELECT e.log_id, e.seat_id, e.pass_id, e.start_time, e.expected_end_time FROM (
                    SELECT * FROM entry_log
                    WHERE entry_status = "IN") e
                WHERE e.pass_id = (
                    SELECT p.pass_id FROM member_pass p
                    WHERE p.member_id = ?
                    AND p.selected = 1
                )
                """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    EntryLogDTO entry = new EntryLogDTO();

                    entry.setLogId(rs.getInt("log_id"));
                    entry.setSeatId(rs.getInt("seat_id"));
                    entry.setPassId(rs.getInt("pass_id"));
                    entry.setStartTime(rs.getObject("start_time", LocalDateTime.class));
                    entry.setExpectedEndTime(rs.getObject("expected_end_time", LocalDateTime.class));

                    return entry;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 찾은 로그에 entry_status, 퇴실 시간, extra_fee 업데이트
    public int updateExitEntryLog(int logId, LocalDateTime endTime, int extraFee) {

        String sql = """
                UPDATE entry_log
                SET entry_status = "OUT", end_time = ?, extra_fee = ?
                WHERE log_id = ?
                """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, endTime);
            pstmt.setInt(2, extraFee);
            pstmt.setInt(3, logId);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 현재 입실 상태 조회
    public String findEntryStatusByPassId(int passId) {

        String sql = "SELECT entry_status FROM ENTRY_LOG WHERE pass_id = ? AND end_time IS NULL";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, passId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    return rs.getString("entry_status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // plan이 없을 시 null 반환
    }
    
    // memberId로 현재 활성화 되어있는 log_id만 찾기(퇴실X)
    public int findActiveLogId(int memberId) {
        String sql = """
                SELECT e.log_id FROM (
                    SELECT * FROM entry_log
                    WHERE entry_status IN ("IN","BREAK")) e
                WHERE e.pass_id = (
                	SELECT p.pass_id FROM member_pass p
                	WHERE p.member_id = ?
                	AND p.selected = 1
                )
                """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int logId = rs.getInt("log_id");

                    return logId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 입실 상태 변경 - 입실/외출 상태인 것 -> 입실
    public int updateEntryStatus(int logId, String status) {

        String sql = """
                UPDATE entry_log
                SET entry_status = ?
                WHERE log_id = ?
                """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, logId);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static void main(String[] args) {
        EntryLogDAO dao = new EntryLogDAO();
        EntryLogDTO dto = dao.findActiveEntry(2);
        System.out.println(dto);

        // 1. 퇴실시간 찍기
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println(endTime);
        // 2. 퇴실 시간에서 퇴실 예정 시간을 뺀다 -> 잔여시간(분) 도출
        Duration duration = Duration.between(dto.getExpectedEndTime(), endTime);
        double hours = (double) duration.toMinutes() / 60.0;
        int fee = 0;
        if (hours > 0) {
            int hour = (int) Math.ceil(hours);
            System.out.println(hour);
            // 3. 잔여시간(분) * 시간당 2000원
            fee = hour * 2000;
            System.out.println(fee);
        }


        dao.updateExitEntryLog(15, endTime, fee);
    }
}
