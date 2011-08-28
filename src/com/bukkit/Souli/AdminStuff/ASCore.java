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

package com.bukkit.Souli.AdminStuff;

import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import com.bukkit.Souli.AdminStuff.Listener.ASBlockListener;
import com.bukkit.Souli.AdminStuff.Listener.ASPlayerListener;
import com.bukkit.Souli.AdminStuff.commands.CommandList;

public class ASCore extends JavaPlugin {
	// GLOBAL VARS
	public static LogUnit log = null;
	private String pluginName = "";
	private String version = "";
	private static Server server = null;

	// LISTENERS
	private ASBlockListener bListener;
	private ASPlayerListener pListener;

	/**
	 * ON DISABLE
	 */
	@Override
	public void onDisable() {
		log.printInfo("v" + this.version + " disabled!");
	}

	/**
	 * ON ENABLE
	 */
	@Override
	public void onEnable() {
		ASCore.setMCServer(getServer());
		this.pluginName = this.getDescription().getName();
		this.version = this.getDescription().getVersion();
		log = LogUnit.getInstance(pluginName);

		boolean error = false;
		try {
			ASSpawn.loadAllSpawns();

			bListener = new ASBlockListener();
			pListener = new ASPlayerListener();
			new CommandList(getServer());

			for (Player player : getServer().getOnlinePlayers()) {
				ASPlayerListener.playerMap
						.put(player.getName(), new ASPlayer());
				ASPlayerListener.playerMap.get(player.getName()).loadConfig(
						player.getName());
			}
			getServer().getPluginManager().registerEvent(
					Event.Type.BLOCK_PLACE, bListener, Event.Priority.Monitor,
					this);
			getServer().getPluginManager().registerEvent(
					Event.Type.PLAYER_JOIN, pListener, Event.Priority.Monitor,
					this);
			getServer().getPluginManager().registerEvent(
					Event.Type.PLAYER_KICK, pListener, Event.Priority.Monitor,
					this);
			getServer().getPluginManager().registerEvent(
					Event.Type.PLAYER_MOVE, pListener, Event.Priority.Normal,
					this);
			getServer().getPluginManager().registerEvent(
					Event.Type.PLAYER_RESPAWN, pListener,
					Event.Priority.Normal, this);
			getServer().getPluginManager().registerEvent(
					Event.Type.PLAYER_QUIT, pListener, Event.Priority.Monitor,
					this);
		} catch (Exception e) {
			log.printError("ERROR while enabling " + pluginName + "!", e);
		}

		if (!error)
			log.printInfo("v" + this.version + " enabled!");
	}

	/**
	 * ON COMMAND
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		CommandList.handleCommand(sender, label, args);
		return true;
	}

	// GET PLAYER
	public static Player getPlayer(String name) {
		List<Player> matchedPlayers = server.matchPlayer(name);
		if (matchedPlayers != null)
			if (matchedPlayers.size() > 0)
				return matchedPlayers.get(0);

		/*
		 * for(Player player : ASCore.getMCServer().getOnlinePlayers()) {
		 * if(player.getDisplayName() != null) {
		 * if(player.getDisplayName().toLowerCase
		 * ().contains(name.toLowerCase())) { return player; } } }
		 */

		return null;
	}

	public static void setMCServer(Server server) {
		ASCore.server = server;
	}

	public static Server getMCServer() {
		return ASCore.server;
	}
}
