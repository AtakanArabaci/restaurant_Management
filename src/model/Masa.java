package model;

import java.util.ArrayList;
import java.util.List;

public class Masa {

    private int masaNo;
    private String durum; // BOŞ, DOLU, REZERVE
    private List<Siparis> siparisler = new ArrayList<>();

    public Masa(int masaNo, String durum) {
        this.masaNo = masaNo;
        this.durum = durum;
    }

    public int getMasaNo() {
        return masaNo;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public List<Siparis> getSiparisler() {
        return siparisler;
    }

    public void siparisEkle(Urun urun, int adet) {
        siparisler.add(new Siparis(urun, adet));
        durum = "DOLU";
    }
}
