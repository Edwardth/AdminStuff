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
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(player);
        if (thisPlayer.getLastSender() == null) {
            return;
        }

        Player target = ASCore.getPlayer(thisPlayer.getLastSender());
        if (target != null) {
            if (target.isOnline()) {
                ASPlayer thisTarget = ASCore.getOrCreateASPlayer(target);

                String message = "";
                for (int i = 0; i < args.length; i++) {
                    message += args[i] + " ";
                }

                player.sendMessage(ChatColor.GOLD + "[ me -> " + ASCore.getPlayerName(target) + " ] : " + ChatColor.GRAY + message);
                target.sendMessage(ChatColor.GOLD + "[ " + ASCore.getPlayerName(player) + " -> me ] : " + ChatColor.GRAY + message);
                thisPlayer.setLastSender(target.getName());
                thisTarget.setLastSender(player.getName());
            }
        } else {
            player.sendMessage(ChatColor.RED + "Player '" + thisPlayer.getLastSender() + "' not found (or is not online!)");
        }
    }
}
