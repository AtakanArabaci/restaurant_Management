package servis;

import model.Masa;
import model.Urun;

public class SiparisServisi {

    public void siparisEkle(Masa masa, Urun urun, int adet) {
        masa.siparisEkle(urun, adet);
    }
}
