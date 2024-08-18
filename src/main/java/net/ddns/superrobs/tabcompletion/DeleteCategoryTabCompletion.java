package net.ddns.superrobs.tabcompletion;

import java.util.ArrayList;
import java.util.List;

import net.ddns.superrobs.warpmap.CategoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;


public class DeleteCategoryTabCompletion implements TabCompleter{

    @Override
    public List<String> onTabComplete
            (@NotNull CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        if(arg.length==1) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(arg[0], CategoryManager.getCategoryNames(), completions);
            return completions;
        }
        return null;
    }

}
