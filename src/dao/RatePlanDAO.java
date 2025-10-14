package dao;

import dto.RatePlanDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatePlanDAO {
    // planId로 조회
    public RatePlanDTO findById(int planId) {

        String sql = "SELECT * FROM RATE_PLAN WHERE plan_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, planId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    RatePlanDTO p = new RatePlanDTO();

                    p.setPlanId(rs.getInt("plan_id"));
                    p.setPlanName(rs.getString("plan_name"));
                    p.setPrice(rs.getInt("price"));
                    p.setPlanType(rs.getString("plan_type"));
                    // 기간권인 경우 제공 시간 null
                    p.setProvidedTime(rs.getObject("provided_time", Integer.class));
                    p.setValidDays(rs.getInt("valid_days"));

                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // plan이 없을 시 null 반환
    }

    // 전체 요금제 조회
    public List<RatePlanDTO> selectAll() {

        List<RatePlanDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM RATE_PLAN ORDER BY plan_id ASC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RatePlanDTO p = new RatePlanDTO();
                    p.setPlanId(rs.getInt("plan_id"));
                    p.setPlanName(rs.getString("plan_name"));
                    p.setPrice(rs.getInt("price"));
                    p.setPlanType(rs.getString("plan_type"));
                    p.setProvidedTime(rs.getInt("provided_time"));
                    p.setValidDays(rs.getInt("valid_days"));


                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
