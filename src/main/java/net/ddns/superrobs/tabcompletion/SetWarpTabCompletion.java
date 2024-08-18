package net.ddns.superrobs.tabcompletion;

import java.util.ArrayList;
import java.util.List;

import net.ddns.superrobs.warpmap.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public class SetWarpTabCompletion implements TabCompleter{

    @Override
    public List<String> onTabComplete
            (@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] arg) {
        //arg 1 is a name, so it doesn't get tab completion
        if(arg.length == 2) {
            List<String>completions = new ArrayList<>();
            StringUtil.copyPartialMatches(arg[1], Utils.getManageableCategories(sender), completions);
            return completions;
        }
        return null;
    }
}
