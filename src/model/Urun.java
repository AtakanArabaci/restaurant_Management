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

    public String getAd() {
        return ad;
    }

    public double getFiyat() {
        return fiyat;
    }

    public String getKategori() {
        return kategori;
    }

    public String getIconPath() {
        return iconPath;
    }

    @Override
    public String toString() {
        return ad + " (" + fiyat + " TL)";
    }
}
