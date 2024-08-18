package net.ddns.superrobs.commands;

import org.bukkit.command.CommandSender;

class Messages {
    final static String NO_PERMISSION = "§4You don't have permission to perform that command!";
    final static String TOO_MANY_ARGS = "§4Too many arguments!";
    final static String TOO_FEW_ARGS = "§4Too few arguments!";

    final static String WARP_DOES_NOT_EXIST = "§4This warp doesn't exist!";
    final static String CATEGORY_DOES_NOT_EXIST = "§4This category doesn't exist!";
    final static String PLAYER_ONLY = "§4Only a player can perform that command!";

    public static String validOptionsMessage(String variable, String... options){
        return "§4Please enter one of the following options for "
                + variable + ": "
                + String.join(", ", options);
    }


}
