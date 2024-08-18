package net.ddns.superrobs.tabcompletion;

import java.util.ArrayList;
import java.util.List;

import net.ddns.superrobs.warpmap.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public class WarpListTabCompletion implements TabCompleter{
    @Override
    public List<String> onTabComplete
            (@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] arg) {
        if(arg.length == 1) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(arg[0], Utils.getAccessibleCategories(sender), completions);
            return completions;
        }
        return null;
    }

}
