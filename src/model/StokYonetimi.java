package model;

import java.util.HashMap;
import java.util.Map;

public class StokYonetimi {

    // kategoriye göre max limit (her ürün için)
    private static final Map<String, Integer> KATEGORI_LIMIT = new HashMap<>();

    // ürün adına göre tüm masalardaki toplam adet
    private static final Map<String, Integer> URUN_TOPLAM = new HashMap<>();

    static {
        // her ürün için kategori bazlı limit
        KATEGORI_LIMIT.put("yemek", 50);   // her yemek max 50
        KATEGORI_LIMIT.put("tatli", 50);   // her tatlı max 50
        KATEGORI_LIMIT.put("icecek", 100); // her içecek max 100
    }

    // Sipariş EKLERKEN çağıracağız
    public static boolean stoktanDus(Urun urun, int adet) {
        String ad = urun.getAd();
        String kategori = urun.getKategori();

        int limit = KATEGORI_LIMIT.getOrDefault(kategori, Integer.MAX_VALUE);
        int simdiki = URUN_TOPLAM.getOrDefault(ad, 0);

        if (simdiki + adet > limit) {
            return false; // limit aşılır
        }

        URUN_TOPLAM.put(ad, simdiki + adet);
        return true;
    }

    // Sipariş SİLERKEN ( - butonu ) çağıracağız
    public static void stogaIade(Urun urun, int adet) {
        String ad = urun.getAd();
        int simdiki = URUN_TOPLAM.getOrDefault(ad, 0);
        int yeni = simdiki - adet;
        if (yeni < 0) yeni = 0;
        URUN_TOPLAM.put(ad, yeni);
    }

    // (İstersen UI'de göstermek için kullanabilirsin)
    public static int kalanStok(Urun urun) {
        String ad = urun.getAd();
        String kategori = urun.getKategori();
        int limit = KATEGORI_LIMIT.getOrDefault(kategori, Integer.MAX_VALUE);
        int simdiki = URUN_TOPLAM.getOrDefault(ad, 0);
        return limit - simdiki;
    }
}