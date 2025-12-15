package view_components;

import model.Urun;
import model.Siparis;
import model.Masa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProductCard extends JPanel {

    private Urun urun;
    private Masa masa;

    private JLabel lblAdet;
    private JLabel imgLabel;
    private JButton btnPlus;
    private JButton btnMinus;

    public ProductCard(Urun urun, Masa masa) {
        this.urun = urun;
        this.masa = masa;

        setPreferredSize(new Dimension(260, 320));
        setLayout(new BorderLayout());
        setOpaque(false);

        // ============================
        // RESİM + OVERLAY İSİM
        // ============================
        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);
        imgLabel.setOpaque(false);
        imgLabel.setPreferredSize(new Dimension(260, 180));
        imgLabel.setLayout(new GridBagLayout());

        JLabel overlay = new JLabel(urun.getAd());
        overlay.setOpaque(true);
        overlay.setBackground(new Color(0, 0, 0, 160));
        overlay.setForeground(Color.WHITE);
        overlay.setFont(new Font("Segoe UI", Font.BOLD, 18));
        overlay.setBorder(BorderFactory.createEmptyBorder(6,18,6,18));

        imgLabel.add(overlay);
        add(imgLabel, BorderLayout.NORTH);

        // ============================
        // ALT PANEL (+ / − / ADET)
        // ============================
        btnPlus = new JButton("+");
        btnMinus = new JButton("-");

        lblAdet = new JLabel("0", SwingConstants.CENTER);
        lblAdet.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottom.setOpaque(true);
        bottom.setBackground(new Color(245,245,245));

        bottom.add(btnMinus);
        bottom.add(lblAdet);
        bottom.add(btnPlus);

        add(bottom, BorderLayout.SOUTH);

        // ============================
        // + BUTONU — GLOBAL STOK KONTROL
        // ============================
        btnPlus.addActionListener(e -> {

            // Bu masadaki mevcut adet (sadece görüntü için)
            int mevcutBuMasa = getCurrentAdet();

            // Global stoktan 1 daha eklenebilir mi?
            if (!masa.urunLimitUygun(urun, 1)) {
                JOptionPane.showMessageDialog(
                        this,
                        urun.getAd() + " stok limiti dolmuştur!",
                        "Stok Bitti",
                        JOptionPane.WARNING_MESSAGE
                );
                updateUIstatus();
                return;
            }

            masa.siparisEkle(urun, 1);
            updateUIstatus();
        });

        // ============================
        // − BUTONU — GLOBAL STOKTAN DA DÜŞER
        // ============================
        btnMinus.addActionListener(e -> {
            masa.siparisSil(urun);
            updateUIstatus();
        });

        // Resim kart boyutu değişince yeniden scale edilsin
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleImage();
            }
        });

        updateUIstatus();
        scaleImage();
    }

    // Bu MASAda bu ürün kaç adet?
    private int getCurrentAdet() {
        for (Siparis s : masa.getSiparisler()) {
            if (s.getUrun().getAd().equals(urun.getAd()))
                return s.getAdet();
        }
        return 0;
    }

    // ============================
    // ÇERÇEVE & BUTON DURUMU
    // ============================
    private void updateUIstatus() {

        // Bu masadaki adet (label için)
        int adetMasa = getCurrentAdet();
        lblAdet.setText(String.valueOf(adetMasa));

        // GLOBAL stok kullanımını al
        int globalKullanilan = Masa.getGlobalAdet(urun);

        int max = switch (urun.getKategori().toLowerCase()) {
            case "yemek" -> 50;
            case "tatli" -> 50;
            case "icecek" -> 100;
            default -> 999;
        };

        int kalan = max - globalKullanilan;

        // 🔒 Stok bittiyse + kilitlenir
        boolean stokVarMi = kalan > 0;
        btnPlus.setEnabled(stokVarMi);

        if (!stokVarMi) {
            btnPlus.setBackground(Color.GRAY);
            btnPlus.setForeground(Color.DARK_GRAY);
        } else {
            btnPlus.setBackground(null);
            btnPlus.setForeground(null);
        }

        // ÇERÇEVE RENKLERİ (GLOBAL KALAN STOKA GÖRE)
        if (kalan > 5) {
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        } else if (kalan <= 5 && kalan > 3) {
            setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else if (kalan <= 3) {
            setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        }
    }

    // ============================
    // RESMİ KARTA GÖRE SCALE ET
    // ============================
    private void scaleImage() {
        try {
            ImageIcon original = new ImageIcon(urun.getResimYolu());
            Image img = original.getImage();

            int w = imgLabel.getWidth();
            int h = imgLabel.getHeight();

            if (w <= 0 || h <= 0) return;

            Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));

        } catch (Exception e) {
            System.out.println("Resim yüklenemedi: " + urun.getResimYolu());
        }
    }
}
