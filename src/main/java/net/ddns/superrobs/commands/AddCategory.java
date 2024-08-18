package net.ddns.superrobs.commands;

import net.ddns.superrobs.warpmap.CategoryManager;
import net.ddns.superrobs.warpmap.FileHandler;
import net.ddns.superrobs.warpmap.CategoryAvailability;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import static net.ddns.superrobs.warpmap.CategoryManager.categoryExists;

public class AddCategory implements CommandExecutor {

    private final static String ALREADY_EXISTS = "§4This Category already exists!";
    private static String SuccessMessage(String name){
        return "Successfully created Category §6"+name;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        if(!sender.hasPermission("superwarp.categories.create")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if(arg.length==0) {
            sender.sendMessage(Messages.TOO_FEW_ARGS);
            return false;
        }
        if(arg.length==1) {
            if(categoryExists(arg[0])){
                sender.sendMessage(ALREADY_EXISTS);
                return true;
            }
            //Public is considered standard
            CategoryManager.addCategory(arg[0], CategoryAvailability.PUBLIC);
            FileHandler.save();
            sender.sendMessage(SuccessMessage(arg[0]));
            return true;
        }

        if(arg.length == 2) {
            if(categoryExists(arg[0])) {
                sender.sendMessage(ALREADY_EXISTS);
                return true;
            }
            CategoryAvailability availabilityState = null;
            switch(arg[1].toLowerCase()) {
                case "public":
                    availabilityState = CategoryAvailability.PUBLIC; break;
                case "private":
                    availabilityState = CategoryAvailability.PERMISSION; break;
                case "auto":
                    availabilityState = CategoryAvailability.AUTOMATICALLY_APPLIED; break;
                default:
                    sender.sendMessage(Messages.validOptionsMessage("availability",
                            "public", "private", "auto"));
                    return false;
            }
            CategoryManager.addCategory(arg[0], availabilityState);
            FileHandler.save();
            sender.sendMessage(SuccessMessage(arg[0]));
            return true;
        }
        sender.sendMessage(Messages.TOO_MANY_ARGS);
        return false;
    }
}