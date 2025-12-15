package model;

import java.time.LocalDateTime;

public class Siparis {

    private Urun urun;
    private int adet;
    private LocalDateTime zaman;

    public Siparis(Urun urun, int adet) {
        this.urun = urun;
        this.adet = adet;
        this.zaman = LocalDateTime.now();
    }

    public Urun getUrun() {
        return urun;
    }

    public int getAdet() {
        return adet;
    }

    // 🔥 Sipariş artırma / azaltma için gerekli
    public void setAdet(int adet) {
        this.adet = adet;
    }

    // 💡 Toplam her zaman güncel hesaplanır
    public double getToplam() {
        return urun.getFiyat() * adet;
    }

    public LocalDateTime getZaman() {
        return zaman;
    }
}
