package servis;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import model.Kullanici;

public class KullaniciServisi {

    private Map<String, Kullanici> kullanicilar = new HashMap<>();

    public KullaniciServisi() {
        // Sadece admin ve tek bir garson: utku
        kullanicilar.put("admin", new Kullanici("admin", "1234", "admin"));
        kullanicilar.put("utku",  new Kullanici("utku",  "1234", "garson"));
    }

    // Giriş yapma
    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        Kullanici k = kullanicilar.get(kullaniciAdi);

        if (k != null && k.getSifre().equals(sifre)) {
            return k;
        }
        return null;
    }

    // GARSON LİSTESİNİ GETİR (Sadece Utku)
    public List<String> garsonIsimleriniGetir() {
        List<String> garsonlar = new ArrayList<>();

        for (Kullanici k : kullanicilar.values()) {
            if ("garson".equals(k.getRol())) {
                garsonlar.add(k.getKullaniciAdi());
            }
        }

        return garsonlar;
    }
}
