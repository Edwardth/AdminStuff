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
import org.bukkit.inventory.ItemStack;

import com.bukkit.Souli.AdminStuff.ASItem;

public class cmdStack extends Command {

    public cmdStack(String syntax, String arguments, String node, Server server) {
	super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /stack <br>
     * Stack your inventory
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
	ItemStack[] old = new ItemStack[36];
	// SAVE INVENTORY
	for (int i = 0; i < player.getInventory().getSize(); i++) {
	    if (player.getInventory().getItem(i) != null) {
		if (player.getInventory().getItem(i).getTypeId() > 0) {
		    old[i] = player.getInventory().getItem(i).clone();
		}
	    }
	}

	// STACK ITEMS
	for (int i = 0; i < old.length; i++) {
	    if (old[i] != null) {
		if (old[i].getTypeId() > 0) {
		    int restOpen = ASItem.getMaxStackSize(old[i].getType())
			    - old[i].getAmount();
		    if(restOpen < 0)
			restOpen = 0;

		    for (int j = i + 1; j < old.length; j++) {
			if (old[j] != null) {
			    if (old[j].getTypeId() > 0) {
				if (old[i].getTypeId() == old[j].getTypeId()
					&& old[i].getDurability() == old[j]
						.getDurability()) {
				    int canPlace = Math.min(restOpen,
					    old[j].getAmount());
				    old[i].setAmount(old[i].getAmount()
					    + canPlace);

				    if (old[j].getAmount() - canPlace > 0)
					old[j].setAmount(old[j].getAmount()
						- canPlace);
				    else
					old[j] = null;
				}
			    }
			}
		    }
		}
	    }
	}
	
	// CLEAR INVENTORY
	player.getInventory().clear();

	// REWRITE ITEMS
	for (int i = 0; i < old.length; i++) {
	    if (old[i] != null) {
		if (old[i].getTypeId() > 0) {
		    player.getInventory().setItem(i, old[i]);
		}
	    }
	}
	
	player.sendMessage(ChatColor.GRAY + "Items stacked.");
    }
}
