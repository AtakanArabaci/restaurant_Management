package view;

import model.Kullanici;
import servis.KullaniciServisi;

import javax.swing.*;
import java.awt.*;

public class AdminGarsonDialog extends JDialog {

    public AdminGarsonDialog(JFrame parent, Kullanici mevcut) {
        super(parent, "Garson Detayı", true);

        setSize(360, 260);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField adSoyad = new JTextField();
        JTextField kullanici = new JTextField();
        JPasswordField sifre = new JPasswordField();

        String eskiKullaniciAdi = mevcut != null ? mevcut.getKullaniciAdi() : null;

        if (mevcut != null) {
            adSoyad.setText(mevcut.getAdSoyad());
            kullanici.setText(mevcut.getKullaniciAdi());
            sifre.setText(mevcut.getSifre());
        }

        JButton kaydet = new JButton("Kaydet");
        JButton geri = new JButton("Geri");

        add(new JLabel("Ad Soyad:")); add(adSoyad);
        add(new JLabel("Kullanıcı Adı:")); add(kullanici);
        add(new JLabel("Şifre:")); add(sifre);
        add(geri); add(kaydet);

        geri.addActionListener(e -> dispose());

        kaydet.addActionListener(e -> {

            if (adSoyad.getText().isBlank()
                    || kullanici.getText().isBlank()
                    || sifre.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Tüm alanlar zorunlu!");
                return;
            }

            if (!adSoyad.getText().matches("[a-zA-ZçÇğĞıİöÖşŞüÜ ]+")) {
                JOptionPane.showMessageDialog(this, "Ad Soyad sadece harf içermelidir!");
                return;
            }

            KullaniciServisi servis = KullaniciServisi.getInstance();
            String yeniKullaniciAdi = kullanici.getText();

            // ✅ DOĞRU KONTROL (ASLA getGarsonlar() DEĞİL)
            if (eskiKullaniciAdi != null &&
                !eskiKullaniciAdi.equals(yeniKullaniciAdi) &&
                servis.kullaniciAdiVarMi(yeniKullaniciAdi)) {

                JOptionPane.showMessageDialog(this, "Bu kullanıcı adı zaten var!");
                return;
            }

            if (eskiKullaniciAdi == null &&
                servis.kullaniciAdiVarMi(yeniKullaniciAdi)) {

                JOptionPane.showMessageDialog(this, "Bu kullanıcı adı zaten var!");
                return;
            }

            if (eskiKullaniciAdi != null) {
                servis.garsonSil(eskiKullaniciAdi);
            }

            servis.garsonEkle(
                    adSoyad.getText(),
                    yeniKullaniciAdi,
                    new String(sifre.getPassword())
            );

            dispose();
        });

        setVisible(true);
    }
}
