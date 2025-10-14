package view.swing;

import controller.SeatController;
import dto.SeatDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SeatSelectPanel extends JPanel {
    private MainFrame mainFrame;
    private SeatController controller;
    private JPanel seatPanel;

    public SeatSelectPanel(MainFrame mainFrame, SeatController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new BorderLayout());

        // 상단
        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("좌석 선택", SwingConstants.CENTER); // 또는 "좌석 선택"
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        top.add(title, BorderLayout.CENTER);
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        seatPanel = new JPanel(new GridLayout(3, 4, 15, 15));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(seatPanel, BorderLayout.CENTER);
    }

    public void loadSeats(List<SeatDTO> seats, int passId) {
        seatPanel.removeAll();
        for (SeatDTO seat : seats) {
            JButton btn = createSeatButton(seat, passId);
            seatPanel.add(btn);
        }
        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private JButton createSeatButton(SeatDTO seat, int passId) {
        JButton btn = new JButton("좌석 " + seat.getSeatNo());
        btn.setPreferredSize(new Dimension(100, 60));
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (seat.getSeatStatus().equals("AVAILABLE")) {
            btn.setBackground(new Color(173, 230, 181));
            btn.addActionListener(e -> controller.updateSeatStatus(passId, seat.getSeatId(), "OCCUPIED"));
        } else {
            btn.setBackground(new Color(200, 200, 200));
            btn.setEnabled(false);
        }
        return btn;
    }
}

