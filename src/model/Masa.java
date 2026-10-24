package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Masa {

    private int masaNo;
    private String durum; // BOS, DOLU, REZERVE
    private List<Siparis> siparisler = new ArrayList<>();

    public Masa(int masaNo, String durum) {
        this.masaNo = masaNo;
        this.durum = durum.toUpperCase();
    }

    public int getMasaNo() {
        return masaNo;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum.toUpperCase();
    }

    public List<Siparis> getSiparisler() {
        return siparisler;
    }

    public void siparisEkle(Urun urun, int adet) {

        if (durum.equals("BOS") || durum.equals("REZERVE"))
            durum = "DOLU";

        for (Siparis s : siparisler) {
            if (s.getUrun().getAd().equals(urun.getAd())) {
                s.setAdet(s.getAdet() + adet);
                return;
            }
        }

        siparisler.add(new Siparis(urun, adet));
    }

    public void siparisSil(Urun urun) {

        Iterator<Siparis> it = siparisler.iterator();

        while (it.hasNext()) {
            Siparis s = it.next();
            if (s.getUrun().getAd().equals(urun.getAd())) {

                if (s.getAdet() > 1)
                    s.setAdet(s.getAdet() - 1);
                else
                    it.remove();
                break;
            }
        }

        if (siparisler.isEmpty())
            durum = "BOS";
    }

    public void odemeAl() {
        siparisler.clear();
        durum = "BOS";
    }

    // TXT OKUMA İÇİN
    public void siparisEkleSessiz(Urun urun, int adet) {
        siparisler.add(new Siparis(urun, adet));
        durum = "DOLU";
    }
}
