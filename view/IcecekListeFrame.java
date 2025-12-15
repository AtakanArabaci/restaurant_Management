package view;

import model.Masa;
import model.Urun;
import servis.UrunServisi;
import view_components.BackgroundPanel;
import view_components.ProductCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class IcecekListeFrame extends JFrame {

    public IcecekListeFrame(Masa masa) {

        setTitle("İçecek Listesi");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("icons/garson_bg.jpg");
        bg.setLayout(new BorderLayout());
        add(bg);

        JPanel ust = new JPanel(new BorderLayout());
        ust.setOpaque(false);
        ust.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblBaslik = new JLabel("İçecek Çeşitlerimiz - Masa: " + masa.getMasaNo() + "");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);

        // --- GERİ BUTONU ---
        ImageIcon geriIcon = new ImageIcon("icons/ger.png");
        Image geriImg = geriIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        geriIcon = new ImageIcon(geriImg);

        JButton btnGeri = new JButton("   Geri", geriIcon);
        btnGeri.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGeri.setBackground(new Color(255, 107, 107));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFocusPainted(false);
        btnGeri.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGeri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGeri.setIconTextGap(10);

        // hover efekti
        btnGeri.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnGeri.setBackground(new Color(255, 76, 76));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnGeri.setBackground(new Color(255, 107, 107));
            }
        });

        btnGeri.addActionListener(e -> dispose());

        ust.add(lblBaslik, BorderLayout.WEST);
        ust.add(btnGeri, BorderLayout.EAST);
        bg.add(ust, BorderLayout.NORTH);


        UrunServisi servis = UrunServisi.getInstance();

        List<Urun> icecekler = servis.getUrunlerByKategori("icecek");

        JPanel grid = new JPanel(new GridLayout(2, 3, 40, 40));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        for (Urun u : icecekler) {
            grid.add(new ProductCard(u, masa));
        }

        bg.add(grid, BorderLayout.CENTER);

        setVisible(true);
    }
}
