package net.ddns.superrobs.commands;

import net.ddns.superrobs.warpmap.CategoryManager;
import net.ddns.superrobs.warpmap.Utils;
import net.ddns.superrobs.warpmap.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpList implements CommandExecutor {
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        if(!sender.hasPermission("superwarp.list")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (arg.length == 0) {
            if(!(sender instanceof Player player)) {
                sender.sendMessage(Messages.PLAYER_ONLY);
                return true;
            }
            StringBuilder message = new StringBuilder("§aYour personal warp points:");
            WarpManager.getWarpNamesInCategory(player.getName()).forEach(s -> message.append("\n§e- ").append(s));
            player.sendMessage(message.toString());
        }
        if (arg.length == 1) {
            if(!CategoryManager.categoryExists(arg[0])){
                sender.sendMessage(Messages.CATEGORY_DOES_NOT_EXIST);
                return true;
            }
            if(!sender.hasPermission("superwarp.categories.read."+arg[0])) {
                sender.sendMessage(Messages.NO_PERMISSION);
                return true;
            }

            StringBuilder message = new StringBuilder("§aWarp points in category §9" + arg[0] + "§a:\n");
            if(Utils.getAccessibleCategories(sender).contains(arg[0])) {
                WarpManager.getWarpNamesInCategory(arg[0]).forEach(s -> message.append("\n§e- ").append(s));
            }
            return true;
        }
        sender.sendMessage("§4Too many arguments!");
        return false;
    }
}