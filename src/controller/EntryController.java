package controller;

import dto.ExtraFeeDTO;
import service.EntryService;
import service.SeatService;
import view.swing.ExtraFeeDialog;
import view.swing.MainFrame;

import javax.swing.*;
import java.sql.SQLException;

public class EntryController {
    private MainFrame mainFrame;
    private EntryService entryService;
    private SeatService seatService;

    public EntryController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.entryService = new EntryService();
        this.seatService = new SeatService();
    }

    // 입실 - 외출 후 복귀. 이미 자리가 있는 상태.
    public void enterEntry(int memberId) {

        try {
            boolean ok = entryService.returnEntry(memberId);
            if (ok)
                JOptionPane.showMessageDialog(null, "입실 완료!");
            else // log가 없으면
                JOptionPane.showMessageDialog(null, "로그인 후 좌석을 선택해주세요.");

            mainFrame.showCard("login");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 퇴실
    public void exitEntry(int memberId) {
        // entryLog update & 좌석 상태 available & 이용권 selected false
        try {
            ExtraFeeDTO res = entryService.exitEntry(memberId);
            if (res == null){ // res가 null인 경우. 퇴실할 것이 없는 경우.
                JOptionPane.showMessageDialog(null, "이미 퇴실되었습니다.");
            } else if (res.getExtraFee() == 0) {
                // 정산금 없음.
                JOptionPane.showMessageDialog(null, "퇴실 완료!");
            } else {
                // 정산금 추가결제 받아야됨.
                new ExtraFeeDialog(mainFrame, res).setVisible(true);
            }
            mainFrame.showCard("login");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
