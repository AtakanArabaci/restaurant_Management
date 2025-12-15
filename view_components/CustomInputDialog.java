package view_components;

import javax.swing.*;
import java.awt.*;

public class CustomInputDialog extends JDialog {

    private int result = -1;

    public CustomInputDialog(JFrame parent, String urunAdi, int mevcutAdet, String kategori) {

        super(parent, true);

        setSize(380, 220);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lbl = new JLabel(urunAdi + " için yeni adet:");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField txt = new JTextField(String.valueOf(mevcutAdet));
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txt.setHorizontalAlignment(SwingConstants.CENTER);

        JButton okBtn = createButton("Tamam");
        JButton cancelBtn = createButton("İptal");

        okBtn.addActionListener(e -> {
            try {
                String text = txt.getText().trim();
                if (!text.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Adet sadece sayı olabilir!");
                    return;
                }

                int value = Integer.parseInt(text);
                if (value < 1) {
                    JOptionPane.showMessageDialog(this, "Adet 1'den küçük olamaz!");
                    return;
                }

                result = value;
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hatalı giriş!");
            }
        });

        cancelBtn.addActionListener(e -> {
            result = -1;
            dispose();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(txt, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(true);
        btn.setBackground(new Color(200, 120, 60));
        return btn;
    }

    public int getResult() { return result; }
}
