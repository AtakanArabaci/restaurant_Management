package servis;

import model.OdenmisSiparis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OdenmisSiparisServisi {

    private static final OdenmisSiparisServisi INSTANCE = new OdenmisSiparisServisi();

    private final String DOSYA = "data/satislar.txt";
    private final List<OdenmisSiparis> kayitlar = new ArrayList<>();

    private final DateTimeFormatter[] formatlar = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    };

    private OdenmisSiparisServisi() {
        txtdenYukle();
    }

    public static OdenmisSiparisServisi getInstance() {
        return INSTANCE;
    }

    // ===================== TXT OKU =====================
    private void txtdenYukle() {
        kayitlar.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(DOSYA))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                // tarih;masa;urun;adet;toplam
                String[] p = line.split(";");
                if (p.length < 5) continue;

                LocalDateTime zaman = parseTarih(p[0]);
                if (zaman == null) continue;

                int masaNo = Integer.parseInt(p[1]);
                String urunAdi = p[2];
                int adet = Integer.parseInt(p[3]);
                double toplam = Double.parseDouble(p[4]);

                kayitlar.add(new OdenmisSiparis(
                        masaNo,
                        urunAdi,
                        adet,
                        toplam,
                        zaman
                ));
            }
        } catch (Exception e) {
            System.out.println("satislar.txt okunamadı!");
        }
    }

    // ===================== TARİH PARSE =====================
    private LocalDateTime parseTarih(String text) {
        for (DateTimeFormatter f : formatlar) {
            try {
                return LocalDateTime.parse(text, f);
            } catch (Exception ignored) {}
        }
        return null;
    }

    // ===================== TARİHE GÖRE SIRALI =====================
    public List<OdenmisSiparis> getTariheGoreSirali() {
        return kayitlar.stream()
                .sorted(Comparator.comparing(OdenmisSiparis::getZaman))
                .toList();
    }

    // ===================== 🔥 TARİH LİSTESİ (SON 1 AY) =====================
    public List<LocalDate> getMevcutGunler() {
        return kayitlar.stream()
                .map(s -> s.getZaman().toLocalDate())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    // ===================== ÖDEME SONRASI EKLE =====================
    public void ekle(OdenmisSiparis s) {
        kayitlar.add(s);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA, true))) {
            bw.write(
                    s.getZaman().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ";" +
                    s.getMasaNo() + ";" +
                    s.getUrunAdi() + ";" +
                    s.getAdet() + ";" +
                    s.getToplam()
            );
            bw.newLine();
        } catch (Exception ignored) {}
    }
}
