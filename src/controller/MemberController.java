package controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import dto.LoginResponseDTO;
import dto.MemberDTO;
import dto.MemberPassDTO;
import dto.PassResponseDTO;
import service.EntryService;
import service.MemberPassService;
import service.MemberService;
import service.RatePlanService;
import view.swing.MainFrame;


public class MemberController {

    private MemberService memberService;
    private MemberPassService passService;
    private RatePlanService planService;
    private EntryService entryService;
    private MainFrame mainFrame;

    public MemberController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.memberService = new MemberService();
        this.passService = new MemberPassService();
        this.planService = new RatePlanService();
        this.entryService = new EntryService();
    }
	
	public boolean signUp(String phone, String pw, String name) {
		try {
			boolean ok = memberService.signUp(phone, pw, name);
			if (ok) {
                JOptionPane.showMessageDialog(null, "회원가입 성공!");
                return true;
            }
            else {
                JOptionPane.showMessageDialog(null, "이미 등록된 회원입니다.");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "회원가입 실패: 다시 시도해 주세요");
            e.printStackTrace();
            return false;
        }
	}
	
	public void login(String phone, String pw) {
	    try {
	        MemberDTO m = memberService.login(phone, pw);
	        if (m == null) {
	            JOptionPane.showMessageDialog(null, "전화번호 또는 비밀번호가 틀렸습니다.");
	            return;
	        }

            mainFrame.setLoginMember(m.getMemberId());
	        JOptionPane.showMessageDialog(null, m.getMemberName() + "님 환영합니다!");

            List<PassResponseDTO> passList = passService.getAvailablePasses(m.getMemberId());
            // 전체 요금제 조회
            if (passList == null || passList.isEmpty()) {
                mainFrame.getPlanSelectPanel().loadPlans(
                        planService.getRatePassList()
                );
                mainFrame.showCard("planSelect");
            } else { // 보유 이용권 조회
                mainFrame.getPassSelectPanel().loadPassList(passList);
                mainFrame.showCard("passSelect");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "로그인 오류 발생: 다시 시도해주세요");
        }
    }

    // 입/퇴실
    public void entry(String phone, String pw) {
        try {
            MemberDTO m = memberService.login(phone, pw);
            if (m == null) {
                JOptionPane.showMessageDialog(null, "전화번호 또는 비밀번호가 틀렸습니다.");
                return;
            }

            if (entryService.isEnteredByMemberId(m.getMemberId())){
                mainFrame.setLoginMember(m.getMemberId());
                JOptionPane.showMessageDialog(null, m.getMemberName() + "님 환영합니다!");
                mainFrame.showCard("entry"); // EntryPanel로 이동
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "로그인 오류 발생: 다시 시도해주세요");
        }
    }
}
