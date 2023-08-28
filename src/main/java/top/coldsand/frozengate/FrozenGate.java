package top.coldsand.frozengate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import top.coldsand.frozengate.command.ChangePasswordCommand;
import top.coldsand.frozengate.command.LoginCommand;
import top.coldsand.frozengate.command.RegisterCommand;
import top.coldsand.frozengate.data.StorageManager;
import top.coldsand.frozengate.data.yaml.Config;
import top.coldsand.frozengate.listener.BlockListener;
import top.coldsand.frozengate.listener.LoginListener;
import top.coldsand.frozengate.listener.PlayerListener;

import java.util.logging.Logger;

/**
 * FrozenGate Class
 * FrozenGate 插件主类
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public final class FrozenGate extends JavaPlugin {
    public static FrozenGate INSTANCE;
    public static Logger LOGGER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        LOGGER = getLogger();

        //配置文件
        this.saveDefaultConfig();
        getConfig().options().copyDefaults();
        Config.loadConfig(this.getConfig());


        if(!StorageManager.init(Config.INSTANCE.getSQLConfig())){
            LOGGER.severe("数据库初始化错误，请检查数据库配置与报错信息");
        }

        //监听器
        Bukkit.getPluginManager().registerEvents(new LoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        //指令
        Bukkit.getPluginCommand("login").setExecutor(new LoginCommand());
        Bukkit.getPluginCommand("register").setExecutor(new RegisterCommand());
        Bukkit.getPluginCommand("changepassword").setExecutor(new ChangePasswordCommand());


        //插件启动
        getLogger().info(ChatColor.BLUE+"FrozenGate"+ChatColor.YELLOW+"插件已启动！");
        getLogger().info(ChatColor.YELLOW+"作者:"+ChatColor.BLUE+"Cold_sand");
    }

    @Override
    public void onDisable() {
        //插件卸载
        getLogger().info(ChatColor.YELLOW + "FrozenGate" + ChatColor.BLUE+"插件已卸载！");
    }
}
