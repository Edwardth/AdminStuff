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

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import de.minestar.AdminStuff.commands.cmdAFK;
import de.minestar.AdminStuff.commands.cmdBan;
import de.minestar.AdminStuff.commands.cmdBlockCount;
import de.minestar.AdminStuff.commands.cmdBroadcast;
import de.minestar.AdminStuff.commands.cmdBurn;
import de.minestar.AdminStuff.commands.cmdButcher;
import de.minestar.AdminStuff.commands.cmdChat;
import de.minestar.AdminStuff.commands.cmdClassic;
import de.minestar.AdminStuff.commands.cmdClearInventory;
import de.minestar.AdminStuff.commands.cmdCompass;
import de.minestar.AdminStuff.commands.cmdDeleteItem;
import de.minestar.AdminStuff.commands.cmdFillChest;
import de.minestar.AdminStuff.commands.cmdFlash;
import de.minestar.AdminStuff.commands.cmdGive;
import de.minestar.AdminStuff.commands.cmdGlue;
import de.minestar.AdminStuff.commands.cmdGlueHere;
import de.minestar.AdminStuff.commands.cmdGod;
import de.minestar.AdminStuff.commands.cmdHeal;
import de.minestar.AdminStuff.commands.cmdHideChat;
import de.minestar.AdminStuff.commands.cmdInvsee;
import de.minestar.AdminStuff.commands.cmdItem;
import de.minestar.AdminStuff.commands.cmdKick;
import de.minestar.AdminStuff.commands.cmdKickAll;
import de.minestar.AdminStuff.commands.cmdKill;
import de.minestar.AdminStuff.commands.cmdKit;
import de.minestar.AdminStuff.commands.cmdListKits;
import de.minestar.AdminStuff.commands.cmdMe;
import de.minestar.AdminStuff.commands.cmdMessage;
import de.minestar.AdminStuff.commands.cmdMute;
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
import de.minestar.AdminStuff.listener.EntityListener;
import de.minestar.AdminStuff.listener.PlayerListener;
import de.minestar.AdminStuff.manager.KitManager;
import de.minestar.AdminStuff.manager.PlayerManager;
import de.minestar.minestarlibrary.AbstractCore;
import de.minestar.minestarlibrary.commands.CommandList;

public class Core extends AbstractCore {

    /** Listener */
    private Listener entityListener;
    private Listener playerListener;

    /** Manager */
    private DatabaseHandler dbHandler;
    private PlayerManager pManager;
    private KitManager kManager;

    public Core() {
        super("AdminStuff");
    }

    @Override
    protected boolean createManager() {
        dbHandler = new DatabaseHandler(getDataFolder());
        pManager = new PlayerManager(dbHandler);
        kManager = new KitManager(getDataFolder());
        return true;
    }

    @Override
    protected boolean createListener() {
        entityListener = new EntityListener(pManager);
        playerListener = new PlayerListener(pManager);
        return true;
    }

    @Override
    protected boolean registerEvents(PluginManager pm) {
        pm.registerEvents(entityListener, this);
        pm.registerEvents(playerListener, this);
        return true;
    }

