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

public abstract class ExtendedCommand extends Command {

    public ExtendedCommand(String syntax, String arguments, String node,
	    Server server) {
	super(syntax, arguments, node, server);
    }

    public void run(String[] args, Player player) {
	if (!super.hasRights(player)) {
	    player.sendMessage(ChatColor.RED + NO_RIGHT);
	    return;
	}

	if (!this.hasCorrectSyntax(args)) {
	    player.sendMessage(ChatColor.RED + getSyntax() + " "
		    + getArguments() + " " + getDescription());
	    return;
	}

	execute(args, player);
    }

    public abstract void execute(String[] args, Player player);

    @Override
    public boolean hasCorrectSyntax(String[] args) {
	return args.length >= super.countArguments();
    }
}
