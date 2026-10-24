package view;

import model.Masa;
import servis.MasaServisi;
import view_components.BackgroundPanel;
import view_components.MasaCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;


public class GarsonPanel extends JFrame {

    public static GarsonPanel INSTANCE;   // 🔥 Global referans

    private MasaServisi masaServisi;
    private java.util.List<MasaCard> kartlar = new ArrayList<>();

    public GarsonPanel() {

        INSTANCE = this; // 🔥 diğer ekranlar buradan erişiyor
        masaServisi = MasaServisi.getInstance();

        setTitle("Garson Paneli");
        setSize(900, 700);
        setResizable(false);

        setLocationRelativeTo(null);

        // ❗ ÇOK ÖNEMLİ
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        setLayout(new BorderLayout());

        BackgroundPanel anaPanel = new BackgroundPanel("icons/garson_bg.jpg");
        anaPanel.setLayout(new BorderLayout());
        add(anaPanel, BorderLayout.CENTER);

        // =================== ÜST BAR ===================
     // ================== TOP BAR ==================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // BAŞLIK
        JLabel baslik = new JLabel("Garson Girişi", SwingConstants.CENTER);
        baslik.setFont(new Font("Segoe UI", Font.BOLD, 36));
        baslik.setForeground(Color.WHITE);

        // ICON (ger.png)
        ImageIcon geriIcon = new ImageIcon("icons/ger.png");
        Image geriImg = geriIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        geriIcon = new ImageIcon(geriImg);

        // GERİ BUTONU
        JButton btnGeri = new JButton("   Geri", geriIcon);
        btnGeri.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGeri.setBackground(new Color(255, 107, 107));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFocusPainted(false);
        btnGeri.setBorderPainted(false);
        btnGeri.setOpaque(true);
        btnGeri.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGeri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGeri.setIconTextGap(10);

        // HOVER EFEKTİ
        btnGeri.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnGeri.setBackground(new Color(255, 76, 76));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnGeri.setBackground(new Color(255, 107, 107));
            }
        });

        // TIKLAMA (LOGIN'E DÖN)
        btnGeri.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        // YERLEŞTİRME
        topBar.add(baslik, BorderLayout.CENTER);
        topBar.add(btnGeri, BorderLayout.EAST);

        // ANA PANEL’E EKLE
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