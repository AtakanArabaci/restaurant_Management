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
    // key = ürün adı (örn: "Kebap"), value = tüm masalarda toplam adet
    private static Map<String, Integer> globalStokSayaci = new HashMap<>();

    public Masa(int masaNo, String durum) {
        this.masaNo = masaNo;
        this.durum = durum;
    }

    public int getMasaNo() { return masaNo; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public List<Siparis> getSiparisler() { return siparisler; }

    // ===========================
    // GLOBAL TOPLAM (ürün bazlı)
    // ===========================
    public static int getGlobalAdet(Urun urun) {
        return globalStokSayaci.getOrDefault(urun.getAd(), 0);
    }

    // ===========================
    // ÜRÜN BAZLI STOK LIMIT (GLOBAL)
    // ===========================
    public boolean urunLimitUygun(Urun urun, int eklenecekAdet) {

        int mevcutGlobal = getGlobalAdet(urun);
        int yeniGlobal = mevcutGlobal + eklenecekAdet;

        String kategori = urun.getKategori().toLowerCase();

        int max = switch (kategori) {
            case "yemek" -> 50;
            case "tatli" -> 50;
            case "icecek" -> 100;
            default -> Integer.MAX_VALUE;
        };

        return yeniGlobal <= max;
    }

    // ===========================
    // SİPARİŞ EKLEME
    // ===========================
    public void siparisEkle(Urun urun, int adet) {

        // Önce GLOBAL STOK KONTROLÜ
        if (!urunLimitUygun(urun, adet)) {
            // limit aşılıyorsa hiç ekleme
            return;
        }

        if (durum.equalsIgnoreCase("REZERVE")) {
            durum = "DOLU";
        }

        // Bu masada ürün zaten varsa → adet artır
        for (Siparis s : siparisler) {
            if (s.getUrun().getAd().equals(urun.getAd())) {

                s.setAdet(s.getAdet() + adet);

                // GLOBAL STOK GÜNCELLE
                int mevcutGlobal = getGlobalAdet(urun);
                globalStokSayaci.put(urun.getAd(), mevcutGlobal + adet);

                durum = "DOLU";
                return;
            }
        }

        // Yeni ürün ekleniyor
        siparisler.add(new Siparis(urun, adet));

        int mevcutGlobal = getGlobalAdet(urun);
        globalStokSayaci.put(urun.getAd(), mevcutGlobal + adet);

        durum = "DOLU";
    }

    // ===========================
    // SİPARİŞ SİLME (−1)
    // ===========================
    public void siparisSil(Urun urun) {

        Iterator<Siparis> it = siparisler.iterator();

        while (it.hasNext()) {
            Siparis s = it.next();

            if (s.getUrun().getAd().equals(urun.getAd())) {

                // GLOBAL STOKTAN DÜŞ
                int mevcutGlobal = getGlobalAdet(urun);
                int yeniGlobal = Math.max(0, mevcutGlobal - 1);

                if (yeniGlobal == 0) {
                    globalStokSayaci.remove(urun.getAd());
                } else {
                    globalStokSayaci.put(urun.getAd(), yeniGlobal);
                }

                // MASADAKİ ADETİ GÜNCELLE
                if (s.getAdet() > 1) {
                    s.setAdet(s.getAdet() - 1);
                } else {
                    it.remove();
                }

                break;
            }
        }

        if (siparisler.isEmpty()) {
            durum = "BOŞ";
        }
    }

    // ===========================
    // ÖDEME AL → MASAYI SIFIRLA
    // ===========================
    public void odemeAl() {

        // Bu masadaki tüm siparişleri GLOBAL STOKTAN DÜŞ
        for (Siparis s : siparisler) {
            Urun u = s.getUrun();
            int adet = s.getAdet();
            int mevcutGlobal = getGlobalAdet(u);
            int yeniGlobal = Math.max(0, mevcutGlobal - adet);

            if (yeniGlobal == 0) {
                globalStokSayaci.remove(u.getAd());
            } else {
                globalStokSayaci.put(u.getAd(), yeniGlobal);
            }
        }

        siparisler.clear();
        durum = "BOŞ";
    }
}
