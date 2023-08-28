package top.coldsand.frozengate.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.coldsand.frozengate.account.AccountService;
import top.coldsand.frozengate.account.LoginService;

/**
 * RegisterCommand class
 * 有关注册的指令类
 *
 * @author Cold_sand
 * @date 2023/8/27
 */
public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //限制仅限玩家使用登录指令
        if (!(sender instanceof Player)) {
            sender.sendMessage("该指令仅限玩家使用");
            return false;
        }

        Player player  = (Player)sender;
        int commandLength = 2;

        //拦截已注册玩家
        if (LoginService.isLogin(player.getUniqueId()) || LoginService.isRegister(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "你已经注册了！");
        return true;
        }

        //判断指令是否使用正确
        if (args.length != commandLength) {
            player.sendMessage(ChatColor.RED + "注册指令使用错误！请按正确格式输入");
            player.sendMessage(ChatColor.YELLOW + "/register <密码> <再次输入相同的密码>");
            return true;
        }

        //判断两次输入的密码是否相同
        if (!args[1].equals(args[0])) {
            player.sendMessage(ChatColor.RED + "两次输入的密码不相同");
            return true;
        }

        //调用注册方法
        if (!AccountService.playerRegister(player.getUniqueId(), args[0])){
            player.sendMessage(ChatColor.RED + "注册失败，请联系服务器管理员解决问题");
            return false;
        }

        player.sendMessage(ChatColor.GREEN + "注册成功！请登录！");

        return true;
    }
}

