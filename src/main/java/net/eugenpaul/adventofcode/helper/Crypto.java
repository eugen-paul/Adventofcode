package net.eugenpaul.adventofcode.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Crypto {

    private static final String MD5_LOAD_ERROR = "Cannt load MD5";

    private Crypto() {

    }

    public static byte[] doMd5(String data) {
        return doMd5((data).getBytes());
    }

    public static byte[] doMd5(byte[] data) {
        return doMd5(data, 0, data.length);
    }

    public static byte[] doMd5(byte[] data, int offset, int len) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(MD5_LOAD_ERROR, e);
        }
        md.update(data, offset, len);
        return md.digest();
    }

    public static byte[] doMd5(byte[]... data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(MD5_LOAD_ERROR, e);
        }
        for (byte[] bs : data) {
            md.update(bs, 0, bs.length);
        }
        return md.digest();
    }
}
