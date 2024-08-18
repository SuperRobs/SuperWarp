package net.ddns.superrobs.commands;

import net.ddns.superrobs.warpmap.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

public class CategoryList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        if(!sender.hasPermission("superwarp.categories.list")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if(arg.length==0) {
            StringBuilder message = new StringBuilder("§aCategories:");
            for(String categoryName : Utils.getAccessibleCategories(sender)){
                message.append("\n§e- ").append(categoryName);
            }
            sender.sendMessage(message.toString());
            return true;
        }
        sender.sendMessage(Messages.TOO_MANY_ARGS);
        return false;
    }

}