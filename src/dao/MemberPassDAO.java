package dao;

import dto.MemberPassDTO;
import dto.PassResponseDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberPassDAO {

    // 이용권 생성
    public int insertMemberPass(MemberPassDTO pass) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO MEMBER_PASS (member_id, plan_id, remaining_time, expire_date, is_active, selected) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pass.getMemberId());
            pstmt.setInt(2, pass.getPlanId());

            // 기간권인 경우에 남은 시간이 null
            if (pass.getRemainingTime() != null) pstmt.setInt(3, pass.getRemainingTime());
            else pstmt.setNull(3, Types.INTEGER);

            pstmt.setDate(4, Date.valueOf(pass.getExpireDate()));
            pstmt.setBoolean(5, pass.isActive());
            pstmt.setBoolean(6, pass.isSelected());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            // 리소스 정리 작업
            DBManager.releaseConnection(pstmt, conn);
        }
    }

    // member_id로 조회, 요금제 이름, 남은 시간, 종료일 반환
    public List<PassResponseDTO> findByMemberId(int memberId) {

        String sql = "select mp.pass_id, rp.plan_name, rp.plan_type, mp.remaining_time, mp.expire_date\n" +
                "from member_pass mp join rate_plan rp\n" +
                "on mp.plan_id = rp.plan_id\n" +
                "where mp.member_id = ?\n" +
                "and mp.is_active = 1\n" +
                "ORDER BY pass_id DESC";

        List<PassResponseDTO> list = new ArrayList<>();

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PassResponseDTO pass = new PassResponseDTO();
                    pass.setPassId(rs.getInt("pass_id"));
                    pass.setPlanName(rs.getString("plan_name"));
                    pass.setPlanType(rs.getString("plan_type"));
                    pass.setRemainingTime(rs.getInt("remaining_time"));
                    pass.setExpireDate(rs.getDate("expire_date").toLocalDate());

                    list.add(pass);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // pass_id로 조회
    public MemberPassDTO findByPassId(int passId) {

        String sql = "SELECT * FROM MEMBER_PASS WHERE pass_id = ? ORDER BY pass_id DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, passId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MemberPassDTO pass = new MemberPassDTO();

                    pass.setPassId(rs.getInt("pass_id"));
                    pass.setMemberId(rs.getInt("member_id"));
                    pass.setPlanId(rs.getInt("plan_id"));
                    pass.setRemainingTime(rs.getObject("remaining_time", Integer.class));
                    pass.setExpireDate(rs.getDate("expire_date").toLocalDate());
                    pass.setActive(rs.getBoolean("is_active"));
                    pass.setSelected(rs.getBoolean("selected"));

                    return pass;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // pass_id로 이용권 선택 : selected -> true
    public void updateSelectedByPassId(int passId, int selected) {

        String sql = "UPDATE MEMBER_PASS SET selected = ? WHERE pass_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, selected);
            pstmt.setInt(2, passId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // memberid로 passid 중 selected된 데이터 조회
    public int findByMemberIdAndSelected(int memberId) {

        String sql = "SELECT * FROM MEMBER_PASS WHERE member_id = ? AND selected = 1";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int passId = rs.getInt("pass_id");

                    return passId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    // 퇴실 : 이용권 사용량 업데이트 - 남은시간에서 사용시간만큼 차감, selected false
    public void updateSelectedAndRemainingTime(int passId, int selected, int usedTime) {

        String sql = "UPDATE MEMBER_PASS SET selected = ?, remaining_time = remaining_time - ? WHERE pass_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, selected);
            pstmt.setInt(2, usedTime);
            pstmt.setInt(3, passId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 다쓴(만료된) 이용권 비활성화
    public void updateIsActive(int passId, boolean isActive) {

        String sql = "UPDATE MEMBER_PASS SET is_active = ? WHERE pass_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isActive);
            pstmt.setInt(2, passId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
