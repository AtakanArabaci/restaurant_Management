package view;

import controller.LoginController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Giriş ekranı
 */
public class LoginFrame extends JFrame {

    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris;
    private JPanel cardPanel;

    private final LoginController loginController;

    public LoginFrame() {
        this.loginController = new LoginController();

        setTitle("Giriş Yap");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ARKA PLAN
        ImageIcon bgIcon = new ImageIcon("icons/login_bg.png");
        Image bgImage = bgIcon.getImage();

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null);
        setContentPane(bgPanel);

        // CAM PANEL
        cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(100, 100, 100, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setLayout(null);
        cardPanel.setBounds(250, 170, 400, 360);
        cardPanel.setBackground(new Color(255, 255, 255, 60));
        Border cardBorder = BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2);
        cardPanel.setBorder(cardBorder);
        bgPanel.add(cardPanel);

        // BAŞLIK
        JLabel title = new JLabel("Giriş Yap", SwingConstants.CENTER);
        title.setBounds(0, 20, 400, 40);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        cardPanel.add(title);

        // ==== Kullanıcı Adı ====
        JLabel lblUser = new JLabel("Kullanıcı Adı");
        lblUser.setBounds(50, 80, 300, 20);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 18));
        lblUser.setForeground(Color.WHITE);
        cardPanel.add(lblUser);

        txtKullaniciAdi = new JTextField();
        txtKullaniciAdi.setBounds(50, 105, 300, 40);
        txtKullaniciAdi.setFont(new Font("Arial", Font.PLAIN, 16));
        txtKullaniciAdi.setOpaque(false);
        txtKullaniciAdi.setForeground(Color.WHITE);
        txtKullaniciAdi.setCaretColor(Color.WHITE);
        txtKullaniciAdi.setBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2)
        );
        cardPanel.add(txtKullaniciAdi);

        // ==== Şifre ====
        JLabel lblPass = new JLabel("Şifre");
        lblPass.setBounds(50, 165, 300, 20);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPass.setForeground(Color.WHITE);
        cardPanel.add(lblPass);

        txtSifre = new JPasswordField();
        txtSifre.setBounds(50, 190, 300, 40);
        txtSifre.setFont(new Font("Arial", Font.PLAIN, 16));
        txtSifre.setOpaque(false);
        txtSifre.setForeground(Color.WHITE);
        txtSifre.setCaretColor(Color.WHITE);
        txtSifre.setBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2)
        );
        cardPanel.add(txtSifre);

        // ==== GİRİŞ BUTONU ====
        btnGiris = new JButton("Giriş") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(230, 150, 90),
                        0, getHeight(), new Color(200, 120, 60)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(new Color(255, 255, 255, 80));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                super.paintComponent(g);
            }
        };
        btnGiris.setBounds(120, 260, 160, 45);
        btnGiris.setFont(new Font("Arial", Font.BOLD, 18));
        btnGiris.setForeground(Color.WHITE);
        btnGiris.setContentAreaFilled(false);
        btnGiris.setFocusPainted(false);
        btnGiris.setBorderPainted(false);
        cardPanel.add(btnGiris);

        // LOGIN OLAYI
        btnGiris.addActionListener(e -> {
            String user = txtKullaniciAdi.getText().trim();
            String pass = new String(txtSifre.getPassword());

            String sonuc = loginController.girisYap(user, pass); // "admin", "garson" veya null

            if ("admin".equals(sonuc)) {
                // Admin Paneli aç
                new AdminPanel().setVisible(true);
                dispose();
            } else if ("garson".equals(sonuc)) {
                // Garson Paneli aç
                new GarsonPanel().setVisible(true);
                dispose();
            } else {
                // Hatalı giriş
                shake(cardPanel);
                JOptionPane.showMessageDialog(
                        this,
                        "Hatalı kullanıcı adı veya şifre!",
                        "Giriş Başarısız",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }

    /**
     * Panel sallama efekti (hatalı girişte kullanılıyor)
     */
    private void shake(JPanel panel) {
        Thread t = new Thread(() -> {
            int originalX = panel.getX();
            int originalY = panel.getY();

            try {
                for (int i = 0; i < 8; i++) {
                    panel.setLocation(originalX - 10, originalY);
                    Thread.sleep(40);
                    panel.setLocation(originalX + 10, originalY);
                    Thread.sleep(40);
                }
            } catch (Exception ignored) {
            } finally {
                panel.setLocation(originalX, originalY);
            }
        });
        t.start();
    }
}
