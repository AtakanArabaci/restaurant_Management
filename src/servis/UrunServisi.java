package servis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Urun;

public class UrunServisi {

    // Kategori -> ürün listesi
    private Map<String, List<Urun>> kategoriHaritasi = new HashMap<>();

    public UrunServisi() {
        // Başlangıç ürünleri
        kategoriHaritasi.put("YEMEK", new ArrayList<>());
        kategoriHaritasi.put("TATLI", new ArrayList<>());
        kategoriHaritasi.put("ICECEK", new ArrayList<>());

        // === YEMEKLER ===
        kategoriHaritasi.get("YEMEK").add(
                new Urun("Kebap", 180, "YEMEK", "icons/kebap.png"));
        kategoriHaritasi.get("YEMEK").add(
                new Urun("Lahmacun", 60, "YEMEK", "icons/lahmacun.png"));
        kategoriHaritasi.get("YEMEK").add(
                new Urun("Hamburger", 95, "YEMEK", "icons/hamburger.png"));

        // === TATLILAR ===
        kategoriHaritasi.get("TATLI").add(
                new Urun("Baklava", 80, "TATLI", "icons/baklava.png"));
        kategoriHaritasi.get("TATLI").add(
                new Urun("Kunefe", 90, "TATLI", "icons/kunefe.png"));
        kategoriHaritasi.get("TATLI").add(
                new Urun("Sutlac", 50, "TATLI", "icons/sutlac.png"));

        // === İÇECEKLER ===
        kategoriHaritasi.get("ICECEK").add(
                new Urun("Ayran", 20, "ICECEK", "icons/ayran.png"));
        kategoriHaritasi.get("ICECEK").add(
                new Urun("Kola", 35, "ICECEK", "icons/kola.png"));
        kategoriHaritasi.get("ICECEK").add(
                new Urun("Cay", 10, "ICECEK", "icons/cay.png"));
    }

    // Seçilen kategoriye göre ürünleri döndür
    public List<Urun> kategoriGetir(String kategori) {
        if (kategori == null) return new ArrayList<>();
        kategori = kategori.toUpperCase();
        List<Urun> list = kategoriHaritasi.get(kategori);
        if (list == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(list); // kopya veriyoruz
    }

    // Menünün içine yeni bir ürün ekle
    public boolean urunEkle(String kategori, String ad, double fiyat, String resimYolu) {
        if (kategori == null || ad == null) return false;
        kategori = kategori.toUpperCase();

        List<Urun> list = kategoriHaritasi.get(kategori);
        if (list == null) {
            list = new ArrayList<>();
            kategoriHaritasi.put(kategori, list);
        }

        // Aynı isimde ürün varsa ekleme
        for (Urun u : list) {
            if (u.getAd().equalsIgnoreCase(ad.trim())) {
                return false; // zaten var
            }
        }

        list.add(new Urun(ad.trim(), fiyat, kategori, resimYolu));
        return true;
    }

    // Menünün içinden ürün sil
    public boolean urunSil(String kategori, String ad) {
        if (kategori == null || ad == null) return false;
        kategori = kategori.toUpperCase();

        List<Urun> list = kategoriHaritasi.get(kategori);
        if (list == null) return false;

        Urun silinecek = null;
        for (Urun u : list) {
            if (u.getAd().equalsIgnoreCase(ad.trim())) {
                silinecek = u;
                break;
            }
        }

        if (silinecek != null) {
            list.remove(silinecek);
            return true;
        }
        return false; // bulunamadı
    }
}
