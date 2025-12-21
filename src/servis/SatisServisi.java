package servis;

import model.Masa;
import model.Siparis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SatisServisi {

    private static final SatisServisi INSTANCE = new SatisServisi();
    private final String DOSYA = "data/satislar.txt";

    private SatisServisi() {}

    public static SatisServisi getInstance() {
        return INSTANCE;
    }

    // =======================
    // SATIŞ KAYDET
    // =======================
    public void satisKaydet(Masa masa) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA, true))) {

            DateTimeFormatter fmt =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Siparis s : masa.getSiparisler()) {
                double toplam = s.getUrun().getFiyat() * s.getAdet();

                bw.write(
                        LocalDateTime.now().format(fmt) + ";" +
                        masa.getMasaNo() + ";" +
                        s.getUrun().getAd() + ";" +
                        s.getAdet() + ";" +
                        toplam
                );
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
