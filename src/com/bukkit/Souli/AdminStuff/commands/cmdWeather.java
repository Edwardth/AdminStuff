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

public class cmdWeather extends Command {

    public cmdWeather(String syntax, String arguments, String node,
	    Server server) {
	super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /weather sun | rain | storm<br>
     * This changes the worldweather
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the weather
     */
    public void execute(String[] args, Player player) {
	if (args[0].equalsIgnoreCase("sun")) {
	    player.getWorld().setThundering(false);
	    player.getWorld().setStorm(false);
	    player.sendMessage(ChatColor.GRAY
		    + "Weather in your world changed to '"
		    + args[0].toLowerCase() + "'!");
	    return;
	} else if (args[0].equalsIgnoreCase("rain")) {
	    player.getWorld().setStorm(true);
	    player.getWorld().setThundering(false);
	    player.sendMessage(ChatColor.GRAY
		    + "Weather in your world changed to '"
		    + args[0].toLowerCase() + "'!");
	    return;
	} else if (args[0].equalsIgnoreCase("storm")) {
	    player.getWorld().setStorm(true);
	    player.getWorld().setThundering(true);
	    player.sendMessage(ChatColor.GRAY
		    + "Weather in your world changed to '"
		    + args[0].toLowerCase() + "'!");
	    return;
	}
	player.sendMessage(ChatColor.RED + "Wrong syntax!");
	player.sendMessage(ChatColor.GRAY + this.getSyntax() + " "
		+ this.getArguments());
    }
}
