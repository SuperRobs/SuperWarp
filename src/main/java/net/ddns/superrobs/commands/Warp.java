package net.ddns.superrobs.commands;

import net.ddns.superrobs.warpmap.CategoryAvailability;
import net.ddns.superrobs.warpmap.CategoryManager;
import net.ddns.superrobs.warpmap.Utils;
import net.ddns.superrobs.warpmap.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Warp implements CommandExecutor {

    public boolean onCommand
            (@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, String[] arg) {

        if(!(sender instanceof Player player)) {
            sender.sendMessage(Messages.PLAYER_ONLY);
            return true;
        }

        if(!player.hasPermission("superwarp.warp")) {
            player.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (arg.length == 0) {
            //TODO
            player.sendMessage("§aThis feature is currently WIP, please use the warp point's name as argument for now!");
            return false;
        }

        if (arg.length == 1) {
            //first try if warp is in player's own category
            if(WarpManager.warpExists(arg[0], player.getName())){
                //noinspection OptionalGetWithoutIsPresent
                player.teleport(WarpManager.getWarp(arg[0], player.getName()).get());
                return true;
            }
            //then try if it is in any auto applied category
            //if there are multiple auto applied warps with the same name, an arbitrary one will be chosen
            Optional<String> categoryName = CategoryManager.getCategoryNames(CategoryAvailability.AUTOMATICALLY_APPLIED)
                    .stream()
                    .filter(s -> WarpManager.warpExists(arg[0], s))
                    .findFirst();
            if(categoryName.isPresent()) {
                //noinspection OptionalGetWithoutIsPresent
                player.teleport(WarpManager.getWarp(arg[0], categoryName.get()).get());
                return true;
            }

            player.sendMessage("§4This warp could not be found!");
            return false;
        }

        if(arg.length == 2) {
            if(!Utils.getAccessibleCategories(sender).contains(arg[1])){
                sender.sendMessage("§4This category doesn't exist, or is not accessible to you!");
                return true;
            }
            if(!WarpManager.warpExists(arg[0], arg[1])){
                sender.sendMessage(Messages.WARP_DOES_NOT_EXIST);
                return true;
            }

            //noinspection OptionalGetWithoutIsPresent
            player.teleport(WarpManager.getWarp(arg[0], arg[1]).get());
            return true;
        }

        player.sendMessage(Messages.TOO_MANY_ARGS);
        return false;
    }
}
