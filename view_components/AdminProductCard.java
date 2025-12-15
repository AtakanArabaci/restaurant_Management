package view_components;

import model.Urun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminProductCard extends JPanel {

    private static AdminProductCard selected;
    private Urun urun;

    public AdminProductCard(Urun urun) {
        this.urun = urun;

        setPreferredSize(new Dimension(260,200));
        setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        setLayout(new BorderLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(Color.WHITE);
        setOpaque(true);

        JLabel lbl = new JLabel(urun.getAd(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lbl, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (selected != null)
                    selected.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));

                selected = AdminProductCard.this;
                setBorder(BorderFactory.createLineBorder(Color.RED,3));
            }
        });
    }

    public Urun getUrun() {
        return urun;
    }

    public static AdminProductCard getSelected() {
        return selected;
    }

    public static void clearSelection() {
        selected = null;
    }
}
