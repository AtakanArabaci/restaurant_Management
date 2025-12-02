package servis;

import model.Urun;
import java.util.ArrayList;
import java.util.List;

public class UrunServisi {

    private List<Urun> urunler = new ArrayList<>();

    public UrunServisi() {

        // =======================
        //  YEMEKLER (6 ürün)
        // =======================
        urunler.add(new Urun("Kebap", 180, "yemek", "icons/kebap.jpg"));
        urunler.add(new Urun("Pizza", 170, "yemek", "icons/pizza.jpg"));
        urunler.add(new Urun("Hamburger", 165, "yemek", "icons/hamburger.jpg"));
        urunler.add(new Urun("Tavuk Şiş", 145, "yemek", "icons/tavuksis.jpg"));
        urunler.add(new Urun("Lahmacun", 135, "yemek", "icons/lahmacun.jpg"));
        urunler.add(new Urun("Makarna", 150, "yemek", "icons/makarna.jpg"));

        // =======================
        //  İÇECEKLER (6 ürün)
        // =======================
        urunler.add(new Urun("Kola", 40, "icecek", "icons/kola.jpg"));
        urunler.add(new Urun("Ayran", 15, "icecek", "icons/ayran.jpg"));
        urunler.add(new Urun("Fanta", 30, "icecek", "icons/fanta.jpg"));
        urunler.add(new Urun("Soda", 18, "icecek", "icons/soda.jpg"));
        urunler.add(new Urun("Çay", 13, "icecek", "icons/çay.jpg"));
        urunler.add(new Urun("Su", 5, "icecek", "icons/su.jpg"));

        // =======================
        //  TATLILAR (6 ürün)
        // =======================
        urunler.add(new Urun("Pasta", 135, "tatli", "icons/pasta.jpg"));
        urunler.add(new Urun("Sufle", 99, "tatli", "icons/sufle.jpg"));
        urunler.add(new Urun("Cheesecake", 146, "tatli", "icons/cheesecake.jpg"));
        urunler.add(new Urun("Profiterol", 38, "tatli", "icons/profiterol.jpg"));
        urunler.add(new Urun("Künefe", 100, "tatli", "icons/künefe.jpg"));
        urunler.add(new Urun("Baklava", 144, "tatli", "icons/baklava.jpg"));
    }

    // ✔ KATEGORİYE GÖRE ÜRÜN GETİR
    public List<Urun> getUrunlerByKategori(String kategori) {
        List<Urun> list = new ArrayList<>();
        for (Urun u : urunler) {
            if (u.getKategori().equalsIgnoreCase(kategori)) {
                list.add(u);
            }
        }
        return list;
    }

    // ============================================
    // ✔ ÜRÜN ADINA GÖRE ÜRÜN GETİR  (EKLENEN METHOD)
    // ============================================
    public Urun getUrunByAd(String ad) {
        for (Urun u : urunler) {
            if (u.getAd().equalsIgnoreCase(ad)) {
                return u;
            }
        }
        return null; // bulunamazsa null döner
    }
}
