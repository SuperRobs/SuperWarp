package net.ddns.superrobs.tabcompletion;

import java.util.ArrayList;
import java.util.List;

import net.ddns.superrobs.warpmap.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class WarpTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arg) {
        if(arg.length==1) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(arg[0], Utils.getAccessibleWarps(sender), completions);
            return completions;
        }
        if(arg.length==2) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(arg[1],
                    Utils.getAccessibleCategoriesContainingWarp(sender, arg[1]), completions);
            return completions;
        }
        return null;
    }
}