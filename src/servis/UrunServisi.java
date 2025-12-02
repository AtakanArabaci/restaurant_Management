package servis;

import java.util.ArrayList;
import java.util.List;

import model.Urun;

public class UrunServisi {

    // Kategoriye göre ürünleri döndürür
    public List<Urun> kategoriGetir(String kategori) {

        List<Urun> list = new ArrayList<>();

        if (kategori.equals("YEMEK")) {
            list.add(new Urun("Kebap",     180, "YEMEK",  "icons/kebap.png"));
            list.add(new Urun("Lahmacun",   60, "YEMEK",  "icons/lahmacun.png"));
            list.add(new Urun("Hamburger",  95, "YEMEK",  "icons/hamburger.png"));
        }

        if (kategori.equals("TATLI")) {
            list.add(new Urun("Baklava",    80, "TATLI",  "icons/baklava.png"));
            list.add(new Urun("Kunefe",     90, "TATLI",  "icons/kunefe.png"));
            list.add(new Urun("Sutlac",     50, "TATLI",  "icons/sutlac.png"));
        }

        if (kategori.equals("ICECEK")) {
            list.add(new Urun("Ayran",      20, "ICECEK", "icons/ayran.png"));
            list.add(new Urun("Kola",       35, "ICECEK", "icons/kola.png"));
            list.add(new Urun("Cay",        10, "ICECEK", "icons/cay.png"));
        }

        return list;
    }
}
