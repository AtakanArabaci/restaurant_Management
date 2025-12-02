package servis;

import java.util.ArrayList;
import java.util.List;

import model.Siparis;

public class SiparisServisi {

    private List<Siparis> siparisler = new ArrayList<>();

    public SiparisServisi() {
        // ÖRNEK SİPARİŞLER
        siparisler.add(new Siparis(2, "atakan", "Lahmacun", 2, 60, "12.08.2025"));
        siparisler.add(new Siparis(2, "atakan", "Ayran",     2, 20, "12.08.2025"));
        siparisler.add(new Siparis(2, "atakan", "Sufle",     3, 90, "12.08.2025"));
    }

    // MÜŞTERİ ADINA GÖRE SİPARİŞLERİ GETİR
    public List<Siparis> musteriyeGoreSiparisGetir(String musteriAdi) {
        List<Siparis> sonuc = new ArrayList<>();

        if (musteriAdi == null) {
            return sonuc;
        }

        String aranan = musteriAdi.trim().toLowerCase();

        for (Siparis s : siparisler) {
            if (s.getMusteriAdi() != null &&
                    s.getMusteriAdi().toLowerCase().equals(aranan)) {
                sonuc.add(s);
            }
        }

        return sonuc;
    }

    // TARİHE GÖRE SİPARİŞLERİ GETİR
    public List<Siparis> tariheGoreSiparisGetir(String tarih) {
        List<Siparis> sonuc = new ArrayList<>();

        if (tarih == null) {
            return sonuc;
        }

        String aranan = tarih.trim();

        for (Siparis s : siparisler) {
            if (s.getTarih() != null && s.getTarih().equals(aranan)) {
                sonuc.add(s);
            }
        }

        return sonuc;
    }

    // TÜM SİPARİŞLERİ GETİR (Gün sonu raporu için)
    public List<Siparis> tumSiparisleriGetir() {
        return new ArrayList<>(siparisler);
    }
}
