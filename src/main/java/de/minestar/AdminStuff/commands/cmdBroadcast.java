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

package de.minestar.AdminStuff.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;

public class cmdBroadcast extends AbstractExtendedCommand {

    public cmdBroadcast(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /broadcast <Message><br>
     * Broadcast a message
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        broadCast(args);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        broadCast(args);
    }

    private void broadCast(String[] args) {

        StringBuilder sBuilder = new StringBuilder(256);
        int i = 0;
        for (; i < args.length; ++i) {
            sBuilder.append(args[i]);
            sBuilder.append(' ');
        }
        sBuilder.append(args[i]);

        Bukkit.broadcastMessage(ChatColor.RED + "[BROADCAST] : " + ChatColor.GREEN + sBuilder.toString());
    }
}
