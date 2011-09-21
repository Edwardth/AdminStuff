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
import com.bukkit.Souli.AdminStuff.ASLocalizer;
import com.bukkit.Souli.AdminStuff.ASPlayer;

public class cmdSlap extends Command {

    public cmdSlap(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /slap <br>
     * SLAP A PLAYER
     * 
     * @param player
     *            Called the command
     * @param split
     * 			args[0] = Player to be slapped
     */
    public void execute(String[] args, Player player) {
        Player target = ASCore.getPlayer(args[0]);
        if (target != null) {
            if (!target.isDead() && target.isOnline()) {
                ASPlayer thisTarget = ASCore.getOrCreateASPlayer(target);
                thisTarget.setSlapped(true);
                ASCore.getMCServer().broadcastMessage(ASLocalizer.format("SLAP_PLAYER", ChatColor.BLUE, ASCore.getPlayerName(player), ASCore.getPlayerName(target)));
                thisTarget.updateNick();
            }
        } else {
            player.sendMessage(ASLocalizer.format("PLAYER_NOT_FOUND", ChatColor.RED, args[0]));
        }
    }
}
