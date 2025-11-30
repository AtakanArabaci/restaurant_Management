package servis;

import java.util.HashMap;
import java.util.Map;

import model.Kullanici;

public class KullaniciServisi {

    private Map<String, Kullanici> kullanicilar = new HashMap<>();

    public KullaniciServisi() {
        kullanicilar.put("admin", new Kullanici("admin", "1234", "admin"));
        kullanicilar.put("utku", new Kullanici("utku", "1234", "garson"));
    }

    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        Kullanici k = kullanicilar.get(kullaniciAdi);
        if (k != null && k.getSifre().equals(sifre)) {
            return k;
        }
        return null;
    }
}
