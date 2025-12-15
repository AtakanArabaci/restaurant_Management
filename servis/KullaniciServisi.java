package servis;

import java.util.HashMap;
import java.util.Map;

import model.Kullanici;

public class KullaniciServisi {

    private static final KullaniciServisi INSTANCE = new KullaniciServisi();
    private Map<String, Kullanici> kullanicilar = new HashMap<>();

    private KullaniciServisi() {
        kullanicilar.put("admin", new Kullanici("admin", "1234", "admin"));

        Kullanici g = new Kullanici("utku", "1234", "garson");
        g.setAdSoyad("Utku Bera Özkan");
        kullanicilar.put("utku", g);
    }

    public static KullaniciServisi getInstance() {
        return INSTANCE;
    }

    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        Kullanici k = kullanicilar.get(kullaniciAdi);
        if (k != null && k.getSifre().equals(sifre)) {
            return k;
        }
        return null;
    }

    public Map<String, Kullanici> getGarsonlar() {
        Map<String, Kullanici> garsonlar = new HashMap<>();
        for (Kullanici k : kullanicilar.values()) {
            if ("garson".equals(k.getRol())) {
                garsonlar.put(k.getKullaniciAdi(), k);
            }
        }
        return garsonlar;
    }

    // kullanıcı adı var mı?
    public boolean kullaniciAdiVarMi(String kullaniciAdi) {
        return kullanicilar.containsKey(kullaniciAdi);
    }

    // kullanıcı sil
    public void kullaniciSil(String kullaniciAdi) {
        kullanicilar.remove(kullaniciAdi);
    }

    public void garsonEkle(String kullaniciAdi, String sifre) {
        Kullanici yeni = new Kullanici(kullaniciAdi, sifre, "garson");
        kullanicilar.put(kullaniciAdi, yeni);
    }

    public void garsonEkle(String adSoyad, String kullaniciAdi, String sifre) {
        Kullanici yeni = new Kullanici(kullaniciAdi, sifre, "garson");
        yeni.setAdSoyad(adSoyad);
        kullanicilar.put(kullaniciAdi, yeni);
    }

    public void garsonSil(String kullaniciAdi) {
        kullanicilar.remove(kullaniciAdi);
    }
}
