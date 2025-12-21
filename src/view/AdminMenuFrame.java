package view;

import model.Urun;
import servis.UrunServisi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminMenuFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private UrunServisi urunServisi;

    public AdminMenuFrame() {

        urunServisi = UrunServisi.getInstance();

        setTitle("Admin - Menü Kontrolü");
        setSize(700, 450);
        setResizable(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= TABLO =================
        model = new DefaultTableModel(
                new Object[]{"Ürün", "Kategori", "Fiyat"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ================= BUTONLAR =================
        JPanel buttons = new JPanel();

        JButton btnFiyat = new JButton("Fiyat Güncelle");
        JButton btnPasif = new JButton("Pasif Yap");

        buttons.add(btnFiyat);
        buttons.add(btnPasif);

        add(buttons, BorderLayout.SOUTH);

        btnFiyat.addActionListener(e -> fiyatGuncelle());
        btnPasif.addActionListener(e -> pasifYap());

        tabloyuDoldur();
        setVisible(true);
    }

    // ================= TABLO DOLDUR =================
    private void tabloyuDoldur() {
        model.setRowCount(0);

        for (Urun u : urunServisi.getUrunler()) {
            model.addRow(new Object[]{
                    u.getAd(),
                    u.getKategori(),
                    u.getFiyat()
            });
        }
    }

    private void fiyatGuncelle() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Ürün seç!");
            return;
        }

        String ad = model.getValueAt(row, 0).toString();
        Urun urun = urunServisi.getUrunByAd(ad);

        String input = JOptionPane.showInputDialog(
                this,
                "Yeni fiyat:",
                urun.getFiyat()
        );

        if (input == null) return;

        try {
            int yeniFiyat = Integer.parseInt(
                input.replace(",", "").replace(".", "").trim()
            );

            if (yeniFiyat <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Fiyat 0'dan büyük olmalı!");
                return;
            }

            urunServisi.fiyatGuncelle(urun, yeniFiyat);
            tabloyuDoldur();

            JOptionPane.showMessageDialog(this,
                    "Fiyat güncellendi!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Geçerli bir fiyat gir!");
        }
    }


    // ================= PASİF YAP =================
    private void pasifYap() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Ürün seç!");
            return;
        }

        String ad = model.getValueAt(row, 0).toString();
        String kategori = model.getValueAt(row, 1).toString();

        urunServisi.urunSil(ad, kategori);
        tabloyuDoldur();

        JOptionPane.showMessageDialog(this, "Ürün pasif yapıldı!");
    }
}
