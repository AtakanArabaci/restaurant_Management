package view;

import model.OdenmisSiparis;
import servis.OdenmisSiparisServisi;
import view_components.BackgroundPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

// ===== iText =====
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.time.LocalDateTime;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.draw.LineSeparator;


// ===== Mail =====
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

public class GunSonuRaporuFrame extends JFrame {

    private JFrame parent;
    private DefaultTableModel model;
    private JLabel lblToplam;
    private JComboBox<LocalDate> cmbTarih;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public GunSonuRaporuFrame(JFrame parent) {
        this.parent = parent;

        setTitle("Gün Sonu Raporu");
        setSize(950, 650);
        setResizable(false);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("icons/front.png");
        bg.setLayout(new BorderLayout());
        setContentPane(bg);

        // ===================== ÜST BAR =====================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(0, 0, 0, 180));
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblBaslik = new JLabel("Gün Sonu Raporu");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblBaslik.setForeground(Color.WHITE);

        JButton btnPdf = new JButton("PDF Al");
        JButton btnGeri = new JButton("Geri");

        btnPdf.addActionListener(e -> {
            LocalDate secilen = (LocalDate) cmbTarih.getSelectedItem();
            if (secilen != null) pdfOlustur(secilen);
        });

        btnGeri.addActionListener(e -> {
            dispose();
            parent.setVisible(true);
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(btnPdf);
        rightPanel.add(btnGeri);

        topBar.add(lblBaslik, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        bg.add(topBar, BorderLayout.NORTH);

        // ===================== MERKEZ =====================
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        bg.add(center, BorderLayout.CENTER);

        // ===================== TARİH =====================
        JPanel tarihPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        tarihPanel.setOpaque(false);

        JLabel lblTarih = new JLabel("Tarih");
        lblTarih.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        lblTarih.setForeground(Color.WHITE);


        cmbTarih = new JComboBox<>();
        OdenmisSiparisServisi.getInstance()
                .getMevcutGunler()
                .forEach(cmbTarih::addItem);
     // 🔥 TARİH FORMATINI dd.MM.yyyy YAP
        cmbTarih.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (value instanceof LocalDate date) {
                    setText(formatter.format(date)); // 10.12.2025
                }

                return this;
            }
        });


        cmbTarih.setFont(new Font("Segoe UI", Font.PLAIN, 15));


        tarihPanel.add(lblTarih);
        tarihPanel.add(cmbTarih);
        center.add(tarihPanel, BorderLayout.NORTH);

        // ===================== TABLO =====================
        model = new DefaultTableModel(
                new Object[]{"Ürün", "Toplam Adet", "Toplam Kazanç"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(30, 30, 30));
        header.setForeground(Color.WHITE);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0
                            ? new Color(245, 245, 245)
                            : Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        center.add(scroll, BorderLayout.CENTER);

        // ===================== ALT TOPLAM =====================
     
     // ===================== ALT TOPLAM (FOOTER BAR) =====================
        JPanel footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(950, 70));
        footer.setBackground(new Color(0, 0, 0, 200)); // daha koyu
        footer.setOpaque(true);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        lblToplam = new JLabel("Toplam: 0 ₺");
        lblToplam.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblToplam.setForeground(Color.WHITE);

        lblToplam.setHorizontalAlignment(SwingConstants.RIGHT);

        footer.add(lblToplam, BorderLayout.EAST);

        // ❗ ÖNEMLİ: bg'nin SOUTH'una ekle
        bg.add(footer, BorderLayout.SOUTH);


        cmbTarih.addActionListener(e -> {
            LocalDate secilen = (LocalDate) cmbTarih.getSelectedItem();
            if (secilen != null) tabloyuDoldur(secilen);
        });

        if (cmbTarih.getItemCount() > 0)
            cmbTarih.setSelectedIndex(0);

        setVisible(true);
    }

    // ===================== TABLO DOLDUR =====================
    private void tabloyuDoldur(LocalDate tarih) {
        model.setRowCount(0);

        List<OdenmisSiparis> liste =
                OdenmisSiparisServisi.getInstance()
                        .getTariheGoreSirali()
                        .stream()
                        .filter(s -> s.getZaman().toLocalDate().equals(tarih))
                        .toList();

        Map<String, Integer> adetMap = new LinkedHashMap<>();
        Map<String, Double> tutarMap = new LinkedHashMap<>();
     // ================= EN ÇOK SATAN ÜRÜN =================
        String enCokSatanUrun = "";
        int maxAdet = 0;

        for (Map.Entry<String, Integer> entry : adetMap.entrySet()) {
            if (entry.getValue() > maxAdet) {
                maxAdet = entry.getValue();
                enCokSatanUrun = entry.getKey();
            }
        }



        double toplam = 0;

        for (OdenmisSiparis s : liste) {
            adetMap.put(s.getUrunAdi(),
                    adetMap.getOrDefault(s.getUrunAdi(), 0) + s.getAdet());
            tutarMap.put(s.getUrunAdi(),
                    tutarMap.getOrDefault(s.getUrunAdi(), 0.0) + s.getToplam());
            toplam += s.getToplam();
        }

        for (String urun : adetMap.keySet()) {
            model.addRow(new Object[]{
                    urun,
                    adetMap.get(urun),
                    String.format("%.2f ₺", tutarMap.get(urun))
            });
        }

        lblToplam.setText(
                formatter.format(tarih) +
                " Toplam Kazanç: " +
                String.format("%.2f ₺", toplam)
        );
    }

