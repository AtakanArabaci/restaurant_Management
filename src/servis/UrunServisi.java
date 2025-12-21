package servis;

import java.io.*;
import java.util.*;
import model.Urun;

public class UrunServisi {

    private static final UrunServisi INSTANCE = new UrunServisi();
    private final String DOSYA = "data/urunler.txt";
    private List<Urun> urunler = new ArrayList<>();

    private UrunServisi() {
        txtdenYukle();
    }

    public static UrunServisi getInstance() {
        return INSTANCE;
    }

    private void txtdenYukle() {
        urunler.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(DOSYA))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (line.isBlank()) continue;
                String[] p = line.split(";");
                if (p.length < 4) continue;

                String ad = p[0];
                int fiyat = Integer.parseInt(p[1]); // 🔥 int
                String kategori = p[2];
                String resim = p[3];

                urunler.add(new Urun(ad, fiyat, kategori, resim));
            }
        } catch (Exception e) {
            System.out.println("urunler.txt okunamadı!");
        }
    }

    public List<Urun> getUrunler() { return urunler; }

    public List<Urun> getUrunlerByKategori(String kategori) {
        List<Urun> list = new ArrayList<>();
        for (Urun u : urunler)
            if (u.getKategori().equalsIgnoreCase(kategori))
                list.add(u);
        return list;
    }

    public Urun getUrunByAd(String ad) {
        for (Urun u : urunler)
            if (u.getAd().equalsIgnoreCase(ad))
                return u;
        return null;
    }

    public void urunEkle(Urun urun) {
        if (urun == null) return;
        urunler.add(urun);
        txtyeYaz();
    }

    public void urunSil(String ad, String kategori) {
        urunler.removeIf(u ->
                u.getAd().equalsIgnoreCase(ad) &&
                u.getKategori().equalsIgnoreCase(kategori)
        );
        txtyeYaz();
    }

    // 🔥 FİYAT GÜNCELLE (KRİTİK)
    public void fiyatGuncelle(Urun urun, int yeniFiyat) {
        if (urun == null) return;
        urun.setFiyat(yeniFiyat);
        txtyeYaz();
    }

    private void txtyeYaz() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA, false))) {
            for (Urun u : urunler) {
                bw.write(
                        u.getAd() + ";" +
                        u.getFiyat() + ";" +
                        u.getKategori() + ";" +
                        u.getResimYolu()
                );
                bw.newLine();
            }
        } catch (Exception ignored) {}
    }

    public String havuzdanResimBul(String urunAdi, String kategori) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("data/urun_havuzu.txt"))) {

            String s;
            while ((s = br.readLine()) != null) {
                if (s.isBlank()) continue;
                String[] p = s.split(";");
                if (p.length < 3) continue;

                if (p[0].equalsIgnoreCase(urunAdi)
                        && p[1].equalsIgnoreCase(kategori))
                    return p[2];
            }
        } catch (Exception e) {
            System.out.println("urun_havuzu.txt okunamadı!");
        }
        return "icons/default.jpg";
    }
}
