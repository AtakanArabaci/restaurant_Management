package view;

import model.Masa;
import model.Urun;
import servis.UrunServisi;
import servis.SiparisServisi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class SiparisEkleFrame extends JFrame {

    private Masa masa;
    private UrunServisi urunServisi;
    private SiparisServisi siparisServisi;

    private JComboBox<String> cmbKategori;
    private JComboBox<Urun> cmbUrun;
    private JTextField txtAdet;

    public SiparisEkleFrame(Masa masa) {
        this.masa = masa;
        this.urunServisi = new UrunServisi();
        this.siparisServisi = new SiparisServisi();

        setTitle("Sipariş Ekle - Masa " + masa.getMasaNo());
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Arka plan paneli
        JPanel anaPanel = new JPanel(new BorderLayout());
        anaPanel.setBackground(new Color(245, 245, 245));
        anaPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(anaPanel);

        // Başlık
        JLabel lblBaslik = new JLabel("Sipariş Ekle");
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 20));
        anaPanel.add(lblBaslik, BorderLayout.NORTH);

        // Orta kutu
        JPanel ortaPanel = new JPanel(null);
        ortaPanel.setPreferredSize(new Dimension(360, 220));
        ortaPanel.setBackground(Color.WHITE);
        ortaPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        anaPanel.add(ortaPanel, BorderLayout.CENTER);

        // Kategori
        JLabel lblKategori = new JLabel("Kategori:");
        lblKategori.setBounds(30, 25, 120, 25);
        ortaPanel.add(lblKategori);

        cmbKategori = new JComboBox<>(new String[]{"YEMEK", "TATLI", "İÇECEK"});
        cmbKategori.setBounds(160, 25, 160, 28);
        ortaPanel.add(cmbKategori);

        // Ürün
        JLabel lblUrun = new JLabel("Ürün:");
        lblUrun.setBounds(30, 70, 120, 25);
        ortaPanel.add(lblUrun);

        cmbUrun = new JComboBox<>();
        cmbUrun.setBounds(160, 70, 160, 28);
        ortaPanel.add(cmbUrun);

        // Adet
        JLabel lblAdet = new JLabel("Adet:");
        lblAdet.setBounds(30, 115, 120, 25);
        ortaPanel.add(lblAdet);

        txtAdet = new JTextField("1");
        txtAdet.setBounds(160, 115, 160, 28);
        ortaPanel.add(txtAdet);

        // Buton
        JButton btnEkle = new JButton("Siparişi Ekle");
        btnEkle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEkle.setBackground(new Color(66, 135, 245));
        btnEkle.setForeground(Color.WHITE);
        btnEkle.setBounds(110, 160, 140, 32);
        ortaPanel.add(btnEkle);

        // Kategori değişince ürünleri yenile
        cmbKategori.addActionListener(e -> urunListeGuncelle());
        urunListeGuncelle(); // ilk açılışta doldur

        // Sipariş ekle butonu
        btnEkle.addActionListener(e -> siparisEkle());
    }

    private void urunListeGuncelle() {
        String kategori = (String) cmbKategori.getSelectedItem();
        List<Urun> liste = urunServisi.kategoriyeGore(kategori); // ← SENDEKİ METOT

        cmbUrun.removeAllItems();
        for (Urun u : liste) {
            cmbUrun.addItem(u);
        }
    }

    private void siparisEkle() {
        try {
            Urun secili = (Urun) cmbUrun.getSelectedItem();
            int adet = Integer.parseInt(txtAdet.getText());

            siparisServisi.siparisEkle(masa, secili, adet);

            JOptionPane.showMessageDialog(this, "✔ Sipariş eklendi!");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Adet sayısı geçerli bir sayı olmalı!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }
}
