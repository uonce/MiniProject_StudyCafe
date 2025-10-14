package view.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 커스텀 "Slide to confirm" 컴포넌트
 * - 손잡이를 오른쪽 끝까지 드래그하면 onConfirmed 실행
 */
public class SlideConfirmView extends JComponent {
    private int knobX = 0;                 // 손잡이의 현재 X
    private boolean dragging = false;
    private int pressOffsetX = 0;
    private Runnable onConfirmed;

    private final int trackRadius = 28;
    private final int knobDiameter = 52;
    private final String text;

    public SlideConfirmView(String text) {
        this.text = text;
        setOpaque(false);
        setPreferredSize(new Dimension(400, 64));

        MouseAdapter ma = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                Rectangle knob = knobBounds();
                if (knob.contains(e.getPoint())) {
                    dragging = true;
                    pressOffsetX = e.getX() - knob.x;
                }
            }
            @Override public void mouseDragged(MouseEvent e) {
                if (!dragging) return;
                int maxX = getWidth() - knobDiameter - 6;
                knobX = Math.max(6, Math.min(maxX, e.getX() - pressOffsetX));
                repaint();
            }
            @Override public void mouseReleased(MouseEvent e) {
                if (!dragging) return;
                dragging = false;
                int maxX = getWidth() - knobDiameter - 6;
                if (knobX >= maxX - 4) {
                    // 성공
                    knobX = maxX;
                    repaint();
                    if (onConfirmed != null) onConfirmed.run();
                } else {
                    // 되돌아감(간단히 즉시 복귀; 원하면 타이머로 부드럽게 애니메이션 가능)
                    knobX = 6;
                    repaint();
                }
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void setOnConfirmed(Runnable r) {
        this.onConfirmed = r;
    }

    private Rectangle knobBounds() {
        int y = (getHeight() - knobDiameter) / 2;
        return new Rectangle(knobX, y, knobDiameter, knobDiameter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 트랙(바탕)
        int trackX = 0;
        int trackY = (h - trackRadius * 2) / 2;
        int trackW = w;
        int trackH = trackRadius * 2;

        // 배경 트랙
        g2.setColor(new Color(235, 238, 245));
        g2.fillRoundRect(trackX, trackY, trackW, trackH, trackH, trackH);

        // 진행 채움
        int fillW = Math.min(trackW, knobX + knobDiameter / 2);
        g2.setColor(new Color(68, 114, 196));
        g2.fillRoundRect(trackX, trackY, fillW, trackH, trackH, trackH);

        // 안내 텍스트
        String guide = text != null ? text : "Slide to confirm";
        g2.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (w - fm.stringWidth(guide)) / 2;
        int ty = trackY + (trackH + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(new Color(255, 255, 255, 220));
        g2.drawString(guide, tx, ty);

        // 손잡이(노브)
        Rectangle knob = knobBounds();
        g2.setColor(Color.WHITE);
        g2.fillOval(knob.x, knob.y, knob.width, knob.height);
        g2.setColor(new Color(180,180,180));
        g2.drawOval(knob.x, knob.y, knob.width, knob.height);

        // 손잡이 아이콘(▶▶)
        g2.setColor(new Color(120, 120, 120));
        int cx = knob.x + knob.width / 2;
        int cy = knob.y + knob.height / 2;
        int triW = 8, triH = 10, gap = 4;
        Polygon p1 = new Polygon(
                new int[]{cx - triW - gap, cx - gap, cx - triW - gap},
                new int[]{cy - triH/2, cy, cy + triH/2}, 3);
        Polygon p2 = new Polygon(
                new int[]{cx + gap, cx + triW + gap, cx + gap},
                new int[]{cy - triH/2, cy, cy + triH/2}, 3);
        g2.fillPolygon(p1);
        g2.fillPolygon(p2);

        g2.dispose();
    }
}
