package view_components;

import javax.swing.*;
import java.awt.*;

public class UIUtilsGeri {

    public static JButton createGeriButonu(JFrame current, JFrame target) {

        ImageIcon icon = new ImageIcon("icons/ger.png");
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton btn = new JButton("  Geri", icon);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(255, 107, 107));

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(10);

        // HOVER
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(255, 76, 76));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(255, 107, 107));
            }
        });

        btn.addActionListener(e -> {
            current.dispose();
            target.setVisible(true);
        });

        return btn;
    }
}
