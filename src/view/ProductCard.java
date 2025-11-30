package view;

import model.Masa;
import model.Urun;
import servis.SiparisServisi;

import javax.swing.*;
import java.awt.*;

public class ProductCard extends JPanel {

    public ProductCard(Urun urun, Masa masa) {

        setLayout(null);
        setPreferredSize(new Dimension(150, 200));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setBackground(Color.WHITE);

        // Ürün Adı
        JLabel lblName = new JLabel(urun.getAd());
        lblName.setFont(new Font("Arial", Font.BOLD, 15));
        lblName.setBounds(10, 10, 130, 20);
        add(lblName);

        // Fotoğraf
        ImageIcon img = new ImageIcon(urun.getIconPath());
        Image scaled = img.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(scaled));
        lblImage.setBounds(25, 40, 100, 80);
        add(lblImage);

        // Fiyat
        JLabel lblPrice = new JLabel(urun.getFiyat() + " TL");
        lblPrice.setBounds(10, 130, 130, 20);
        add(lblPrice);

        // + butonu
        JButton btnAdd = new JButton("+");
        btnAdd.setBounds(105, 130, 40, 35);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 18));
        btnAdd.setBackground(new Color(0, 180, 0));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBorder(null);

        btnAdd.addActionListener(e -> {
            new SiparisServisi().siparisEkle(masa, urun, 1);
            JOptionPane.showMessageDialog(this, urun.getAd() + " eklendi!");
        });

        add(btnAdd);
    }
}