    // ===================== PDF OLUŞTUR =====================
    private void pdfOlustur(LocalDate tarih) {
        try {
            String formatliTarih = formatter.format(tarih);
            String dosyaAdi = "Gun_Sonu_Raporu_" + formatliTarih + ".pdf";

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(dosyaAdi));
            document.open();

            BaseFont bf = BaseFont.createFont(
                    "fonts/arial.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );

            com.itextpdf.text.Font baslikFont =
                    new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font normalFont =
                    new com.itextpdf.text.Font(bf, 12);


            document.add(new Paragraph(
                    "Gün Sonu Raporu - " + formatliTarih, baslikFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 2, 2});

            table.addCell(new PdfPCell(new Phrase("Ürün", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Toplam Adet", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Toplam Kazanç", normalFont)));

            List<OdenmisSiparis> liste =
                    OdenmisSiparisServisi.getInstance()
                            .getTariheGoreSirali()
                            .stream()
                            .filter(s -> s.getZaman().toLocalDate().equals(tarih))
                            .toList();

            double toplam = 0;
            Map<String, Integer> adetMap = new LinkedHashMap<>();
            Map<String, Double> tutarMap = new LinkedHashMap<>();

            for (OdenmisSiparis s : liste) {
                adetMap.put(s.getUrunAdi(),
                        adetMap.getOrDefault(s.getUrunAdi(), 0) + s.getAdet());
                tutarMap.put(s.getUrunAdi(),
                        tutarMap.getOrDefault(s.getUrunAdi(), 0.0) + s.getToplam());
                toplam += s.getToplam();
            }

            // ✅ EN ÇOK SATAN ÜRÜN (TANIMLAR BURADA OLACAK)
            String enCokSatanUrun = "-";
            int maxAdet = 0;

            for (Map.Entry<String, Integer> entry : adetMap.entrySet()) {
                if (entry.getValue() > maxAdet) {
                    maxAdet = entry.getValue();
                    enCokSatanUrun = entry.getKey();
                }
            }

            // tablo satırlarını ekleme (senin mevcut kodun)
            for (String urun : adetMap.keySet()) {
                table.addCell(new Phrase(urun, normalFont));
                table.addCell(new Phrase(String.valueOf(adetMap.get(urun)), normalFont));
                table.addCell(new Phrase(String.format("%.2f ₺", tutarMap.get(urun)), normalFont));
            }

            document.add(table);
            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Günün En Çok Satan Ürünü: " + enCokSatanUrun + " (" + maxAdet + " adet)",
                    normalFont
            ));

            document.add(new Paragraph(
                    "\nToplam Kazanç: " +
                    String.format("%.2f ₺", toplam),
                    baslikFont
            ));

            document.close();

            File pdfFile = new File(dosyaAdi);
            mailGonder(pdfFile, formatliTarih);

            JOptionPane.showMessageDialog(this,
                    "PDF oluşturuldu ve mail gönderildi.");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "PDF oluşturulamadı!",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== MAIL GÖNDER =====================
    private void mailGonder(File pdfDosyasi, String formatliTarih) {
        try {
            Properties config = new Properties();
            config.load(new FileInputStream("src/mail/mail.properties"));

            final String GONDEREN = config.getProperty("mail.username");
            final String SIFRE = config.getProperty("mail.password");
            final String ALICI = config.getProperty("mail.to");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication
                        getPasswordAuthentication() {
                            return new PasswordAuthentication(GONDEREN, SIFRE);
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(GONDEREN));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(ALICI)
            );
            message.setSubject("Gün Sonu Raporu - " + formatliTarih);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(
                    "Merhaba,\n\n" +
                    formatliTarih +
                    " tarihli gün sonu raporu PDF formatında ekte sunulmuştur.\n\n" +
                    "Bilgilerinize arz eder, iyi çalışmalar dileriz.\n\n" +
                    "UKA Restaurant"
            );

            MimeBodyPart filePart = new MimeBodyPart();
            DataSource source = new FileDataSource(pdfDosyasi);
            filePart.setDataHandler(new DataHandler(source));
            filePart.setFileName(pdfDosyasi.getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(filePart);

            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Mail gönderilemedi!",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
