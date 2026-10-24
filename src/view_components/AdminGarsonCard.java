package view_components;

import model.Kullanici;
import view.AdminGarsonDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.Set;

public class AdminGarsonCard extends JPanel {

    private static final Set<AdminGarsonCard> selectedCards = new LinkedHashSet<>();
    private final Kullanici garson;
    private final JLabel lbl; // 🔥 ARTIK SINIF SEVİYESİNDE

    public AdminGarsonCard(Kullanici garson, JFrame parent) {
        this.garson = garson;

        setPreferredSize(new Dimension(220, 120));
        setLayout(new GridBagLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(false);

        lbl = new JLabel(getGosterimAdi(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        add(lbl);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 1) {
                    toggleSelect();
                } 
                else if (e.getClickCount() == 2) {
                    // 🔥 MODAL DIALOG
                    new AdminGarsonDialog(parent, garson);

                    // 🔥 DIALOG KAPANINCA LABEL GÜNCELLE
                    lbl.setText(getGosterimAdi());
                    repaint();
                }
            }
        });
    }

    private String getGosterimAdi() {
        return (garson.getAdSoyad() == null || garson.getAdSoyad().isBlank())
                ? garson.getKullaniciAdi()
                : garson.getAdSoyad();
    }

    private void toggleSelect() {
        if (selectedCards.contains(this))
            selectedCards.remove(this);
        else
            selectedCards.add(this);

        repaint();
    }

    public static Set<AdminGarsonCard> getSelectedCards() {
        return selectedCards;
    }

    public static void clearSelection() {
        selectedCards.clear();
    }

    public Kullanici getGarson() {
        return garson;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        boolean secili = selectedCards.contains(this);

        g.setColor(secili
                ? new Color(255, 140, 80)
                : new Color(220, 140, 80, 210));

        g.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
    }
}