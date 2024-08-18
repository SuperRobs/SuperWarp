package net.ddns.superrobs.commands;

import net.ddns.superrobs.warpmap.FileHandler;
import net.ddns.superrobs.warpmap.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Backup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        if(!sender.hasPermission("superwarp.backup")) {
            sender.sendMessage(Messages.NO_PERMISSION);
            return true;
        }
        if(arg.length==0) {
            FileHandler.backup();
        }
        sender.sendMessage(Messages.TOO_MANY_ARGS);
        return false;
    }
}
