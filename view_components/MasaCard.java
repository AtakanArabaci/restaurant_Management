package view_components;

import model.Masa;
import view.KategoriSecimFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MasaCard extends JPanel {

    private Masa masa;
    private boolean hover = false;

    public MasaCard(Masa masa) {
        this.masa = masa;

        setPreferredSize(new Dimension(180, 180));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new KategoriSecimFrame(masa);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color bgColor;
        switch (masa.getDurum().toUpperCase()) {
            case "DOLU" -> bgColor = new Color(220, 40, 40);
            case "REZERVE" -> bgColor = new Color(255, 215, 0);
            default -> bgColor = new Color(0, 200, 80);
        }

        int shadow = hover ? 8 : 4;

        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRoundRect(shadow, shadow, getWidth() - shadow * 2,
                getHeight() - shadow * 2, 25, 25);

        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth() - shadow,
                getHeight() - shadow, 25, 25);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        g2.setColor(Color.BLACK);

        String text = "Masa " + masa.getMasaNo();
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 10;

        g2.drawString(text, x, y);

        g2.dispose();
    }

    // 🔥 MASA DURUMU DEĞİŞTİĞİNDE ANINDA RENK GÜNCELLENSİN
    public void guncelle() {
        revalidate();   // layout yenile
        repaint();      // görünümü yenile
    }
}
