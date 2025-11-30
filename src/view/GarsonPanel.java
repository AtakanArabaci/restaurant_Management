package view;

import controller.GarsonController;
import model.Masa;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GarsonPanel extends JFrame {

    public GarsonPanel() {

        setTitle("Garson Paneli");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 4, 10, 10));

        ArrayList<Masa> masalar = new GarsonController().masalariGetir();

        for (Masa m : masalar) {
            JButton btn = new JButton("Masa " + m.getMasaNo());

            if (m.getDurum().equals("BOŞ")) btn.setBackground(Color.GREEN);
            if (m.getDurum().equals("DOLU")) btn.setBackground(Color.RED);
            if (m.getDurum().equals("REZERVE")) btn.setBackground(Color.YELLOW);

            btn.addActionListener(e -> new MasaDetayFrame(m));

            add(btn);
        }

        setVisible(true);
    }
}
