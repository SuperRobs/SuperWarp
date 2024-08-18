package net.ddns.superrobs.commands;

import net.ddns.superrobs.Main;
import net.ddns.superrobs.warpmap.CategoryAvailability;
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

public class SetWarp implements CommandExecutor {

    public static final String SUCCESS = "§aSuccessfully created warp point!";

    public boolean onCommand
            (@NotNull CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        //This needs the player's location and the plugin currently does not support setting a warp with
        //explicit coordinates
        if(!(sender instanceof Player player)) {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }

        if(!player.hasPermission("superwarp.set")) {
            player.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if(arg.length==0) {
            player.sendMessage(Messages.TOO_FEW_ARGS);
            return false;
        }

        if (arg.length == 1) {

            WarpManager.getWarp(arg[0], player.getName())
                    .ifPresent(s -> sender.sendMessage("§eWarp point overwritten! Old Coordinates: " + s));

            if(!CategoryManager.categoryExists(player.getName())) {
                CategoryManager.addCategory(player.getName(), CategoryAvailability.PLAYER);
            }

            WarpManager.addWarp(arg[0], player.getLocation(), player.getName());
            player.sendMessage(SUCCESS);
            FileHandler.save();
            return true;
        }

        if (arg.length == 2) {
            //placeholder, later there should be different permissions for every category, prly just "Superwarp.setwarp.[CategoryName]"
            if(!CategoryManager.categoryExists(arg[1])) {
                player.sendMessage(Messages.CATEGORY_DOES_NOT_EXIST);
                return true;
            }
            if(!player.hasPermission("superwarp.categories.manage."+arg[1])) {
                player.sendMessage(Messages.NO_PERMISSION);
                if(sender.hasPermission("superwarp.categories.create")){
                    player.sendMessage("§eIf you intended to create that category, you can do so via /AddCategory");
                }
                return true;
            }

            WarpManager.addWarp(arg[0], player.getLocation(), arg[1]);
            player.sendMessage(SUCCESS);
            FileHandler.save();
            return true;
        }

        player.sendMessage(Messages.TOO_MANY_ARGS);
        return false;
    }
}
