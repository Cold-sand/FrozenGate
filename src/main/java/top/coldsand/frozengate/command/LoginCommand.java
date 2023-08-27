package top.coldsand.frozengate.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.coldsand.frozengate.account.LoginService;

import java.util.UUID;

/**
 * LoginCommand class
 * 有关登录的指令类
 *
 * @author Cold_sand
 * @date 2023/8/16
 */
public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //限制仅限玩家使用登录指令
        if (!(sender instanceof Player)) {
            sender.sendMessage("该指令仅限玩家使用");
            return false;
        }

        Player player = (Player) sender;
        UUID playerUuid = player.getUniqueId();

        //拦截已登录玩家
        if (LoginService.isLogin(playerUuid)) {
            player.sendMessage(ChatColor.RED + "你已经登录了！");
            return true;
        }

        //拦截未注册玩家
        if (!LoginService.isRegister(playerUuid)) {
            player.sendMessage(ChatColor.RED + "你还没有注册！");
            return true;
        }

        //判断指令使用方式是否正确
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "登录指令使用错误！请按正确格式输入");
            player.sendMessage(ChatColor.YELLOW + "/login <密码>");
            return true;
        }

        //调用登录方法
        if (LoginService.playerLogin(playerUuid, args[0])) {
            sender.sendMessage(ChatColor.GREEN + "登录成功");
        } else {
            sender.sendMessage(ChatColor.RED + "登录失败，请检查密码是否正确");
        }
        return true;
    }
}