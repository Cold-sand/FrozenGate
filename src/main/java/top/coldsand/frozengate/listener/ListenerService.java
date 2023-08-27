package top.coldsand.frozengate.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import top.coldsand.frozengate.account.LoginService;

/**
 * ListenerService class
 * 有关监听事件的服务类
 *
 * @author Cold_sand
 * @date 2023/8/16
 */
public class ListenerService {
    public static boolean isNotLogin(Player player) {
        return !LoginService.isLogin(player.getUniqueId());
    }

    /**
     *判断是否撤销实体事件
     *
     * @param entity 传入实例
     * @return 如果继承player，则强转并执行islogin方法，否则返回flase
     */
    public static boolean shouldCancelEnityEvent(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            return isNotLogin(player);
        }
        return false;
    }

}

