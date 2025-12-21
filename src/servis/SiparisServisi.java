package servis;

import model.Masa;
import model.Siparis;

import servis.MasaServisi;

public class SiparisServisi {

    // GARSON
    public void siparisEkle(Masa masa, model.Urun urun, int adet) {
        masa.siparisEkle(urun, adet);
    }

    // 🔥 ADMIN
 // 🔥 ADMIN
    public boolean siparisteMi(String urunAdi) {

        for (Masa m : MasaServisi.getInstance().getMasalar().values()) {
            for (Siparis s : m.getSiparisler()) {
                if (s.getUrun().getAd().equalsIgnoreCase(urunAdi)) {
                    return true;
                }
            }
        }
        return false;
    }
}
