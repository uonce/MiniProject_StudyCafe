package view.swing;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExtraFeeDialog extends JDialog {

    private final Object extraFeeDTO;
    //추가결제
    public ExtraFeeDialog(JFrame owner, Object extraFeeDTO) {
        super(owner, "정산 상세 / 추가 결제", true); // modal
        this.extraFeeDTO = extraFeeDTO;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setSize(520, 600);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("정산 상세", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(250, 250, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,180,180), 1, true),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(scroll, BorderLayout.CENTER);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0;
        gc.insets = new Insets(6, 6, 6, 6);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        Field[] fields = extraFeeDTO.getClass().getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));

        String highlightKey = findHighlightKey(fields);

        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) continue;
            String label = prettifyName(f.getName());
            Object value = readValue(extraFeeDTO, f);
            JPanel row = buildRow(label, formatValue(value), f.getName().equalsIgnoreCase(highlightKey));
            card.add(row, gc);
            gc.gridy++;
        }

        if (highlightKey != null) {
            Object v = readByName(extraFeeDTO, highlightKey);
            JLabel strong = new JLabel("추가 결제 금액: " + formatValue(v), SwingConstants.CENTER);
            strong.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            strong.setForeground(new Color(200, 60, 60));
            gc.insets = new Insets(16, 6, 6, 6);
            card.add(strong, gc);
            gc.gridy++;
        }

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 14));
        bottom.setBackground(Color.WHITE);

        JButton payBtn = createMainButton("결제하기");
        payBtn.setEnabled(true);

        payBtn.addActionListener(e -> {
            int amount = 0;
            try {
                Object v = readByName(extraFeeDTO, highlightKey);
                amount = Integer.parseInt(String.valueOf(v));
            } catch (Exception ignored) {}

            // 슬라이드 결제 모달: 성공해야만 닫힘
            SlideToPayDialog dialog = new SlideToPayDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    amount
            );
            dialog.setVisible(true); // 여기서 블록 → 결제 성공 시 자동 닫힘
            if (dialog.isConfirmed()) {
                // 결제 성공: 정산 팝업 닫기
                dispose();
            } else {} // 결제 실패
        });

        bottom.add(payBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel buildRow(String labelText, String valueText, boolean highlight) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("맑은 고딕", Font.BOLD, highlight ? 15 : 14));
        JLabel v = new JLabel(valueText);
        v.setFont(new Font("맑은 고딕", Font.PLAIN, highlight ? 15 : 14));
        if (highlight) v.setForeground(new Color(200, 60, 60));
        row.add(l, BorderLayout.WEST);
        row.add(v, BorderLayout.CENTER);
        return row;
    }

    private JButton createMainButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(140, 44));
        btn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btn.setBackground(new Color(68, 114, 196));
//        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(new Color(54, 98, 174)); }
            public void mouseExited (java.awt.event.MouseEvent e) { btn.setBackground(new Color(68, 114, 196)); }
        });
        return btn;
    }

    private String findHighlightKey(Field[] fields) {
        for (String key : new String[]{"extraFee", "extra_fee", "fee", "overFee"}) {
            for (Field f : fields) if (f.getName().equalsIgnoreCase(key)) return f.getName();
        }
        return null;
    }

    private Object readByName(Object dto, String name) {
        try {
            Method m = findGetter(dto.getClass(), name);
            if (m != null) return m.invoke(dto);
            Field f = dto.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(dto);
        } catch (Exception ignored) { return null; }
    }

    private Object readValue(Object dto, Field f) {
        try {
            Method m = findGetter(dto.getClass(), f.getName());
            if (m != null) return m.invoke(dto);
            f.setAccessible(true);
            return f.get(dto);
        } catch (Exception e) { return null; }
    }

    private Method findGetter(Class<?> cls, String fieldName) {
        String base = fieldName.substring(0,1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
        for (String p : new String[]{"get"+base, "is"+base}) {
            try {
                Method m = cls.getMethod(p);
                if (m.getParameterCount() == 0) return m;
            } catch (NoSuchMethodException ignored) {}
        }
        return null;
    }

    private String formatValue(Object v) {
        if (v == null) return "-";
        if (v instanceof LocalDateTime ldt) return ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (v instanceof LocalDate ld)   return ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return String.valueOf(v);
    }

    private String prettifyName(String name) {
        return name.replaceAll("([a-z])([A-Z])", "$1 $2").replace('_', ' ');
    }
}
