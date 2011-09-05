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

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASItem;
import com.bukkit.Souli.AdminStuff.ASPlayer;
import com.gemo.utils.BlockUtils;

public class cmdUnlimitedOther extends Command {

    public cmdUnlimitedOther(String syntax, String arguments, String node,
	    Server server) {
	super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /unlimited <ItemID or Name> <Player><br>
     * This gives the given player unlimited amount of an ItemStack
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the item name
     *            split[1] is the player
     */
    public void execute(String[] args, Player player) {

	try {
	    Player target = ASCore.getPlayer(args[1]);
	    if (target != null) {
		if (!target.isDead() && target.isOnline()) {
		    String ID = ASItem.getIDPart(args[0]);
		    if (ASItem.isValid(ID, (byte) 0)) {
			// ADD PLAYER, IF NOT FOUND
			ASPlayer thisTarget = ASCore
				.getOrCreateASPlayer(target);

			boolean result = false;
			if (ASItem.isInteger(ID))
			    result = thisTarget.toggleUnlimitedItem(Integer
				    .valueOf(ID));
			else
			    result = thisTarget
				    .toggleUnlimitedItem(ASItem.matList.get(ID
					    .toLowerCase()));

			String matName = ID;
			if (ASItem.isInteger(ID))
			    matName = Material.getMaterial(Integer.valueOf(ID))
				    .name().toLowerCase();
			else
			    matName = Material
				    .getMaterial(
					    BlockUtils.getItemIDFromName(ID))
				    .name().toLowerCase();

			if (result) {
			    player.sendMessage(ChatColor.GRAY + "'"
				    + ASCore.getPlayerName(target)
				    + "' has now unlimited amount of '"
				    + matName + "'.");
			    target.sendMessage(ChatColor.GRAY
				    + "You have now unlimited amount of '"
				    + matName + "'.");
			} else {
			    player.sendMessage(ChatColor.GRAY + "'"
				    + ASCore.getPlayerName(target)
				    + "' has no longer unlimited amount of '"
				    + matName + "'.");
			    target.sendMessage(ChatColor.GRAY
				    + "You do no longer have unlimited amount of '"
				    + matName + "'.");
			}
			thisTarget.saveConfig(false, false, true, false, false,
				false, false);

		    } else {
			player.sendMessage(ChatColor.RED + "Item '" + args[0]
				+ "' not found!");
			player.sendMessage(ChatColor.GRAY + this.getSyntax()
				+ " " + this.getArguments());
		    }
		}
	    } else {
		player.sendMessage(ChatColor.RED + "Player '" + args[0]
			+ "' not found (or is not online!)");
	    }
	} catch (Exception e) {
	    player.sendMessage(ChatColor.RED + "Wrong Syntax!");
	    player.sendMessage(ChatColor.GRAY + this.getSyntax() + " "
		    + this.getArguments());
	}
    }
}
