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

package com.bukkit.Souli.AdminStuff.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandList {
    private static Map<String, Command> commandList = new TreeMap<String, Command>();

    public CommandList(Server server) {
	Command[] commands = new Command[] {
		// USER PUNISH COMMANDS
		new cmdSlap("/slap", "<Player>", "commands.admin.slap", server),
		new cmdKill("/kill", "<Player>", "commands.admin.kill", server),
		new cmdGlue("/glue", "<Player>", "commands.admin.glue", server),
		new cmdGlueHere("/gluehere", "<Player>",
			"commands.admin.gluehere", server),

		// GIVE ITEMS
		new cmdINoAmount("/i", "<ItemID or Name>[:SubID]",
			"commands.admin.i", server),
		new cmdIAmount("/i", "<ItemID or Name>[:SubID] <Amount>",
			"commands.admin.i", server),
		new cmdGiveNoAmount("/give",
			"<Player> <ItemID or Name>[:SubID]",
			"commands.admin.give", server),
		new cmdGiveAmount("/give",
			"<Player> <ItemID or Name>[:SubID] <Amount>",
			"commands.admin.give", server),

		// INVENTORY COMMANDS
		new cmdClearInventory("/clearinventory", "",
			"commands.user.clearinventory", server),
		new cmdClearInventoryOther("/clearinventory", "<Player>",
			"commands.admin.clearinventoryother", server),
		new cmdInvsee("/invsee", "", "commands.admin.invsee", server),
		new cmdInvseePlayer("/invsee", "<Player>", "commands.admin.invsee", server),

		// UNLIMITED COMMANDS
		new cmdUnlimited("/unlimited", "<ItemID or Name>",
			"commands.admin.unlimited", server),
		new cmdUnlimitedOther("/unlimited",
			"<ItemID or Name> <Player>",
			"commands.admin.unlimited", server),

		// USER COMMANDS
		new cmdAFK("/afk", "", "commands.user.afk", server),
		new cmdHome("/home", "", "commands.user.home", server),
		new cmdSetHome("/sethome", "", "commands.user.sethome", server),
		new cmdCompass("/compass", "", "commands.user.compass", server),

		// SPAWN COMMANDS
		new cmdSpawn("/spawn", "", "commands.admin.spawn", server),
		new cmdSetSpawn("/setspawn", "", "commands.admin.setspawn",
			server),

		// TIME & WEATHER COMMAND
		new cmdTime("/time", "<day | night>", "commands.admin.time",
			server),
		new cmdWeather("/weather", "<sun | rain | storm>",
			"commands.admin.weather", server), };

	initCommandList(commands);
    }

    public static void initCommandList(Command[] cmds) {
	for (Command cmd : cmds) {
	    commandList.put(
		    cmd.getSyntax() + "_"
			    + (cmd.getArguments().split("<").length - 1), cmd);
	}
    }

    public static void handleCommand(CommandSender sender, String label,
	    String[] args) {
	if (!label.startsWith("/"))
	    label = "/" + label;

	if (sender instanceof Player) {
	    if (commandList.containsKey(label + "_" + args.length)) {
		// EXECUTE COMMAND
		commandList.get(label + "_" + args.length).run(args,
			(Player) sender);
		return;
	    } else {
		// NO COMMAND FOUND
		((Player) sender).sendMessage(ChatColor.RED
			+ "Wrong Syntax for command: '" + label + "'");

		// FIND RELATED COMMANDS
		ArrayList<Command> cmdList = new ArrayList<Command>();
		for (Map.Entry<String, Command> entry : commandList.entrySet()) {
		    if (entry.getKey().startsWith(label))
			cmdList.add(entry.getValue());
		}

		// PRINT SYNTAX
		for (Command cmd : cmdList) {
		    ((Player) sender).sendMessage(ChatColor.GRAY
			    + cmd.getSyntax() + " " + cmd.getArguments());
		}
		return;
	    }
	}
    }
}
