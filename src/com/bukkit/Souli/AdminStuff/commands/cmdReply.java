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
import com.bukkit.Souli.AdminStuff.ASPlayer;
import com.bukkit.Souli.AdminStuff.Listener.ASPlayerListener;

public class cmdReply extends ExtendedCommand {

    public cmdReply(String syntax, String arguments, String node, Server server) {
	super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /r <Message><br>
     * Reply a player
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
	// ADD PLAYER, IF NOT FOUND
	if (!ASPlayerListener.playerMap.containsKey(player.getName())) {
	    ASPlayerListener.playerMap.put(player.getName(), new ASPlayer());
	}
	
	if(ASPlayerListener.playerMap.get(player.getName()).getLastSender() == null)
	{
	    return;
	}
	
	Player target = ASCore.getPlayer(ASPlayerListener.playerMap.get(player.getName()).getLastSender());
	if (target != null) {
	    if (target.isOnline()) {
		// ADD PLAYER, IF NOT FOUND
		if (!ASPlayerListener.playerMap.containsKey(target.getName())) {
		    ASPlayerListener.playerMap.put(target.getName(),
			    new ASPlayer());
		}

		String message = "";
		for (int i = 0; i < args.length; i++) {
		    message += args[i] + " ";
		}

		player.sendMessage(ChatColor.GOLD + "[ me -> "
			+ target.getName() + " ] : " + ChatColor.GRAY + message);
		target.sendMessage(ChatColor.GOLD + "[ " + player.getName()
			+ " -> me ] : " + ChatColor.GRAY + message);

		ASPlayerListener.playerMap.get(player.getName()).setLastSender(
			target.getName());
		ASPlayerListener.playerMap.get(target.getName()).setLastSender(
			player.getName());
	    }
	} else {
	    player.sendMessage(ChatColor.RED + "Player '" + ASPlayerListener.playerMap.get(player.getName()).getLastSender()
		    + "' not found (or is not online!)");
	}
    }
}
