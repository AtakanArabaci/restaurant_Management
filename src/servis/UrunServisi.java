package servis;

import model.Urun;
import java.util.ArrayList;
import java.util.List;

public class UrunServisi {

    private List<Urun> urunler = new ArrayList<>();

    public UrunServisi() {

        // =======================
        // YEMEKLER (stok 50)
        // =======================
        urunler.add(create("Kebap", 180, "yemek", "icons/kebap.jpg", 50));
        urunler.add(create("Pizza", 170, "yemek", "icons/pizza.jpg", 50));
        urunler.add(create("Hamburger", 165, "yemek", "icons/hamburger.jpg", 50));
        urunler.add(create("Tavuk Şiş", 145, "yemek", "icons/tavuksis.jpg", 50));
        urunler.add(create("Lahmacun", 135, "yemek", "icons/lahmacun.jpg", 50));
        urunler.add(create("Makarna", 150, "yemek", "icons/makarna.jpg", 50));

        // =======================
        // İÇECEKLER (stok 100)
        // =======================
        urunler.add(create("Kola", 40, "icecek", "icons/kola.jpg", 100));
        urunler.add(create("Ayran", 15, "icecek", "icons/ayran.jpg", 100));
        urunler.add(create("Fanta", 30, "icecek", "icons/fanta.jpg", 100));
        urunler.add(create("Soda", 18, "icecek", "icons/soda.jpg", 100));
        urunler.add(create("Çay", 13, "icecek", "icons/çay.jpg", 100));
        urunler.add(create("Su", 5, "icecek", "icons/su.jpg", 100));

        // =======================
        // TATLILAR (stok 50)
        // =======================
        urunler.add(create("Pasta", 135, "tatli", "icons/pasta.jpg", 50));
        urunler.add(create("Sufle", 99, "tatli", "icons/sufle.jpg", 50));
        urunler.add(create("Cheesecake", 146, "tatli", "icons/cheesecake.jpg", 50));
        urunler.add(create("Profiterol", 38, "tatli", "icons/profiterol.jpg", 50));
        urunler.add(create("Künefe", 100, "tatli", "icons/künefe.jpg", 50));
        urunler.add(create("Baklava", 144, "tatli", "icons/baklava.jpg", 50));
    }

    private Urun create(String ad, double fiyat, String kategori, String resim, int limit) {
        Urun u = new Urun(ad, fiyat, kategori, resim);
        u.setStokLimiti(limit);
        return u;
    }

    public List<Urun> getUrunlerByKategori(String kategori) {
        List<Urun> list = new ArrayList<>();
        for (Urun u : urunler)
            if (u.getKategori().equalsIgnoreCase(kategori))
                list.add(u);
        return list;
    }

    public Urun getUrunByAd(String ad) {
        for (Urun u : urunler)
            if (u.getAd().equalsIgnoreCase(ad))
                return u;
        return null;
    }
}
