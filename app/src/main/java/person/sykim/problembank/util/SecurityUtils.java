package person.sykim.problembank.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {
    private static final String TAG = SecurityUtils.class.getSimpleName();

    public static String encrypt(String key, String cleartext) throws Exception {
//        byte[] rawKey = getRawKey(seed.getBytes());
//        System.out.println("encrypt: "+toHex(rawKey));
        byte[] result = encrypt(key.getBytes(), cleartext.getBytes());
        return toHex(result);
    }

    public static String decrypt(String key, String encrypted) throws Exception {
//        byte[] rawKey = getRawKey(seed.getBytes());
//        System.out.println("encrypt: "+toHex(rawKey));
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(key.getBytes(), enc);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        SecureRandom sr = new SecureRandom(seed);
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }

    public static String generateKey() {
        try {
            byte[] bytes = getRawKey(UUID.randomUUID().toString().getBytes());
            return toHex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }

    public static void main(String[] args) throws Exception {
        String key = generateKey();
        String encrypted = encrypt(key, "ZDVc+6501645");
        System.out.println(encrypted);
        encrypted = encrypt(key, "ZDVc+6501645");
        System.out.println(encrypted);
        encrypted = encrypt(key, "ZDVc+6501645");
        System.out.println(encrypted);
        System.out.println(decrypt(key, encrypted));
//        System.out.println(decrypt(key, "31CD9A711302552720EE49588BD7AF96"));
    }
    // 4988908906BD008E95D680632731BD00
}
