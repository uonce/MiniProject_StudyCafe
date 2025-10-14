package controller;

import dto.SeatDTO;
import service.EntryService;
import service.MemberPassService;
import service.SeatService;
import view.swing.MainFrame;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class SeatController {
    private SeatService seatService;
    private EntryService entryService;
    private MainFrame mainFrame;

    public SeatController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.seatService = new SeatService();
        this.entryService = new EntryService();
    }

    // 좌석 상태 변화
    // 좌석을 선택하면 자동으로 입실 처리가 되어야됨(자리만 차지하고 있을 수는 없음)
    public void updateSeatStatus(int passId, int seatId, String status) {
        try {
            // 사용자가 이미 좌석을 선택한 경우
            // 같은 passId -> entrylog에 입실 중인 log가 있으면 -> 로그인 화면으로 복귀
            //***************여기서 좌석 변경 기능 추가***************
            if (entryService.isEntered(passId)) {
                JOptionPane.showMessageDialog(null, "이미 선택된 좌석이 있습니다.");
                mainFrame.showCard("login");
                return;
            }

            boolean ok = seatService.updateSeatStatus(seatId, status);
            if (ok) {
                // 입실 처리
                boolean result = entryService.enterEntry(passId, seatId);
                if (result) {
                    JOptionPane.showMessageDialog(null, "입실 완료!");
                    mainFrame.showCard("login");
                }
            } else {
                JOptionPane.showMessageDialog(null, "입실 실패. 다시 선택해 주세요");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 좌석 전체 조회
    public void getSeats(int passId) {
        try {
            mainFrame.getSeatSelectPanel().loadSeats(seatService.getSeats(), passId);
            mainFrame.showCard("seatSelect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
