package model;

public class Urun {

    private String ad;
    private double fiyat;
    private String kategori;
    private String iconPath;

    public Urun(String ad, double fiyat, String kategori, String iconPath) {
        this.ad = ad;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.iconPath = iconPath;
    }

    // GETTER – SETTER

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return ad + " - " + fiyat + " TL";
    }
}
