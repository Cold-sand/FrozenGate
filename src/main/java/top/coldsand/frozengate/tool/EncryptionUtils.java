package top.coldsand.frozengate.tool;

import top.coldsand.frozengate.data.yaml.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * EncryptionUtils Class
 * 有关加密的类
 *
 * @author Cold_sand
 *
 */
public class EncryptionUtils {
    public static String getSalt() { //随机盐生成器
        @SuppressWarnings("SpellCheckingInspection") String str = "Tc4xh98tIHWMqkOlzPU1ua3dXwQeSsVvBFERCYbZ7LmiD6Kry0ngGpNf2J5jAo";
        StringBuilder salt = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i <= Config.INSTANCE.getSaltLength(); i++) {
            char randomChar = str.charAt(r.nextInt(50));
            salt.append(randomChar);
        }
        return salt.toString();
    }

    /**
     * MD5加密
     *
     * @param input 待加密字符串
     * @return MD5加密后的值
     */
    private static String getMd5(String input) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");

            md.update(input.getBytes());

            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密玩家密码，并加盐
     *
     * @param userPassword 玩家传入的密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public static String EncryptedUserPassword(String userPassword, String salt) {
        return getMd5(userPassword + salt) + "$" + salt;
    }

}