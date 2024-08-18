package net.ddns.superrobs.warpmap;

import org.bukkit.command.CommandSender;

import java.util.Set;
import java.util.stream.Collectors;


public class Utils {

    private static final Set<CategoryAvailability> openAvailabilities =
            Set.of(CategoryAvailability.PUBLIC, CategoryAvailability.AUTOMATICALLY_APPLIED);

    public static Set<String> getAccessibleCategories(CommandSender sender){
        //noinspection OptionalGetWithoutIsPresent
        return CategoryManager.getCategoryNames().stream()
                .filter(s -> openAvailabilities.contains(CategoryManager.getCategoryAvailability(s).get())
                        ||sender.hasPermission("superwarp.categories.read."+s)).collect(Collectors.toSet());
    }

    public static Set<String> getAutoAppliedCategories(){
        return CategoryManager.getCategoryNames(CategoryAvailability.AUTOMATICALLY_APPLIED);
    }

    public static Set<String> getManageableCategories(CommandSender sender){
        return CategoryManager.getCategoryNames().stream()
                .filter(s -> sender.hasPermission("superwarp.categories.manage."+s)).collect(Collectors.toSet());
    }

    public static Set<String> getAccessibleWarps(CommandSender sender){
        return getAccessibleCategories(sender).stream()
                .flatMap(s -> WarpManager.getWarpNamesInCategory(s).stream())
                .collect(Collectors.toSet());
    }

    public static Set<String> getManageableWarps(CommandSender sender){
        return getManageableCategories(sender).stream()
                .flatMap(s -> WarpManager.getWarpNamesInCategory(s).stream())
                .collect(Collectors.toSet());
    }

    public static Set<String> getAccessibleCategoriesContainingWarp(CommandSender sender, String warpName){
        return getAccessibleCategories(sender).stream()
                .filter(s -> WarpManager.warpExists(warpName, s))
                .collect(Collectors.toSet());
    }

    public static Set<String> getManageableCategoriesContainingWarp(CommandSender sender, String warpName){
        return getManageableCategories(sender).stream()
                .filter(s -> WarpManager.warpExists(warpName, s))
                .collect(Collectors.toSet());
    }
}
