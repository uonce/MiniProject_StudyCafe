package controller;

import dto.MemberPassDTO;
import dto.PassResponseDTO;
import service.MemberPassService;
import view.swing.MainFrame;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class MemberPassController {
    private MemberPassService passService;
    private MainFrame mainFrame;

    public MemberPassController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.passService = new MemberPassService();
    }

    // 이용권이 있을 경우
    // 이용권 선택
    public void selectPass(int passId) {
        try {
            passService.selectPass(passId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 이용권 조회 -> memberController에서.
    public List<PassResponseDTO> getAvailablePasses(int memberId) {
        try {
            return passService.getAvailablePasses(memberId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 요금제 선택(이용권이 없을 경우)
    public void buyPass(int memberId, int planId) {
        try {
            boolean ok = passService.buyPass(memberId, planId);
            if (ok) {
                JOptionPane.showMessageDialog(null, "이용권 구매 완료!");
                List<PassResponseDTO> updatedPass = passService.getAvailablePasses(memberId);
                mainFrame.getPassSelectPanel().loadPassList(updatedPass);
                mainFrame.showCard("passSelect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
