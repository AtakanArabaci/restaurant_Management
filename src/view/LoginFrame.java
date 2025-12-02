package view;

import controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris;
    private JPanel cardPanel; // cam panel

    public LoginFrame() {

        setTitle("Giriş Yap");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        LoginController loginController = new LoginController();

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

                // CAM EFEKTİ 
                g2.setColor(new Color(100,100,100,100)); //burada cam efekti var
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };

        cardPanel.setBounds(250, 170, 400, 360);
        cardPanel.setOpaque(false);
        cardPanel.setLayout(null);
        cardPanel.setBackground(new Color(255,255,255,60));
        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,100), 2));
        bgPanel.add(cardPanel);

        // BAŞLIK 
        JLabel title = new JLabel("Giriş Yap", SwingConstants.CENTER);
        title.setBounds(0, 20, 400, 40);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        cardPanel.add(title);

       
        //      Kullanıcı Adı Labeli
      
        JLabel lblUser = new JLabel("Kullanıcı Adı");
        lblUser.setBounds(50, 80, 300, 20);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 18));
        lblUser.setForeground(Color.WHITE);
        cardPanel.add(lblUser);

        // === Kullanıcı Adı Input ===
        txtKullaniciAdi = new JTextField();
        txtKullaniciAdi.setBounds(50, 105, 300, 40);
        txtKullaniciAdi.setFont(new Font("Arial", Font.PLAIN, 16));
        txtKullaniciAdi.setOpaque(false);
        txtKullaniciAdi.setForeground(Color.WHITE);
        txtKullaniciAdi.setCaretColor(Color.WHITE);
        txtKullaniciAdi.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,150), 2));
        cardPanel.add(txtKullaniciAdi);

       
        //              Şifre Labeli
       
        JLabel lblPass = new JLabel("Şifre");
        lblPass.setBounds(50, 155, 300, 20);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 18));
        lblPass.setForeground(Color.WHITE);
        cardPanel.add(lblPass);

        //  Şifre Input 
        txtSifre = new JPasswordField();
        txtSifre.setBounds(50, 180, 300, 40);
        txtSifre.setFont(new Font("Arial", Font.PLAIN, 16));
        txtSifre.setOpaque(false);
        txtSifre.setForeground(Color.WHITE);
        txtSifre.setCaretColor(Color.WHITE);
        txtSifre.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,150), 2));
        cardPanel.add(txtSifre);

        //  GİRİŞ BUTONU 
        btnGiris = new JButton("Giriş") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(230,150,90),
                        getWidth(), getHeight(), new Color(200,120,60)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };

        btnGiris.setBounds(50, 250, 300, 50);
        btnGiris.setFont(new Font("Arial", Font.BOLD, 18));
        btnGiris.setOpaque(false);
        btnGiris.setContentAreaFilled(false);
        btnGiris.setBorderPainted(false);
        btnGiris.setFocusPainted(false);
        btnGiris.setForeground(Color.WHITE);
        cardPanel.add(btnGiris);

        // LOGIN  
        btnGiris.addActionListener(e -> {
            String user = txtKullaniciAdi.getText();
            String pass = new String(txtSifre.getPassword());

            String sonuc = loginController.girisYap(user, pass);

            if ("admin".equals(sonuc)) {
            	new AdminPanel().setVisible(true);
                dispose();
            }
            else if ("garson".equals(sonuc)) {
                new GarsonPanel().setVisible(true);
                dispose();
            }
            else {
                shake(cardPanel);
                JOptionPane.showMessageDialog(this, "Hatalı kullanıcı adı veya şifre!");
            }
        });

        setVisible(true);
    }

    // PANEL TİTREME 
    private void shake(JPanel panel) {
        Thread t = new Thread(() -> {
            int originalX = panel.getX();
            try {
                for (int i = 0; i < 8; i++) {
                    panel.setLocation(originalX - 10, panel.getY());
                    Thread.sleep(40);
                    panel.setLocation(originalX + 10, panel.getY());
                    Thread.sleep(40);
                }
            } catch (Exception ignored) {}
            panel.setLocation(originalX, panel.getY());
        });
        t.start();
    }
}
