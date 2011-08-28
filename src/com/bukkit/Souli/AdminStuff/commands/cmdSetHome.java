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

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.bukkit.Souli.AdminStuff.ASPlayer;
import com.bukkit.Souli.AdminStuff.Listener.ASPlayerListener;

public class cmdSetHome extends Command {

	public cmdSetHome(String syntax, String arguments, String node, Server server) {
		super(syntax, arguments, node, server);
	}

	@Override
	/**
	 * Representing the command <br>
	 * /sethome <br>
	 * Set homelocation
	 * 
	 * @param player
	 *            Called the command
	 * @param split
	 */
	public void execute(String[] args, Player player) {
		// ADD PLAYER, IF NOT FOUND
		if (!ASPlayerListener.playerMap.containsKey(player.getName())) {
			ASPlayerListener.playerMap.put(player.getName(), new ASPlayer());
		}
		ASPlayerListener.playerMap.get(player.getName()).setHomeLocation(
				player.getLocation());
		ASPlayerListener.playerMap.get(player.getName()).saveConfig(player.getName(), true, false, false, false, false);
		player.sendMessage(ChatColor.GRAY + "Home set.");
	}
}
