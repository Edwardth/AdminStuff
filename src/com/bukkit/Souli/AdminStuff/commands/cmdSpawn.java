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

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASSpawn;

public class cmdSpawn extends Command {

	public cmdSpawn(String syntax, String arguments, String node,
			Server server) {
		super(syntax, arguments, node, server);
	}

	@Override
	/**
	 * Representing the command <br>
	 * /spawn <br>
	 * Teleport to worldspawn
	 * 
	 * @param player
	 *            Called the command
	 * @param split
	 */
	public void execute(String[] args, Player player) {
		Location loc = ASSpawn.getSpawn(ASCore.getMCServer().getWorlds().get(0));
		Location nloc = loc.getWorld().getHighestBlockAt(loc).getLocation();
		nloc.setYaw(loc.getYaw());
		nloc.setPitch(loc.getPitch());
		player.teleport(nloc);
		player.teleport(ASSpawn.getSpawn(player.getWorld()));			
	}
}
