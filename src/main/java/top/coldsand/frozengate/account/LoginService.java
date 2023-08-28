package top.coldsand.frozengate.account;

import top.coldsand.frozengate.data.StorageInterface;
import top.coldsand.frozengate.data.StorageManager;

import java.util.HashSet;
import java.util.UUID;

/**
 * LoginService class
 * 有关登录服务的类
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public class LoginService {
    /**
     * 已登录玩家列表
     */
    private static final HashSet<UUID> LOGIN_LIST = new HashSet<>();

    private static final StorageInterface STORAGE = StorageManager.get();

    /**
     * 玩家登录方法
     *
     * @param playerUuid 玩家UUID
     * @param userPassword  玩家传入的密码
     *
     * @return 玩家是否成功登录
     */
    public static boolean playerLogin(UUID playerUuid, String userPassword){

        if (AccountService.checkPassword(playerUuid, userPassword)){
            LOGIN_LIST.add(playerUuid);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 玩家登出方法
     *
     * @param playerUuid 玩家UUID
     */
    public static void playerLogout(UUID playerUuid) {
        if (!LOGIN_LIST.contains(playerUuid)) {
            return;
        }
        LOGIN_LIST.remove(playerUuid);
    }

    /**
     * 检查玩家是否登录
     *
     * @param playerUuid 玩家UUID
     *
     * @return 玩家是否登录
     */
    public static boolean isLogin(UUID playerUuid) {
        return LOGIN_LIST.contains(playerUuid);
    }

    /**
     * 检查玩家是否注册
     *
     * @param playerUuid 玩家UUID
     *
     * @return 玩家是否注册
     */
    public static boolean isRegister(UUID playerUuid) {
        return STORAGE.isRegistered(playerUuid);
    }


}

