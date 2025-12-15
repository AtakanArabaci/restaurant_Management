package view;

import model.Masa;
import servis.OdenmisSiparisServisi;
import view_components.BackgroundPanel;
import model.OdenmisSiparis;

import model.Siparis;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OdemeFrame extends JFrame {

    private Masa masa;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblToplam;

    public OdemeFrame(Masa masa) {

        this.masa = masa;

        setTitle("Ödeme Ekranı - Masa " + masa.getMasaNo());
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel arkaPlan = new BackgroundPanel("icons/garson_bg.jpg");
        arkaPlan.setLayout(new BorderLayout());
        add(arkaPlan);

        // ================== ÜST BAR ==================
     // ================== ÜST BAR ==================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblBaslik = new JLabel(masa.getMasaNo() + ". Masa - Ödeme Ekranı");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);

        // ---- GERİ BUTONU (ikonlu modern tasarım) ----
        ImageIcon iconGeri = new ImageIcon("icons/ger.png");
        Image geriImg = iconGeri.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon geriIcon = new ImageIcon(geriImg);

        JButton btnGeri = new JButton("  Geri", geriIcon);
        btnGeri.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnGeri.setIconTextGap(10);

        btnGeri.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setBackground(new Color(255, 107, 107));
        btnGeri.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGeri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnGeri.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnGeri.setBackground(new Color(255, 76, 76)); }
            public void mouseExited(MouseEvent e) { btnGeri.setBackground(new Color(255, 107, 107)); }
        });

        btnGeri.addActionListener(e -> dispose());

        topBar.add(lblBaslik, BorderLayout.WEST);
        topBar.add(btnGeri, BorderLayout.EAST);
        arkaPlan.add(topBar, BorderLayout.NORTH);

        // ================== TABLO PANELİ (ŞEFFAF CAM) ==================
        JPanel tableHolder = new JPanel(new BorderLayout());
        tableHolder.setOpaque(false);
        tableHolder.setBorder(BorderFactory.createEmptyBorder(40, 50, 20, 50));

        JPanel glassPanel = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 160));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // ================== TABLO ==================
        model = new DefaultTableModel(new Object[]{"Ürün", "Adet", "Fiyat", "Toplam"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        glassPanel.add(scroll, BorderLayout.CENTER);
        tableHolder.add(glassPanel, BorderLayout.CENTER);
        arkaPlan.add(tableHolder, BorderLayout.CENTER);

        // ================== ALT PANEL ==================
        JPanel altPanel = new JPanel(new BorderLayout());
        altPanel.setOpaque(false);
        altPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        lblToplam = new JLabel("Toplam: 0 ₺");
        lblToplam.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblToplam.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton btnKapat = createModernButton("Hesabı Kapat");
        buttonPanel.add(btnKapat);

        altPanel.add(lblToplam, BorderLayout.WEST);
        altPanel.add(buttonPanel, BorderLayout.EAST);
        arkaPlan.add(altPanel, BorderLayout.SOUTH);

        // ================== BUTON AKSİYON ==================
        btnKapat.addActionListener(e -> odemePopupAc());

        tabloyuDoldur();
        setVisible(true);
    }

    // ================== MODERN TURUNCU GRADIENT BUTON ==================
    private JButton createModernButton(String text) {
        JButton btn = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(232, 137, 81),
                        getWidth(), getHeight(), new Color(205, 112, 55)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 35, 12, 35));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ============================================================
    //       MODERN YEŞİL / KIRMIZI ÖDEME POPUP
    // ============================================================
    private void odemePopupAc() {

        if (masa.getSiparisler().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bu masada sipariş yok!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Onay", true);
        dialog.setSize(350, 180);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel soru = new JLabel("Ödeme alındı mı?", SwingConstants.CENTER);
        soru.setFont(new Font("Segoe UI", Font.BOLD, 18));
        dialog.add(soru, BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton evetBtn = new JButton("Evet");
        JButton hayirBtn = new JButton("Hayır");

        // YEŞİL EVET
        evetBtn.setBackground(new Color(46, 204, 113));
        evetBtn.setForeground(Color.WHITE);
        evetBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // KIRMIZI HAYIR
        hayirBtn.setBackground(new Color(231, 76, 60));
        hayirBtn.setForeground(Color.WHITE);
        hayirBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));

        buttons.add(evetBtn);
        buttons.add(hayirBtn);
        dialog.add(buttons, BorderLayout.SOUTH);

        evetBtn.addActionListener(a -> {
            dialog.dispose();
            islemiBitir();
        });

        hayirBtn.addActionListener(a -> dialog.dispose());

        dialog.setVisible(true);
    }

    // ============================================================
    //       ÖDEME TAMAMLAMA (PDF + MASA TEMİZLEME)
    // ============================================================
    private void islemiBitir() {

        // 🔥 ÖDENMİŞ SİPARİŞLERİ KAYDET (SABİT ZAMANLA)
        for (Siparis s : masa.getSiparisler()) {
            OdenmisSiparisServisi.getInstance().ekle(
                    new OdenmisSiparis(
                            masa.getMasaNo(),
                            s.getUrun().getAd(),
                            s.getAdet(),
                            s.getToplam(),
                            java.time.LocalDateTime.now() // ✅ KRİTİK EK
                    )
            );
        }

        pdfOlustur();

        // 🔥 MASA TEMİZLE (global stok + durum)
        masa.odemeAl();

        if (GarsonPanel.INSTANCE != null)
            GarsonPanel.INSTANCE.yenileMasalar();

        JOptionPane.showMessageDialog(this,
                "Ödeme alındı!",
                "Başarılı",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    // ================== TABLOYU DOLDUR ==================
    private void tabloyuDoldur() {
        model.setRowCount(0);

        for (Siparis s : masa.getSiparisler()) {
            double fiyat = s.getUrun().getFiyat();
            double toplam = fiyat * s.getAdet();

            model.addRow(new Object[]{
                    s.getUrun().getAd(),
                    s.getAdet(),
                    fiyat + " ₺",
                    toplam + " ₺"
            });
        }

        lblToplam.setText("Toplam: " + hesaplaToplam() + " ₺");
    }

    private double hesaplaToplam() {
        return masa.getSiparisler().stream()
                .mapToDouble(s -> s.getUrun().getFiyat() * s.getAdet())
                .sum();
    }


    // ================== AŞAĞIDAN SONRA ***PDF KODUNA DOKUNMADIM*** ==================
    // ================== PDF OLUŞTURMA – AYNEN SENDEKİ GİBİ ==================

    private String fisNumarasiGetir() throws IOException {
        String filePath = System.getProperty("user.home")
                + File.separator + "Desktop" + File.separator + "fis_numaralari.txt";

        File file = new File(filePath);
        if (!file.exists()) file.createNewFile();

        String bugun = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int sonSira = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line, last = null;
            while ((line = br.readLine()) != null) last = line;

            if (last != null && last.startsWith(bugun + "-")) {
                sonSira = Integer.parseInt(last.split("-")[1]);
            }
        }

        int yeni = sonSira + 1;
        String fisNo = bugun + "-" + String.format("%03d", yeni);

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(fisNo);
            bw.newLine();
        }

        return fisNo;
    }

    private void pdfOlustur() {

        try {

            LocalDateTime now = LocalDateTime.now();
            String tarih = now.toLocalDate().toString();
            String saat = now.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            String fisNo = fisNumarasiGetir();

            String path = System.getProperty("user.home")
                    + "\\Desktop\\Masa_" + masa.getMasaNo() + "_Fis.pdf";

            Rectangle fisBoyutu = new Rectangle(226, 900);
            Document document = new Document(fisBoyutu, 10, 10, 10, 10);

            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            BaseFont baseFont;
            InputStream fontStream = null;
            try {
                fontStream = getClass().getClassLoader().getResourceAsStream("fonts/arial.ttf");
                if (fontStream != null) {
                    byte[] fontBytes = fontStream.readAllBytes();
                    baseFont = BaseFont.createFont(
                            "arial.ttf",
                            BaseFont.IDENTITY_H,
                            BaseFont.EMBEDDED,
                            true,
                            fontBytes,
                            null
                    );
                } else {
                    baseFont = BaseFont.createFont(
                            BaseFont.HELVETICA,
                            BaseFont.WINANSI,
                            BaseFont.NOT_EMBEDDED
                    );
                }
            } finally {
                if (fontStream != null) {
                    try { fontStream.close(); } catch (IOException ignored) {}
                }
            }

            com.itextpdf.text.Font normal = new com.itextpdf.text.Font(baseFont, 9);
            com.itextpdf.text.Font bold   = new com.itextpdf.text.Font(baseFont, 11, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font title  = new com.itextpdf.text.Font(baseFont, 14, com.itextpdf.text.Font.BOLD);

            Paragraph p = new Paragraph("UKA RESTAURANT\n", title);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            p = new Paragraph(
                    "Adres: İnönü Üniversitesi Kampüsü\n" +
                    "Tel: 05433462536\n",
                    normal
            );
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            addLine(document, normal);

            Paragraph info = new Paragraph(
                    "Tarih : " + tarih + "     Saat : " + saat +
                            "\nFiş No : " + fisNo +
                            "\nMasa : " + masa.getMasaNo(),
                    normal
            );
            info.setAlignment(Element.ALIGN_CENTER);
            document.add(info);

            addLine(document, normal);

            Paragraph titleProducts = new Paragraph("Ürün        Adet   Fiyat   Toplam\n", bold);
            titleProducts.setAlignment(Element.ALIGN_CENTER);
            document.add(titleProducts);

            addLine(document, normal);

            double araToplam = 0;

            for (Siparis s : masa.getSiparisler()) {
                double fiyat = s.getUrun().getFiyat();
                double toplam = fiyat * s.getAdet();
                araToplam += toplam;

                String satir = String.format("%-10s %5d %7.2f %8.2f",
                        s.getUrun().getAd(), s.getAdet(), fiyat, toplam);

                Paragraph pSatir = new Paragraph(satir, normal);
                pSatir.setAlignment(Element.ALIGN_CENTER);
                document.add(pSatir);
            }

            addLine(document, normal);

            double kdv   = araToplam * 0.10;
            double genel = araToplam + kdv;

            Paragraph p1 = new Paragraph("Ara Toplam : " + String.format("%.2f", araToplam), bold);
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);

            Paragraph p2 = new Paragraph("KDV (%10) : " + String.format("%.2f", kdv), bold);
            p2.setAlignment(Element.ALIGN_CENTER);
            document.add(p2);

            Paragraph p3 = new Paragraph("GENEL TOPLAM : " + String.format("%.2f", genel), bold);
            p3.setAlignment(Element.ALIGN_CENTER);
            document.add(p3);

            addLine(document, normal);

            Paragraph thanks = new Paragraph(
                    "Afiyet olsun!\n" +
                    "Bizi tercih ettiğiniz için teşekkür ederiz.",
                    bold
            );
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);

            document.close();

            JOptionPane.showMessageDialog(this, "PDF başarıyla oluşturuldu!\n" + path);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "PDF oluşturulamadı!\n" + ex.getMessage());
        }
    }

    private void addLine(Document doc, com.itextpdf.text.Font f) throws Exception {
        Paragraph p = new Paragraph("--------------------------------", f);
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
    }
}
