package net.eugenpaul.adventofcode.helper;

public final class HexConverter {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

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
}
