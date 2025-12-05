package view;

import controller.AdminController;
import model.Urun;
import servis.KullaniciServisi;
import servis.MasaServisi;
import servis.SiparisServisi;
import servis.UrunServisi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JFrame {

    private final AdminController adminController;
    private final UrunServisi urunServisi;

    private JComboBox<String> cmbMasaNo;   // MASA SEÇİMİ ARTIK BURADA
    private JTextField txtTarih;

    // DEFAULT CONSTRUCTOR – LoginFrame burayı çağırıyor
    public AdminPanel() {
        this.urunServisi = new UrunServisi();
        KullaniciServisi kullaniciServisi = new KullaniciServisi();
        MasaServisi masaServisi = new MasaServisi();
        SiparisServisi siparisServisi = new SiparisServisi();

        this.adminController = new AdminController(
                urunServisi,
                kullaniciServisi,
                masaServisi,
                siparisServisi
        );

        initComponents();
    }

    // İstersen dışarıdan da controller + servis verebilesin diye ekstra ctor
    public AdminPanel(AdminController adminController, UrunServisi urunServisi) {
        this.adminController = adminController;
        this.urunServisi = urunServisi;
        initComponents();
    }

    private void initComponents() {
        setTitle("Admin Paneli");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout(15, 15));

        JLabel lblBaslik = new JLabel("Admin Paneli", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(lblBaslik, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // ÜST SATIR: Menü & Garson kontrol
        JPanel ustSatir = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton btnMenuKontrol = new JButton("Menü Kontrolü");
        JButton btnGarsonKontrol = new JButton("Garson Kontrolü");
        styleBigButton(btnMenuKontrol);
        styleBigButton(btnGarsonKontrol);
        ustSatir.add(btnMenuKontrol);
        ustSatir.add(btnGarsonKontrol);

        // ALT SATIR: Masa ve tarih sorgu
        JPanel altSatir = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel lblMasaNo = new JLabel("Masa Numarasına Göre Sorgu:");
        // 1–8 arası masalar combobox’ta
        String[] masalar = {"1", "2", "3", "4", "5", "6", "7", "8"};
        cmbMasaNo = new JComboBox<>(masalar);

        JLabel lblTarih = new JLabel("Tarihe Göre Sorgu (gg.aa.yyyy):");
        txtTarih = new JTextField();

        JButton btnMasaSorgu = new JButton("Masayı Sorgula");
        JButton btnTarihSorgu = new JButton("Tarihi Sorgula");
        styleSmallButton(btnMasaSorgu);
        styleSmallButton(btnTarihSorgu);

        altSatir.add(lblMasaNo);
        altSatir.add(cmbMasaNo);
        altSatir.add(btnMasaSorgu);
        altSatir.add(lblTarih);
        altSatir.add(txtTarih);
        altSatir.add(btnTarihSorgu);

        centerPanel.add(ustSatir);
        centerPanel.add(altSatir);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ALT PANEL: Girişe dön + Gün sonu
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGirisDon = new JButton("Girişe Dön");
        JButton btnGunSonu = new JButton("Gün Sonu Raporu");
        styleSmallButton(btnGirisDon);
        styleSmallButton(btnGunSonu);
        bottomPanel.add(btnGirisDon);
        bottomPanel.add(btnGunSonu);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // ===== BUTON İŞLEMLERİ =====

        // MENÜ BUTONU: Menüyü göster / ürün ekle / ürün sil
        btnMenuKontrol.addActionListener(e -> {
            Object[] secenekler = {
                    "Menüyü Göster",
                    "Ürün Ekle",
                    "Ürün Sil",
                    "İptal"
            };

            int secim = JOptionPane.showOptionDialog(
                    this,
                    "Menü işlemini seç:",
                    "Menü Kontrolü",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    secenekler,
                    secenekler[0]
            );

            if (secim == 0) {
                adminController.menuyuKontrolEt();
            } else if (secim == 1) {
                urunEklePenceresi();
            } else if (secim == 2) {
                urunSilPenceresi();
            }
        });

        // GARSON BUTONU: listele / ekle / sil
        btnGarsonKontrol.addActionListener(e -> {
            Object[] secenekler = {
                    "Garsonları Listele",
                    "Garson Ekle",
                    "Garson Sil",
                    "İptal"
            };

            int secim = JOptionPane.showOptionDialog(
                    this,
                    "Garson işlemini seç:",
                    "Garson Kontrolü",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    secenekler,
                    secenekler[0]
            );

            if (secim == 0) {
                adminController.garsonlariKontrolEt();
            } else if (secim == 1) {
                garsonEklePenceresi();
            } else if (secim == 2) {
                garsonSilPenceresi();
            }
        });

        // MASA SORGU – seçili combobox değerini kullan
        btnMasaSorgu.addActionListener(e -> {
            String masaNoMetni = (String) cmbMasaNo.getSelectedItem();
            adminController.masayaGoreSorgula(masaNoMetni);
        });

        // TARİH SORGU
        btnTarihSorgu.addActionListener(e -> {
            String tarih = txtTarih.getText().trim();
            adminController.tariheGoreSorgula(tarih);
        });

        // GÜN SONU
        btnGunSonu.addActionListener(e -> adminController.gunSonuAl());

        // GİRİŞE DÖN
        btnGirisDon.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        setContentPane(mainPanel);
    }

    // ======= YARDIMCI PENCERELER =======

    // ÜRÜN EKLEME
    private void urunEklePenceresi() {
        String[] kategoriler = {"YEMEK", "TATLI", "ICECEK"};
        JComboBox<String> cmbKategori = new JComboBox<>(kategoriler);
        JTextField txtAd = new JTextField();
        JTextField txtFiyat = new JTextField();
        JTextField txtIcon = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Kategori:"));
        panel.add(cmbKategori);
        panel.add(new JLabel("Ürün Adı:"));
        panel.add(txtAd);
        panel.add(new JLabel("Fiyat:"));
        panel.add(txtFiyat);
        panel.add(new JLabel("Icon Yolu (opsiyonel):"));
        panel.add(txtIcon);

        int sonuc = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Yeni Ürün Ekle",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (sonuc == JOptionPane.OK_OPTION) {
            String kategori = (String) cmbKategori.getSelectedItem();
            String ad = txtAd.getText().trim();
            String fiyatStr = txtFiyat.getText().trim();
            String icon = txtIcon.getText().trim();
            if (icon.isEmpty()) icon = null;

            double fiyat;
            try {
                fiyat = Double.parseDouble(fiyatStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Fiyat sayısal olmalıdır.",
                        "Hatalı Fiyat",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            adminController.urunEkle(kategori, ad, fiyat, icon);
        }
    }

    // ÜRÜN SİLME – kategoriye göre ürünler listeleniyor
    private void urunSilPenceresi() {
        String[] kategoriler = {"YEMEK", "TATLI", "ICECEK"};
        JComboBox<String> cmbKategori = new JComboBox<>(kategoriler);
        JComboBox<String> cmbUrun = new JComboBox<>();

        String ilkKategori = (String) cmbKategori.getSelectedItem();
        kategoriyeGoreUrunleriYukle(ilkKategori, cmbUrun);

        cmbKategori.addActionListener(e -> {
            String seciliKategori = (String) cmbKategori.getSelectedItem();
            kategoriyeGoreUrunleriYukle(seciliKategori, cmbUrun);
        });

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Kategori:"));
        panel.add(cmbKategori);
        panel.add(new JLabel("Silinecek Ürün:"));
        panel.add(cmbUrun);

        int sonuc = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Ürün Sil",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (sonuc == JOptionPane.OK_OPTION) {
            String kategori = (String) cmbKategori.getSelectedItem();
            String urunAdi = (String) cmbUrun.getSelectedItem();

            if (urunAdi == null || urunAdi.isBlank()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Bu kategoride silinecek ürün bulunamadı.",
                        "Ürün Sil",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            adminController.urunSil(kategori, urunAdi);
        }
    }

    // Kategoriye göre comboBox'a ürün isimlerini doldurur
    private void kategoriyeGoreUrunleriYukle(String kategori, JComboBox<String> cmbUrun) {
        cmbUrun.removeAllItems();

        List<Urun> urunler = urunServisi.kategoriGetir(kategori);
        for (Urun u : urunler) {
            cmbUrun.addItem(u.getAd());
        }
    }

    // GARSON EKLEME
    private void garsonEklePenceresi() {
        JTextField txtAd = new JTextField();
        JPasswordField txtSifre = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Garson Adı:"));
        panel.add(txtAd);
        panel.add(new JLabel("Şifre:"));
        panel.add(txtSifre);

        int sonuc = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Yeni Garson Ekle",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (sonuc == JOptionPane.OK_OPTION) {
            String ad = txtAd.getText().trim();
            String sifre = new String(txtSifre.getPassword());
            adminController.garsonEkle(ad, sifre);
        }
    }

    // GARSON SİLME – garson adını listeden seçiyoruz
    private void garsonSilPenceresi() {
        KullaniciServisi ks = new KullaniciServisi();
        java.util.List<String> garsonlar = ks.garsonIsimleriniGetir();

        if (garsonlar.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Kayıtlı garson bulunmuyor.",
                    "Garson Sil",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JComboBox<String> cmbGarson = new JComboBox<>();
        for (String g : garsonlar) {
            cmbGarson.addItem(g);
        }

        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        panel.add(new JLabel("Silinecek Garson:"));
        panel.add(cmbGarson);

        int sonuc = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Garson Sil",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (sonuc == JOptionPane.OK_OPTION) {
            String seciliGarson = (String) cmbGarson.getSelectedItem();
            if (seciliGarson != null && !seciliGarson.isBlank()) {
                adminController.garsonSil(seciliGarson);
            }
        }
    }

    // ===== BUTON GÖRÜNÜMÜ YARDIMCILARI =====

    private void styleBigButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(230, 230, 240));
        btn.setFocusPainted(false);
    }

    private void styleSmallButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setBackground(new Color(230, 230, 240));
        btn.setFocusPainted(false);
    }
}
