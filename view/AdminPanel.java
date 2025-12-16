package view;

import model.Urun;
import model.Kullanici;
import model.Masa;
import model.Siparis;
import servis.UrunServisi;
import view_components.BackgroundPanel;
import servis.KullaniciServisi;
import servis.MasaServisi;
import model.OdenmisSiparis;
import servis.OdenmisSiparisServisi;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.temporal.ChronoUnit;

public class AdminPanel extends JFrame {

	public AdminPanel() {

	    setTitle("Admin Paneli");
	    setSize(900, 700);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);

	    // ARKA PLAN
	    BackgroundPanel bg = new BackgroundPanel("icons/garson_bg.jpg");
	    bg.setLayout(new BorderLayout());
	    add(bg, BorderLayout.CENTER);

	    // ===================== ÜST BAR =====================
	    JPanel topBar = new JPanel(new BorderLayout());
	    topBar.setPreferredSize(new Dimension(900, 90));
	    topBar.setBackground(new Color(0, 0, 0, 140));
	    topBar.setOpaque(true);
	    topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

	    // BAŞLIK
	    JLabel lblBaslik = new JLabel("Admin Paneli");
	    lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 36));
	    lblBaslik.setForeground(Color.WHITE);

	    // GERİ BUTONU
	    JButton btnGeri = createGeriButonu(this, new LoginFrame());

	    // YERLEŞTİR
	    topBar.add(lblBaslik, BorderLayout.WEST);
	    topBar.add(btnGeri, BorderLayout.EAST);

	    bg.add(topBar, BorderLayout.NORTH);

	    // ===================== ORTA ALAN =====================
	    JPanel grid = new JPanel(new GridLayout(2, 3, 25, 25));
	    grid.setOpaque(false);
	    grid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
	    bg.add(grid, BorderLayout.CENTER);

	    grid.add(createMenuCard("Menü Kontrolü", null, () -> {
	        setVisible(false);                 // AdminPanel’i gizle (dispose ETME)
	        new AdminKategoriSecimFrame(this); // parent olarak this gönder
	    }));


	    grid.add(createMenuCard("Garson Kontrolü", null,
	            () -> new AdminGarsonListeFrame()));

	    grid.add(createMenuCard("Masa No'ya Göre Sorgu", null,
	            () -> new MasaListesiFrame()));

	    grid.add(createMenuCard("Tarihe Göre Sırala", null,
	            this::acTarihSiralaPenceresi));

	    grid.add(createMenuCard("Gün Sonu Raporu", null,
	            () -> placeholder("Gün sonu")));

	    grid.add(new JLabel());

	    setVisible(true);
	}

    // ======================= MENÜ KONTROLÜ =======================
    private void acMenuYonetimiPenceresi() {

        JFrame pencere = new JFrame("Menü Yönetimi");
        pencere.setSize(800, 600);
        pencere.setLocationRelativeTo(null);
        pencere.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pencere.add(panel);

        JPanel top = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Menü Yönetimi", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JButton btnGeri = new JButton("Geri");
        btnGeri.addActionListener(e -> pencere.dispose());

        top.add(lbl, BorderLayout.WEST);
        top.add(btnGeri, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Ad", "Kategori", "Fiyat", "Stok"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(26);

        // 🔒 TAM KİLİT – SADECE GÖRÜNTÜLEME
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setFocusable(false);
        table.setEnabled(false);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        UrunServisi servis = UrunServisi.getInstance();

        Runnable tabloyuDoldur = () -> {
            model.setRowCount(0);
            List<Urun> hepsi = servis.getUrunler();
            for (Urun u : hepsi) {
                model.addRow(new Object[]{
                        u.getAd(), u.getKategori(), u.getFiyat(), u.getStokLimiti()
                });
            }
        };
        tabloyuDoldur.run();

        JPanel bottom = new JPanel();
        JButton ekle = new JButton("Ekle");
        JButton sil = new JButton("Sil");
        bottom.add(ekle);
        bottom.add(sil);
        panel.add(bottom, BorderLayout.SOUTH);

        ekle.addActionListener(e -> {
            JTextField ad = new JTextField();
            JTextField fiyat = new JTextField();
            JTextField stok = new JTextField();
            JComboBox<String> kat = new JComboBox<>(new String[]{"yemek", "icecek", "tatli"});

            JPanel p = new JPanel(new GridLayout(4, 2, 10, 10));
            p.add(new JLabel("Ad:")); p.add(ad);
            p.add(new JLabel("Kategori:")); p.add(kat);
            p.add(new JLabel("Fiyat:")); p.add(fiyat);
            p.add(new JLabel("Stok:")); p.add(stok);

            int r = JOptionPane.showConfirmDialog(pencere, p, "Ürün Ekle",
                    JOptionPane.OK_CANCEL_OPTION);

            if (r == JOptionPane.OK_OPTION) {
                try {
                    Urun urun = new Urun(
                            ad.getText(),
                            Double.parseDouble(fiyat.getText()),
                            kat.getSelectedItem().toString(),
                            "none"
                    );
                    urun.setStokLimiti(Integer.parseInt(stok.getText()));
                    servis.urunEkle(urun);
                    tabloyuDoldur.run();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pencere, "Geçersiz değer!");
                }
            }
        });

        sil.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            String ad = table.getValueAt(row, 0).toString();
            String kat = table.getValueAt(row, 1).toString();

            servis.urunSil(ad, kat);
            tabloyuDoldur.run();
        });

        pencere.setVisible(true);
    }

    // ======================= GARSON KONTROLÜ =======================
    private void acGarsonKontrolPenceresi() {

        JFrame pencere = new JFrame("Garson Kontrolü");
        pencere.setSize(700, 500);
        pencere.setLocationRelativeTo(null);
        pencere.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pencere.add(panel);

        JLabel lbl = new JLabel("Garson Yönetimi", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JButton geri = new JButton("Geri");
        geri.addActionListener(e -> pencere.dispose());

        JPanel top = new JPanel(new BorderLayout());
        top.add(lbl, BorderLayout.WEST);
        top.add(geri, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Kullanıcı Adı", "Şifre"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(26);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        KullaniciServisi servis = KullaniciServisi.getInstance();

        Runnable yenile = () -> {
            model.setRowCount(0);
            for (Kullanici k : servis.getGarsonlar().values()) {
                model.addRow(new Object[]{k.getKullaniciAdi(), k.getSifre()});
            }
        };
        yenile.run();

        JPanel bottom = new JPanel();
        JButton ekle = new JButton("Garson Ekle");
        JButton sil = new JButton("Garson Sil");
        bottom.add(ekle);
        bottom.add(sil);
        panel.add(bottom, BorderLayout.SOUTH);

        ekle.addActionListener(e -> {
            JTextField ad = new JTextField();
            JTextField sifre = new JTextField();

            JPanel p = new JPanel(new GridLayout(2, 2, 10, 10));
            p.add(new JLabel("Kullanıcı Adı:")); p.add(ad);
            p.add(new JLabel("Şifre:")); p.add(sifre);

            int r = JOptionPane.showConfirmDialog(pencere, p, "Garson Ekle",
                    JOptionPane.OK_CANCEL_OPTION);

            if (r == JOptionPane.OK_OPTION) {
                servis.garsonEkle(ad.getText(), sifre.getText());
                yenile.run();
            }
        });

        sil.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            String ad = table.getValueAt(row, 0).toString();
            servis.garsonSil(ad);
            yenile.run();
        });

        pencere.setVisible(true);
    }

    // ======================= MASA NO'YA GÖRE SORGULAMA =======================
    private void acMasaSorguPenceresi() {

        JFrame pencere = new JFrame("Masa No'ya Göre Sorgu");
        pencere.setSize(700, 500);
        pencere.setLocationRelativeTo(null);
        pencere.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel anaPanel = new JPanel(new BorderLayout());
        anaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pencere.add(anaPanel);

        JLabel lbl = new JLabel("Masalara Göre Siparişler", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JButton geri = new JButton("Geri");
        geri.addActionListener(e -> pencere.dispose());

        JPanel top = new JPanel(new BorderLayout());
        top.add(lbl, BorderLayout.WEST);
        top.add(geri, BorderLayout.EAST);
        anaPanel.add(top, BorderLayout.NORTH);

        MasaServisi masaServisi = MasaServisi.getInstance();

        JPanel grid = new JPanel(new GridLayout(3, 3, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Integer numara : masaServisi.getMasalar().keySet()) {
            JButton btnMasa = new JButton("Masa " + numara);
            btnMasa.setFont(new Font("Segoe UI", Font.BOLD, 18));

            btnMasa.addActionListener(e -> {
                Masa masa = masaServisi.getMasa(numara);
                if (masa == null) {
                    JOptionPane.showMessageDialog(pencere, "Masa bulunamadı!");
                    return;
                }

                java.util.List<Siparis> siparisler = masa.getSiparisler();
                String durum = masa.getDurum();

                if (siparisler.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            pencere,
                            "Masa " + numara + " için kayıtlı sipariş yok.\nDurum: " + durum
                    );
                    return;
                }

                DefaultTableModel model = new DefaultTableModel(
                        new Object[]{"Ürün", "Adet", "Fiyat", "Toplam"}, 0
                );
                for (Siparis s : siparisler) {
                    model.addRow(new Object[]{
                            s.getUrun().getAd(),
                            s.getAdet(),
                            s.getUrun().getFiyat(),
                            s.getToplam()
                    });
                }

                JTable table = new JTable(model);
                table.setRowHeight(26);

                JPanel p = new JPanel(new BorderLayout());
                p.add(new JLabel("Masa " + numara + " - Durum: " + durum), BorderLayout.NORTH);
                p.add(new JScrollPane(table), BorderLayout.CENTER);

                JOptionPane.showMessageDialog(pencere, p, "Masa " + numara + " Siparişleri",
                        JOptionPane.PLAIN_MESSAGE);
            });

            grid.add(btnMasa);
        }

        anaPanel.add(grid, BorderLayout.CENTER);

        pencere.setVisible(true);
    }

    // ======================= TARİHE GÖRE SIRALAMA =======================
    private void acTarihSiralaPenceresi() {

        JFrame pencere = new JFrame("Ödenmiş Siparişler");
        pencere.setSize(800, 600);
        pencere.setLocationRelativeTo(null);
        pencere.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pencere.add(panel);

        // =================== ÜST ALAN ===================
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        JLabel lbl = new JLabel("Ödenmiş Siparişler (Tarihe Göre)");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        topPanel.add(lbl, BorderLayout.WEST);

        JComboBox<String> cmbFiltre = new JComboBox<>(
                new String[]{"Tümü", "Bugün", "Son 7 Gün", "Son 1 Ay"}
        );
        cmbFiltre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        topPanel.add(cmbFiltre, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        // =================== TABLO ===================
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Tarih", "Masa", "Ürün", "Toplam"}, 0   // 🔥 Adet kaldırıldı
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        // 🔥 TABLO SATIRI → DETAY HARİTASI
        Map<Integer, List<OdenmisSiparis>> satirDetayMap = new LinkedHashMap<>();

        // =================== TABLOYU DOLDUR ===================
        Runnable tabloyuDoldur = () -> {

            model.setRowCount(0);
            satirDetayMap.clear();

            List<OdenmisSiparis> liste =
                    OdenmisSiparisServisi.getInstance().getTariheGoreSirali();

            LocalDateTime now = LocalDateTime.now();
            String secim = (String) cmbFiltre.getSelectedItem();

            Map<String, List<OdenmisSiparis>> gruplu = new LinkedHashMap<>();

            for (OdenmisSiparis s : liste) {

                LocalDateTime zaman = s.getZaman();
                boolean eklenecek = true;

                if ("Bugün".equals(secim)) {
                    eklenecek = zaman.toLocalDate().isEqual(LocalDate.now());
                } else if ("Son 7 Gün".equals(secim)) {
                    eklenecek = zaman.isAfter(now.minusDays(7));
                } else if ("Son 1 Ay".equals(secim)) {
                    eklenecek = zaman.isAfter(now.minusMonths(1));
                }

                if (!eklenecek) continue;

                String key = s.getMasaNo() + "_"
                        + zaman.truncatedTo(ChronoUnit.MINUTES);

                gruplu.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
            }

            for (List<OdenmisSiparis> grup : gruplu.values()) {

                OdenmisSiparis ilk = grup.get(0);

                StringBuilder urunler = new StringBuilder();
                double toplamTutar = 0;

                for (OdenmisSiparis s : grup) {
                    urunler.append(s.getUrunAdi())
                           .append(" x")
                           .append(s.getAdet())
                           .append(", ");
                    toplamTutar += s.getToplam();
                }

                if (urunler.length() > 2)
                    urunler.setLength(urunler.length() - 2);

                int rowIndex = model.getRowCount();

                model.addRow(new Object[]{
                        ilk.getZaman().format(fmt),
                        ilk.getMasaNo(),
                        urunler.toString(),
                        toplamTutar
                });

                satirDetayMap.put(rowIndex, grup);
            }
        };

        tabloyuDoldur.run();
        cmbFiltre.addActionListener(e -> tabloyuDoldur.run());

        // =================== ÇİFT TIK → DETAY ===================
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {

                    int row = table.rowAtPoint(e.getPoint());
                    if (!satirDetayMap.containsKey(row)) return;

                    List<OdenmisSiparis> detay = satirDetayMap.get(row);

                    DefaultTableModel detayModel = new DefaultTableModel(
                            new Object[]{"Ürün", "Adet", "Toplam"}, 0
                    ) {
                        @Override
                        public boolean isCellEditable(int r, int c) {
                            return false;
                        }
                    };

                    for (OdenmisSiparis s : detay) {
                        detayModel.addRow(new Object[]{
                                s.getUrunAdi(),
                                s.getAdet(),
                                s.getToplam()
                        });
                    }

                    JTable detayTable = new JTable(detayModel);
                    detayTable.setRowHeight(24);
                    detayTable.setEnabled(false);

                    JFrame detayPencere = new JFrame("Sipariş Detayı");
                    detayPencere.setSize(400, 300);
                    detayPencere.setLocationRelativeTo(pencere);
                    detayPencere.add(new JScrollPane(detayTable));
                    detayPencere.setVisible(true);
                }
            }
        });

        pencere.setVisible(true);
    }

    // ======================= KART TASARIMI =======================
    private JPanel createMenuCard(String title, String desc, Runnable onClick) {

        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(220, 140, 80, 230));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
            }
        };

        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Segoe UI", Font.BOLD, 22));
        t.setForeground(Color.WHITE);

        JLabel d = new JLabel(desc, SwingConstants.CENTER);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        d.setForeground(new Color(255, 255, 255, 180));

        card.add(t, BorderLayout.CENTER);
        card.add(d, BorderLayout.SOUTH);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                onClick.run();
            }
        });

        return card;
    }

    private JButton createGeriButonu(JFrame current, JFrame target) {

        // ikon yükle + küçült
        ImageIcon icon = new ImageIcon("icons/ger.png");
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton btn = new JButton("  Geri", icon);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);

        // KIRMIZI ZEMİN
        Color normal = new Color(255, 107, 107);
        Color hover  = new Color(255, 76, 76);

        btn.setBackground(normal);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 🔥 ÖNEMLİ: BOYUT DEĞİL, PADDING
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setIconTextGap(10);
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        // HOVER EFEKTİ
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

        // AKSİYON
        btn.addActionListener(e -> {
            current.dispose();
            target.setVisible(true);
        });

        return btn;
    }



    private void placeholder(String msg) {
        JOptionPane.showMessageDialog(this, msg + " ekranı henüz yapılmadı.");
    }


}

