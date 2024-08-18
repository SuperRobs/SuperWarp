package net.ddns.superrobs.logging;

import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {

    private final static Logger logger = getLogger();

    private static Logger getLogger(){
        Logger returnValue;
        try{
            returnValue = Bukkit.getLogger();
        } catch (Exception ignored){
            returnValue = null;
        }
        return returnValue;
    }

    public static void log(Level level, String message){
        if(logger == null) return;
        logger.log(level, message);
    }

}
