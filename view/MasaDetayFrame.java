package view;

import model.Masa;
import model.Urun;
import model.Siparis;
import servis.UrunServisi;
import view_components.ProductCard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MasaDetayFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JPanel kategoriPanel;
    private JPanel urunPanel;

    private Masa masa;
    private MasaDetayModu mod;

    private UrunServisi urunServisi = UrunServisi.getInstance();

    public MasaDetayFrame(Masa masa, MasaDetayModu mod) {
        this.masa = masa;
        this.mod = mod;

        setTitle("Masa " + masa.getMasaNo());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (mod == MasaDetayModu.SORGU) {
            sorguEkrani();
        } else {
            siparisEkrani();
        }

        setVisible(true);
    }

    // =======================
    // SİPARİŞ MODU
    // =======================
    private void siparisEkrani() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        kategoriPanel = kategoriEkrani();
        urunPanel = new JPanel();

        mainPanel.add(kategoriPanel, "KATEGORI");
        mainPanel.add(urunPanel, "URUNLER");

        add(mainPanel);
    }

    private JPanel kategoriEkrani() {
        JPanel panel = new JPanel(null);

        JLabel lblTitle = new JLabel("Kategoriler");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(20, 20, 300, 30);
        panel.add(lblTitle);

        panel.add(kategoriButonu("yemek", "YEMEK", 60, 100));
        panel.add(kategoriButonu("tatli", "TATLI", 230, 100));
        panel.add(kategoriButonu("icecek", "İÇECEK", 400, 100));

        return panel;
    }

    private JButton kategoriButonu(String kategoriKey, String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 150, 150);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        btn.addActionListener(e -> urunleriGoster(kategoriKey));

        return btn;
    }

    private void urunleriGoster(String kategori) {

        urunPanel = new JPanel();
        urunPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        urunPanel.setBackground(Color.WHITE);

        List<Urun> urunler = urunServisi.getUrunlerByKategori(kategori);

        for (Urun u : urunler) {
            urunPanel.add(new ProductCard(u, masa));
        }

        mainPanel.add(urunPanel, "URUNLER");
        cardLayout.show(mainPanel, "URUNLER");
    }

    // =======================
    // SORGU MODU (AKTİF SİPARİŞ)
    // =======================
    private void sorguEkrani() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel baslik = new JLabel(
                "Masa " + masa.getMasaNo() + " - Durum: " + masa.getDurum()
        );
        baslik.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(baslik, BorderLayout.NORTH);

        if (masa.getSiparisler().isEmpty()) {
            panel.add(
                    new JLabel("Bu masada aktif sipariş yok.", SwingConstants.CENTER),
                    BorderLayout.CENTER
            );
            add(panel);
            return;
        }

        String[] kolonlar = {"Ürün", "Adet", "Birim Fiyat", "Toplam"};
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0);

        double toplam = 0;

        for (Siparis s : masa.getSiparisler()) {
            model.addRow(new Object[]{
                    s.getUrun().getAd(),
                    s.getAdet(),
                    s.getUrun().getFiyat(),
                    s.getToplam()
            });
            toplam += s.getToplam();
        }

        JTable table = new JTable(model);
        table.setRowHeight(26);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JLabel toplamLbl = new JLabel("Toplam Tutar: " + toplam + " TL");
        toplamLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(toplamLbl, BorderLayout.SOUTH);

        add(panel);
    }
}
