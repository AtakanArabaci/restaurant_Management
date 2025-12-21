package view;

import model.Masa;
import view_components.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KategoriSecimFrame extends JFrame {

    private final Masa masa;

    public KategoriSecimFrame(Masa masa) {
        this.masa = masa;

        setTitle("Kategori Seçimi");
        setSize(950, 650);
        setResizable(false);

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

        JLabel lblMasa = new JLabel("Masa: " + masa.getMasaNo());
        lblMasa.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMasa.setForeground(Color.WHITE);

        JButton btnGeri = createGeriButon();
        btnGeri.addActionListener(e -> dispose());

        topBar.add(lblMasa, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);
        arkaPlan.add(topBar, BorderLayout.NORTH);

        // ================== KATEGORİLER ==================
        JPanel grid = new JPanel(new GridLayout(1, 3, 30, 30));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        grid.add(createKategoriCard("Yemek", "icons/yemek.jpg"));
        grid.add(createKategoriCard("İçecek", "icons/icecek.jpg"));
        grid.add(createKategoriCard("Tatlı", "icons/tatli.jpg"));

        arkaPlan.add(grid, BorderLayout.CENTER);

        // ================== ALT BAR ==================
        JButton btnSiparisDetay = createSiparisDetayButon();
        btnSiparisDetay.addActionListener(e -> new SiparisDetayFrame(masa));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnSiparisDetay);

        arkaPlan.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ================== GERİ BUTONU ==================
    private JButton createGeriButon() {

        ImageIcon icon = new ImageIcon("icons/ger.png");
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        JButton btn = new JButton(" Geri", new ImageIcon(img));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(255, 107, 107));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(255, 76, 76));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(255, 107, 107));
            }
        });

        return btn;
    }

    // ================== SİPARİŞ DETAY BUTONU ==================
    private JButton createSiparisDetayButon() {

        JButton btn = new JButton("Sipariş Detayları") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(211, 138, 75),
                        getWidth(), 0, new Color(196, 115, 46)
                );

                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(260, 55));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ================== KATEGORİ KARTI ==================
    private JPanel createKategoriCard(String kategoriAdi, String imgPath) {

        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage();
        final boolean[] hover = {false};

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);

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
