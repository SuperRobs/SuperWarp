package net.ddns.superrobs.commands;

import net.ddns.superrobs.Main;

import net.ddns.superrobs.warpmap.CategoryManager;
import net.ddns.superrobs.warpmap.FileHandler;
import net.ddns.superrobs.warpmap.WarpManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DelWarp implements CommandExecutor {

    private static final String SUCCESS = "§aSuccessfully deleted warp!";

    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        //don't execute if permission is missing
        if(!sender.hasPermission("superwarp.delwarp")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if(arg.length == 0) {
            sender.sendMessage(Messages.TOO_FEW_ARGS);
            return false;
        }

        if(arg.length == 1) {
            //as the category is in this case inferred from the player name, the sender must be a player
            if(!(sender instanceof Player player)) {
                sender.sendMessage("§4Please specify the category you want to delete from!");
                return false;
            }
            //it is assumed that a player will always have permission to delete their own warps, so no permission check
            //here
            if(!CategoryManager.categoryExists(player.getName())){
                player.sendMessage(Messages.CATEGORY_DOES_NOT_EXIST);
                return true;
            }
            if(!WarpManager.warpExists(arg[0], player.getName())){
                player.sendMessage(Messages.WARP_DOES_NOT_EXIST);
                return true;
            }
            Optional<Location> oldLocation = WarpManager.removeWarp(arg[0], player.getName());

            player.sendMessage(SUCCESS);if(oldLocation.isPresent()) {
                player.sendMessage("§eIf that was not your intention, here is the old location for recovery: \n"
                        + oldLocation.get());
            }
            FileHandler.save();
            return true;
        }

        if(arg.length == 2) {

            if(!CategoryManager.categoryExists(arg[1])){
                sender.sendMessage(Messages.CATEGORY_DOES_NOT_EXIST);
                return true;
            }

            if(!sender.hasPermission("superwarp.categories.manage."+arg[1])){
                sender.sendMessage(Messages.NO_PERMISSION);
                return true;
            }

            if(!WarpManager.warpExists(arg[0], arg[1])){
                sender.sendMessage(Messages.WARP_DOES_NOT_EXIST);
                return true;
            }


            WarpManager.removeWarp(arg[0], arg[1]);
            sender.sendMessage(SUCCESS);
            FileHandler.save();
            return true;

        }
        sender.sendMessage(Messages.TOO_FEW_ARGS);
        return false;
    }
}