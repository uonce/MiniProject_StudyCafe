package view.swing;

import controller.MemberController;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private MemberController controller;

    public LoginPanel(MainFrame mainFrame, MemberController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("로그인", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        add(title, BorderLayout.NORTH);

        JPanel formWrap = new JPanel(new GridBagLayout());
        formWrap.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(formWrap, BorderLayout.CENTER);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel phoneLabel = new JLabel("전화번호");
        JTextField phoneField = new JTextField();

        JLabel pwLabel = new JLabel("비밀번호 (4자리)");
        JPasswordField pwField = new JPasswordField();

        // 폼
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        formWrap.add(phoneLabel, gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1;
        formWrap.add(phoneField, gc);

        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0;
        formWrap.add(pwLabel, gc);
        gc.gridx = 1; gc.gridy = 1; gc.weightx = 1;
        formWrap.add(pwField, gc);

        // 우측 버튼 묶음
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        JButton loginBtn = new JButton("로그인");
        JButton joinBtn  = new JButton("회원가입");
        JButton entryBtn = new JButton("입/퇴실");

        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        joinBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        entryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons.add(loginBtn);
        buttons.add(Box.createVerticalStrut(8));
        buttons.add(joinBtn);
        buttons.add(Box.createVerticalStrut(8));
        buttons.add(entryBtn);

        gc.gridx = 2; gc.gridy = 0; gc.gridheight = 2; gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        formWrap.add(buttons, gc);

        // 이벤트(기존 로직 유지)
        loginBtn.addActionListener(e -> {
            controller.login(phoneField.getText(), new String(pwField.getPassword()));
            phoneField.setText("");
            pwField.setText("");
        });

        joinBtn.addActionListener(e -> mainFrame.showCard("join"));

        entryBtn.addActionListener(e -> {
            controller.entry(phoneField.getText(), new String(pwField.getPassword()));
            phoneField.setText("");
            pwField.setText("");
        });
    }
}
