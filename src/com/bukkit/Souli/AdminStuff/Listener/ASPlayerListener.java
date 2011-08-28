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

package com.bukkit.Souli.AdminStuff.Listener;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASPlayer;
import com.bukkit.Souli.AdminStuff.ASSpawn;
import com.gemo.utils.BlockUtils;

public class ASPlayerListener extends PlayerListener {
	public static Map<String, ASPlayer> playerMap = new TreeMap<String, ASPlayer>();

	@Override
	public void onPlayerMove(PlayerMoveEvent event)
	{		
		if(BlockUtils.LocationEquals(event.getTo(), event.getFrom()))
			return;
		
		// ADD PLAYER, IF NOT FOUND
		Player player = event.getPlayer();
		if (!ASPlayerListener.playerMap.containsKey(player.getName())) {
			ASPlayerListener.playerMap.put(player.getName(), new ASPlayer());
			return;
		}
		
		// IS PLAYER GLUED = RETURN TO GLUELOCATION
		if(ASPlayerListener.playerMap.get(player.getName()).isGlued())
		{
			event.setTo(ASPlayerListener.playerMap.get(player.getName()).getGlueLocation());
		}
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!playerMap.containsKey(event.getPlayer().getName())) {
			playerMap.put(event.getPlayer().getName(), new ASPlayer());
		}
		
		playerMap.get(event.getPlayer().getName()).loadConfig(event.getPlayer().getName());
		ASPlayer.updateNick(event.getPlayer().getName(), playerMap.get(event.getPlayer().getName()).isAFK(), playerMap.get(event.getPlayer().getName()).isSlapped());
		
	}

	@Override
	public void onPlayerKick(PlayerKickEvent event) {
		if (event.isCancelled())
			return;
		if (playerMap.containsKey(event.getPlayer().getName())) {
			playerMap.remove(event.getPlayer().getName());
		}
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (playerMap.containsKey(event.getPlayer().getName())) {
			playerMap.remove(event.getPlayer().getName());
		}
	}

	@Override
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Location loc = ASSpawn.getSpawn(ASCore.getMCServer().getWorlds().get(0));
		Location nloc = loc.getWorld().getHighestBlockAt(loc).getLocation();
		nloc.setYaw(loc.getYaw());
		nloc.setPitch(loc.getPitch());
		event.setRespawnLocation(nloc);
	}
}
