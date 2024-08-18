package net.ddns.superrobs.tabcompletion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public class AddCategoryTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete
            (@NotNull CommandSender sender, @NotNull Command command, @NotNull String str, String[] arg) {
        //first argument doesn't get tab completion
        if(arg.length==2) {
            List<String> possible_completions = List.of("public", "private", "auto");
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(arg[1], possible_completions, completions);
            return completions;
        }
        return null;
    }

}
