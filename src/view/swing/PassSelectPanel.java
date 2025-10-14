package view.swing;

import controller.MemberPassController;
import controller.SeatController;
import dto.MemberPassDTO;
import dto.PassResponseDTO;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PassSelectPanel extends JPanel {
    private MainFrame mainFrame;
    private MemberPassController passController;
    private SeatController seatController;
    private JPanel passPanel;

    public PassSelectPanel(MainFrame mainFrame, SeatController seatController,  MemberPassController passController) {
        this.mainFrame = mainFrame;
        this.seatController = seatController;
        this.passController = passController;

        setLayout(new BorderLayout());

        // 상단
        JPanel top = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("뒤로가기");
        JLabel title = new JLabel("보유 이용권 선택", SwingConstants.CENTER); // 또는 "좌석 선택"
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        top.add(backBtn, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(top, BorderLayout.NORTH);

        backBtn.addActionListener(e -> mainFrame.showCard("login"));

        passPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        passPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(new JScrollPane(passPanel), BorderLayout.CENTER);
    }

    public void loadPassList(List<PassResponseDTO> list) {
        passPanel.removeAll();
        if (list == null || list.isEmpty()) {
            passPanel.add(new JLabel("보유 중인 이용권이 없습니다."));
        } else {
            for (PassResponseDTO pass : list) {
                String html = String.format(
                        "<b>%s</b><br>%s<br>남은 시간: %d분<br>만료일: %s",
                        pass.getPlanName(),
                        pass.getPlanType(),
                        pass.getRemainingTime(),
                        pass.getExpireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                );
                JButton btn = createCardButton(html);
                btn.addActionListener(e -> {
                    passController.selectPass(pass.getPassId());
                    JOptionPane.showMessageDialog(this,
                            pass.getPlanName() + " 이용권을 선택했습니다.\n좌석을 선택하세요!");

                    seatController.getSeats(pass.getPassId());
                });
                passPanel.add(btn);
            }
        }
        passPanel.revalidate();
        passPanel.repaint();
    }

    private JButton createCardButton(String html) {
        JButton btn = new JButton("<html>" + html + "</html>");
        btn.setPreferredSize(new Dimension(200, 120));
        btn.setBackground(new Color(250, 250, 245));
        btn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(230, 240, 255)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(250, 250, 245)); }
        });
        return btn;
    }
}
