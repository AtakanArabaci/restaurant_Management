package view;

import controller.GarsonController;
import model.Masa;
import model.Urun;
import servis.UrunServisi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MasaDetayFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JPanel kategoriPanel;
    private JPanel urunPanel;
    private Masa masa;

    private UrunServisi urunServisi = new UrunServisi();

    public MasaDetayFrame(Masa masa) {
        this.masa = masa;

        setTitle("Masa " + masa.getMasaNo());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        kategoriPanel = kategoriEkrani();
        urunPanel = new JPanel();

        mainPanel.add(kategoriPanel, "KATEGORI");
        mainPanel.add(urunPanel, "URUNLER");

        add(mainPanel);

        setVisible(true);
    }

    // ------------------ KATEGORİ EKRANI ------------------
    private JPanel kategoriEkrani() {
        JPanel panel = new JPanel(null);

        JLabel lblTitle = new JLabel("Kategoriler");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(20, 20, 300, 30);
        panel.add(lblTitle);

        panel.add(kategoriButonu("YEMEK", 60, 100));
        panel.add(kategoriButonu("TATLI", 230, 100));
        panel.add(kategoriButonu("ICECEK", 400, 100));

        return panel;
    }

    private JButton kategoriButonu(String kategori, int x, int y) {
        JButton btn = new JButton(kategori);
        btn.setBounds(x, y, 150, 150);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        btn.addActionListener(e -> urunleriGoster(kategori));

        return btn;
    }

    // ------------------ ÜRÜN EKRANI ------------------
    private void urunleriGoster(String kategori) {

        urunPanel = new JPanel();
        urunPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        urunPanel.setBackground(Color.WHITE);

        List<Urun> urunler = urunServisi.kategoriGetir(kategori);

        for (Urun u : urunler) {
            urunPanel.add(new ProductCard(u, masa));
        }

        mainPanel.add(urunPanel, "URUNLER");
        cardLayout.show(mainPanel, "URUNLER");
    }
}
