package view;

import model.Masa;
import model.Urun;
import servis.UrunServisi;
import view_components.BackgroundPanel;
import view_components.ProductCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class YemekListeFrame extends JFrame {

    public YemekListeFrame(Masa masa) {

        setTitle("Yemek Listesi");
        setSize(1100, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("icons/garson_bg.jpg");
        bg.setLayout(new BorderLayout());
        add(bg);

        // ================= ÜST BAR =================
        JPanel ust = new JPanel(new BorderLayout());
        ust.setOpaque(false);
        ust.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblBaslik = new JLabel("Yemek Çeşitlerimiz - Masa: " + masa.getMasaNo());
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);

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
        btnGeri.addActionListener(e -> dispose());

        Color normalColor = new Color(255, 107, 107);
        Color hoverColor  = new Color(255, 76, 76);

        btnGeri.setBackground(normalColor);

        btnGeri.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnGeri.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnGeri.setBackground(normalColor);
            }
        });

        ust.add(lblBaslik, BorderLayout.WEST);
        ust.add(btnGeri, BorderLayout.EAST);
        bg.add(ust, BorderLayout.NORTH);

        // ================= ÜRÜNLER =================
        UrunServisi servis = UrunServisi.getInstance();
        List<Urun> yemekler = servis.getUrunlerByKategori("yemek");

        JPanel grid = new JPanel(new GridLayout(0, 3, 40, 40));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        for (Urun u : yemekler) {
            ProductCard card = new ProductCard(u, masa);

            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            wrapper.setOpaque(false);
            wrapper.add(card);

            grid.add(wrapper);
        }

        JScrollPane scrollPane = new JScrollPane(grid);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // ✅ AKICI SCROLL
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);

        bg.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
