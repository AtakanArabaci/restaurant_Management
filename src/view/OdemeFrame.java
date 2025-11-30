package view;

import javax.swing.*;
import model.Masa;

public class OdemeFrame extends JFrame {

    public OdemeFrame(Masa masa) {
        setTitle("Ödeme - Masa " + masa.getMasaNo());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Ödeme ekranı (sonra dolduracağız)", SwingConstants.CENTER));
    }
}
