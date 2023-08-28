package top.coldsand.frozengate.tool;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import top.coldsand.frozengate.FrozenGate;
import top.coldsand.frozengate.data.yaml.Config;

/**
 * SafePlayerLocation class
 * 有关玩家安全位置搜索与传送的类
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public class SafePlayerLocation {

    /**
     * 检查位置是否安全（所在位置与该位置上方方块可被穿过，位置下方不可被穿过）
     *
     * @param world 位置所在世界实例
     * @param location 位置实例
     *
     * @return 位置是否安全
     */
    public static boolean checkLocationSafe(World world, Location location) {
        Block block = world.getBlockAt(location);
        return block.getRelative(BlockFace.UP).isPassable() && block.isPassable() && !block.getRelative(BlockFace.DOWN).isPassable();
    }

    /**
     * 在指定半径内查找安全位置
     *
     * @param player 玩家实例
     * @param searchRadius 搜索半径
     *
     * @return 寻找到的安全位置，否则返回false
     */
    private static boolean searchSafeLocation(Player player, int searchRadius) {
        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int y = -searchRadius; y <= searchRadius; y++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    Location newLocation = player.getLocation().clone().add(x, y, z);

                    if (checkLocationSafe(player.getWorld(), newLocation)) {
                        player.teleport(newLocation);
                        player.sendMessage(ChatColor.GREEN + "已为您传送到安全位置");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 传送到搜素到的安全位置
     *
     * @param player 玩家实例
     */
    public static void teleportPlayerToSafeLocation(Player player) {
        if (Config.INSTANCE.getSearchSafeLocationBoolean()) {
            if (searchSafeLocation(player, Config.INSTANCE.getSearchRadius())) {
                return; //如果找到了安全位置，直接返回，不进行后续的传送操作
            }

        if (!searchSafeLocation(player, Config.INSTANCE.getSearchRadius())) {
            try {
                Location bedLocation = player.getBedSpawnLocation();

                if (bedLocation != null) {
                    player.teleport(bedLocation);
                    player.sendMessage(ChatColor.GREEN + "已为您传送到床位置" );
                } else {
                    player.teleport(Config.INSTANCE.getSpawnLocation());
                    player.sendMessage(ChatColor.GREEN + "已为您传送到管理员指定的位置");
                }
            } catch (IllegalArgumentException e) {
                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(ChatColor.YELLOW + "管理员未正确设置默认出生点或您的床位置不可用，已为您传送到世界出生点，请联系管理员修复此问题" );
                FrozenGate.LOGGER.warning("您未正确设置默认出生点，请尽快修复，以免影响玩家体验");
                }
            }
        }
    }
}

