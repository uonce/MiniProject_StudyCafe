package view.swing;

import controller.MemberPassController;
import dto.RatePlanDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlanSelectPanel extends JPanel {
    private MainFrame mainFrame;
    private MemberPassController controller;
    private JPanel gridPanel;

    public PlanSelectPanel(MainFrame mainFrame, MemberPassController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new BorderLayout());

        // 상단: 뒤로가기 + 타이틀
        JPanel top = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("뒤로가기");
        JLabel title = new JLabel("이용권 구매", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        top.add(backBtn, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(top, BorderLayout.NORTH);

        backBtn.addActionListener(e -> mainFrame.showCard("login"));

        // 중앙: 그리드(최대 3열)
        gridPanel = new JPanel();
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(gridPanel, BorderLayout.CENTER);
    }

    public void loadPlans(List<RatePlanDTO> plans) {
        gridPanel.removeAll();

        if (plans == null || plans.isEmpty()) {
            gridPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
            gridPanel.add(new JLabel("현재 구매 가능한 요금제가 없습니다."));
        } else {
            int n = plans.size();
            int cols = Math.min(3, n);
            int rows = (int) Math.ceil(n / (double) cols);
            gridPanel.setLayout(new GridLayout(rows, cols, 16, 16));

            int memberId = mainFrame.getLoginMember();

            for (RatePlanDTO plan : plans) {
                String html = String.format(
                        "<b>%s</b><br>%s<br>가격: %,d원<br>%s",
                        plan.getPlanName(),
                        plan.getPlanType(),
                        plan.getPrice(),
                        (plan.getValidDays() != null
                                ? "유효기간: " + plan.getValidDays() + "일"
                                : "제공시간: " + plan.getProvidedTime() + "분")
                );
                JButton card = new JButton("<html>" + html + "</html>");
                card.setPreferredSize(new Dimension(240, 140));
                card.addActionListener(e -> controller.buyPass(memberId, plan.getPlanId()));
                gridPanel.add(card);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
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
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(255, 245, 230)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(250, 250, 245)); }
        });
        return btn;
    }
}
