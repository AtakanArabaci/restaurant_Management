package view;

import model.Masa;
import servis.MasaServisi;
import view_components.BackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class MasaListesiFrame extends JFrame {

    private JFrame parent;

    public MasaListesiFrame(JFrame parent) {
        this.parent = parent;

        setTitle("Masalara Göre Siparişler");
        setSize(900, 600);
        setResizable(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===================== ARKA PLAN =====================
        BackgroundPanel bg = new BackgroundPanel("icons/front.png");
        bg.setLayout(new BorderLayout());
        add(bg, BorderLayout.CENTER);

        // ===================== ÜST BAR =====================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(900, 90));
        topBar.setBackground(new Color(0, 0, 0, 140));
        topBar.setOpaque(true);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblBaslik = new JLabel("Masalara Göre Siparişler");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblBaslik.setForeground(Color.WHITE);

        JButton btnGeri = createGeriButonu();

        topBar.add(lblBaslik, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);

        bg.add(topBar, BorderLayout.NORTH);

        // ===================== GRID =====================
        JPanel grid = new JPanel(new GridLayout(3, 3, 20, 20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        MasaServisi servis = MasaServisi.getInstance();

        for (Masa masa : servis.getMasalar().values()) {
            JButton btn = new JButton("Masa " + masa.getMasaNo());
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e ->
                new MasaDetayFrame(masa, MasaDetayModu.SORGU)
            );

            grid.add(btn);
        }

        bg.add(grid, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===================== GERİ BUTONU =====================
    private JButton createGeriButonu() {

        ImageIcon icon = new ImageIcon("icons/ger.png");
        Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton btn = new JButton("Geri", icon);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);

        Color normal = new Color(255, 107, 107);
        Color hover  = new Color(255, 76, 76);

        btn.setBackground(normal);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(10);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hover);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(normal);
            }
        });

        // 🔥 ASIL ÖNEMLİ KISIM
        btn.addActionListener(e -> {
            dispose();              // bu pencere kapansın
            parent.setVisible(true); // admin panel geri gelsin
        });

        return btn;
    }
}