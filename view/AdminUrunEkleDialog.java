package view;

import model.Urun;
import servis.UrunServisi;

import javax.swing.*;
import java.awt.*;

public class AdminUrunEkleDialog extends JDialog {

    public AdminUrunEkleDialog(JFrame parent, String kategori) {
        super(parent, "Ürün Ekle", true);

        // ✅ kategori'yi final değişkene al (lambda için kesin çözüm)
        final String kat = kategori.toLowerCase().replace("ı", "i");

        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));

        JComboBox<String> ad = new JComboBox<>(urunAdlari(kat));
        JTextField fiyat = new JTextField();

        JButton ekle = new JButton("Ekle");

        add(new JLabel("Ürün Adı:")); add(ad);
        add(new JLabel("Fiyat:")); add(fiyat);
        add(new JLabel()); add(ekle);

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

            // ❌ Aynı ürün kontrolü
            if (UrunServisi.getInstance().getUrunByAd(secilenAd) != null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Bu ürün zaten menüde mevcut!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            double f;
            try {
                f = Double.parseDouble(fiyat.getText());
                if (f <= 0) throw new Exception();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Fiyat pozitif bir sayı olmalıdır!",
                        "Geçersiz Fiyat",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int stok = kat.equals("icecek") ? 100 : 50;

            // ✅ burada artık kat kullanıyoruz
            Urun u = new Urun(secilenAd, f, kat, "none");
            u.setStokLimiti(stok);

            UrunServisi.getInstance().urunEkle(u);
            dispose();
        });

        setVisible(true);
    }

    private String[] urunAdlari(String kategori) {

        return switch (kategori) {

            case "yemek" -> new String[]{
                    "İçli Köfte","Maraş Tava","Iskender","Eli Böğründe",
                    "Hamsi","Mantı","Döner","Tantuni",
                    "Çökertme","Beyti"
            };

            case "icecek" -> new String[]{
                    "Şalgam","Meyve Suyu","Türk Kahvesi","Sprite",
                    "Sıcak Çikolata","Soğuk Çay","Limonata",
                    "Bitki Çayı","Milkshake"
            };

            case "tatli" -> new String[]{
                    "Fıstık Sarma","Maraş Dondurması","Sütlaç","Şobiyet",
                    "Katmer","Magnolia ","Trileçe",
                    "İrmik Helvası","Brownie","Tiramusu"
            };

            default -> new String[]{};
        };
    }
}
