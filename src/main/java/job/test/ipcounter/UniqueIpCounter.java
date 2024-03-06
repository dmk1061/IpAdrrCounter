package job.test.ipcounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

public class UniqueIpCounter {


        public static void main(String[] args) {
            final long start = System.currentTimeMillis();
            final String filePath = "ip3.txt";

            try (final BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
                final long uniqueCount = countUniqueIPs(reader);
                System.out.println("Total unique IP addresses: " + uniqueCount);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
            final long finish = System.currentTimeMillis() - start;
            System.out.println("Time: " + finish + " milliseconds");
        }

        private static long countUniqueIPs(final BufferedReader reader) throws IOException {
            long uniqueCount = 0;
            final BitSet positiveBitSet = new BitSet(1 << 23); // 2^24 bits
            final BitSet negativeBitSet = new BitSet(1 << 23);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                final int ip = ipToInt(line);
                if (ip > 0) {
                    if (!positiveBitSet.get(ip)) {
                        positiveBitSet.set(ip);
                        uniqueCount++;
                    }
                } else {
                    if (!negativeBitSet.get(Math.abs(ip))) {
                        negativeBitSet.set(Math.abs(ip));
                        uniqueCount++;
                    }
                }
            }
            return uniqueCount;
        }

    private static int ipToInt(final String ip) {
        final String[] parts = ip.split("\\.");
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) | Integer.parseInt(parts[i]);
        }
        return result;
    }
    }

