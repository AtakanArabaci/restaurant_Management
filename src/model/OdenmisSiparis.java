package model;

import java.time.LocalDateTime;

public class OdenmisSiparis {

    private int masaNo;
    private String urunAdi;
    private int adet;
    private double toplam;
    private LocalDateTime zaman;

    public OdenmisSiparis(int masaNo, String urunAdi, int adet, double toplam, LocalDateTime zaman) {
        this.masaNo = masaNo;
        this.urunAdi = urunAdi;
        this.adet = adet;
        this.toplam = toplam;
        this.zaman = zaman;
    }

    public int getMasaNo() {
        return masaNo;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public int getAdet() {
        return adet;
    }

    public double getToplam() {
        return toplam;
    }

    public LocalDateTime getZaman() {
        return zaman;
    }
}