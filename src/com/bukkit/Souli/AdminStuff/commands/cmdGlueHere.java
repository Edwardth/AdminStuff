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
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASPlayer;
import com.bukkit.Souli.AdminStuff.Listener.ASPlayerListener;

public class cmdGlueHere extends Command {

    public cmdGlueHere(String syntax, String arguments, String node,
	    Server server) {
	super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /gluehere <Player><br>
     * Kills the Player and clears the inventory
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
	Player target = ASCore.getPlayer(args[0]);
	if (target != null) {
	    if (!target.isDead() && target.isOnline()) {
		// ADD PLAYER, IF NOT FOUND
		if (!ASPlayerListener.playerMap.containsKey(target.getName())) {
		    ASPlayerListener.playerMap.put(target.getName(),
			    new ASPlayer());
		}

		Location glueLocation = player.getLastTwoTargetBlocks(null, 50)
			.get(0).getLocation();

		ASPlayer thisPlayer = ASPlayerListener.playerMap.get(target
			.getName());
		thisPlayer.setGlued(!thisPlayer.isGlued());
		if (thisPlayer.isGlued()) {
		    target.teleport(glueLocation);
		    thisPlayer.setGlueLocation(glueLocation);
		    player.sendMessage(ChatColor.GRAY + "Player '"
			    + ASCore.getPlayerName(target) + "' glued!");
		    target.sendMessage(ChatColor.BLUE + "You are now glued!");
		} else {
		    thisPlayer.setGlueLocation(null);
		    player.sendMessage(ChatColor.GRAY + "Player '"
			    + ASCore.getPlayerName(target) + "' is no longer glued!");
		    target.sendMessage(ChatColor.BLUE
			    + "You are no longer glued!");
		}

		thisPlayer.saveConfig(target.getName(), false, false,
			false, true, false, false, false);
	    }
	} else {
	    player.sendMessage(ChatColor.RED + "Player '" + args[0]
		    + "' not found (or is not online!)");
	}
    }
}
