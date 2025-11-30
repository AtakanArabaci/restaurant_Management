package view;

import javax.swing.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Giriş Ekranı");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblUser = new JLabel("Kullanıcı Adı:");
        lblUser.setBounds(40, 30, 100, 20);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(150, 30, 150, 25);
        add(txtUser);

        JLabel lblPass = new JLabel("Şifre:");
        lblPass.setBounds(40, 70, 100, 20);
        add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(150, 70, 150, 25);
        add(txtPass);

        JButton btnLogin = new JButton("Giriş");
        btnLogin.setBounds(150, 110, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(e -> new GarsonPanel());

        setVisible(true);
    }
}
