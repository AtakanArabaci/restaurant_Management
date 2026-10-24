package servis;

import model.Kullanici;

import java.io.*;
import java.util.*;

public class KullaniciServisi {

    private static final KullaniciServisi INSTANCE = new KullaniciServisi();
    private Map<String, Kullanici> kullanicilar = new HashMap<>();

    private final String DOSYA_YOLU = "data/kullanicilar.txt";

    private KullaniciServisi() {
        txtdenYukle();
    }

    public static KullaniciServisi getInstance() {
        return INSTANCE;
    }

    // 🔥 TXT OKUMA
    private void txtdenYukle() {
        kullanicilar.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(DOSYA_YOLU))) {
            String satir;

            while ((satir = br.readLine()) != null) {
                if (satir.trim().isEmpty()) continue;

                String[] p = satir.split(";");

                String kullaniciAdi = p[0];
                String sifre = p[1];
                String rol = p[2];

                Kullanici k;
                if (p.length >= 4) {
                    String adSoyad = p[3];
                    k = new Kullanici(adSoyad, kullaniciAdi, sifre, rol.toLowerCase());
                } else {
                    k = new Kullanici(kullaniciAdi, sifre, rol.toLowerCase());
                }

                kullanicilar.put(kullaniciAdi, k);
            }

        } catch (Exception e) {
            System.out.println("kullanicilar.txt okunamadı!");
            e.printStackTrace();
        }
    }

    // 🔐 LOGIN
    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        Kullanici k = kullanicilar.get(kullaniciAdi);
        if (k != null && k.getSifre().equals(sifre)) {
            return k;
        }
        return null;
    }

    // 👨‍🍳 GARSON LİSTESİ
    public Map<String, Kullanici> getGarsonlar() {
        Map<String, Kullanici> garsonlar = new HashMap<>();
        for (Kullanici k : kullanicilar.values()) {
            if ("garson".equalsIgnoreCase(k.getRol())) {
                garsonlar.put(k.getKullaniciAdi(), k);
            }
        }
        return garsonlar;
    }

    // 🔎 KULLANICI VAR MI?
    public boolean kullaniciAdiVarMi(String kullaniciAdi) {
        return kullanicilar.containsKey(kullaniciAdi);
    }

    // ==============================
    // 🔽 EKLENEN METOTLAR (ÖNEMLİ)
    // ==============================

    // ➕ GARSON EKLE (TXT'YE YAZAR)
    public void garsonEkle(String adSoyad, String kullaniciAdi, String sifre) {

        if (kullaniciAdiVarMi(kullaniciAdi)) return;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA_YOLU, true))) {
            bw.write(kullaniciAdi + ";" + sifre + ";garson;" + adSoyad);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtdenYukle(); // belleği güncelle
    }

    // ➕ GARSON EKLE (adSoyad yoksa)
    public void garsonEkle(String kullaniciAdi, String sifre) {
        garsonEkle(kullaniciAdi, kullaniciAdi, sifre);
    }

    // ❌ GARSON SİL (TXT'DEN KALDIRIR)
    public void garsonSil(String kullaniciAdi) {

        List<String> satirlar = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DOSYA_YOLU))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (!satir.startsWith(kullaniciAdi + ";")) {
                    satirlar.add(satir);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA_YOLU, false))) {
            for (String s : satirlar) {
                bw.write(s);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtdenYukle(); // belleği güncelle
    }
}
