package view;

import model.Masa;
import servis.MasaServisi;
import view_components.BackgroundPanel;
import view_components.MasaCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class GarsonPanel extends JFrame {

    public static GarsonPanel INSTANCE;   // 🔥 Global referans

    private MasaServisi masaServisi;
    private java.util.List<MasaCard> kartlar = new ArrayList<>();

    public GarsonPanel() {

        INSTANCE = this; // 🔥 diğer ekranlar buradan erişiyor
        masaServisi = MasaServisi.getInstance();

        setTitle("Garson Paneli");
        setSize(900, 700);
        setLocationRelativeTo(null);

        // ❗ ÇOK ÖNEMLİ
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        setLayout(new BorderLayout());

        BackgroundPanel anaPanel = new BackgroundPanel("icons/garson_bg.jpg");
        anaPanel.setLayout(new BorderLayout());
        add(anaPanel, BorderLayout.CENTER);

        // =================== ÜST BAR ===================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JButton btnGeri = new JButton("Geri");
        btnGeri.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGeri.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JLabel baslik = new JLabel("Garson Girişi", SwingConstants.CENTER);
        baslik.setFont(new Font("Segoe UI", Font.BOLD, 36));
        baslik.setForeground(Color.WHITE);

        topBar.add(btnGeri, BorderLayout.WEST);
        topBar.add(baslik, BorderLayout.CENTER);

        anaPanel.add(topBar, BorderLayout.NORTH);
        

        // =================== MASA GRID ===================
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 25, 25));
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

    // 🔥 SiparisDetayFrame burayı kullanıyor
    public MasaServisi getMasaServisi() {
        return masaServisi;
    }

    // ======================================================
    // 🔥 MASA RENKLERİ VE DURUMLARI ANINDA GÜNCELLENSİN
    // ======================================================
    public void yenileMasalar() {
        for (MasaCard card : kartlar) {
            card.guncelle();
        }
        revalidate();
        repaint();
    }
}
