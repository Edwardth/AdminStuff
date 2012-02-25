/*
 * Copyright (C) 2011 MineStar.de 
 * 
 * This file is part of 'AdminStuff'.
 * 
 * 'AdminStuff' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * 'AdminStuff' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'AdminStuff'.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * AUTHOR: GeMoschen
 * 
 */

package de.minestar.AdminStuff;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.minestar.AdminStuff.Listener.ASEntityListener;
import de.minestar.AdminStuff.Listener.ASPlayerListener;
import de.minestar.AdminStuff.commands.cmdAFK;
import de.minestar.AdminStuff.commands.cmdBan;
import de.minestar.AdminStuff.commands.cmdBroadcast;
import de.minestar.AdminStuff.commands.cmdBurn;
import de.minestar.AdminStuff.commands.cmdChatAdd;
import de.minestar.AdminStuff.commands.cmdClassic;
import de.minestar.AdminStuff.commands.cmdClearInventory;
import de.minestar.AdminStuff.commands.cmdCompass;
import de.minestar.AdminStuff.commands.cmdFillChest;
import de.minestar.AdminStuff.commands.cmdFlash;
import de.minestar.AdminStuff.commands.cmdGiveAmount;
import de.minestar.AdminStuff.commands.cmdGlue;
import de.minestar.AdminStuff.commands.cmdGlueHere;
import de.minestar.AdminStuff.commands.cmdGod;
import de.minestar.AdminStuff.commands.cmdHeal;
import de.minestar.AdminStuff.commands.cmdHelp;
import de.minestar.AdminStuff.commands.cmdHelpPage;
import de.minestar.AdminStuff.commands.cmdHideChat;
import de.minestar.AdminStuff.commands.cmdIAmount;
import de.minestar.AdminStuff.commands.cmdInvsee;
import de.minestar.AdminStuff.commands.cmdKick;
import de.minestar.AdminStuff.commands.cmdKickAll;
import de.minestar.AdminStuff.commands.cmdKill;
import de.minestar.AdminStuff.commands.cmdKit;
import de.minestar.AdminStuff.commands.cmdListKits;
import de.minestar.AdminStuff.commands.cmdMe;
import de.minestar.AdminStuff.commands.cmdMessage;
import de.minestar.AdminStuff.commands.cmdMutePlayer;
import de.minestar.AdminStuff.commands.cmdNickname;
import de.minestar.AdminStuff.commands.cmdPing;
import de.minestar.AdminStuff.commands.cmdReply;
import de.minestar.AdminStuff.commands.cmdSeen;
import de.minestar.AdminStuff.commands.cmdSlap;
import de.minestar.AdminStuff.commands.cmdStack;
import de.minestar.AdminStuff.commands.cmdTempBan;
import de.minestar.AdminStuff.commands.cmdTime;
import de.minestar.AdminStuff.commands.cmdUnban;
import de.minestar.AdminStuff.commands.cmdWeather;
import de.minestar.AdminStuff.database.DatabaseHandler;
import de.minestar.AdminStuff.manager.PlayerManager;
import de.minestar.minestarlibrary.commands.CommandList;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class Core extends JavaPlugin {

    /** GLOBAL VARS */
    public final static String NAME = "AdminStuff";

    /** LISTENERS */
    private ASEntityListener eListener;
    private ASPlayerListener pListener;

    private CommandList cmdList;

    public static HashMap<String, ASKit> kitList = new HashMap<String, ASKit>();

    private DatabaseHandler dbHandler;
    private PlayerManager pManager;

    /**
     * ON DISABLE
     */
    @Override
    public void onDisable() {
        cmdList = null;
        ConsoleUtils.printError(NAME, "Disabled!");
    }
    /**
     * ON ENABLE
     */
    @Override
    public void onEnable() {

        try {
            File dataFolder = getDataFolder();
            dataFolder.mkdirs();

            dbHandler = new DatabaseHandler(NAME, dataFolder);
            pManager = new PlayerManager(dbHandler, dataFolder);

            eListener = new ASEntityListener(pManager);
            pListener = new ASPlayerListener(pManager);

            PluginManager pm = getServer().getPluginManager();
            pm.registerEvents(eListener, this);
            pm.registerEvents(pListener, this);

            loadConfig();
            initCommands();
            ConsoleUtils.printInfo(NAME, "Version " + getDescription().getVersion() + " enabled!");
        } catch (Exception e) {
            ConsoleUtils.printException(e, NAME, "Can't enable!");
        }
    }

    private void initCommands() {
        //@formatter:off
        cmdList = new CommandList(NAME,
            // USER PUNISH COMMANDS
            new cmdBurn             ("/burn",       "<Player> <Time in seconds>",   "commands.admin.burn"),
            new cmdSlap             ("/slap",       "<Player>",                     "commands.admin.slap", pManager),
            new cmdKill             ("/kill",       "[Player_1] ... [Player_N]",    "commands.admin.kill"),
            new cmdGlue             ("/glue",       "<Player>",                     "commands.admin.glue", pManager),
            new cmdGlueHere         ("/gluehere",   "<Player>",                     "commands.admin.gluehere", pManager),

            // CLASSICMODE
            new cmdClassic          ("/classic", "[Player_1] ... [Player_N]",            "commands.admin.classic", pManager),

            // FLASH COMMANDS
            new cmdFlash            ("/flash",      "",         "commands.admin.flash"),
            new cmdFlash            ("/lightning",  "",         "commands.admin.flash"),

            // KICK COMMANDS
            new cmdKick             ("/kick",       "<Player> [Message]",             "commands.admin.kick"),
            new cmdKickAll          ("/kickall",    "[Message]",                     "commands.admin.kickall"),

            // BAN COMMANDS
            new cmdUnban            ("/unban",      "<Player>",             "commands.admin.unban", pManager),
            new cmdBan              ("/ban",        "<Player> [Message]",   "commands.admin.ban", pManager),
            new cmdTempBan          ("/tempban",    "<Player> <Time>",      "commands.admin.tempban", pManager),

             // GIVE COMMANDS
            new cmdIAmount          ("/i",      "<ItemID or Name>[:SubID] [Amount]",            "commands.admin.i"),
            new cmdIAmount          ("/item",   "<ItemID or Name>[:SubID] [Amount]",            "commands.admin.i"),
            new cmdGiveAmount       ("/give",   "<Player> <ItemID or Name>[:SubID] [Amount]",   "commands.admin.give"),

            // KIT COMMAND
            new cmdKit              ("/kit",        "<Name>",   "commands.admin.usekit"),
            new cmdListKits         ("/listkits",   "",         "commands.admin.listkits"),

            // GOD & HEAL COMMANDS
            new cmdGod              ("/god",    "",         "commands.admin.god", pManager),
            new cmdHeal             ("/heal",   "",         "commands.admin.heal"),

            // INVENTORY COMMANDS
            new cmdClearInventory   ("/clearinventory", "",                         "commands.user.clearinventory"),
            new cmdClearInventory   ("/cli",            "",                         "commands.user.clearinventory"),
            new cmdInvsee           ("/invsee",         "",                         "commands.admin.invsee", pManager),
            new cmdFillChest        ("/fillchest",      "<ItemID or Name>[:SubID]", "commands.admin.fillchest"),
            new cmdStack            ("/stack",          "",                         "commands.user.stack"),

            // MESSAGE COMMANDS
            new cmdBroadcast        ("/broadcast",  "<Message>",            "commands.admin.broadcast"),
            new cmdBroadcast        ("/cast",       "<Message>",            "commands.admin.broadcast"), 
            new cmdMutePlayer       ("/mute",       "<Player>",             "commands.admin.mute", pManager),
            new cmdMessage          ("/message",    "<Player> <Message>",   "commands.user.message", pManager),
            new cmdMessage          ("/msg",        "<Player> <Message>",   "commands.user.message", pManager),
            new cmdMessage          ("/m",          "<Player> <Message>",   "commands.user.message", pManager),
            new cmdReply            ("/r",          "<Message>",            "commands.user.reply", pManager),
            new cmdMe               ("/me",         "<Message>",            "commands.admin.me", pManager),
            new cmdHideChat         ("/hidechat",   "",                     "commands.admin.hidechat", pManager),

            // RECIPIENT COMMANDS
            new cmdChatAdd          ("/chat", "",                           "commands.user.chat", pManager),

            // USER COMMANDS
            new cmdHelp             ("/help",       "",                     "commands.user.help"),
            new cmdHelpPage         ("/help",       "<Page>",               "commands.user.help"),
            new cmdAFK              ("/afk",        "",                     "commands.user.afk", pManager),
            new cmdCompass          ("/compass",    "",                     "commands.user.compass"), 
            new cmdNickname         ("/nickname",   "<Nickname> [Player]",  "commands.admin.nickname", pManager),
            new cmdNickname         ("/nick",       "<Nickname> [Player]",  "commands.admin.nickname", pManager),

            // TIME & WEATHER COMMAND
            new cmdTime             ("/time",       "<Time>",               "commands.admin.time"),
            new cmdWeather          ("/weather",    "<Weather>",            "commands.admin.weather"),

            // MISC COMMANDS
            new cmdPing             ("/ping",       "",         "commands.admin.ping"),
            new cmdSeen             ("/seen",       "<Player>", "commands.admin.seen", pManager)
        );
        //@formatter:on
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        cmdList.handleCommand(sender, label, args);
        return true;
    }

    @SuppressWarnings("unchecked")
    public void loadConfig() {
        new File("plugins/AdminStuff/").mkdirs();
        YamlConfiguration config = new YamlConfiguration();
        try {
            if (new File("plugins/AdminStuff/config.yml").exists()) {
                config.load(new File("plugins/AdminStuff/config.yml"));

                Map<String, ArrayList<String>> nodeList = (Map<String, ArrayList<String>>) config.getList("kits");

                if (nodeList == null)
                    return;

                for (Map.Entry<String, ArrayList<String>> entry : nodeList.entrySet()) {
                    ASKit thisKit = new ASKit();
                    for (String part : entry.getValue()) {
                        try {
                            String[] split = part.split(" ");

                            int TypeID = 0;
                            byte Data = 0;
                            int Amount = 1;

                            String[] itemSplit = split[0].split(":");
                            if (split.length > 1) {
                                Amount = Integer.valueOf(split[1]);
                            }

                            TypeID = Integer.valueOf(itemSplit[0]);
                            if (itemSplit.length > 1) {
                                Data = Byte.valueOf(itemSplit[1]);
                            }

                            if (ASItem.isValid(TypeID, Data)) {
                                ItemStack item = new ItemStack(TypeID);
                                item.setAmount(Amount);
                                item.setDurability(Data);
                                thisKit.addItem(item);
                            }
                        } catch (Exception e) {
                        }
                    }
                    kitList.put(entry.getKey().toLowerCase(), thisKit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
