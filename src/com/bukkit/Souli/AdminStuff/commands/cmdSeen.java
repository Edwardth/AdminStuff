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

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASLocalizer;
import com.bukkit.Souli.AdminStuff.ASPlayer;

public class cmdSeen extends Command {

    public cmdSeen(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /seen <Player><br>
     * Show the LastSeen-Date
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        Player target = ASCore.getPlayer(args[0]);
        if (target != null) {
            if (target.isOnline()) {
                // ADD PLAYER, IF NOT FOUND
                ASPlayer thisTarget = ASCore.getOrCreateASPlayer(target);
                player.sendMessage(ASLocalizer.format("LAST_SEEN", ChatColor.GRAY, ASCore.getPlayerName(target), thisTarget.getLastSeen()));
            }
        } else {
            File playerFile = new File("plugins/AdminStuff/userdata/" + args[0].toLowerCase() + ".yml");
            if (playerFile.exists()) {
                ASPlayer thisTarget = ASCore.getOrCreateASPlayer(args[0]);
                player.sendMessage(ASLocalizer.format("LAST_SEEN", ChatColor.GRAY, args[0], thisTarget.getLastSeen()));
            } else {
                player.sendMessage(ASLocalizer.format("LAST_SEEN", ChatColor.GRAY, args[0], ASLocalizer.getValue("LAST_SEEN_NEVER")));
            }
        }
    }
}
