package view;

import model.Urun;
import servis.UrunServisi;
import view_components.AdminProductCard;
import view_components.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminUrunListeFrame extends JFrame {

    private String kategori;
    private JPanel grid;

    public AdminUrunListeFrame(String kategori) {
        this.kategori = kategori;

        setTitle("Menü Yönetimi - " + kategori);
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("icons/garson_bg.jpg");
        bg.setLayout(new BorderLayout());
        add(bg);

        JButton btnEkle = new JButton("Ürün Ekle");
        JButton btnSil = new JButton("Seçili Ürünü Sil");

        JPanel top = new JPanel();
        top.add(btnEkle);
        top.add(btnSil);
        bg.add(top, BorderLayout.NORTH);

        // 🔥 DİKEY AKAN GRID
        grid = new JPanel(new GridLayout(0,3,40,40));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(30,40,40,40));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.getVerticalScrollBar().setUnitIncrement(30);
        scroll.getHorizontalScrollBar().setUnitIncrement(30);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));

        bg.add(scroll, BorderLayout.CENTER);

        doldur();

        btnSil.addActionListener(e -> {
            AdminProductCard secili = AdminProductCard.getSelected();
            if (secili == null) {
                JOptionPane.showMessageDialog(this, "Silmek için ürün seç!");
                return;
            }

            int cevap = JOptionPane.showConfirmDialog(
                    this,
                    "\"" + secili.getUrun().getAd() + "\" ürününü silmek istediğinize emin misiniz?",
                    "Ürün Silme Onayı",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (cevap != JOptionPane.YES_OPTION) {
                return; // ❌ Vazgeçildi
            }

            UrunServisi.getInstance()
                    .urunSil(secili.getUrun().getAd(), kategori);

            AdminProductCard.clearSelection();
            doldur();
        });

        btnEkle.addActionListener(e -> {
            new AdminUrunEkleDialog(this, kategori);
            doldur();
        });

        setVisible(true);
    }

    public void doldur() {
        grid.removeAll();
        List<Urun> list = UrunServisi.getInstance()
                .getUrunlerByKategori(kategori);

        for (Urun u : list)
            grid.add(new AdminProductCard(u));

        grid.revalidate();
        grid.repaint();
    }
}
