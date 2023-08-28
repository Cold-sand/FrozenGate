package top.coldsand.frozengate.account;

import top.coldsand.frozengate.data.StorageInterface;
import top.coldsand.frozengate.data.StorageManager;
import top.coldsand.frozengate.tool.EncryptionUtils;

import java.util.UUID;

/**
 * AccountService class
 * 有关玩家服务的类
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public class AccountService {

    private static final StorageInterface STORAGE = StorageManager.get();


     /**
      *注册服务，加密玩家密码并向数据库中添加用户信息
      *
      * @param playerUuid 玩家UUID
      * @param userPassword 玩家传入的密码
      *
      * @return 注册是否成功
      */
    public static boolean playerRegister(UUID playerUuid, String userPassword) {
        STORAGE.insertAccount(playerUuid, EncryptionUtils.EncryptedUserPassword(userPassword, EncryptionUtils.getSalt()));

        return true;
    }

     /**
      * 修改用户密码
      *
      * @param playerUuid 玩家UUID
      * @param newPassword 用户新设置的密码
      *
      * @return 修改密码是否成功
      */

     public static boolean changePassword(UUID playerUuid, String newPassword) {
        String encryptedPassword = EncryptionUtils.EncryptedUserPassword(newPassword, EncryptionUtils.getSalt());

         return STORAGE.updatePassword(playerUuid, encryptedPassword);
     }

     /**
      * 检查玩家密码是否正确
      *
      * @param playerUuid 玩家UUID
      * @param userPassword 玩家传入的密码
      *
      * @return 玩家密码是否正确
      */

     public static boolean checkPassword(UUID playerUuid, String userPassword) {
         String key = STORAGE.getUserEncryptedPassword(playerUuid);
         String[] saltedHashKey = key.split("\\$");

         return EncryptionUtils.EncryptedUserPassword(userPassword, saltedHashKey[1]).equals(key);
    }
}
