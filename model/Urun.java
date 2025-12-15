package model;

public class Urun {

    private String ad;
    private double fiyat;
    private String kategori;
    private String resimYolu;

    // 🔥 STOK LİMİTİ EKLENDİ
    private int stokLimiti;

    public Urun(String ad, double fiyat, String kategori, String resimYolu) {
        this.ad = ad;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.resimYolu = resimYolu;
    }

    public String getAd() { return ad; } 
    public double getFiyat() { return fiyat; }
    public String getKategori() { return kategori; }
    public String getResimYolu() { return resimYolu; }

    public void setAd(String ad) { this.ad = ad; }
    public void setFiyat(double fiyat) { this.fiyat = fiyat; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public void setResimYolu(String resimYolu) { this.resimYolu = resimYolu; }

    // 🔥 STOK GET/SET
    public int getStokLimiti() { return stokLimiti; }
    public void setStokLimiti(int stokLimiti) { this.stokLimiti = stokLimiti; }

    @Override
    public String toString() {
        return ad + " - " + fiyat + " TL";
    }
}
