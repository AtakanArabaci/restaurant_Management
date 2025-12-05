package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Masa;
import model.Siparis;
import model.Urun;
import servis.KullaniciServisi;
import servis.MasaServisi;
import servis.SiparisServisi;
import servis.UrunServisi;

public class AdminController {

    private final UrunServisi urunServisi;
    private final KullaniciServisi kullaniciServisi;
    private final MasaServisi masaServisi;
    private final SiparisServisi siparisServisi;

    public AdminController(UrunServisi urunServisi,
                           KullaniciServisi kullaniciServisi,
                           MasaServisi masaServisi,
                           SiparisServisi siparisServisi) {
        this.urunServisi = urunServisi;
        this.kullaniciServisi = kullaniciServisi;
        this.masaServisi = masaServisi;
        this.siparisServisi = siparisServisi;
    }

    // ===== MENÜ KONTROLÜ =====
    public void menuyuKontrolEt() {

        StringBuilder sb = new StringBuilder();
        sb.append("=== MENÜ LİSTESİ ===\n\n");

        // YEMEKLER
        List<Urun> yemekler = urunServisi.kategoriGetir("YEMEK");
        sb.append("YEMEKLER:\n");
        for (Urun u : yemekler) {
            sb.append(" - ").append(u.getAd())
              .append(" : ").append(u.getFiyat())
              .append(" TL\n");
        }
        sb.append("\n");

        // TATLILAR
        List<Urun> tatlilar = urunServisi.kategoriGetir("TATLI");
        sb.append("TATLILAR:\n");
        for (Urun u : tatlilar) {
            sb.append(" - ").append(u.getAd())
              .append(" : ").append(u.getFiyat())
              .append(" TL\n");
        }
        sb.append("\n");

        // İÇECEKLER
        List<Urun> icecekler = urunServisi.kategoriGetir("ICECEK");
        sb.append("İÇECEKLER:\n");
        for (Urun u : icecekler) {
            sb.append(" - ").append(u.getAd())
              .append(" : ").append(u.getFiyat())
              .append(" TL\n");
        }

        JOptionPane.showMessageDialog(
                null,
                sb.toString(),
                "Menü Kontrolü",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // MENÜYE ÜRÜN EKLE
    public void urunEkle(String kategori, String ad, double fiyat, String iconPath) {
        if (kategori == null || kategori.isBlank()
                || ad == null || ad.isBlank()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Kategori ve ürün adı boş olamaz.",
                    "Ürün Ekle",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean basarili = urunServisi.urunEkle(kategori, ad, fiyat, iconPath);

        if (basarili) {
            JOptionPane.showMessageDialog(
                    null,
                    "Ürün eklendi:\n" + kategori.toUpperCase() +
                            " -> " + ad + " (" + fiyat + " TL)",
                    "Ürün Ekle",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Ürün eklenemedi. Bu kategoride aynı isimde ürün olabilir.",
                    "Ürün Ekle",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // MENÜDEN ÜRÜN SİL
    public void urunSil(String kategori, String ad) {
        if (kategori == null || kategori.isBlank()
                || ad == null || ad.isBlank()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Kategori ve ürün adı boş olamaz.",
                    "Ürün Sil",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean basarili = urunServisi.urunSil(kategori, ad);

        if (basarili) {
            JOptionPane.showMessageDialog(
                    null,
                    "Ürün silindi:\n" + kategori.toUpperCase() +
                            " -> " + ad,
                    "Ürün Sil",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Ürün silinemedi. Bu isimde ürün bulunamadı.",
                    "Ürün Sil",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===== GARSON KONTROLÜ =====
    public void garsonlariKontrolEt() {
        List<String> garsonlar = kullaniciServisi.garsonIsimleriniGetir();

        if (garsonlar.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Kayıtlı garson bulunmuyor.",
                    "Garson Kontrolü",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== GARSON LİSTESİ ===\n\n");
        for (String g : garsonlar) {
            sb.append("- ").append(g).append("\n");
        }

        JOptionPane.showMessageDialog(
                null,
                sb.toString(),
                "Garson Kontrolü",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void garsonEkle(String ad, String sifre) {
        boolean basarili = kullaniciServisi.garsonEkle(ad, sifre);

        if (basarili) {
            JOptionPane.showMessageDialog(
                    null,
                    "Garson eklendi: " + ad,
                    "Garson Ekle",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Garson eklenemedi. (Boş alan, aynı isim veya başka bir hata)",
                    "Garson Ekle",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void garsonSil(String ad) {
        boolean basarili = kullaniciServisi.garsonSil(ad);

        if (basarili) {
            JOptionPane.showMessageDialog(
                    null,
                    "Garson silindi: " + ad,
                    "Garson Sil",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Garson silinemedi. (Böyle bir garson yok veya admin'i silmeye çalışıyorsun)",
                    "Garson Sil",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===== MASA NUMARASINA GÖRE SORGU =====
    public void masayaGoreSorgula(String masaNoMetni) {
        if (masaNoMetni == null || masaNoMetni.isBlank()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Masa numarası boş olamaz.",
                    "Masa Sorgu",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int masaNo;
        try {
            masaNo = Integer.parseInt(masaNoMetni.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Masa numarası sayısal olmalıdır.",
                    "Masa Sorgu",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Masa masa = masaServisi.getMasa(masaNo);
        if (masa == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Bu numarada kayıtlı masa yok: " + masaNo,
                    "Masa Sorgu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // Masa BOŞ ise direk söyle
        if ("boş".equalsIgnoreCase(masa.getDurum())) {
            JOptionPane.showMessageDialog(
                    null,
                    "Masa " + masaNo + " şu an BOŞ.",
                    "Masa Sorgu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // Masa dolu/rezerve ise siparişlere bakalım
        List<Siparis> tumSiparisler = siparisServisi.tumSiparisleriGetir();
        List<Siparis> masaSiparisleri = new ArrayList<>();

        for (Siparis s : tumSiparisler) {
            if (s.getMasaNo() == masaNo) {
                masaSiparisleri.add(s);
            }
        }

        if (masaSiparisleri.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Masa " + masaNo + " dolu/rezerve görünüyor fakat kayıtlı sipariş bulunamadı.",
                    "Masa Sorgu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Masa ").append(masaNo)
          .append(" - Durum: ").append(masa.getDurum().toUpperCase())
          .append("\n\n");

        double toplam = 0.0;

        for (Siparis s : masaSiparisleri) {
            sb.append("Müşteri: ").append(s.getMusteriAdi())
              .append(" - ")
              .append(s.getUrunAdi())
              .append(" x ")
              .append(s.getAdet())
              .append(" = ")
              .append(String.format("%.2f", s.getToplamTutar()))
              .append(" TL (")
              .append(s.getTarih())
              .append(")\n");

            toplam += s.getToplamTutar();
        }

        sb.append("\nToplam Tutar: ")
          .append(String.format("%.2f", toplam))
          .append(" TL");

        JOptionPane.showMessageDialog(
                null,
                sb.toString(),
                "Masa Sorgu",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ===== TARİHE GÖRE SORGU =====
    public void tariheGoreSorgula(String tarihMetni) {
        if (tarihMetni == null || tarihMetni.isBlank()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tarih boş olamaz.",
                    "Tarih Sorgu",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        List<Siparis> siparisList = siparisServisi.tariheGoreSiparisGetir(tarihMetni);

        if (siparisList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Bu tarihte sipariş bulunamadı: " + tarihMetni,
                    "Tarih Sorgu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Tarih: ").append(tarihMetni).append("\n\n");

        double toplamCiro = 0.0;

        for (Siparis s : siparisList) {
            sb.append("Masa ")
              .append(s.getMasaNo())
              .append(" - ")
              .append(s.getMusteriAdi())
              .append(" - ")
              .append(s.getUrunAdi())
              .append(" x ")
              .append(s.getAdet())
              .append(" = ")
              .append(String.format("%.2f", s.getToplamTutar()))
              .append(" TL\n");

            toplamCiro += s.getToplamTutar();
        }

        sb.append("\nToplam Ciro: ")
          .append(String.format("%.2f", toplamCiro))
          .append(" TL");

        JOptionPane.showMessageDialog(
                null,
                sb.toString(),
                "Tarih Sorgu",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ===== GÜN SONU RAPORU =====
    public void gunSonuAl() {
        List<Siparis> siparisList = siparisServisi.tumSiparisleriGetir();

        if (siparisList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Henüz hiç sipariş bulunmuyor.",
                    "Gün Sonu Raporu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== GÜN SONU RAPORU ===\n\n");

        double toplamCiro = 0.0;
        int toplamSiparis = siparisList.size();

        for (Siparis s : siparisList) {
            sb.append("Tarih: ")
              .append(s.getTarih())
              .append(" | Masa ")
              .append(s.getMasaNo())
              .append(" | Müşteri: ")
              .append(s.getMusteriAdi())
              .append(" | ")
              .append(s.getUrunAdi())
              .append(" x ")
              .append(s.getAdet())
              .append(" = ")
              .append(String.format("%.2f", s.getToplamTutar()))
              .append(" TL\n");

            toplamCiro += s.getToplamTutar();
        }

        sb.append("\nToplam Sipariş Adedi: ")
          .append(toplamSiparis)
          .append("\nToplam Ciro: ")
          .append(String.format("%.2f", toplamCiro))
          .append(" TL");

        JOptionPane.showMessageDialog(
                null,
                sb.toString(),
                "Gün Sonu Raporu",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
