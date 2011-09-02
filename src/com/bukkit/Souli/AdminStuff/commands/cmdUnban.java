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

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASPlayer;
import com.bukkit.Souli.AdminStuff.Listener.ASPlayerListener;

public class cmdUnban extends Command {

    public cmdUnban(String syntax, String arguments, String node, Server server) {
	super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /unban <Player><br>
     * Unban a single player
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
	File playerFile = new File("plugins/AdminStuff/userdata/" + args[0]
		+ ".yml");
	if (playerFile.exists()) {
	    if (ASPlayerListener.playerMap.containsKey(args[0])) {
		ASPlayer unbanned = ASPlayerListener.playerMap.get(args[0]);
		unbanned.setBanned(false);
		unbanned.setTempBanned(false);
		unbanned.setBanEndTime(0);
		unbanned.saveConfig(args[0], false, false, false, false,
			true, false);
	    } else {
		ASPlayer unbanned = new ASPlayer();
		unbanned.loadConfig(args[0]);
		unbanned.setBanned(false);
		unbanned.setTempBanned(false);
		unbanned.setBanEndTime(0);
		unbanned.saveConfig(args[0], false, false, false, false,
			true, false);
	    }
	}
	player.sendMessage(ChatColor.GRAY + "Player '" + args[0]
		+ "' unbanned!");
	((CraftServer) ASCore.getMCServer()).getHandle().b(args[0]);
    }
}
