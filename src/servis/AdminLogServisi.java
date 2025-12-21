package servis;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminLogServisi {

    private static final Path LOG_PATH = Paths.get("logs", "admin_log.txt");
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void log(String baslik, String mesaj) {
        try {
            Files.createDirectories(LOG_PATH.getParent());

            String satir = "[" + LocalDateTime.now().format(FMT) + "] "
                    + baslik + " - " + mesaj;

            try (BufferedWriter w = Files.newBufferedWriter(
                    LOG_PATH,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            )) {
                w.write(satir);
                w.newLine();
            }
        } catch (IOException e) {
            // log yazılamadıysa uygulama çökmesin
            e.printStackTrace();
        }
    }
}