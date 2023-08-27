package top.coldsand.frozengate.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * BlockListener class
 * 有关方块的监听类
 *
 * @author Cold_sand
 * @date 2023/8/16
 */
public class BlockListener implements Listener {
    /**
     * 监听实体方式事件，在未登录的情况下撤销事件
     *
     * @param event 监听到的实例
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (ListenerService.isNotLogin(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    /**
     * 监听实体破坏方块事件，在未登录的情况下撤销事件
     *
     * @param event 监听到的实例
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (ListenerService.isNotLogin(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
