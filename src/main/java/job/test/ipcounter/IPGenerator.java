package job.test.ipcounter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class IPGenerator {
    private static final long MEGABYTE = 1024L * 1024L;
    private static final long TARGET_SIZE_GB = 30L;
    private static final long TARGET_SIZE_BYTES = TARGET_SIZE_GB * 1024L * 1024L * 1024L;
    private static final long REPORT_INTERVAL_MB = 200L;
    private static final long REPORT_INTERVAL_BYTES = REPORT_INTERVAL_MB * MEGABYTE;

    public static void main(String[] args) {
        final File outputFile = new File("ip.txt");

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            final Random random = new Random();
            long bytesWritten = 0;
            long lastReportedBytes = 0;

            while (bytesWritten < TARGET_SIZE_BYTES) {
                final String ipAddress = generateRandomIPAddress(random);
                writer.write(ipAddress);
                writer.newLine();

                bytesWritten += 15 + System.lineSeparator().getBytes().length; // Приблизительный размер строки в байтах

                if (bytesWritten - lastReportedBytes >= REPORT_INTERVAL_BYTES) {
                    System.out.println("Сгенерировано " + bytesToMegabytes(bytesWritten) + " мегабайт данных.");
                    lastReportedBytes = bytesWritten;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Генерация завершена. Файл создан: " + outputFile.getAbsolutePath());
    }

    private static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }


    private static String generateRandomIPAddress(final Random random) {
        final StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            ipAddress.append(random.nextInt(256));
            if (i < 3) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }
}