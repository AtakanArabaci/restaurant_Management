package servis;

import java.util.HashMap;
import java.util.Map;

import model.Masa;

public class MasaServisi {

    private Map<Integer, Masa> masalar = new HashMap<>();

    public MasaServisi() {
        // 8 masa oluşturduk
        masalar.put(1, new Masa(1, "boş"));
        masalar.put(2, new Masa(2, "dolu"));
        masalar.put(3, new Masa(3, "rezerve"));
        masalar.put(4, new Masa(4, "boş"));
        masalar.put(5, new Masa(5, "dolu"));
        masalar.put(6, new Masa(6, "boş"));
        masalar.put(7, new Masa(7, "rezerve"));
        masalar.put(8, new Masa(8, "boş"));
    }

    public Map<Integer, Masa> getMasalar() {
        return masalar;
    }

    public Masa getMasa(int numara) {
        return masalar.get(numara);
    }

    public void masaEkle() {
        int yeniNumara = masalar.size() + 1;
        masalar.put(yeniNumara, new Masa(yeniNumara, "boş"));
    }

    public void masaSil(int numara) {
        masalar.remove(numara);
    }
}
