package service;

import dao.MemberPassDAO;
import dao.RatePlanDAO;
import dto.MemberPassDTO;
import dto.PassResponseDTO;
import dto.RatePlanDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MemberPassService {

    private MemberPassDAO memberPassDAO;
    private RatePlanDAO ratePlanDAO;

    public MemberPassService() {
        this.memberPassDAO = new MemberPassDAO();
        this.ratePlanDAO = new RatePlanDAO();
    }

    // 이용권이 있을 경우 => 전체 이용권 조회
    public List<PassResponseDTO> getAvailablePasses(int memberId) throws SQLException {
        // 이용권 존재 확인
        List<PassResponseDTO> list = memberPassDAO.findByMemberId(memberId);
        if(list.isEmpty()){
            return null;
        }

        return list;
    }

    // 이용권 선택
    public void selectPass(int passId) throws SQLException {
        memberPassDAO.updateSelectedByPassId(passId, 1);
    }

    // 이용권 구매
    public boolean buyPass(int memberId, int planId) throws SQLException {
        RatePlanDTO rp = ratePlanDAO.findById(planId);

        MemberPassDTO pass = new MemberPassDTO();
        pass.setMemberId(memberId);
        pass.setPlanId(planId);
        // 남은 시간 설정
        pass.setRemainingTime(rp.getProvidedTime());
        //유효기간 설정
        LocalDate expireDate = LocalDate.now().plusDays(rp.getValidDays());
        pass.setExpireDate(expireDate);

        pass.setActive(true);
        pass.setSelected(false);

        int result = memberPassDAO.insertMemberPass(pass);
        return result == 1;
    }

    // memberId로 선택된 passId 찾기
    public int getPassId(int memberId) throws SQLException {
        return memberPassDAO.findByMemberIdAndSelected(memberId);
    }
}
