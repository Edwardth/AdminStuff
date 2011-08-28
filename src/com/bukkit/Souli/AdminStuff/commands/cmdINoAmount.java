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

public class cmdINoAmount extends Command {

	public cmdINoAmount(String syntax, String arguments, String node,
			Server server) {
		super(syntax, arguments, node, server);
	}

	@Override
	/**
	 * Representing the command <br>
	 * /i <ItemID or Name>[:SubID] <br>
	 * This gives the player a specified itemstack
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
			player.getInventory().addItem(item);
			player.sendMessage(ChatColor.YELLOW + "Giving you " + amount + " of " + Material.getMaterial(item.getTypeId()) + ((Data > 0) ? (":" + Data) : ""));
		} else {
			player.sendMessage(ChatColor.RED + "Item '" + args[0]
					+ "' not found!");
			player.sendMessage(ChatColor.GRAY + this.getSyntax() + " "
					+ this.getArguments());
		}

	}
}
