package view.swing;

import controller.*;
import dto.MemberDTO;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout layout;
    private JPanel parent;

    // 각 화면
    private LoginPanel loginPanel;
    private JoinPanel joinPanel;
    private PassSelectPanel passSelectPanel;
    private PlanSelectPanel planSelectPanel;
    private SeatSelectPanel seatSelectPanel;
    private EntryPanel entryPanel;

    // 컨트롤러
    private MemberController memberController;
    private MemberPassController memberPassController;
    private SeatController seatController;
    private EntryController entryController;

    // 현재 사용자 저장
    private int memberId;

    public MainFrame() {
        setTitle("스터디카페 관리 시스템");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layout = new CardLayout();
        parent = new JPanel(layout);
        add(parent);

        // 컨트롤러 초기화 (View ↔ Controller 연결)
        memberController = new MemberController(this);
        memberPassController = new MemberPassController(this);
        seatController = new SeatController(this);
        entryController = new EntryController(this);

        // View 초기화
        loginPanel = new LoginPanel(this, memberController);
        joinPanel = new JoinPanel(this, memberController);
        passSelectPanel = new PassSelectPanel(this, seatController, memberPassController);
        planSelectPanel = new PlanSelectPanel(this, memberPassController);
        seatSelectPanel = new SeatSelectPanel(this, seatController);
        entryPanel = new EntryPanel(this, entryController);

        // 화면 등록
        parent.add(loginPanel, "login");
        parent.add(joinPanel, "join");
        parent.add(passSelectPanel, "passSelect");
        parent.add(planSelectPanel, "planSelect");
        parent.add(seatSelectPanel, "seatSelect");
        parent.add(entryPanel, "entry");

        setVisible(true);
    }

    public void showCard(String name) {
        layout.show(parent, name);
    }

    // 현재 로그인한 사용자 id 설정
    public void setLoginMember(int memberId) {
        this.memberId = memberId;
    }
    // 현재 로그인한 사용자 id 조회
    public int getLoginMember() {
        return memberId;
    }

    // 다른 패널 접근용 getter
    public PassSelectPanel getPassSelectPanel() { return passSelectPanel; }
    public PlanSelectPanel getPlanSelectPanel() { return planSelectPanel; }
    public SeatSelectPanel getSeatSelectPanel() { return seatSelectPanel; }

    public static void main(String[] args) {
        try {
            // LookAndFeel을 OS 스타일로 통일 (선택사항)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

