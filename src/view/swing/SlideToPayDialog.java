package view.swing;

import javax.swing.*;
import java.awt.*;

public class SlideToPayDialog extends JDialog {

    private boolean confirmed = false;

    public SlideToPayDialog(JFrame owner, int amount) {
        super(owner, "추가 결제", true); // modal
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(520, 240);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // 상단 타이틀
        JLabel title = new JLabel("밀어서 결제하기", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // 중앙: 금액 + 슬라이드
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));

        JLabel amountLabel = new JLabel(String.format("결제 금액: %,d원", amount), SwingConstants.CENTER);
        amountLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        amountLabel.setForeground(new Color(200, 60, 60));
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hint = new JLabel("▶ 오른쪽 끝까지 밀어주세요", SwingConstants.CENTER);
        hint.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        hint.setForeground(new Color(110,110,110));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(amountLabel);
        center.add(Box.createVerticalStrut(10));

        // 커스텀 슬라이드 버튼
        SlideConfirmView slider = new SlideConfirmView("밀어서 결제하기");
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.setPreferredSize(new Dimension(420, 64));

        center.add(slider);
        center.add(Box.createVerticalStrut(6));
        center.add(hint);

        add(center, BorderLayout.CENTER);

        // 하단: 취소 버튼(원하면 제거 가능)
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 14));
        bottom.setBackground(Color.WHITE);
        JButton cancel = new JButton("취소");
        cancel.addActionListener(e -> {
            confirmed = false;           // 취소
            dispose();
        });
        bottom.add(cancel);
        add(bottom, BorderLayout.SOUTH);

        // 슬라이드 완료 콜백
        slider.setOnConfirmed(() -> {
            confirmed = true;
            dispose();
        });
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
