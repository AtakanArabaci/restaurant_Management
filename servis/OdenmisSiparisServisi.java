package servis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.OdenmisSiparis;

public class OdenmisSiparisServisi {

    private static final OdenmisSiparisServisi INSTANCE = new OdenmisSiparisServisi();
    private List<OdenmisSiparis> kayitlar = new ArrayList<>();

    private OdenmisSiparisServisi() {}

    public static OdenmisSiparisServisi getInstance() {
        return INSTANCE;
    }

    public void ekle(OdenmisSiparis s) {
        kayitlar.add(s);
    }

    public List<OdenmisSiparis> getTariheGoreSirali() {
        return kayitlar.stream()
                .sorted(Comparator.comparing(OdenmisSiparis::getZaman))
                .toList();
    }
}
