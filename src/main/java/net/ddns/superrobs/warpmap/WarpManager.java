package net.ddns.superrobs.warpmap;

import org.bukkit.Location;

import java.util.Optional;
import java.util.Set;

public class WarpManager {

    public static Optional<Location> removeWarp(String name, String categoryName) {
        Optional<Category> category = CategoryManager.getCategory(categoryName);
        if(category.isEmpty()) return Optional.empty();
        Optional<Location> warpLocation = category.get().getWarpLocation(name);
        if(warpLocation.isEmpty()) return Optional.empty();
        category.get().removeWarpPoint(name);
        return warpLocation;
    }

    //return value specifies whether operation was completed successfully
    public static void addWarp(String name, Location loc, String categoryName) {
        Optional<Category> category = CategoryManager.getCategory(categoryName);
        if(category.isEmpty()) return;
        category.get().addWarpPoint(name, loc);
    }

    public static Optional<Location> getWarp(String name, String categoryName) {
        return CategoryManager.getCategory(categoryName).flatMap(c -> c.getWarpLocation(name));
    }

    public static Set<String> getWarpNamesInCategory(String categoryName){
        Optional<Category> category = CategoryManager.getCategory(categoryName);
        if(category.isEmpty()) return Set.of();
        return category.get().getWarpPointNames();
    }

    public static boolean warpExists(String name, String categoryName) {
        return CategoryManager.getCategory(categoryName).map(value -> value.warpPointExists(name)).orElse(false);
    }
}
