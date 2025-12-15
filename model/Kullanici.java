package model;

public class Kullanici {

    private String kullaniciAdi;
    private String sifre;
    private String rol;
    private String adSoyad;

    // 🔥 MEVCUT CONSTRUCTOR
    public Kullanici(String kullaniciAdi, String sifre, String rol) {
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.rol = rol;
        this.adSoyad = kullaniciAdi; // ✅ DEFAULT
    }

    // 🔥 YENİ CONSTRUCTOR (tercihli)
    public Kullanici(String adSoyad, String kullaniciAdi, String sifre, String rol) {
        this.adSoyad = adSoyad;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.rol = rol;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getRol() {
        return rol;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }
}
