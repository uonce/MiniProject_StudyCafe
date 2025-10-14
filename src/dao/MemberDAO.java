package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.MemberDTO;

public class MemberDAO {

	// 회원가입
	public int insertMember(MemberDTO m) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO MEMBER (phone_no, password, member_name) VALUES (?, ?, ?)";
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getPhoneNo());
            pstmt.setString(2, m.getPassword());
            pstmt.setString(3, m.getMemberName());
            
            return pstmt.executeUpdate(); // 성공 시 1 반환
        
		} catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
			// 리소스 정리 작업
			DBManager.releaseConnection(pstmt, conn);
		}
	}
	
	// 로그인(전화번호, 비밀번호)
	public MemberDTO findByPhoneAndPassword(String phone, String pw) {
		
		String sql = "SELECT * FROM MEMBER WHERE phone_no = ? AND password = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setString(1, phone);
            pstmt.setString(2, pw);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MemberDTO m = new MemberDTO();

                    m.setMemberId(rs.getInt("member_id"));
                    m.setPhoneNo(rs.getString("phone_no"));
                    m.setPassword(rs.getString("password"));
                    m.setMemberName(rs.getString("member_name"));

                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
        return null; // 로그인 실패 시 null 반환
    }
	
	// 중복 확인
	public MemberDTO findByPhone(String phone) {
		
		String sql = "SELECT * FROM MEMBER WHERE phone_no = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setString(1, phone);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    MemberDTO m = new MemberDTO();

                    m.setMemberId(rs.getInt("member_id"));
                    m.setPhoneNo(rs.getString("phone_no"));
                    m.setPassword(rs.getString("password"));
                    m.setMemberName(rs.getString("member_name"));

                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
        return null; // 로그인 실패 시 null 반환
    }
	
	// 전체 회원 조회
	public List<MemberDTO> selectAll() {
		
        List<MemberDTO> list = new ArrayList<>();
        
        String sql = "SELECT * FROM MEMBER ORDER BY member_id ASC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MemberDTO m = new MemberDTO();
                    m.setMemberId(rs.getInt("member_id"));
                    m.setPhoneNo(rs.getString("phone_no"));
                    m.setPassword(rs.getString("password"));
                    m.setMemberName(rs.getString("member_name"));

                    list.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
	
	public static void main(String[] args) {
		MemberDAO dao = new MemberDAO();
		int ret = dao.insertMember(new MemberDTO("01099999999", "1111", "테스트"));
		
		System.out.println("회원가입 결과: " + (ret == 1 ? "성공" : "실패"));
	}
}
