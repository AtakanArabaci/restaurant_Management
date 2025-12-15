package view;

import model.Kullanici;
import servis.KullaniciServisi;
import view_components.AdminGarsonCard;
import view_components.BackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class AdminGarsonListeFrame extends JFrame {

    private JPanel grid;

    public AdminGarsonListeFrame() {

        setTitle("Garson Kontrolü");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("icons/garson_bg.jpg");
        bg.setLayout(new BorderLayout());
        add(bg);

        JLabel lbl = new JLabel("Garson Kontrolü", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(Color.WHITE);
        lbl.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        bg.add(lbl, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0,3,20,20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(20); // 🔥 hız
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        bg.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton ekle = new JButton("Garson Ekle");
        JButton sil = new JButton("Garson Sil");
        bottom.add(ekle);
        bottom.add(sil);
        bg.add(bottom, BorderLayout.SOUTH);

        ekle.addActionListener(e -> {
            new AdminGarsonDialog(this, null);
            doldur();
        });

        sil.addActionListener(e -> {
            var secililer = AdminGarsonCard.getSelectedCards();

            if (secililer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Garson seç!");
                return;
            }

            int r = JOptionPane.showConfirmDialog(
                    this,
                    secililer.size() + " garson silinsin mi?",
                    "Onay",
                    JOptionPane.YES_NO_OPTION
            );

            if (r != JOptionPane.YES_OPTION) return;

            for (AdminGarsonCard c : secililer) {
                KullaniciServisi.getInstance().garsonSil(c.getGarson().getKullaniciAdi());
            }

            AdminGarsonCard.clearSelection();
            doldur();
        });


        doldur();
        setVisible(true);
    }

    private void doldur() {
        grid.removeAll();

        for (Kullanici k : KullaniciServisi.getInstance().getGarsonlar().values()) {
            grid.add(new AdminGarsonCard(k, this));
        }

        grid.revalidate();
        grid.repaint();
    }
}
