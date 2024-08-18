package net.ddns.superrobs;

import net.ddns.superrobs.commands.*;
import net.ddns.superrobs.logging.Logging;
import net.ddns.superrobs.tabcompletion.*;

import java.util.logging.Level;

import net.ddns.superrobs.warpmap.FileHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("setwarp").setExecutor(new SetWarp());
        getCommand("warp").setExecutor(new Warp());
        getCommand("warplist").setExecutor(new WarpList());
        getCommand("delwarp").setExecutor(new DelWarp());
        getCommand("addcategory").setExecutor(new AddCategory());
        getCommand("deletecategory").setExecutor(new DeleteCategory());
        getCommand("categorylist").setExecutor(new CategoryList());
        getCommand("editcategorytype").setExecutor(new EditCategoryType());
        getCommand("backup").setExecutor(new Backup());

        //Note: tab completion is not yet fully adapted to 3.0
        getCommand("setwarp").setTabCompleter(new SetWarpTabCompletion());
        getCommand("warp").setTabCompleter(new WarpTabCompletion());
        getCommand("warplist").setTabCompleter(new WarpListTabCompletion());
        getCommand("delwarp").setTabCompleter(new DelWarpTabCompletion());
        getCommand("addcategory").setTabCompleter(new AddCategoryTabCompletion());
        getCommand("deletecategory").setTabCompleter(new DeleteCategoryTabCompletion());

        Logging.log(Level.INFO, "SuperWarp started");
    }

    @Override
    public void onDisable() {
        FileHandler.backup();
        FileHandler.save();
    }
}