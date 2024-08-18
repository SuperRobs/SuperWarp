package net.ddns.superrobs.commands;

import net.ddns.superrobs.Main;
import net.ddns.superrobs.warpmap.CategoryAvailability;
import net.ddns.superrobs.warpmap.CategoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class EditCategoryType implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String str, String[] arg) {

        if(!sender.hasPermission("superwarp.categories.edittype")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if(arg.length < 2 ) {
            sender.sendMessage(Messages.TOO_FEW_ARGS);
            return true;
        }
        if(arg.length == 2) {
            if(!sender.hasPermission("superwarp.categories.manage."+arg[0])){
                sender.sendMessage(Messages.NO_PERMISSION);
                return true;
            }
            CategoryAvailability newAvailability;
            switch(arg[1].toLowerCase()) {
                case "player":
                    newAvailability = CategoryAvailability.PLAYER;
                    break;
                case "auto":
                    newAvailability = CategoryAvailability.AUTOMATICALLY_APPLIED;
                    break;
                case "permission":
                    newAvailability = CategoryAvailability.PERMISSION;
                    break;
                case "public":
                    newAvailability = CategoryAvailability.PUBLIC;
                    break;
                default:
                    sender.sendMessage(Messages.validOptionsMessage("availability",
                            "player", "auto", "permission", "public"));
                    return true;
            }
            CategoryManager.changeCategoryAvailability(arg[0], newAvailability);
            sender.sendMessage("§asuccessfully changed type");
            return true;
        }
        sender.sendMessage(Messages.TOO_MANY_ARGS);
        return false;

    }

}
