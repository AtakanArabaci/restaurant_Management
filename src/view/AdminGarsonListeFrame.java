package view;

import model.Kullanici;
import servis.KullaniciServisi;
import view_components.AdminGarsonCard;
import view_components.BackgroundPanel;
import servis.AdminLogServisi;


import javax.swing.*;
import java.awt.*;

public class AdminGarsonListeFrame extends JFrame {

    private JPanel grid;
    private JFrame parent; // 🔥 GERİ DÖNÜŞ İÇİN

    public AdminGarsonListeFrame(JFrame parent) {
        this.parent = parent;

        setTitle("Garson Kontrolü");
        setSize(900, 600);
        setResizable(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===================== ARKA PLAN =====================
        BackgroundPanel bg = new BackgroundPanel("icons/front.png");
        bg.setLayout(new BorderLayout());
        add(bg, BorderLayout.CENTER);

        // ===================== ÜST BAR =====================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(900, 90));
        topBar.setBackground(new Color(0, 0, 0, 140));
        topBar.setOpaque(true);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblBaslik = new JLabel("Garson Kontrolü");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblBaslik.setForeground(Color.WHITE);

        JButton btnGeri = createGeriButonu();

        topBar.add(lblBaslik, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);

        bg.add(topBar, BorderLayout.NORTH);

        // ===================== ORTA ALAN =====================
        grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        bg.add(scroll, BorderLayout.CENTER);

        // ===================== ALT BAR =====================
        JPanel bottom = new JPanel();
        JButton ekle = new JButton("Garson Ekle");
        JButton sil = new JButton("Garson Sil");
        bottom.add(ekle);
        bottom.add(sil);

        bg.add(bottom, BorderLayout.SOUTH);

        ekle.addActionListener(e -> {
            new AdminGarsonDialog(this, null);
            doldur();
        });

        sil.addActionListener(e -> {
            var secililer = AdminGarsonCard.getSelectedCards();

            if (secililer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson seç!");
                return;
            }

            int r = JOptionPane.showConfirmDialog(
                    this,
                    secililer.size() + " garson silinsin mi?",
                    "Onay",
                    JOptionPane.YES_NO_OPTION
            );

            if (r != JOptionPane.YES_OPTION) return;

            for (AdminGarsonCard c : secililer) {
                String ka = c.getGarson().getKullaniciAdi();
                String ad = c.getGarson().getAdSoyad();

                KullaniciServisi.getInstance().garsonSil(ka);

                AdminLogServisi.log(
                        "GARSON SİL",
                        "Silindi: kullaniciAdi=" + ka + ", adSoyad=" + (ad == null ? "-" : ad)
                );
            }


            AdminGarsonCard.clearSelection();
            doldur();
        });

        doldur();
        setVisible(true);
    }

    // ===================== GERİ BUTONU (ger.png) =====================
    private JButton createGeriButonu() {

        ImageIcon icon = new ImageIcon("icons/ger.png");
        Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton btn = new JButton("Geri", icon);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);

        Color normal = new Color(255, 102, 102);
        Color hover  = new Color(230, 80, 80);

        btn.setBackground(normal);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setIconTextGap(10);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(normal);
            }
        });

        // 🔥 ASIL DÜZELTME BURASI
        btn.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        return btn;
    }

    // ===================== GARSONLARI DOLDUR =====================
    private void doldur() {
        grid.removeAll();

        for (Kullanici k : KullaniciServisi.getInstance()
                .getGarsonlar().values()) {
            grid.add(new AdminGarsonCard(k, this));
        }

        grid.revalidate();
        grid.repaint();
    }
}