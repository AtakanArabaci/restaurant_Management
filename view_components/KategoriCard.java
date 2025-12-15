package view_components;

import model.Masa;
import view.YemekListeFrame;
import view.IcecekListeFrame;
import view.TatliListeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KategoriCard extends JPanel {

    private String kategoriAdi;
    private String imagePath;
    private Masa masa;

    public KategoriCard(String kategoriAdi, String imagePath, Masa masa) {
        this.kategoriAdi = kategoriAdi;
        this.imagePath = imagePath;
        this.masa = masa;

        setPreferredSize(new Dimension(260, 350));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // --- TIKLANINCA AÇILACAK EKRAN ---
                if (kategoriAdi.equalsIgnoreCase("Yemek")) {
                    new YemekListeFrame(masa);
                }
                else if (kategoriAdi.equalsIgnoreCase("İçecek")) {
                    new IcecekListeFrame(masa);
                }
                else if (kategoriAdi.equalsIgnoreCase("Tatlı")) {
                    new TatliListeFrame(masa);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(img, 0, 0, null);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0,0,0,90));
        g2.fillRect(0,0,getWidth(),getHeight());

        g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        g2.setColor(Color.WHITE);

        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(kategoriAdi)) / 2;
        int y = getHeight() - 40;

        g2.drawString(kategoriAdi, x, y);
    }
}
