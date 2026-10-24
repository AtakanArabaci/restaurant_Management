package servis;

import java.io.*;
import java.util.*;

import model.Masa;
import model.Siparis;
import model.Urun;

public class MasaServisi {

    private static final MasaServisi INSTANCE = new MasaServisi();

    private final String MASA_DOSYA = "data/masalar.txt";
    private final String SIPARIS_DOSYA = "data/masa_siparisleri.txt";

    private Map<Integer, Masa> masalar = new HashMap<>();

    private MasaServisi() {
        txtdenYukle();
    }
    

    public static MasaServisi getInstance() {
    	
        return INSTANCE;
    }
    

    public Map<Integer, Masa> getMasalar() {
        return masalar;
    }

    public Masa getMasa(int no) {
        return masalar.get(no);
    }

    // =========================
    // TXT OKU
    // =========================
    private void txtdenYukle() {
        masalar.clear();

        // 1) MASALAR
        try (BufferedReader br = new BufferedReader(new FileReader(MASA_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.isBlank()) continue;

                String[] p = satir.split(";");
                int masaNo = Integer.parseInt(p[0]);
                String durum = p[1];

                masalar.put(masaNo, new Masa(masaNo, durum));
            }
        } catch (Exception e) {
            System.out.println("masalar.txt okunamadı!");
        }

        // 2) SİPARİŞLER
        UrunServisi urunServisi = UrunServisi.getInstance();
        File f = new File(SIPARIS_DOSYA);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(SIPARIS_DOSYA))) {
                String satir;
                while ((satir = br.readLine()) != null) {
                    if (satir.isBlank()) continue;

                    String[] p = satir.split(";");
                    int masaNo = Integer.parseInt(p[0]);
                    String urunAdi = p[1];
                    int adet = Integer.parseInt(p[2]);

                    Masa m = masalar.get(masaNo);
                    if (m == null) continue;

                    Urun u = urunServisi.getUrunByAd(urunAdi);
                    if (u == null) continue;

                    // burada stok oynamıyoruz
                    m.siparisEkleSessiz(u, adet);
                }
            } catch (Exception e) {
                System.out.println("masa_siparisleri.txt okunamadı!");
            }
        }

        for (Masa m : masalar.values()) {
            m.setDurum(m.getSiparisler().isEmpty() ? "BOS" : "DOLU");
        }
    }

    // =========================
    // TXT YAZ
    // =========================
    public void kaydetHerSey() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MASA_DOSYA, false))) {
            List<Integer> keys = new ArrayList<>(masalar.keySet());
            Collections.sort(keys);

            for (int k : keys) {
                Masa m = masalar.get(k);
                bw.write(m.getMasaNo() + ";" + m.getDurum());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SIPARIS_DOSYA, false))) {
            for (Masa m : masalar.values()) {
                for (Siparis s : m.getSiparisler()) {
                    bw.write(m.getMasaNo() + ";" +
                             s.getUrun().getAd() + ";" +
                             s.getAdet());
                    bw.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // ✅ SİPARİŞ EKLE (stok düşer)
    // =========================
    public void siparisEkle(int masaNo, Urun urun, int adet) {

        Masa m = masalar.get(masaNo);
        if (m == null) return;

        StokServisi stok = StokServisi.getInstance();

        if (!stok.stokYeterliMi(urun.getAd(), adet)) {
            throw new RuntimeException("Yetersiz stok: " + urun.getAd());
        }

        m.siparisEkle(urun, adet);
        m.setDurum("DOLU");

        stok.stokDus(urun.getAd(), adet);

        kaydetHerSey();
    }

    // =========================
    // ✅ 1 ADET AZALT (stok +1)
    // ProductCard '-' bunu çağıracak
    // =========================
    public void siparisAzalt(int masaNo, Urun urun, int adetAzalt) {

        Masa m = masalar.get(masaNo);
        if (m == null) return;
        if (adetAzalt <= 0) return;

        // Masada o üründen var mı?
        int mevcut = 0;
        for (Siparis s : m.getSiparisler()) {
            if (s.getUrun().getAd().equalsIgnoreCase(urun.getAd())) {
                mevcut = s.getAdet();
                break;
            }
        }
        if (mevcut <= 0) return;

        int gercekAzalt = Math.min(adetAzalt, mevcut);

        // Modelde siparisSil(urun) 1 azaltıyorsa, gercekAzalt kadar çağırıyoruz:
        for (int i = 0; i < gercekAzalt; i++) {
            m.siparisSil(urun);
        }

        // Stok sadece azaltılan kadar geri gelir
        StokServisi.getInstance().stokArttir(urun.getAd(), gercekAzalt);

        m.setDurum(m.getSiparisler().isEmpty() ? "BOS" : "DOLU");

        kaydetHerSey();
    }

    // =========================
    // ✅ TAMAMINI SİL (stok +tamamı)
    // SiparisDetay “Sil” bunu çağırmalı
    // =========================
    public void siparisSilTamamen(int masaNo, Urun urun) {

        Masa m = masalar.get(masaNo);
        if (m == null) return;

        int mevcut = 0;
        for (Siparis s : m.getSiparisler()) {
            if (s.getUrun().getAd().equalsIgnoreCase(urun.getAd())) {
                mevcut = s.getAdet();
                break;
            }
        }
        if (mevcut <= 0) return;

        // Burada modelin "tam sil" metodu yoksa, mevcut kadar azaltarak sıfırlarız
        for (int i = 0; i < mevcut; i++) {
            m.siparisSil(urun);
        }

        StokServisi.getInstance().stokArttir(urun.getAd(), mevcut);

        m.setDurum(m.getSiparisler().isEmpty() ? "BOS" : "DOLU");

        kaydetHerSey();
    }

    // =========================
    // ✅ ÖDEME AL (stokla iş yok)
    // =========================
    public void odemeAl(int masaNo) {

        Masa m = masalar.get(masaNo);
        if (m == null) return;

        SatisServisi.getInstance().satisKaydet(m);

        m.odemeAl();

        kaydetHerSey();
    }
 // =========================
 // ✅ ADMIN: ÜRÜN SİPARİŞTE Mİ?
 // =========================
 public boolean urunSiparisteMi(String urunAdi) {

     for (Masa m : masalar.values()) {
         for (Siparis s : m.getSiparisler()) {
             if (s.getUrun().getAd().equalsIgnoreCase(urunAdi)) {
                 return true;
             }
         }
     }
     return false;
 }

}
