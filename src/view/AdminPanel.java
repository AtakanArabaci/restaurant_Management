package view;

import controller.AdminController;
import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JFrame {

    private final AdminController adminController;

    // 1) PARAMETRESİZ CONSTRUCTOR
    public AdminPanel() {
        this(new AdminController());   // Artık burası HATASIZ
    }

    // 2) PARAMETRELİ CONSTRUCTOR
    public AdminPanel(AdminController adminController) {
        this.adminController = adminController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Admin Paneli");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout(15, 15));

        JLabel lblBaslik = new JLabel("Admin Paneli", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(lblBaslik, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Üst satır: Menü ve Garson kontrolü
        JPanel ustSatir = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton btnMenuKontrol = new JButton("Menü Kontrolü");
        JButton btnGarsonKontrol = new JButton("Garson Kontrolü");
        ustSatir.add(btnMenuKontrol);
        ustSatir.add(btnGarsonKontrol);

        // Alt satır: Müşteri adına göre sorgu & Tarihe göre sorgu
        JPanel altSatir = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel lblMusteriAdi = new JLabel("Müşteri Adına Göre Sorgu:");
        JTextField txtMusteriAdi = new JTextField();

        JLabel lblTarih = new JLabel("Tarihe Göre Sorgu (gg.aa.yyyy):");
        JTextField txtTarih = new JTextField();

        JButton btnMusteriSorgu = new JButton("Müşteriyi Sorgula");
        JButton btnTarihSorgu = new JButton("Tarihi Sorgula");

        altSatir.add(lblMusteriAdi);
        altSatir.add(txtMusteriAdi);
        altSatir.add(btnMusteriSorgu);
        altSatir.add(lblTarih);
        altSatir.add(txtTarih);
        altSatir.add(btnTarihSorgu);

        centerPanel.add(ustSatir);
        centerPanel.add(altSatir);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGunSonu = new JButton("Gün Sonu Raporu");
        bottomPanel.add(btnGunSonu);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Listener'lar
        btnMenuKontrol.addActionListener(e -> adminController.menuyuKontrolEt());
        btnGarsonKontrol.addActionListener(e -> adminController.garsonlariKontrolEt());
        btnMusteriSorgu.addActionListener(e ->
                adminController.musteriyeGoreSorgula(txtMusteriAdi.getText().trim()));
        btnTarihSorgu.addActionListener(e ->
                adminController.tariheGoreSorgula(txtTarih.getText().trim()));
        btnGunSonu.addActionListener(e -> adminController.gunSonuAl());

        setContentPane(mainPanel);
    }
}
