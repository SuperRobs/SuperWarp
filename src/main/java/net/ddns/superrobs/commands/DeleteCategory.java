package net.ddns.superrobs.commands;

import net.ddns.superrobs.warpmap.CategoryManager;
import net.ddns.superrobs.warpmap.FileHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.ddns.superrobs.Main;
import org.jetbrains.annotations.NotNull;

public class DeleteCategory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        if(!sender.hasPermission("superwarp.categories.delete")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if(arg.length==0) {
            sender.sendMessage(Messages.TOO_FEW_ARGS);
            return false;
        }
        if(arg.length==1) {
            if(!CategoryManager.categoryExists(arg[0])){
                sender.sendMessage("§4This category does not exist!");
                return true;
            }
            if(!sender.hasPermission("superwarp.categories.manage."+arg[0])) {
                sender.sendMessage(Messages.NO_PERMISSION);
                return true;
            }
            CategoryManager.removeCategory(arg[0]);
            sender.sendMessage("§aSuccessfully deleted category");
            FileHandler.save();
            return true;
        }
        sender.sendMessage(Messages.TOO_MANY_ARGS);
        return false;
    }

}
