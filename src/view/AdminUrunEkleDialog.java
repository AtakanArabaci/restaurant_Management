package view;

import model.Urun;
import servis.UrunServisi;
import servis.StokServisi;

import javax.swing.*;
import java.awt.*;

public class AdminUrunEkleDialog extends JDialog {

    public AdminUrunEkleDialog(JFrame parent, String kategori) {
        super(parent, "Ürün Ekle", true);

        final String kat = kategori.toLowerCase().replace("ı", "i");

        setSize(350, 250);
        setResizable(false);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));

        JComboBox<String> ad = new JComboBox<>(urunAdlari(kat));
        JTextField fiyat = new JTextField();
        JButton ekle = new JButton("Ekle");

        add(new JLabel("Ürün Adı:")); add(ad);
        add(new JLabel("Fiyat:"));    add(fiyat);
        add(new JLabel());             add(ekle);

        ekle.addActionListener(e -> {

            if (ad.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Bu kategori için eklenebilir ürün bulunamadı!",
                        "Uyarı",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String secilenAd = ad.getSelectedItem().toString();

            if (UrunServisi.getInstance().getUrunByAd(secilenAd) != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Bu ürün zaten menüde mevcut!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int fiyatInt;
            try {
                fiyatInt = Integer.parseInt(
                        fiyat.getText().replace(",", "").replace(".", "").trim()
                );
                if (fiyatInt <= 0) throw new NumberFormatException();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Fiyat pozitif bir tam sayı olmalıdır!",
                        "Geçersiz Fiyat",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // 🔥 STOK BURADA TANIMLANIR
            int stok = kat.equals("icecek") ? 100 : 50;

            String resim = UrunServisi.getInstance()
                    .havuzdanResimBul(secilenAd, kat);

            Urun urun = new Urun(secilenAd, fiyatInt, kat, resim);
            urun.setStokLimiti(stok);

            UrunServisi.getInstance().urunEkle(urun);
            StokServisi.getInstance().stokArttir(secilenAd, stok);

            dispose();
        });

        setVisible(true);
    }

    private String[] urunAdlari(String kategori) {

        return switch (kategori) {

            case "yemek" -> new String[]{
                    "İçli Köfte","Maraş Tava","İskender",
                    "Mantı","Döner", "Çökertme","Kebap","Makarna",
                    "Lahmacun","Tavuk Şiş","Pizza","Hamburger"
            };

            case "icecek" -> new String[]{
                    "Şalgam","Meyve Suyu","Türk Kahvesi","Sprite",
                    "Sıcak Çikolata","Milkshake","Kola",
                    "Fanta","Su","Ayran","Çay","Soda"
            };

            case "tatli" -> new String[]{
                    "Fıstık Sarma","Maraş Dondurması","Sütlaç","Şobiyet",
                    "Katmer","Trileçe","İrmik Helvası","Pasta","Sufle",
                    "Profiterol","Künefe","Baklava"
            };

            default -> new String[]{};
        };
    }
}
