package view;

import javax.swing.*;
import model.Masa;

public class SiparisDetayFrame extends JFrame {

    public SiparisDetayFrame(Masa masa) {
        setTitle("Sipariş Detayları - Masa " + masa.getMasaNo());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Sipariş detay ekranı (sonra dolduracağız)", SwingConstants.CENTER));
    }
}
