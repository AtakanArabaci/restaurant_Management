package view;

import model.Masa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import view.components.BackgroundPanel;

public class KategoriSecimFrame extends JFrame {

    private Masa masa;

    public KategoriSecimFrame(Masa masa) {
        this.masa = masa;

        setTitle("Kategori Seçimi");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        BackgroundPanel arkaPlan = new BackgroundPanel("icons/garson_bg.jpg");
        arkaPlan.setLayout(new BorderLayout());
        add(arkaPlan, BorderLayout.CENTER);

        // ================== ÜST BAR ==================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblMasa = new JLabel("Masa ID: " + masa.getMasaNo());
        lblMasa.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMasa.setForeground(Color.WHITE);

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
            public void mouseEntered(MouseEvent e) { btnGeri.setBackground(new Color(255, 76, 76)); }
            @Override
            public void mouseExited(MouseEvent e) { btnGeri.setBackground(new Color(255, 107, 107)); }
        });

        btnGeri.addActionListener(e -> dispose());

        topBar.add(lblMasa, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);
        arkaPlan.add(topBar, BorderLayout.NORTH);

        // ================== KATEGORİ KARTLARI ==================
        JPanel grid = new JPanel(new GridLayout(1, 3, 30, 30));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        grid.add(createKategoriCard("Yemek", "icons/yemek.jpg"));
        grid.add(createKategoriCard("İçecek", "icons/icecek.jpg"));
        grid.add(createKategoriCard("Tatlı", "icons/tatli.jpg"));

        arkaPlan.add(grid, BorderLayout.CENTER);

        // ================== ALT BAR — SİPARİŞ DETAYLARI ==================
     // ================== ALT BAR — SİPARİŞ DETAYLARI ==================
        JButton btnSiparisDetay = new JButton("Sipariş Detayları") {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 🔥 Gradient turuncu renk
                GradientPaint gp = new GradientPaint(
                        0, 0,
                        new Color(211, 138, 75),   // #d38a4b
                        getWidth(), 0,
                        new Color(196, 115, 46)    // #c4732e
                );

                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                super.paintComponent(g);
            }
        };

        btnSiparisDetay.setOpaque(false);
        btnSiparisDetay.setContentAreaFilled(false);
        btnSiparisDetay.setBorderPainted(false);
        btnSiparisDetay.setFocusPainted(false);

        btnSiparisDetay.setForeground(Color.WHITE);
        btnSiparisDetay.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSiparisDetay.setPreferredSize(new Dimension(250, 55));
        btnSiparisDetay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover efekti
        btnSiparisDetay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSiparisDetay.setForeground(new Color(255, 240, 220)); // Açık beyaz
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSiparisDetay.setForeground(Color.WHITE);
            }
        });


        // Hover efekti
        btnSiparisDetay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSiparisDetay.setBackground(new Color(41, 128, 185));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSiparisDetay.setBackground(new Color(52, 152, 219));
            }
        });

        btnSiparisDetay.addActionListener(e -> new SiparisDetayFrame(masa));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnSiparisDetay);

        arkaPlan.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ================== KATEGORİ KARTI ==================
    private JPanel createKategoriCard(String kategoriAdi, String imgPath) {

        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage();

        // hover bilgisini dışarıdan da değiştirebilmek için dizi kullanıyoruz
        final boolean[] hover = { false };

        JPanel card = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);

                // Hover efekti
                g2.setColor(new Color(0, 0, 0, hover[0] ? 40 : 70));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                FontMetrics fm = g2.getFontMetrics();

                int x = (getWidth() - fm.stringWidth(kategoriAdi)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 10;

                g2.drawString(kategoriAdi, x, y);
            }
        };

        card.setOpaque(false);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover + click
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover[0] = true;
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover[0] = false;
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
