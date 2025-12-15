package view;

import javax.swing.*;

import view_components.BackgroundPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminKategoriSecimFrame extends JFrame {

    public AdminKategoriSecimFrame() {

        setTitle("Menü Yönetimi");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        BackgroundPanel bg = new BackgroundPanel("icons/garson_bg.jpg");
        bg.setLayout(new BorderLayout());
        add(bg);

        JLabel lbl = new JLabel("Menü Yönetimi", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);
        lbl.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        bg.add(lbl, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(1,3,30,30));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        grid.add(createCard("Yemek"));
        grid.add(createCard("İçecek"));
        grid.add(createCard("Tatlı"));

        bg.add(grid, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createCard(String kategori) {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(220,140,80,220));
                g.fillRoundRect(0,0,getWidth(),getHeight(),30,30);
            }
        };

        card.setOpaque(false);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setLayout(new GridBagLayout());

        JLabel lbl = new JLabel(kategori);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        card.add(lbl);

        // 🔥 SADECE BU KISIM DÜZELTİLDİ
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                String kat = kategori
                        .toLowerCase()
                        .replace("ı", "i")
                        .replace("ç", "c")
                        .replace("ş", "s")
                        .replace("ğ", "g")
                        .replace("ü", "u")
                        .replace("ö", "o");

                new AdminUrunListeFrame(kat);
            }
        });

        return card;
    }
}
