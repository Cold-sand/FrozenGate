package top.coldsand.frozengate.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * PlayerListener class
 * 有关玩家的监听类
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public class PlayerListener implements Listener {
    /**
     * 监听玩家输入指令事件，在未登录的状态下拦截/login /register /changepassword指令外的指令
     */
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        boolean isNotLogin = ListenerService.isNotLogin(e.getPlayer());
        boolean isNotLoginCommand = !(e.getMessage().split(" ")[0].contains("login")
                || e.getMessage().split(" ")[0].contains("register")
                || e.getMessage().split(" ")[0].contains("changepassword"));
        if (isNotLogin && isNotLoginCommand){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.YELLOW + "你还没有登录，请登录后再使用其他命令");
        }
    }

    @EventHandler
    public void onPlayerTarget(EntityTargetEvent e) {
        if (ListenerService.shouldCancelEnityEvent(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    /**
     * 监听玩家聊天事件，在未登录的情况下撤销事件
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String separator = "/";

        if (event.getMessage().startsWith(separator)) {
            return;
        }
        event.setCancelled(ListenerService.isNotLogin(event.getPlayer()));
        event.getPlayer().sendMessage(ChatColor.YELLOW + "你还没有登录，请登录后再发送消息");
    }

     /**
     * 监听玩家移动事件，在玩家未登录的情况下撤销事件
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (ListenerService.isNotLogin(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    /**
     * 监听玩家按下左右键事件，在玩家未登录的情况下撤销此事件
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(ListenerService.isNotLogin(event.getPlayer()));
    }

    /**
     *监听玩家打开背包事件，在玩家未登录的情况下撤销事件
     */
    @EventHandler
    public void onPlayerInventory(InventoryOpenEvent event) {
        event.setCancelled(ListenerService.isNotLogin((Player) event.getPlayer()));
        event.getPlayer().sendMessage(ChatColor.YELLOW + "你还没有登录，请登录后再使用背包");
    }
}

