package controller;

import model.Kullanici;

public class LoginController {

    // Şimdilik sabit kullanıcılar
    private Kullanici admin = new Kullanici("admin", "1234", "admin");
    private Kullanici garson = new Kullanici("utku", "1234", "garson");

    public String girisYap(String kullaniciAdi, String sifre) {

        if (kullaniciAdi.equals(admin.getKullaniciAdi()) &&
            sifre.equals(admin.getSifre())) {

            return "admin";
        }

        if (kullaniciAdi.equals(garson.getKullaniciAdi()) &&
            sifre.equals(garson.getSifre())) {

            return "garson";
        }

        return "hata"; // yanlış giriş
    }
}
