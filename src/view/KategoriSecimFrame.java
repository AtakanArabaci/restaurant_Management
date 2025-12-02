package view;

import model.Masa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import view.components.BackgroundPanel; // 🔥 EKLENDİ

public class KategoriSecimFrame extends JFrame {

    private Masa masa;

    public KategoriSecimFrame(Masa masa) {
        this.masa = masa;

        setTitle("Kategori Seçimi");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ============================================
        // 🔥 ANA PANEL → ARKA PLAN FOTOĞRAFLI
        // ============================================
        BackgroundPanel arkaPlan = new BackgroundPanel("icons/garson_bg.jpg");
        arkaPlan.setLayout(new BorderLayout());
        add(arkaPlan, BorderLayout.CENTER);

        // ============================================
        // ÜST BAR
        // ============================================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);  // 🔥 ŞEFFAF
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblMasa = new JLabel("Masa ID: " + masa.getMasaNo());
        lblMasa.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMasa.setForeground(Color.WHITE); // 🔥 Arka planda görünmesi için

        // ---------- GERİ BUTONU (SOFT KIRMIZI) ----------
        ImageIcon geriIcon = new ImageIcon("icons/ger.png");
        Image geriImg = geriIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        geriIcon = new ImageIcon(geriImg);

        JButton btnGeri = new JButton("   Geri", geriIcon);
        btnGeri.setFocusPainted(false);
        btnGeri.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setBackground(new Color(255, 107, 107));
        btnGeri.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGeri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGeri.setIconTextGap(10);

        btnGeri.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGeri.setBackground(new Color(255, 76, 76));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnGeri.setBackground(new Color(255, 107, 107));
            }
        });

        btnGeri.addActionListener(e -> dispose());

        topBar.add(lblMasa, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);

        arkaPlan.add(topBar, BorderLayout.NORTH);

        // ============================================
        // KATEGORİ GRID PANELİ
        // ============================================
        JPanel grid = new JPanel(new GridLayout(1, 3, 30, 30));
        grid.setOpaque(false);  // 🔥 Arka plan görünsün
        grid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        grid.add(createKategoriCard("Yemek", "icons/yemek.jpg"));
        grid.add(createKategoriCard("İçecek", "icons/icecek.jpg"));
        grid.add(createKategoriCard("Tatlı", "icons/tatli.jpg"));

        arkaPlan.add(grid, BorderLayout.CENTER);

        // ============================================
        // ALT PANEL - SİPARİŞ DETAYLARI
        // ============================================
        JButton btnSiparisDetay = new JButton("🧾 Sipariş Detayları");
        btnSiparisDetay.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSiparisDetay.setFocusPainted(false);
        btnSiparisDetay.setPreferredSize(new Dimension(200, 50));
        btnSiparisDetay.setBackground(new Color(225, 225, 225));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false); // 🔥 Arka plan görünsün
        bottomPanel.add(btnSiparisDetay);

        arkaPlan.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // =====================================================================
    // KATEGORİ KARTI – FOTOĞRAFLI & HOVER ANİMASYONLU (Aynı bende)
    // =====================================================================
    private JPanel createKategoriCard(String kategoriAdi, String imgPath) {

        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage();

        JPanel card = new JPanel() {
            boolean hover = false;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // ARKA PLAN FOTO
                g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);

                // KAPATMA (darken)
                g2.setColor(new Color(0, 0, 0, hover ? 40 : 70));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // KATEGORİ ADI
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(kategoriAdi)) / 2;
                int textY = (getHeight() + fm.getAscent()) / 2 - 10;
                g2.drawString(kategoriAdi, textX, textY);
            }
        };

        card.setOpaque(false);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (kategoriAdi) {
                    case "Yemek" -> new YemekListeFrame(masa);
                    case "İçecek" -> new IcecekListeFrame(masa);
                    case "Tatlı" -> new TatliListeFrame(masa);
                }
            }
        });

        return card;
    }
}
