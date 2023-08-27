package top.coldsand.frozengate.data;

import java.util.UUID;

/**
 * StorageInterface class
 * 有关数据库存储服务接口
 *
 * @author Cold_sand
 * @date 2023/8/16
 */
public interface StorageInterface {

    /**
     * 初始化数据库操作
     *
     * @return {salt}${salted_password}
     */
    boolean setup();

    /**
     * 获取玩家密码哈希与盐值
     *
     * @param uuid 玩家UUID数据
     *
     * @return 玩家密码哈希值与盐值
     */
    String getUserEncryptedPassword(UUID uuid);

    /**
     *插入玩家信息
     *
     * @param uuid 玩家UUID数据
     * @param userEncryptedPassword 玩家密码哈希值与盐值
     *
     * @return 插入值数据操作是否成功
     */
    boolean insertAccount(UUID uuid, String userEncryptedPassword);

    /**
     * 更新玩家密码
     *
     * @param uuid 玩家UUID数据
     * @param userEncryptedPassword 玩家新密码哈希值与盐值
     *
     * @return 更新值数据库操作是否成功
     */
    boolean updatePassword(UUID uuid, String userEncryptedPassword);

    /**
     * 判断玩家是否注册
     *
     * @param uuid 玩家UUID数据
     *
     * @return 玩家是否注册
     */
    boolean isRegistered(UUID uuid);

}
