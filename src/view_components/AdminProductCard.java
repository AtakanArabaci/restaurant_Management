package view_components;

import model.Urun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.net.URL;

public class AdminProductCard extends JPanel {

    // 🔥 ÇOKLU SEÇİM SETİ
    private static final Set<AdminProductCard> selectedCards = new HashSet<>();

    private Urun urun;

    public AdminProductCard(Urun urun) {
        this.urun = urun;

        setPreferredSize(new Dimension(260, 200));
        setLayout(new BorderLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(Color.WHITE);
        setOpaque(true);

        resetBorder();

        // ===================== RESİM =====================
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = null;

        // 1️⃣ Dosya sisteminden dene
        String yol = System.getProperty("user.dir")
                + File.separator
                + urun.getResimYolu();

        File f = new File(yol);
        if (f.exists()) {
            icon = new ImageIcon(yol);
        } else {
            // 2️⃣ Classpath'ten dene (icons/)
            URL res = getClass().getClassLoader().getResource(urun.getResimYolu());
            if (res != null) {
                icon = new ImageIcon(res);
            }
        }

        ImageIcon finalIcon = icon; // 🔥 lambda için sabit referans

        if (finalIcon != null && finalIcon.getIconWidth() > 0) {

            imgLabel.addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    Image img = finalIcon.getImage().getScaledInstance(
                            imgLabel.getWidth(),
                            imgLabel.getHeight(),
                            Image.SCALE_SMOOTH
                    );
                    imgLabel.setIcon(new ImageIcon(img));
                }
            });

        } else {
            imgLabel.setText("Resim yok");
            imgLabel.setForeground(Color.GRAY);
        }

        add(imgLabel, BorderLayout.CENTER);


        // ===================== ÜRÜN ADI =====================
        JLabel lbl = new JLabel(urun.getAd(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        add(lbl, BorderLayout.SOUTH);

        // ===================== SEÇİM =====================
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedCards.contains(AdminProductCard.this)) {
                    selectedCards.remove(AdminProductCard.this);
                    resetBorder();
                } else {
                    selectedCards.add(AdminProductCard.this);
                    setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                }
            }
        });
    }

    private void resetBorder() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }

    public Urun getUrun() {
        return urun;
    }

    public static Set<AdminProductCard> getSelectedCards() {
        return selectedCards;
    }

    public static void clearSelection() {
        for (AdminProductCard c : selectedCards) {
            c.resetBorder();
        }
        selectedCards.clear();
    }
}
