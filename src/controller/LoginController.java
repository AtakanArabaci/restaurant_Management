package controller;

import model.Kullanici;
import servis.KullaniciServisi;

public class LoginController {

    private final KullaniciServisi kullaniciServisi = KullaniciServisi.getInstance();

    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        return kullaniciServisi.girisYap(kullaniciAdi, sifre);
    }
}
