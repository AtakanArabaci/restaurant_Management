package view;

import view_components.BackgroundPanel;

import view_components.UIUtilsGeri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminKategoriSecimFrame extends JFrame {

    private final JFrame parent;

    public AdminKategoriSecimFrame(JFrame parent) {
        this.parent = parent;

        setTitle("Menü Yönetimi");
        setSize(950, 650);
        setResizable(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        BackgroundPanel bg = new BackgroundPanel("icons/front.png");
        bg.setLayout(new BorderLayout());
        add(bg, BorderLayout.CENTER);

        // ===================== ÜST BAR =====================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(900, 90));
        topBar.setBackground(new Color(0, 0, 0, 140));
        topBar.setOpaque(true);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lbl = new JLabel("Menü Yönetimi", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);

        // GERİ BUTONU (ORTAK UTIL)  ✅
        JButton btnGeri = UIUtilsGeri.createGeriButonu(this, parent);

        topBar.add(lbl, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);
        bg.add(topBar, BorderLayout.NORTH);

        // ===================== ORTA ALAN =====================
        JPanel grid = new JPanel(new GridLayout(1, 3, 30, 30));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        grid.add(createCard("Yemek"));
        grid.add(createCard("İçecek"));
        grid.add(createCard("Tatlı"));

        bg.add(grid, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createCard(String kategori) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(220, 140, 80, 220));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };

        card.setOpaque(false);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setLayout(new GridBagLayout());

        JLabel lbl = new JLabel(kategori);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);
        card.add(lbl);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String kat = kategori.toLowerCase()
                        .replace("ı", "i")
                        .replace("ç", "c")
                        .replace("ş", "s")
                        .replace("ğ", "g")
                        .replace("ü", "u")
                        .replace("ö", "o");

                new AdminUrunListeFrame(
                        AdminKategoriSecimFrame.this,
                        kat
                );
                setVisible(false);


            }
        });

        return card;
    }
}