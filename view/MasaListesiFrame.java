package view;

import model.Masa;
import servis.MasaServisi;

import javax.swing.*;
import java.awt.*;

public class MasaListesiFrame extends JFrame {

    public MasaListesiFrame() {

        setTitle("Masalara Göre Siparişler");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel grid = new JPanel(new GridLayout(3, 3, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        MasaServisi servis = MasaServisi.getInstance();

        for (Masa masa : servis.getMasalar().values()) {
            JButton btn = new JButton("Masa " + masa.getMasaNo());
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));

            btn.addActionListener(e ->
                new MasaDetayFrame(masa, MasaDetayModu.SORGU)
            );

            grid.add(btn);
        }

        add(grid);
        setVisible(true);
    }
}
