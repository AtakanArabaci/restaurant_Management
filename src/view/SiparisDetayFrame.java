package view;

import model.Masa;
import model.Siparis;
import view.GarsonPanel;
import view.components.BackgroundPanel;
import view.components.CustomInputDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SiparisDetayFrame extends JFrame {

    private Masa masa;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblToplam;

    public SiparisDetayFrame(Masa masa) {

        this.masa = masa;

        setTitle("Sipariş Detayı - Masa " + masa.getMasaNo());
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel arkaPlan = new BackgroundPanel("icons/garson_bg.jpg");
        arkaPlan.setLayout(new BorderLayout());
        add(arkaPlan);

        // ================== ÜST BAR ==================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblBaslik = new JLabel(masa.getMasaNo() + ". Masa - Sipariş Detayı");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);

        // ---- GERİ BUTONU (ikonlu modern tasarım) ----
        ImageIcon iconGeri = new ImageIcon("icons/ger.png");
        Image geriImg = iconGeri.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon geriIcon = new ImageIcon(geriImg);

        JButton btnGeri = new JButton("  Geri", geriIcon);
        btnGeri.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGeri.setIconTextGap(10);

        btnGeri.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setBackground(new Color(255, 107, 107));
        btnGeri.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGeri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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

        topBar.add(lblBaslik, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);
        arkaPlan.add(topBar, BorderLayout.NORTH);

        // ================== TABLO PANELİ ==================
        JPanel tableHolder = new JPanel(new BorderLayout());
        tableHolder.setOpaque(false);
        tableHolder.setBorder(BorderFactory.createEmptyBorder(40, 50, 20, 50));

        JPanel glassPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 160));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ================== TABLO MODELİ ==================
        tableModel = new DefaultTableModel(new Object[]{"Ürün", "Adet", "Fiyat", "Toplam"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        // ================== SCROLL ==================
        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getViewport().setBorder(null);

        glassPanel.add(scroll, BorderLayout.CENTER);
        tableHolder.add(glassPanel, BorderLayout.CENTER);
        arkaPlan.add(tableHolder, BorderLayout.CENTER);

        // ================== ALT PANEL ==================
        JPanel altPanel = new JPanel(new BorderLayout());
        altPanel.setOpaque(false);
        altPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        lblToplam = new JLabel("Toplam: 0 ₺");
        lblToplam.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblToplam.setForeground(Color.WHITE);
        altPanel.add(lblToplam, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton btnGuncelle = createModernButton("Güncelle");
        JButton btnSil      = createModernButton("Sil");
        JButton btnOdeme    = createModernButton("Ödeme Al");

        buttonPanel.add(btnGuncelle);
        buttonPanel.add(btnSil);
        buttonPanel.add(btnOdeme);

        altPanel.add(buttonPanel, BorderLayout.EAST);
        arkaPlan.add(altPanel, BorderLayout.SOUTH);

        // Verileri yükle
        tabloyuDoldur();

        // Aksiyonlar
        btnGuncelle.addActionListener(e -> guncelle());
        btnSil.addActionListener(e -> sil());
        btnOdeme.addActionListener(e -> new OdemeFrame(masa));

        setVisible(true);
    }

    // ================== MODERN BUTON ==================
    private JButton createModernButton(String text) {

        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(232, 137, 81),
                        getWidth(), getHeight(), new Color(205, 112, 55)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ================== TABLO DOLDUR ==================
    private void tabloyuDoldur() {

        tableModel.setRowCount(0);

        for (Siparis s : masa.getSiparisler()) {

            double fiyat  = s.getUrun().getFiyat();
            int adet      = s.getAdet();
            double toplam = fiyat * adet;

            tableModel.addRow(new Object[]{
                    s.getUrun().getAd(),
                    adet,
                    fiyat + " ₺",
                    toplam + " ₺"
            });
        }

        // Toplam fiyat güncelle
        lblToplam.setText("Toplam: " + hesaplaToplam() + " ₺");

        // === MASA BOŞALDI MI? OTOMATİK YEŞİLE DÖN ===
        if (masa.getSiparisler().isEmpty()) {
            masa.setDurum("BOŞ");
            if (GarsonPanel.INSTANCE != null) {
                GarsonPanel.INSTANCE.yenileMasalar();
            }
        }
    }

    // Toplam hesaplayan yardımcı metod
    private double hesaplaToplam() {
        return masa.getSiparisler().stream()
                .mapToDouble(s -> s.getUrun().getFiyat() * s.getAdet())
                .sum();
    }

    // ================== GÜNCELLE ==================
    private void guncelle() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin!");
            return;
        }

        Siparis siparis = masa.getSiparisler().get(row);

        CustomInputDialog dialog = new CustomInputDialog(
                this,
                siparis.getUrun().getAd(),
                siparis.getAdet(),
                siparis.getUrun().getKategori()
        );

        dialog.setVisible(true);

        int yeni = dialog.getResult();
        if (yeni == -1) return;

     // ============================
     // 🔥 STOK KONTROLÜ (DÜZGÜN)
     // ============================
        String urunAdi = siparis.getUrun().getAd();
        int eskiAdet = siparis.getAdet();
        int stokLimiti = siparis.getUrun().getStokLimiti();

        // 1- Tüm masalardaki toplam kullanım
        int toplamKullanim = 0;

        for (Masa m : GarsonPanel.INSTANCE.getMasaServisi().getMasalar().values()) {
            for (Siparis sp : m.getSiparisler()) {
                if (sp.getUrun().getAd().equalsIgnoreCase(urunAdi)) {
                    toplamKullanim += sp.getAdet();
                }
            }
        }

        // 2- Bu masanın eski adedini çıkar → diğer masalardaki toplam adet
        int digerMasalar = toplamKullanim - eskiAdet;

        // 3- Yeni toplam = diğer masalar + yeni adet
        int yeniToplam = digerMasalar + yeni;

     // 4- Limit kontrolü
        if (yeniToplam > stokLimiti) {

            // 🔥 OK yazan butonu TAMAM olarak değiştiriyoruz
            UIManager.put("OptionPane.okButtonText", "Tamam");

            JOptionPane.showMessageDialog(this,
                    "Bu ürünün stok limiti: " + stokLimiti +
                    "\nŞu an diğer masalarda kullanım: " + digerMasalar +
                    "\nEkleyebileceğiniz maksimum: " + (stokLimiti - digerMasalar),
                    "Stok Aşıldı!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 5- Güncelle
        siparis.setAdet(yeni);
        tabloyuDoldur();

    }


    // ================== SİL ==================
    private void sil() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin!");
            return;
        }

        masa.getSiparisler().remove(row);
        tabloyuDoldur();
    }
}
