package view;

import javax.swing.*;

public class AdminPanel extends JFrame {

    public AdminPanel() {
        setTitle("Admin Paneli");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Admin Paneli (tasarım sonra doldurulacak)", SwingConstants.CENTER);
        add(lbl);

        setVisible(true);
    }
}
