package service;

import java.sql.SQLException;

import dao.MemberDAO;
import dto.MemberDTO;

public class MemberService {
	
	private MemberDAO dao;
	
	public MemberService() {
        this.dao = new MemberDAO();
    }
	
	//회원가입
	public boolean signUp(String phone, String pw, String name) throws SQLException {
		
		MemberDTO m = new MemberDTO(phone, pw, name);
		if(dao.findByPhone(m.getPhoneNo()) != null) {
			return false; // 전화번호 중복(이미 회원임)
		}
		
		dao.insertMember(m);
        return true;
	}
	
	// 로그인
	public MemberDTO login(String phone, String pw) throws SQLException {
		
		return dao.findByPhoneAndPassword(phone, pw);
	}
}
