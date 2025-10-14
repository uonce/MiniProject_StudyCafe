package dao;

import dto.MemberDTO;
import dto.RatePlanDTO;
import dto.SeatDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    // 좌석 전체 조회
    public List<SeatDTO> selectAll() {

        List<SeatDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM SEAT ORDER BY seat_id ASC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SeatDTO s = new SeatDTO();
                    s.setSeatId(rs.getInt("seat_id"));
                    s.setSeatNo(rs.getString("seat_no"));
                    s.setSeatStatus(rs.getString("seat_status"));

                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 좌석 상태 변화
    public int updateBySeatId(int seatId, String status) {

        String sql = "UPDATE SEAT SET seat_status = ? WHERE seat_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, seatId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}