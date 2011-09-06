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

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASItem;

public class cmdGiveNoAmount extends Command {

    public cmdGiveNoAmount(String syntax, String arguments, String node,
	    Server server) {
	super(syntax, arguments, node, server);
    }

    @SuppressWarnings("deprecation")
    @Override
    /**
     * Representing the command <br>
     * /give <ItemID or Name>[:SubID] <br>
     * This gives the player a specified itemstack
     * 
     * @param player
     *            Called the command
     * @param split
     * 	          split[0] is the targets name
     *            split[1] is the item name
     */
    public void execute(String[] args, Player player) {
	Player target = ASCore.getPlayer(args[0]);
	if (target != null) {
	    int amount = 64;
	    String ID = ASItem.getIDPart(args[1]);
	    byte Data = ASItem.getDataPart(args[1]);
	    if (ASItem.isValid(ID, Data)) {
		ItemStack item = ASItem.getItemStack(ID, Data, amount);
		target.getInventory().addItem(item);
		player.updateInventory();
		player.sendMessage(ChatColor.GRAY + "Giving '" + ASCore.getPlayerName(target) + "' " + amount
			+ " of " + Material.getMaterial(item.getTypeId())
			+ ((Data > 0) ? (":" + Data) : ""));
		target.sendMessage(ChatColor.GRAY + "Giving you " + amount
			+ " of " + Material.getMaterial(item.getTypeId())
			+ ((Data > 0) ? (":" + Data) : ""));
	    } else {
		player.sendMessage(ChatColor.RED + "Item '" + args[1]
			+ "' not found!");
		player.sendMessage(ChatColor.GRAY + this.getSyntax() + " "
			+ this.getArguments());
	    }
	} else {
	    player.sendMessage(ChatColor.RED + "Player '" + args[0]
		    + "' not found (or is not online!)");
	}
    }
}
