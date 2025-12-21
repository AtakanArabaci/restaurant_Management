package servis;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StokServisi {

    private static final StokServisi INSTANCE = new StokServisi();
    private final String DOSYA = "data/stok.txt";

    private Map<String, Integer> stoklar = new HashMap<>();

    private StokServisi() {
        txtdenYukle();
    }

    public static StokServisi getInstance() {
        return INSTANCE;
    }

    public void yenidenYukle() {
        txtdenYukle();
    }

    private void txtdenYukle() {
        stoklar.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(DOSYA))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (s.isBlank()) continue;
                String[] p = s.split(";");
                stoklar.put(p[0], Integer.parseInt(p[2]));
            }
        } catch (Exception e) {
            System.out.println("stok.txt okunamadı!");
        }
    }

    private void txtyeYaz() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA, false))) {
            for (var e : stoklar.entrySet()) {
                bw.write(e.getKey() + ";x;" + e.getValue());
                bw.newLine();
            }
        } catch (Exception ignored) {}
    }

    // =====================
    // 🔥 GARSON KULLANIMI
    // =====================
    public boolean stokYeterliMi(String urunAdi, int adet) {
        return stoklar.getOrDefault(urunAdi, 0) >= adet;
    }

    public void stokDus(String urunAdi, int adet) {
        stoklar.put(urunAdi,
                Math.max(0, stoklar.getOrDefault(urunAdi, 0) - adet));
        txtyeYaz();
    }

    public void stokArttir(String urunAdi, int adet) {
        stoklar.put(urunAdi,
                stoklar.getOrDefault(urunAdi, 0) + adet);
        txtyeYaz();
    }

    public int getStok(String urunAdi) {
        return stoklar.getOrDefault(urunAdi, 0);
    }

    // =====================
    // 🔥 ADMIN KULLANIMI
    // =====================
    public boolean stokVarMi(String urunAdi) {
        return stoklar.containsKey(urunAdi);
    }

    public void stokOlustur(String urunAdi, int baslangicStok) {
        if (stoklar.containsKey(urunAdi)) return;
        stoklar.put(urunAdi, baslangicStok);
        txtyeYaz();
    }

    public void stokSil(String urunAdi) {
        stoklar.remove(urunAdi);
        txtyeYaz();
    }
}
