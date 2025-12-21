package view_components;

import model.Masa;
import model.Siparis;
import model.Urun;
import servis.MasaServisi;
import servis.StokServisi;
import view.GarsonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProductCard extends JPanel {

    private final Urun urun;
    private final Masa masa;

    private JLabel lblAdet;
    private JLabel imgLabel;
    private JButton btnPlus;
    private JButton btnMinus;

    private final MasaServisi masaServisi = MasaServisi.getInstance();
    private final StokServisi stokServisi = StokServisi.getInstance();

    public ProductCard(Urun urun, Masa masa) {
        this.urun = urun;
        this.masa = masa;

        // ✅ İSTEDİĞİN KART BOYUTU
        setPreferredSize(new Dimension(280, 220));
        setMinimumSize(new Dimension(280, 220));
        setMaximumSize(new Dimension(280, 220));
        setLayout(new BorderLayout());
        setOpaque(false);

     // ================= IMAGE PANEL =================
        imgLabel = new JLabel();
        imgLabel.setPreferredSize(new Dimension(260, 300));
        imgLabel.setLayout(new GridBagLayout());

        // === ORTADAKİ ÜRÜN ADI ===
        JLabel overlayTitle = new JLabel(urun.getAd(), SwingConstants.CENTER);
        overlayTitle.setOpaque(true);
        overlayTitle.setBackground(new Color(0, 0, 0, 160));
        overlayTitle.setForeground(Color.WHITE);
        overlayTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        overlayTitle.setBorder(BorderFactory.createEmptyBorder(6, 18, 6, 18));

        // === ALTTAKİ - 0 + PANELİ ===
        btnPlus = new JButton("+");
        btnMinus = new JButton("-");

        lblAdet = new JLabel("0", SwingConstants.CENTER);
        lblAdet.setFont(new Font("Segoe UI", Font.BOLD, 15));

     // ALT BAR (kart genişliğini kaplar)
        JPanel bottomOverlay = new JPanel(new BorderLayout());
        bottomOverlay.setOpaque(true);
        bottomOverlay.setBackground(new Color(255, 255, 255, 230));
        bottomOverlay.setPreferredSize(new Dimension(240, 40));

        // İçeride butonları ortalayan panel
        JPanel centerControls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        centerControls.setOpaque(false);

        centerControls.add(btnMinus);
        centerControls.add(lblAdet);
        centerControls.add(btnPlus);

        bottomOverlay.add(centerControls, BorderLayout.CENTER);

        // === GRIDBAG KONUMLARI ===
        GridBagConstraints gbc = new GridBagConstraints();

        // 🔥 YAZI: TAM ORTA
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        imgLabel.add(overlayTitle, gbc);

        // 🔥 ALT PANEL: EN ALT
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        imgLabel.add(bottomOverlay, gbc);

        add(imgLabel, BorderLayout.CENTER);

        // ================= EVENTS =================
        btnPlus.addActionListener(e -> {
            try {
                masaServisi.siparisEkle(masa.getMasaNo(), urun, 1);
                GarsonPanel.INSTANCE.yenileMasalar();
                updateUIstatus();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Stok Bitti",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        btnMinus.addActionListener(e -> {
            if (getCurrentAdet() == 0) return;

            masaServisi.siparisAzalt(masa.getMasaNo(), urun, 1);
            GarsonPanel.INSTANCE.yenileMasalar();
            updateUIstatus();
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleImage();
            }
        });

        updateUIstatus();
        scaleImage();
    }

    private int getCurrentAdet() {
        for (Siparis s : masa.getSiparisler()) {
            if (s.getUrun().getAd().equals(urun.getAd()))
                return s.getAdet();
        }
        return 0;
    }

    private void updateUIstatus() {
        lblAdet.setText(String.valueOf(getCurrentAdet()));

        int kalan = stokServisi.getStok(urun.getAd());
        btnPlus.setEnabled(kalan > 0);

        if (kalan > 5)
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        else if (kalan > 2)
            setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        else
            setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    private void scaleImage() {
        try {
            ImageIcon icon = new ImageIcon(urun.getResimYolu());
            Image img = icon.getImage().getScaledInstance(
                    imgLabel.getWidth(),
                    imgLabel.getHeight(),
                    Image.SCALE_SMOOTH
            );
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception ignored) {}
    }
}
