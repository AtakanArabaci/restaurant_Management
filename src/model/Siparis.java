package model;

public class Siparis {

    private int masaNo;
    private String musteriAdi;
    private String urunAdi;
    private int adet;
    private double birimFiyat;
    private String tarih; // gg.aa.yyyy

    public Siparis(int masaNo, String musteriAdi, String urunAdi,
                   int adet, double birimFiyat, String tarih) {
        this.masaNo = masaNo;
        this.musteriAdi = musteriAdi;
        this.urunAdi = urunAdi;
        this.adet = adet;
        this.birimFiyat = birimFiyat;
        this.tarih = tarih;
    }

    public int getMasaNo() {
        return masaNo;
    }

    public void setMasaNo(int masaNo) {
        this.masaNo = masaNo;
    }

    public String getMusteriAdi() {
        return musteriAdi;
    }

    public void setMusteriAdi(String musteriAdi) {
        this.musteriAdi = musteriAdi;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public double getBirimFiyat() {
        return birimFiyat;
    }

    public void setBirimFiyat(double birimFiyat) {
        this.birimFiyat = birimFiyat;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public double getToplamTutar() {
        return adet * birimFiyat;
    }
}
