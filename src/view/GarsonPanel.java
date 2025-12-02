package view;

import model.Masa;
import servis.MasaServisi;
import view.components.MasaCard;
import view.components.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class GarsonPanel extends JFrame {

    public static GarsonPanel INSTANCE;  // 🔥 Global referans

    private MasaServisi masaServisi;
    private java.util.List<MasaCard> kartlar = new ArrayList<>();

    public GarsonPanel() {

        INSTANCE = this; // 🔥 Burada atanıyor

        masaServisi = new MasaServisi();

        setTitle("Garson Paneli");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        BackgroundPanel anaPanel = new BackgroundPanel("icons/garson_bg.jpg");
        anaPanel.setLayout(new BorderLayout());
        add(anaPanel, BorderLayout.CENTER);

        JLabel baslik = new JLabel("Garson Girişi", SwingConstants.CENTER);
        baslik.setFont(new Font("Segoe UI", Font.BOLD, 36));
        baslik.setForeground(Color.WHITE);
        anaPanel.add(baslik, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3, 25, 25));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        for (Map.Entry<Integer, Masa> entry : masaServisi.getMasalar().entrySet()) {
            Masa masa = entry.getValue();
            MasaCard card = new MasaCard(masa);
            kartlar.add(card);
            gridPanel.add(card);
        }

        anaPanel.add(gridPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // 🔥 MASA RENKLERİNİ TEKRAR ÇİZDİR
    public void yenileMasalar() {
        for (MasaCard card : kartlar) {
            card.guncelle();
        }
    }
}
