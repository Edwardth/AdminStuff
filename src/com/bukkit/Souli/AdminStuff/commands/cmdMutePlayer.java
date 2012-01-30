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

public class cmdMutePlayer extends ExtendedCommand {

    public cmdMutePlayer(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /mute <Player><br>
     * Flashes a player and kills him
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        Player target = null;
        boolean one = true;
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("mute")) {
                target = ASCore.getPlayer(args[1]);
                one = false;
            } else
                player.sendMessage(ASLocalizer.format("WRONG_SYNTAX", ChatColor.RED, args[0]));
        } else {
            target = ASCore.getPlayer(args[0]);
        }
        if (target != null) {
            if (target.isOnline()) {
                // ADD PLAYER, IF NOT FOUND
                ASPlayer thisTarget = ASCore.getOrCreateASPlayer(target);

                if (one)
                    if (thisTarget.isMuted() == 1 || thisTarget.isMuted() == 2)
                        thisTarget.setMuted((byte) 0);
                    else
                        thisTarget.setMuted((byte) 1);
                else
                    thisTarget.setMuted((byte) 2);

                if (thisTarget.isMuted() == 1) {
                    player.sendMessage(ASLocalizer.format("MUTE_PLAYER_SOFT", ChatColor.GRAY, ASCore.getPlayerName(target)));
                } else if (thisTarget.isMuted() == 2) {
                    player.sendMessage(ASLocalizer.format("MUTE_PLAYER_HARD", ChatColor.GRAY, ASCore.getPlayerName(target)));
                } else {
                    player.sendMessage(ASLocalizer.format("UNMUTE_PLAYER", ChatColor.GRAY, ASCore.getPlayerName(target)));
                }
                thisTarget.saveConfig(false, true, false, false, false, false, false, false);
            }
        } else {
            player.sendMessage(ASLocalizer.format("PLAYER_NOT_FOUND", ChatColor.RED, args[0]));
        }
    }
}
