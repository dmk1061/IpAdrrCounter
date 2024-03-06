package job.test.ipcounter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IPGenerator2 {
    private static final long MEGABYTE = 1024L * 1024L;
    private static final long TARGET_SIZE_GB = 3L;
    private static final long TARGET_SIZE_BYTES = TARGET_SIZE_GB * 1024L * 1024L * 1024L;
    private static final long REPORT_INTERVAL_MB = 200L;
    private static final long REPORT_INTERVAL_BYTES = REPORT_INTERVAL_MB * MEGABYTE;

    public static void main(String[] args) {
        final File outputFile = new File("ip3.txt");

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            long bytesWritten = 0;
            long lastReportedBytes = 0;
            long counter =0;

            for (int i = 1; bytesWritten < TARGET_SIZE_BYTES; i++) {
                final String ipAddress = generateIPAddress(i);
                writer.write(ipAddress);
                writer.newLine();
                counter++;
                bytesWritten += ipAddress.getBytes().length;

                if (bytesWritten - lastReportedBytes >= REPORT_INTERVAL_BYTES) {
                    System.out.println("Сгенерировано " + bytesToMegabytes(bytesWritten) + " мегабайт данных.");

                    lastReportedBytes = bytesWritten;
                }

            }
            System.out.println(counter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Генерация завершена. Файл создан: " + outputFile.getAbsolutePath());
    }

    private static long bytesToMegabytes(final long bytes) {
        return bytes / MEGABYTE;
    }

    private static String generateIPAddress(int counter) {
        final int octet1 = (counter >> 24) & 0xFF;
        final int octet2 = (counter >> 16) & 0xFF;
        final int octet3 = (counter >> 8) & 0xFF;
        final int octet4 = counter & 0xFF;

        return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
    }
}