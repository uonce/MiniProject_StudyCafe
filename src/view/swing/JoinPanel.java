package view.swing;

import controller.MemberController;
import javax.swing.*;
import java.awt.*;

public class JoinPanel extends JPanel {
    private MainFrame mainFrame;
    private MemberController controller;

    public JoinPanel(MainFrame mainFrame, MemberController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("회원가입", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        add(form, BorderLayout.CENTER);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(12, 12, 12, 12);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel phoneLabel = new JLabel("전화번호");
        JTextField phoneField = new JTextField();

        JLabel pwLabel = new JLabel("비밀번호 (4자리)");
        JPasswordField pwField = new JPasswordField();

        JLabel nameLabel = new JLabel("이름");
        JTextField nameField = new JTextField();

        // 폼 배치
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        form.add(phoneLabel, gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1;
        form.add(phoneField, gc);

        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0;
        form.add(pwLabel, gc);
        gc.gridx = 1; gc.gridy = 1; gc.weightx = 1;
        form.add(pwField, gc);

        gc.gridx = 0; gc.gridy = 2; gc.weightx = 0;
        form.add(nameLabel, gc);
        gc.gridx = 1; gc.gridy = 2; gc.weightx = 1;
        form.add(nameField, gc);

        // 하단 버튼
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton joinBtn = new JButton("가입 완료");
        JButton backBtn = new JButton("돌아가기");
        bottom.add(joinBtn);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        joinBtn.addActionListener(e -> {
            boolean ok = controller.signUp(
                    phoneField.getText(),
                    new String(pwField.getPassword()),
                    nameField.getText()
            );
            if (ok) mainFrame.showCard("login");
            phoneField.setText("");
            pwField.setText("");
            nameField.setText("");
        });

        backBtn.addActionListener(e -> mainFrame.showCard("login"));
    }
}