    @Override
    protected boolean createCommands() {
        //@formatter:off
        cmdList = new CommandList(NAME,
                // USER PUNISH COMMANDS
                new cmdBurn             ("/burn",       "<Player> <Time in seconds>",   "adminstuff.commands.admin.burn"),
                new cmdSlap             ("/slap",       "<Player>",                     "adminstuff.commands.admin.slap", pManager),
                new cmdKill             ("/kill",       "[Player_1] ... [Player_N]",    "adminstuff.commands.admin.kill"),
                new cmdGlue             ("/glue",       "<Player>",                     "adminstuff.commands.admin.glue", pManager),
                new cmdGlueHere         ("/gluehere",   "<Player>",                     "adminstuff.commands.admin.gluehere", pManager),

                // CLASSICMODE
                new cmdClassic          ("/classic", "[Player_1] ... [Player_N]",            "adminstuff.commands.admin.classic", pManager),

                // FLASH COMMANDS
                new cmdFlash            ("/flash",      "",         "adminstuff.commands.admin.flash"),
                new cmdFlash            ("/lightning",  "",         "adminstuff.commands.admin.flash"),

                // KICK COMMANDS
                new cmdKick             ("/kick",       "<Player> [Message]",             "adminstuff.commands.admin.kick"),
                new cmdKickAll          ("/kickall",    "[Message]",                     "adminstuff.commands.admin.kickall"),

                // BAN COMMANDS
                new cmdUnban            ("/unban",      "<Player>",             "adminstuff.commands.admin.unban"),
                new cmdBan              ("/ban",        "<Player> [Message]",   "adminstuff.commands.admin.ban"),
                new cmdTempBan          ("/tempban",    "<Player> <Time>",      "adminstuff.commands.admin.tempban"),

                 // GIVE COMMANDS
                new cmdItem                ("/i",      "<ItemID or Name>[:SubID] [Amount]",            "adminstuff.commands.admin.i"),
                new cmdItem                ("/item",   "<ItemID or Name>[:SubID] [Amount]",            "adminstuff.commands.admin.i"),
                new cmdGive       ("/give",   "<Player> <ItemID or Name>[:SubID] [Amount]",   "adminstuff.commands.admin.give"),

                // KIT COMMAND
                new cmdKit              ("/kit",        "<Name>",   "adminstuff.commands.admin.usekit", kManager),
                new cmdListKits         ("/listkits",   "",         "adminstuff.commands.admin.listkits", kManager),

                // GOD & HEAL COMMANDS
                new cmdGod              ("/god",    "",         "adminstuff.commands.admin.god", pManager),
                new cmdHeal             ("/heal",   "",         "adminstuff.commands.admin.heal"),

                // INVENTORY COMMANDS
                new cmdClearInventory   ("/clearinventory", "",                         "adminstuff.commands.user.clearinventory"),
                new cmdClearInventory   ("/cli",            "",                         "adminstuff.commands.user.clearinventory"),
                new cmdInvsee           ("/invsee",         "",                         "adminstuff.commands.admin.invsee"),
                new cmdFillChest        ("/fillchest",      "<ItemID or Name>[:SubID]", "adminstuff.commands.admin.fillchest"),
                new cmdStack            ("/stack",          "",                         "adminstuff.commands.user.stack"),

                // MESSAGE COMMANDS
                new cmdBroadcast        ("/broadcast",  "<Message>",            "adminstuff.commands.admin.broadcast"),
                new cmdBroadcast        ("/cast",       "<Message>",            "adminstuff.commands.admin.broadcast"), 
                new cmdMute       ("/mute",       "<Player>",             "adminstuff.commands.admin.mute", pManager),
                new cmdMessage          ("/message",    "<Player> <Message>",   "adminstuff.commands.user.message", pManager),
                new cmdMessage          ("/msg",        "<Player> <Message>",   "adminstuff.commands.user.message", pManager),
                new cmdMessage          ("/m",          "<Player> <Message>",   "adminstuff.commands.user.message", pManager),
                new cmdReply            ("/r",          "<Message>",            "adminstuff.commands.user.reply", pManager),
                new cmdMe               ("/me",         "<Message>",            "adminstuff.commands.admin.me", pManager),
                new cmdHideChat         ("/hidechat",   "",                     "adminstuff.commands.admin.hidechat", pManager),

                // RECIPIENT COMMANDS
                new cmdChat          ("/chat", "",                           "adminstuff.commands.user.chat", pManager),

                // USER COMMANDS
                new cmdAFK              ("/afk",        "",                     "adminstuff.commands.user.afk"),
                new cmdCompass          ("/compass",    "",                     "adminstuff.commands.user.compass"), 
                new cmdNickname         ("/nickname",   "<Nickname> [Player]",  "adminstuff.commands.admin.nickname"),
                new cmdNickname         ("/nick",       "<Nickname> [Player]",  "adminstuff.commands.admin.nickname"),

                // TIME & WEATHER COMMAND
                new cmdTime             ("/time",       "<Time>",               "adminstuff.commands.admin.time"),
                new cmdWeather          ("/weather",    "<Weather>",            "adminstuff.commands.admin.weather"),

                // MISC COMMANDS
                new cmdPing             ("/ping",       "",         "adminstuff.commands.admin.ping"),
                new cmdSeen             ("/seen",       "<Player>", "adminstuff.commands.admin.seen", pManager),

                // BLOCK COUNT
                new cmdBlockCount       ("/bcount",     "",         "adminstuff.commands.admin.count", pManager),

                // REMOVE ENTITYS
                new cmdDeleteItem       ("/delitem",    "i[ItemID] r[Radius]",    "adminstuff.commands.admin.delitem"),
                new cmdButcher          ("/butcher",    "m[MobName] r[Radius]",    "adminstuff.commands.admin.butcher")
            );
            //@formatter:on
        return true;
    }
}
