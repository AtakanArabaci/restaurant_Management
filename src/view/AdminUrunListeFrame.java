package view;

import java.util.Set;
import servis.StokServisi;

import model.Urun;
import servis.MasaServisi;
import servis.UrunServisi;
import view_components.AdminProductCard;
import view_components.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminUrunListeFrame extends JFrame {

    private String kategori;
    private JPanel grid;
    private JFrame parent;

    public AdminUrunListeFrame(JFrame parent, String kategori) {
        this.parent = parent;
        this.kategori = kategori;

        setTitle("Menü Yönetimi - " + kategori);
        setSize(1100, 700);
        setResizable(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===================== ARKA PLAN =====================
        BackgroundPanel bg = new BackgroundPanel("icons/adminn.png");
        bg.setLayout(new BorderLayout());
        add(bg, BorderLayout.CENTER);

        // ===================== ÜST BAR =====================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(1100, 80));
        topBar.setBackground(new Color(0, 0, 0, 140));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topBar.setOpaque(true);

        JLabel lblBaslik = new JLabel("Menü Yönetimi - " + kategori);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBaslik.setForeground(Color.WHITE);

        JButton btnGeri = createGeriButonu();

        topBar.add(lblBaslik, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);

        bg.add(topBar, BorderLayout.NORTH);

        // ===================== ALT BUTONLAR =====================
        JButton btnEkle = new JButton("Ürün Ekle");
        JButton btnSil  = new JButton("Seçili Ürünü Sil");
        JButton btnFiyatGuncelle = new JButton("Fiyat Güncelle"); // 🔥 EKLENDİ

        JPanel butonPanel = new JPanel();
        butonPanel.add(btnEkle);
        butonPanel.add(btnSil);
        butonPanel.add(btnFiyatGuncelle); // 🔥 EKLENDİ

        bg.add(butonPanel, BorderLayout.SOUTH);

        // ===================== GRID =====================
        grid = new JPanel(new GridLayout(0, 3, 40, 40));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(30);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        bg.add(scroll, BorderLayout.CENTER);

        // ===================== SİL =====================
        btnSil.addActionListener(e -> {

            Set<AdminProductCard> secililer = AdminProductCard.getSelectedCards();

            if (secililer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Silmek için ürün seç!");
                return;
            }

            for (AdminProductCard card : secililer) {
                String urunAdi = card.getUrun().getAd();

                if (MasaServisi.getInstance().urunSiparisteMi(urunAdi)) {
                    JOptionPane.showMessageDialog(
                            this,
                            urunAdi + " şu anda masalarda siparişte!",
                            "Silme Engellendi",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }

            int cevap = JOptionPane.showConfirmDialog(
                    this,
                    secililer.size() + " adet ürünü silmek istediğinize emin misiniz?",
                    "Ürün Silme Onayı",
                    JOptionPane.YES_NO_OPTION
            );

            if (cevap != JOptionPane.YES_OPTION) return;

            for (AdminProductCard card : new java.util.HashSet<>(secililer)) {
                UrunServisi.getInstance()
                        .urunSil(card.getUrun().getAd(), kategori);

                StokServisi.getInstance()
                        .stokSil(card.getUrun().getAd());
            }

            AdminProductCard.clearSelection();
            doldur();
        });

        // ===================== EKLE =====================
        btnEkle.addActionListener(e -> {

            AdminUrunEkleDialog dialog =
                    new AdminUrunEkleDialog(this, kategori);

            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    doldur();
                }
            });

            dialog.setVisible(true);
        });

        // ===================== 🔥 FİYAT GÜNCELLE =====================
        btnFiyatGuncelle.addActionListener(e -> {

            // 🔴 HİÇ SEÇİM YOK
            if (AdminProductCard.getSelectedCards().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Fiyat güncellemek için bir ürün seçin!"
                );
                return;
            }

            // 🔴 BİRDEN FAZLA SEÇİM
            if (AdminProductCard.getSelectedCards().size() > 1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Fiyat güncellemek için sadece tek bir ürün seçin!"
                );
                return;
            }

            // ✅ SADECE 1 ÜRÜN
            AdminProductCard card =
                    AdminProductCard.getSelectedCards().iterator().next();

            Urun urun = card.getUrun();

            // =================== 🔥 YENİ FİYAT INPUT (KONTROLLÜ) ===================
            JTextField fiyatField = new JTextField(10);

            ((javax.swing.text.AbstractDocument) fiyatField.getDocument())
                    .setDocumentFilter(new view_components.NumericLimitFilter(4)); // 🔒 max 4 hane, sadece rakam

            fiyatField.setText(String.valueOf(urun.getFiyat()));

            int result = JOptionPane.showConfirmDialog(
                    this,
                    fiyatField,
                    "Yeni fiyat:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) return;

            int yeniFiyat = Integer.parseInt(fiyatField.getText());

            if (yeniFiyat <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Fiyat 0'dan büyük olmalı!"
                );
                return;
            }

            UrunServisi.getInstance().fiyatGuncelle(urun, yeniFiyat);

            JOptionPane.showMessageDialog(
                    this,
                    "Fiyat güncellendi!"
            );

            AdminProductCard.clearSelection();
            doldur();
        });

        doldur();
        setVisible(true);
    }

    // ===================== GERİ =====================
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
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setIconTextGap(10);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(normal);
            }
        });

        btn.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        return btn;
    }

    // ===================== DOLDUR =====================
    private void doldur() {
        grid.removeAll();

        List<Urun> list = UrunServisi.getInstance()
                .getUrunlerByKategori(kategori);

        for (Urun u : list) {
            grid.add(new AdminProductCard(u));
        }

        grid.revalidate();
        grid.repaint();
    }
}
