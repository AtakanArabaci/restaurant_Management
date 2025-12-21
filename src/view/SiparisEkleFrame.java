package view;

import model.Masa;
import servis.MasaServisi;
import servis.StokServisi;
import model.Urun;
import servis.SiparisServisi;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SiparisEkleFrame extends JFrame {

    private Masa masa;
    private Urun urun;
    private SiparisServisi siparisServisi;

    private JTextField txtAdet;

    public SiparisEkleFrame(Urun urun, Masa masa) {
        this.masa = masa;
        this.urun = urun;
        this.siparisServisi = new SiparisServisi();

        setTitle("Sipariş Ekle - " + urun.getAd());
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel anaPanel = new JPanel(new BorderLayout());
        anaPanel.setBackground(new Color(245, 245, 245));
        anaPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(anaPanel);

        JLabel lblBaslik = new JLabel("Sipariş Ekle (" + urun.getAd() + ")");
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 20));
        anaPanel.add(lblBaslik, BorderLayout.NORTH);

        JPanel ortaPanel = new JPanel(null);
        ortaPanel.setBackground(Color.WHITE);
        ortaPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        anaPanel.add(ortaPanel, BorderLayout.CENTER);

        JLabel lblFiyat = new JLabel("Fiyat: " + urun.getFiyat() + " TL");
        lblFiyat.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblFiyat.setBounds(30, 25, 200, 25);
        ortaPanel.add(lblFiyat);

        JLabel lblAdet = new JLabel("Adet:");
        lblAdet.setBounds(30, 65, 120, 25);
        ortaPanel.add(lblAdet);

        txtAdet = new JTextField("1");
        txtAdet.setBounds(160, 65, 160, 28);
        ortaPanel.add(txtAdet);

        JButton btnEkle = new JButton("Siparişi Ekle");
        btnEkle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEkle.setBackground(new Color(66, 135, 245));
        btnEkle.setForeground(Color.WHITE);
        btnEkle.setBounds(110, 120, 140, 32);
        ortaPanel.add(btnEkle);

        btnEkle.addActionListener(e -> siparisEkle());
    }

    private void siparisEkle() {
        try {
            int adet = Integer.parseInt(txtAdet.getText());
            if (adet <= 0) throw new NumberFormatException();

            StokServisi stok = StokServisi.getInstance();

            if (!stok.stokYeterliMi(urun.getAd(), adet)) {
                JOptionPane.showMessageDialog(this,
                        "Yetersiz stok!\nMevcut: " + stok.getStok(urun.getAd()),
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Siparişi ekle
            MasaServisi.getInstance().siparisEkle(
                    masa.getMasaNo(),
                    urun,
                    adet
            );

            // ✅ Stok düş (SADECE 1 KERE)
            stok.stokDus(urun.getAd(), adet);

            // ✅ Masa durumu kalıcı
            masa.setDurum("DOLU");
            MasaServisi.getInstance().kaydetHerSey();

            if (GarsonPanel.INSTANCE != null) {
                GarsonPanel.INSTANCE.yenileMasalar();
            }

            JOptionPane.showMessageDialog(this, "✓ Sipariş eklendi!");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Adet pozitif sayı olmalı!");
        }
    }
}