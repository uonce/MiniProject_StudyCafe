package view.swing;

import controller.EntryController;
import dto.MemberDTO;

import javax.swing.*;
import java.awt.*;

public class EntryPanel extends JPanel {
    private MainFrame mainFrame;
    private EntryController entryController;
    private JPanel buttonPanel;

    public EntryPanel(MainFrame mainFrame, EntryController entryController) {
        this.mainFrame = mainFrame;
        this.entryController = entryController;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("입/퇴실 관리", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // 중앙 버튼 카드 패널
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        buttonPanel.setBackground(Color.WHITE);

        // 버튼 3종 ("입실", "퇴실", "뒤로가기")
        JButton btnEnter = createCardButton("<b>입실</b><br><small>좌석을 선택하여 입실합니다</small>");
        JButton btnExit = createCardButton("<b>퇴실</b><br><small>현재 이용 중 좌석에서 퇴실합니다</small>");
        JButton btnBack = createCardButton("<b>뒤로가기</b><br><small>로그인 화면으로 돌아갑니다</small>");

        // 입실
        btnEnter.addActionListener(e -> {
            int memberId = mainFrame.getLoginMember();
            if (memberId == 0) {
                JOptionPane.showMessageDialog(this, "로그인이 필요합니다.");
                mainFrame.showCard("login");
                return;
            }
            entryController.enterEntry(mainFrame.getLoginMember());
        });

        // 퇴실
        btnExit.addActionListener(e -> {
            int memberId = mainFrame.getLoginMember();
            if (memberId == 0) {
                JOptionPane.showMessageDialog(this, "로그인이 필요합니다.");
                mainFrame.showCard("login");
                return;
            }
            entryController.exitEntry(memberId);
        });

        // 뒤로가기
        btnBack.addActionListener(e -> mainFrame.showCard("login"));

        buttonPanel.add(btnEnter);
        buttonPanel.add(btnExit);
        buttonPanel.add(btnBack);
        add(new JScrollPane(buttonPanel), BorderLayout.CENTER);
    }

    private JButton createCardButton(String html) {
        JButton btn = new JButton("<html><div style='text-align:center;'>" + html + "</div></html>");
        btn.setPreferredSize(new Dimension(220, 120));
        btn.setBackground(new Color(250, 250, 245));
        btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(230, 240, 255));
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(250, 250, 245));
            }
        });
        return btn;
    }
}
