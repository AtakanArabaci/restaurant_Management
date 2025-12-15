package servis;

import java.util.HashMap;
import java.util.Map;

import model.Masa;

public class MasaServisi {

    // 🔒 SINGLETON
    private static final MasaServisi INSTANCE = new MasaServisi();

    private Map<Integer, Masa> masalar = new HashMap<>();

    private MasaServisi() {

        // MASALARI OLUŞTUR
        masalar.put(1, new Masa(1, "boş"));
        masalar.put(2, new Masa(2, "dolu"));
        masalar.put(3, new Masa(3, "rezerve"));
        masalar.put(4, new Masa(4, "boş"));
        masalar.put(5, new Masa(5, "dolu"));
        masalar.put(6, new Masa(6, "boş"));
        masalar.put(7, new Masa(7, "rezerve"));
        masalar.put(8, new Masa(8, "boş"));
        masalar.put(9, new Masa(9, "boş"));

        // 🔥 ÜRÜNLERE ERİŞMEK İÇİN TEK INSTANCE
        UrunServisi urunServisi = UrunServisi.getInstance();

        // MASA 2 — BAŞLANGIÇ
        Masa m2 = masalar.get(2);
        m2.siparisEkle(urunServisi.getUrunByAd("Kebap"), 2);
        m2.siparisEkle(urunServisi.getUrunByAd("Ayran"), 2);
        m2.siparisEkle(urunServisi.getUrunByAd("Künefe"), 1);
        m2.siparisEkle(urunServisi.getUrunByAd("Sufle"), 1);
        m2.siparisEkle(urunServisi.getUrunByAd("Su"), 2);

        // MASA 5
        Masa m5 = masalar.get(5);
        m5.siparisEkle(urunServisi.getUrunByAd("Pizza"), 1);
        m5.siparisEkle(urunServisi.getUrunByAd("Kola"), 2);
    }

    public static MasaServisi getInstance() {
        return INSTANCE;
    }

    public Map<Integer, Masa> getMasalar() {
        return masalar;
    }

    public Masa getMasa(int numara) {
        return masalar.get(numara);
    }

    public void masaEkle() {
        int yeniNumara = masalar.size() + 1;
        masalar.put(yeniNumara, new Masa(yeniNumara, "boş"));
    }

    public void masaSil(int numara) {
        masalar.remove(numara);
    }
}
