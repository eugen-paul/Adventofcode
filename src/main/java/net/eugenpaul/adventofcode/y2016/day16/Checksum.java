package net.eugenpaul.adventofcode.y2016.day16;

public class Checksum {
    private Checksum() {

    }

    public static String computeChecksum(String data) {
        String checksum = data;
        while (checksum.length() % 2 == 0) {
            checksum = doStep(checksum);
        }
        return checksum;
    }

    private static String doStep(String data) {
        StringBuilder response = new StringBuilder();
        for (int i = 0; i < data.length(); i += 2) {
            if (data.charAt(i) == data.charAt(i + 1)) {
                response.append('1');
            } else {
                response.append('0');
            }
        }
        return response.toString();
    }
}
