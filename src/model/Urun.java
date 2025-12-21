package model;

public class Urun {

    private String ad;
    private int fiyat;              // 🔥 int oldu
    private String kategori;
    private String resimYolu;

    // 🔥 STOK LİMİTİ
    private int stokLimiti;

    public Urun(String ad, int fiyat, String kategori, String resimYolu) {
        this.ad = ad;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.resimYolu = resimYolu;
    }

    public String getAd() { return ad; }
    public int getFiyat() { return fiyat; }
    public String getKategori() { return kategori; }
    public String getResimYolu() { return resimYolu; }

    public void setAd(String ad) { this.ad = ad; }
    public void setFiyat(int fiyat) { this.fiyat = fiyat; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public void setResimYolu(String resimYolu) { this.resimYolu = resimYolu; }

    // 🔥 STOK
    public int getStokLimiti() { return stokLimiti; }
    public void setStokLimiti(int stokLimiti) { this.stokLimiti = stokLimiti; }

    @Override
    public String toString() {
        return ad + " - " + fiyat + " TL";
    }
}
