package top.coldsand.frozengate.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.coldsand.frozengate.account.LoginService;
import top.coldsand.frozengate.tool.SafePlayerLocation;

/**
 * LoginListener class
 * 有关玩家登录登出事件的监听类
 *
 * @author Cold_sand
 * @date 2023/8/16
 */
public class LoginListener implements Listener {
    /**
     *监听玩家登出事件，并执行玩家登出方法
     *
     * @param event event
     */
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event)
    {
        LoginService.playerLogout(event.getPlayer().getUniqueId());
    }

    /**
     * 监听玩家登入事件，传送到安全位置提醒玩家注册或登录
     *
     * @param event event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!SafePlayerLocation.checkLocationSafe(event.getPlayer().getWorld(), event.getPlayer().getLocation())) {
            event.getPlayer().sendMessage(ChatColor.YELLOW + "您的上线位置不安全");
            SafePlayerLocation.teleportPlayerToSafeLocation(event.getPlayer());
        }


        if (LoginService.isRegister(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage("欢迎回来！请输入/login 密码 登录服务器！:");
        } else {
            event.getPlayer().sendMessage("您尚未注册，请输入/register 密码 注册账号！");
        }
    }
}


