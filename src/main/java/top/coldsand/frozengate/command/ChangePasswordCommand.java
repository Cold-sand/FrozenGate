package top.coldsand.frozengate.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.coldsand.frozengate.account.AccountService;
import top.coldsand.frozengate.account.LoginService;

/**
 * ChangerPasswordCommand class
 * 有关修改玩家密码的指令类
 *
 * @author Cold_sand
 * @date 2023/8/16
 */
public class ChangePasswordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //拦截非玩家以外的传入参数，并提醒在控制台执行
        if (!(sender instanceof Player)) {
            sender.sendMessage("请在控制台中使用此命令，格式是/changePassword <玩家ID> <新密码>");
            return true;
        }

        Player player = (Player) sender;
        int commandLength = 2;

        //判断指令使用方式
        if (args.length != commandLength) {
            player.sendMessage(ChatColor.RED + "修改密码指令使用错误！请按正确格式输入");
            player.sendMessage(ChatColor.YELLOW + "/changepassword <新密码>");
            return true;
        }

        //拦截未登录玩家使用此指令
        if (!LoginService.isLogin(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "你还没有登录！");
            return true;
        }

        //判断原密码是否正确
        if (!AccountService.checkPassword(player.getUniqueId(), args[0])) {
            player.sendMessage(ChatColor.RED + "原密码错误，请仔细检查。");
            return true;
        }

        //判断新密码是否与旧密码相同
        if (args[1].equals(args[0])) {
            player.sendMessage(ChatColor.RED + "新密码不得与旧密码相同!");
            return true;
        }

        if (AccountService.checkPassword(player.getUniqueId(), args[0])) {
            if (AccountService.changePassword(player.getUniqueId(), args[1])) {
                player.sendMessage(ChatColor.GREEN + "修改密码成功！");
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "发生内部错误，请联系管理员处理");
            return true;
        }

        return true;
    }
}
