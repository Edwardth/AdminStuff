/*
 * Copyright (C) 2011 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bukkit.Souli.AdminStuff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import com.bukkit.gemo.utils.FlatFile;

public class ASLocalizer {
    private static FlatFile config = null;
    private static Map<String, String> predefinedValues = new TreeMap<String, String>();

    public ASLocalizer() {
        // loadSettings("english");
        initPredefinedValues();
    }

    public ASLocalizer(String language) {
        initPredefinedValues();
        loadSettings(language);
    }

    private void initPredefinedValues() {
        // PLAYER NOT FOUND
        predefinedValues.put("PLAYER_NOT_FOUND", "Player '%s' not found (maybe offline?).");

        // WRONG SYNTAX
        predefinedValues.put("WRONG_SYNTAX", "Wrong syntax!");
        predefinedValues.put("WRONG_SYNTAX_FOR_COMMAND", "Wrong syntax for command %s!");

        // AFK
        predefinedValues.put("YOU_ARE_AFK", "You are now AFK.");
        predefinedValues.put("IS_NOW_AFK", "* %s is now AFK.");
        predefinedValues.put("NO_LONGER_AFK", "You are no longer AFK.");
        predefinedValues.put("IS_NO_LONGER_AFK", "* %s is no longer AFK.");

        // BAN
        predefinedValues.put("YOU_ARE_BANNED", "You are banned.");
        predefinedValues.put("PLAYER_IS_BANNED", "Player '%s' banned.");

        // BROADCAST
        predefinedValues.put("BROADCAST", "Broadcast");

        // BURN
        predefinedValues.put("BURN_PLAYER", "Burning '%s' for %s seconds.");

        // CHAT
        predefinedValues.put("NO_RECIPIENT_FOUND", "No recipient found.");
        predefinedValues.put("ONLY_CHAT_TO", "You are now only sending messages to: %s");
        predefinedValues.put("CHAT_TO_ALL", "You are sending messages to everyone.");

        // INVENTORY
        predefinedValues.put("INVENTORY_CLEARED", "Inventory cleared.");
        predefinedValues.put("CLEARED_OTHER_INVENTORY", "Inventory of '%s' cleared.");
        predefinedValues.put("INVENTORY_RESTORED", "Restoring your inventory.");
        predefinedValues.put("INVENTORY_SHOW_OF_X", "Showing you the inventory of '%s'.");
        predefinedValues.put("INVENTORY_SHOW_X_OF_Y", "Showing '%s' the inventory of '%s'.");
        
        // CLASSICMODE
        predefinedValues.put("YOU_ARE_IN_CLASSICMODE", "You are now in classicmode.");
        predefinedValues.put("YOU_ARE_NO_LONGER_IN_CLASSICMODE", "You are now in survivalmode.");
        
        // COMPASS
        predefinedValues.put("COMPASS", "Compass");        

        // ITEM
        predefinedValues.put("ITEM_NOT_FOUND", "Item '%s' not found!");

        // FILLCHEST
        predefinedValues.put("CLICK_ON_A_CHEST_TO_FILL", "Click on a chest to fill it with '%s'.");
        predefinedValues.put("CHEST_FILLED", "Chest filled with '%s'.");
        predefinedValues.put("CHESTFILL_CANCEL", "Chestfill canceled.");

        // FLASH
        predefinedValues.put("FLASH_PLAYER", "Eat my shorts, %s!");

        // GIVE & I
        predefinedValues.put("GIVING_TO", "Giving %s of '%s' to '%s'.");
        predefinedValues.put("GIVING_YOU", "Giving you %s of '%s'.");

        // GLUE
        predefinedValues.put("PLAYER_GLUED", "Player '%s' glued.");
        predefinedValues.put("NO_LONGER_GLUED", "Player '%s' is no longer glued.");
        predefinedValues.put("YOU_ARE_GLUED", "You are now glued.");
        predefinedValues.put("YOU_ARE_NO_LONGER_GLUED", "You are no longer glued.");

        // GODMODE
        predefinedValues.put("YOU_ARE_A_GOD", "Godmode enabled!");
        predefinedValues.put("NO_LONGER_A_GOD", "Godmode disabled!");
        predefinedValues.put("PLAYER_IS_A_GOD", "'%s' is now a god!");
        predefinedValues.put("PLAYER_IS_NO_LONGER_GOD", "'%s' is no longer a god!");

        // HEAL
        predefinedValues.put("HEALED_YOURSELF", "Healed yourself.");
        predefinedValues.put("HEALED_X", "Healed '%s'.");
        predefinedValues.put("HEALED_BY_X", "Healed by '%s'.");

        // HIDECHAT
        predefinedValues.put("CHAT_HIDDEN", "The chat is now hidden.");
        predefinedValues.put("CHAT_NOT_LONGER_HIDDEN", "The chat is no longer hidden.");

        // KICK
        predefinedValues.put("KICKED_PLAYER", "Player '%s' kicked.");
        predefinedValues.put("KICKED_YOU", "You were kicked.");
        predefinedValues.put("KICKED__ALL", "All other players got kicked!");

        // KILL
        predefinedValues.put("KILLED_PLAYER", "Player '%s' killed.");

        // KIT
        predefinedValues.put("KIT_NOT_FOUND", "Kit '%s' not found.");
        predefinedValues.put("KIT_NOT_ALLOWED", "You are not allowed to use that kit.");
        predefinedValues.put("KIT_GIVE", "Giving you the kit '%s'.");
        predefinedValues.put("KIT_LIST", "List of Kits:");
        
        // MUTE
        predefinedValues.put("MUTE_PLAYER", "Player '%s' is now muted.");
        predefinedValues.put("UNMUTE_PLAYER", "Player '%s' is no longer muted.");
        predefinedValues.put("MUTED", "[Muted]");
        
        // NICKNAME
        predefinedValues.put("NICKNAME", "Your nickname is now '%s'.");
        predefinedValues.put("NICKNAME_PLAYER", "The nickname of '%s' is now '%s'.");
   
        // SLAP
        predefinedValues.put("SLAP_PLAYER", "'%s' got slapped by '%s'.");
        predefinedValues.put("SLAP_SUFFIX", " got slapped!");
        
        // STACK
        predefinedValues.put("ITEMS_STACKED", "Items stacked.");
        
        // TIME
        predefinedValues.put("TIME_SET", "Time in your world changed to '%s'.");
        predefinedValues.put("TIME_DAY", "day");
        predefinedValues.put("TIME_NIGHT", "night");
        
        // TEMPBAN
        predefinedValues.put("TEMPBAN_YOU", "You were temporary banned for %s."); 
        predefinedValues.put("TEMPBAN_PLAYER", "Player '%s' temporary banned!"); 
        predefinedValues.put("TEMPBAN_EXAMPLE", "Example");        
        predefinedValues.put("TEMPBAN_UNTIL", "You are temporary banned until %s!");    
        
        // UNBAN
        predefinedValues.put("UNBAN_PLAYER", "Player '%s' unbanned.");        
        
        // UNLIMITED
        predefinedValues.put("UNLIMITED_ACCESS", "You have now unlimited amount of '%s'.");
        predefinedValues.put("UNLIMITED_DENY", "You do no longer have unlimited amount of '%s'.");
        predefinedValues.put("UNLIMITED_ACCESS_OTHER", "'%s' has now unlimited amount of '%s'.");
        predefinedValues.put("UNLIMITED_DENY_OTHER", "'%s' has no longer have unlimited amount of '%s'.");
        
        // WEATHER
        predefinedValues.put("WEATHER_SET", "Weather in your world changed to '%s'.");
        predefinedValues.put("WEATHER_SUN", "sun");
        predefinedValues.put("WEATHER_RAIN", "rain");
        predefinedValues.put("WEATHER_STORM", "storm");
    }

    public static String format(String field, ChatColor col) {
        return format(field, col, "");
    }

    public static String format(String field, ChatColor col, String... args) {
        String pre = predefinedValues.get(field);
        if (pre == null)
            pre = "NOT DEFINED";

        if (config == null)
            return pre;

        String result = config.getString(field, pre);
        if (args != null)
            return col + String.format(result, (Object[]) args);
        return result;
    }

    public static String format(String field) {
        return format(field, "");
    }

    public static String format(String field, String... args) {
        String pre = predefinedValues.get(field);
        if (pre == null)
            pre = "NOT DEFINED";

        if (config == null)
            return pre;

        String result = config.getString(field, pre);
        if (args != null)
            return String.format(result, (Object[]) args);
        return result;
    }

    // LOAD SETTINGS
    private boolean loadSettings(final String FileName) {
        File folder = new File("plugins/AdminStuff/languages/");
        folder.mkdirs();
        try {
            config = new FlatFile("AdminStuff/languages/" + FileName + ".properties", false);
            if (config.readFile()) {
                ASCore.log.printInfo("Loaded localization: " + FileName);
            } else {
                createDefaultFile(FileName);
                ASCore.log.printWarning("Could not load localization: " + FileName);
            }
            return true;
        } catch (Exception e) {
            createDefaultFile(FileName);
            ASCore.log.printWarning("Could not load localization: " + FileName);
            // ASCore.log.printError("Error while reading file: plugins/AdminStuff/languages/"
            // + FileName + ".properties", e);
            return true;
        }
    }

    private void createDefaultFile(final String FileName) {
        File file = new File("plugins/AdminStuff/languages/" + FileName + ".properties");
        if (file.exists())
            file.delete();

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("plugins/AdminStuff/languages/" + FileName + ".properties"));
            for (Map.Entry<String, String> entry : predefinedValues.entrySet()) {
                out.write(entry.getKey() + "=" + entry.getValue() + "\r\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
