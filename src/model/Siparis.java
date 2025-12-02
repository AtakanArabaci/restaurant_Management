package model;

public class Siparis {

    private Urun urun;
    private int adet;

    public Siparis(Urun urun, int adet) {
        this.urun = urun;
        this.adet = adet;
    }

    public Urun getUrun() {
        return urun;
    }

    public int getAdet() {
        return adet;
    }

    // 🔥 Eksik olan setter (siparis artırma / azaltma için şart)
    public void setAdet(int adet) {
        this.adet = adet;
    }

    public double getToplam() {
        return urun.getFiyat() * adet;
    }
}
