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
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bukkit.Souli.AdminStuff.ASItem;
import com.bukkit.Souli.AdminStuff.Listener.ASPlayerListener;

public class cmdFillChest extends Command {

	public cmdFillChest(String syntax, String arguments, String node,
			Server server) {
		super(syntax, arguments, node, server);
	}

	@Override
	/**
	 * Representing the command <br>
	 * /fillchest <ItemID or Name>[:SubID] <br>
	 * Fill a chest with a specified item
	 * 
	 * @param player
	 *            Called the command
	 * @param split
	 *            split[0] is the item name
	 */
	public void execute(String[] args, Player player) {
		int amount = 64;
		String ID = ASItem.getIDPart(args[0]);
		byte Data = ASItem.getDataPart(args[0]);
		if (ASItem.isValid(ID, Data)) {			
			ItemStack item = ASItem.getItemStack(ID, Data, amount);
			ASPlayerListener.queuedFillChest.put(player.getName(), item);
			player.sendMessage(ChatColor.GRAY + "Click on a chest to fill it with '" + Material.getMaterial(item.getTypeId()).name().toLowerCase() + "'!");
		} else {
			player.sendMessage(ChatColor.RED + "Item '" + args[0]
					+ "' not found!");
			player.sendMessage(ChatColor.GRAY + this.getSyntax() + " "
					+ this.getArguments());
		}
	}
}
