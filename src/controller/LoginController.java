package controller;

import model.Kullanici;
import servis.KullaniciServisi;

public class LoginController {

    private final KullaniciServisi kullaniciServisi;

    public LoginController() {
        // İçinde admin + utku hazır geliyor
        this.kullaniciServisi = new KullaniciServisi();
    }

    // LoginFrame burayı çağırıyor
    public String girisYap(String kullaniciAdi, String sifre) {
        Kullanici k = kullaniciServisi.girisYap(kullaniciAdi, sifre);

        if (k == null) {
            return "hata";          // yanlış kullanıcı / şifre
        }

        // "admin" veya "garson" döner
        return k.getRol();
    }

    // AdminController vs. gerekirse servise buradan ulaşabilir
    public KullaniciServisi getKullaniciServisi() {
        return kullaniciServisi;
    }
}
