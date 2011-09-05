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
		new cmdBurn("/burn", "<Player> <Time in seconds>",
			"commands.admin.burn", server),
		new cmdSlap("/slap", "<Player>", "commands.admin.slap", server),
		new cmdKill("/kill", "<Player>", "commands.admin.kill", server),
		new cmdGlue("/glue", "<Player>", "commands.admin.glue", server),
		new cmdGlueHere("/gluehere", "<Player>",
			"commands.admin.gluehere", server),

		// FLASH COMMANDS
		new cmdFlash("/flash", "", "commands.admin.flash", server),
		new cmdFlashPlayer("/flash", "<Player>",
			"commands.admin.flashplayer", server),
		new cmdFlash("/lightning", "", "commands.admin.flash", server),
		new cmdFlashPlayer("/lightning", "<Player>",
			"commands.admin.flashplayer", server),

		// KICK COMMANDS
		new cmdKick("/kick", "<Player>", "commands.admin.kick", server),
		new cmdKickMessage("/kick", "<Player> <Message>",
			"commands.admin.kick", server),
		new cmdKickAll("/kickall", "", "commands.admin.kickall", server),
		new cmdKickAllMessage("/kickall", "<Message>",
			"commands.admin.kickall", server),

		// BAN COMMANDS
		new cmdUnban("/unban", "<Player>", "commands.admin.unban",
			server),
		new cmdBan("/ban", "<Player>", "commands.admin.ban", server),
		new cmdBanMessage("/ban", "<Player> <Message>",
			"commands.admin.ban", server),
		new cmdTempBan("/tempban", "<Player> <Time>",
			"commands.admin.tempban", server),

		// GIVE COMMANDS
		new cmdINoAmount("/i", "<ItemID or Name>[:SubID]",
			"commands.admin.i", server),
		new cmdIAmount("/i", "<ItemID or Name>[:SubID] <Amount>",
			"commands.admin.i", server),
		new cmdINoAmount("/item", "<ItemID or Name>[:SubID]",
			"commands.admin.i", server),
		new cmdIAmount("/item", "<ItemID or Name>[:SubID] <Amount>",
			"commands.admin.i", server),
		new cmdGiveNoAmount("/give",
			"<Player> <ItemID or Name>[:SubID]",
			"commands.admin.give", server),
		new cmdGiveAmount("/give",
			"<Player> <ItemID or Name>[:SubID] <Amount>",
			"commands.admin.give", server),

		// KIT COMMAND
		new cmdKit("/kit", "<Name>", "commands.admin.usekit", server),
		new cmdListKits("/listkits", "", "commands.admin.listkits",
			server),

		// GOD & HEAL COMMANDS
		new cmdGod("/god", "", "commands.admin.god", server),
		new cmdGodPlayer("/god", "<Name>", "commands.admin.god", server),
		new cmdHeal("/heal", "", "commands.admin.heal", server),
		new cmdHealPlayer("/heal", "<Name>", "commands.admin.heal",
			server),

		// INVENTORY COMMANDS
		new cmdClearInventory("/clearinventory", "",
			"commands.user.clearinventory", server),
		new cmdClearInventoryOther("/clearinventory", "<Player>",
			"commands.admin.clearinventoryother", server),
		new cmdClearInventory("/cli", "",
			"commands.user.clearinventory", server),
		new cmdClearInventoryOther("/cli", "<Player>",
			"commands.admin.clearinventoryother", server),
		new cmdInvsee("/invsee", "", "commands.admin.invsee", server),
		new cmdInvseePlayer("/invsee", "<Player>",
			"commands.admin.invsee", server),
		new cmdFillChest("/fillchest", "<ItemID or Name>[:SubID]",
			"commands.admin.fillchest", server),
		new cmdStack("/stack", "", "commands.user.stack", server),

		// MESSAGE COMMANDS
		new cmdBroadcast("/broadcast", "<Message>",
			"commands.admin.broadcast", server),
		new cmdBroadcast("/cast", "<Message>",
			"commands.admin.broadcast", server),
		new cmdMutePlayer("/mute", "<Player>", "commands.admin.mute",
			server),
		new cmdMessage("/message", "<Player> <Message>",
			"commands.user.message", server),
		new cmdMessage("/msg", "<Player> <Message>",
			"commands.user.message", server),
		new cmdMessage("/m", "<Player> <Message>",
			"commands.user.message", server),
		new cmdReply("/r", "<Message>", "commands.user.reply", server),
		new cmdMe("/me", "<Message>", "commands.admin.me", server),
		new cmdHideChat("/hidechat", "", "commands.admin.hidechat",
			server),

		// RECIPIENT COMMANDS
		new cmdChatAdd("/chat", "<Player1 .. Player n>",
			"commands.user.chat", server),
		new cmdChatDel("/chat", "", "commands.user.chat", server),

		// UNLIMITED COMMANDS
		new cmdUnlimited("/unlimited", "<ItemID or Name>",
			"commands.admin.unlimited", server),
		new cmdUnlimitedOther("/unlimited",
			"<ItemID or Name> <Player>",
			"commands.admin.unlimited", server),

		// USER COMMANDS
		new cmdHelp("/help", "", "commands.user.help", server),
		new cmdHelpPage("/help", "<Page>", "commands.user.help", server),
		new cmdAFK("/afk", "", "commands.user.afk", server),
		new cmdCompass("/compass", "", "commands.user.compass", server),
		new cmdNickname("/nickname", "<Nickname>",
			"commands.admin.nickname", server),
		new cmdNickname("/nick", "<Nickname>",
			"commands.admin.nickname", server),
		new cmdNicknamePlayer("/nickname", "<Nickname> <Player>",
			"commands.admin.nicknameother", server),
		new cmdNicknamePlayer("/nick", "<Nickname> <Player>",
			"commands.admin.nicknameother", server),

		// TIME & WEATHER COMMAND
		new cmdTime("/time", "<day | night>", "commands.admin.time",
			server),
		new cmdWeather("/weather", "<sun | rain | storm>",
			"commands.admin.weather", server),

		// MISC COMMANDS
		new cmdPing("/ping", "", "commands.admin.ping", server), };

	initCommandList(commands);
    }

    public static void initCommandList(Command[] cmds) {
	for (Command cmd : cmds) {
	    if (cmd instanceof ExtendedCommand)
		commandList.put(cmd.getSyntax().toLowerCase(),
			(ExtendedCommand) cmd);
	    else
		commandList.put(cmd.getSyntax().toLowerCase() + "_"
			+ (cmd.getArguments().split("<").length - 1), cmd);
	}
    }

    public static void handleCommand(CommandSender sender, String label,
	    String[] args) {
	if (!label.startsWith("/"))
	    label = "/" + label.toLowerCase();

	if (sender instanceof Player) {
	    if (commandList.containsKey(label + "_" + args.length)) {
		// EXECUTE COMMAND
		commandList.get(label + "_" + args.length).run(args,
			(Player) sender);
		return;
	    } else {
		// SEARCH EXTENDED COMMAND
		if (commandList.containsKey(label)) {
		    if (commandList.get(label) instanceof ExtendedCommand) {
			((ExtendedCommand) commandList.get(label)).run(args,
				(Player) sender);
			return;
		    }
		}

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
