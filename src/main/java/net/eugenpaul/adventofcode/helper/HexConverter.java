package net.eugenpaul.adventofcode.helper;

public final class HexConverter {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String[] hexToBinary = new String[] { //
            "0000", //
            "0001", //
            "0010", //
            "0011", //
            "0100", //
            "0101", //
            "0110", //
            "0111", //
            "1000", //
            "1001", //
            "1010", //
            "1011", //
            "1100", //
            "1101", //
            "1110", //
            "1111",//
    };

    private HexConverter() {

    }

    /**
     * Convert bytes to HEX-String: 0x1234 -> "1234"
     * 
     * @param bytes input
     * @return HEX-String
     */
    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Convert Hex to binary: "1" -> "0001", "01" -> "00000001"
     * 
     * @param input
     * @return
     */
    public static String hexCharsToBinaryString(String input) {
        StringBuilder response = new StringBuilder(input.length() * 4);
        input.chars().forEach(v -> response.append(hexToBinary[(v > '9') ? v - 'a' + 10 : v - '0']));
        return response.toString();
    }
}
