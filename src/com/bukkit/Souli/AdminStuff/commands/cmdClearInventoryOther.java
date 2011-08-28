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

import com.bukkit.Souli.AdminStuff.ASCore;

public class cmdClearInventoryOther extends Command {

	public cmdClearInventoryOther(String syntax, String arguments, String node,
			Server server) {
		super(syntax, arguments, node, server);
	}

	@Override
	/**
	 * Representing the command <br>
	 * /clearinventory <Player><br>
	 * This clears your inventory
	 * 
	 * @param player
	 *            Called the command
	 * @param split
	 *            split[0] is the targets name
	 */
	public void execute(String[] args, Player player) {
		Player target = ASCore.getPlayer(args[0]);
		if(target != null)
		{
			if(!target.isDead() && target.isOnline())
			{
				target.getInventory().clear();
				player.sendMessage(ChatColor.GRAY + "Inventory of '" + target.getName() + "' cleared!");
				target.sendMessage(ChatColor.GRAY + "Your inventory got cleared!");
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + "Player '" + args[0] + "' not found (or is not online!)");
		}
	}
}
