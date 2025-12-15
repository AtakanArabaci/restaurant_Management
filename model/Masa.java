package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Masa {

    private int masaNo;
    private String durum;
    private List<Siparis> siparisler = new ArrayList<>();

    // 🔥 TÜM MASALAR İÇİN GLOBAL STOK SAYACI
    private static Map<String, Integer> globalStokSayaci = new HashMap<>();

    // ✅ YENİ: ÖDEMESİ ALINMIŞ TÜM SİPARİŞLER (GLOBAL)
    private static List<Siparis> tamamlananSiparisler = new ArrayList<>();

    public Masa(int masaNo, String durum) {
        this.masaNo = masaNo;
        this.durum = durum;
    }

    public int getMasaNo() { return masaNo; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public List<Siparis> getSiparisler() { return siparisler; }

    // ✅ YENİ: Admin paneli için
    public static List<Siparis> getTamamlananSiparisler() {
        return tamamlananSiparisler;
    }

    // ===========================
    // GLOBAL TOPLAM (ürün bazlı)
    // ===========================
    public static int getGlobalAdet(Urun urun) {
        return globalStokSayaci.getOrDefault(urun.getAd(), 0);
    }

    // ===========================
    // ÜRÜN BAZLI STOK LIMIT
    // ===========================
    public boolean urunLimitUygun(Urun urun, int eklenecekAdet) {

        int mevcutGlobal = getGlobalAdet(urun);
        int yeniGlobal = mevcutGlobal + eklenecekAdet;

        int max = switch (urun.getKategori().toLowerCase()) {
            case "yemek", "tatli" -> 50;
            case "icecek" -> 100;
            default -> Integer.MAX_VALUE;
        };

        return yeniGlobal <= max;
    }

    // ===========================
    // SİPARİŞ EKLE
    // ===========================
    public void siparisEkle(Urun urun, int adet) {

        if (!urunLimitUygun(urun, adet)) return;

        if (durum.equalsIgnoreCase("REZERVE")) {
            durum = "DOLU";
        }

        for (Siparis s : siparisler) {
            if (s.getUrun().getAd().equals(urun.getAd())) {

                s.setAdet(s.getAdet() + adet);

                globalStokSayaci.put(
                        urun.getAd(),
                        getGlobalAdet(urun) + adet
                );

                durum = "DOLU";
                return;
            }
        }

        siparisler.add(new Siparis(urun, adet));
        globalStokSayaci.put(
                urun.getAd(),
                getGlobalAdet(urun) + adet
        );

        durum = "DOLU";
    }

    // ===========================
    // SİPARİŞ SİL
    // ===========================
    public void siparisSil(Urun urun) {

        Iterator<Siparis> it = siparisler.iterator();

        while (it.hasNext()) {
            Siparis s = it.next();

            if (s.getUrun().getAd().equals(urun.getAd())) {

                int yeniGlobal = Math.max(0, getGlobalAdet(urun) - 1);
                if (yeniGlobal == 0)
                    globalStokSayaci.remove(urun.getAd());
                else
                    globalStokSayaci.put(urun.getAd(), yeniGlobal);

                if (s.getAdet() > 1)
                    s.setAdet(s.getAdet() - 1);
                else
                    it.remove();

                break;
            }
        }

        if (siparisler.isEmpty()) {
            durum = "BOŞ";
        }
    }

    // ===========================
    // 🔥 ÖDEME AL → GEÇMİŞE EKLE
    // ===========================
    public void odemeAl() {

        // ✅ YENİ: ödemesi alınan siparişleri kaydet
        for (Siparis s : siparisler) {
            tamamlananSiparisler.add(s);
        }

        // GLOBAL STOKTAN DÜŞ
        for (Siparis s : siparisler) {
            Urun u = s.getUrun();
            int yeniGlobal = Math.max(0, getGlobalAdet(u) - s.getAdet());

            if (yeniGlobal == 0)
                globalStokSayaci.remove(u.getAd());
            else
                globalStokSayaci.put(u.getAd(), yeniGlobal);
        }

        siparisler.clear();
        durum = "BOŞ";
    }
}
