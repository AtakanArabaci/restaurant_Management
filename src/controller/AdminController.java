package controller;

import servis.KullaniciServisi; 
import servis.MasaServisi;
import servis.SiparisServisi;
import servis.UrunServisi;
import model.Urun;
import model.Siparis;

import javax.swing.*;
import java.util.List;
import javax.swing.JOptionPane;


public class AdminController {

    private final UrunServisi urunServisi;
    private final KullaniciServisi kullaniciServisi;
    private final MasaServisi masaServisi;
    private final SiparisServisi siparisServisi;

    // PARAMETRESİZ CONSTRUCTOR
    // Servis nesnelerini kendi içinde oluşturuyoruz.
    public AdminController() {
        this.urunServisi = new UrunServisi();
        this.kullaniciServisi = new KullaniciServisi();
        this.masaServisi = new MasaServisi();
        this.siparisServisi = new SiparisServisi();
    }

    public void menuyuKontrolEt() {

        StringBuilder sb = new StringBuilder();
        sb.append("=== MENÜ LİSTESİ ===\n\n");

        // YEMEKLER
        List<Urun> yemekler = urunServisi.kategoriGetir("YEMEK");
        sb.append("YEMEKLER:\n");
        for (Urun u : yemekler) {
            sb.append(" - ").append(u.getAd()).append(" : ").append(u.getFiyat()).append(" TL\n");
        }

        sb.append("\n");

        // TATLILAR
        List<Urun> tatlilar = urunServisi.kategoriGetir("TATLI");
        sb.append("TATLILAR:\n");
        for (Urun u : tatlilar) {
            sb.append(" - ").append(u.getAd()).append(" : ").append(u.getFiyat()).append(" TL\n");
        }

        sb.append("\n");

        // İÇECEKLER
        List<Urun> icecekler = urunServisi.kategoriGetir("ICECEK");
        sb.append("İÇECEKLER:\n");
        for (Urun u : icecekler) {
            sb.append(" - ").append(u.getAd()).append(" : ").append(u.getFiyat()).append(" TL\n");
        }

        JOptionPane.showMessageDialog(null,
                sb.toString(),
                "Menü Kontrolü",
                JOptionPane.INFORMATION_MESSAGE);
    }


 // 2) GARSON KONTROLÜ
    public void garsonlariKontrolEt() {

        // KullaniciServisi'nden garson isimlerini al
        List<String> garsonlar = kullaniciServisi.garsonIsimleriniGetir();

        if (garsonlar == null || garsonlar.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Sistemde kayıtlı garson bulunmuyor.",
                    "Garson Kontrolü",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== GARSON LİSTESİ ===\n\n");

        for (String ad : garsonlar) {
            sb.append("- ").append(ad).append("\n");
        }

        JOptionPane.showMessageDialog(
                null,
                sb.toString(),
                "Garson Kontrolü",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

 

 // 3) MÜŞTERİ ADINA GÖRE SORGU
    public void musteriyeGoreSorgula(String musteriAdi) {
        if (musteriAdi == null || musteriAdi.isBlank()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Müşteri adı boş olamaz.",
                    "Müşteri Sorgu",
                    JOptionPane.WARNING_MESSAGE
            );
            System.out.println("AdminController: Müşteri adı boş olamaz.");
            return;
        }

        List<Siparis> siparisList = siparisServisi.musteriyeGoreSiparisGetir(musteriAdi);

        if (siparisList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Bu isimle kayıtlı sipariş bulunamadı: " + musteriAdi,
                    "Müşteri Sorgu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            System.out.println("AdminController: Müşteri bulunamadı: " + musteriAdi);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Müşteri: ").append(musteriAdi).append("\n\n");

        double toplam = 0.0;

        for (Siparis s : siparisList) {
            sb.append("Masa ")
              .append(s.getMasaNo())
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
                "Müşteri Sorgu",
                JOptionPane.INFORMATION_MESSAGE
        );

        System.out.println("AdminController: Müşteri adına göre sorgu = " + musteriAdi);
    }


    // 4) TARİHE GÖRE SORGU
    public void tariheGoreSorgula(String tarihMetni) {
        if (tarihMetni == null || tarihMetni.isBlank()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Tarih boş olamaz.",
                    "Tarih Sorgu",
                    JOptionPane.WARNING_MESSAGE
            );
            System.out.println("AdminController: Tarih boş olamaz.");
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
            System.out.println("AdminController: Bu tarihte sipariş yok = " + tarihMetni);
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

        System.out.println("AdminController: Tarihe göre sorgu = " + tarihMetni);
    }


    // 5) GÜN SONU RAPORU
    public void gunSonuAl() {
        List<Siparis> siparisList = siparisServisi.tumSiparisleriGetir();

        if (siparisList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Henüz hiç sipariş bulunmuyor.",
                    "Gün Sonu Raporu",
                    JOptionPane.INFORMATION_MESSAGE
            );
            System.out.println("AdminController: Gün sonu - hiç sipariş yok.");
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

        System.out.println("AdminController: Gün sonu raporu alındı.");
    }

}
