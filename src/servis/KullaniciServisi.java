package servis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Kullanici;

public class KullaniciServisi {

    // TÜM UYGULAMA İÇİN ORTAK KULLANICI LİSTESİ (STATIC!)
    private static Map<String, Kullanici> kullanicilar = new HashMap<>();

    public KullaniciServisi() {
        // admin ve utku sadece BİR KEZ eklensin
        if (kullanicilar.isEmpty()) {
            kullanicilar.put("admin", new Kullanici("admin", "1234", "admin"));
            kullanicilar.put("utku",  new Kullanici("utku",  "1234", "garson"));
        }
    }

    // ------------------ GİRİŞ ------------------
    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        if (kullaniciAdi == null || sifre == null) return null;

        Kullanici k = kullanicilar.get(kullaniciAdi);
        if (k != null && sifre.equals(k.getSifre())) {
            return k;
        }
        return null;
    }

    // ------------------ GARSON LİSTESİ ------------------
    public List<String> garsonIsimleriniGetir() {
        List<String> garsonlar = new ArrayList<>();
        for (Kullanici k : kullanicilar.values()) {
            if ("garson".equals(k.getRol())) {
                garsonlar.add(k.getKullaniciAdi());
            }
        }
        return garsonlar;
    }

    // ------------------ GARSON EKLE ------------------
    public boolean garsonEkle(String kullaniciAdi, String sifre) {
        if (kullaniciAdi == null || sifre == null) return false;

        // Aynı isimde kullanıcı varsa ekleme
        if (kullanicilar.containsKey(kullaniciAdi)) {
            return false;
        }

        Kullanici yeniGarson = new Kullanici(kullaniciAdi, sifre, "garson");
        kullanicilar.put(kullaniciAdi, yeniGarson);
        return true;
    }

    // ------------------ GARSON SİL ------------------
    public boolean garsonSil(String kullaniciAdi) {
        Kullanici k = kullanicilar.get(kullaniciAdi);
        if (k == null) return false;

        // admin gibi garson olmayanları silme
        if (!"garson".equals(k.getRol())) {
            return false;
        }

        kullanicilar.remove(kullaniciAdi);
        return true;
    }
}
