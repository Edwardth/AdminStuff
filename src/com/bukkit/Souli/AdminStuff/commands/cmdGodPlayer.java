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

public class cmdGodPlayer extends Command {

    public cmdGodPlayer(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /god <Player><br>
     * Toogle God-Status of a player
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
                ASPlayer thisTarget = ASCore.getOrCreateASPlayer(target);
                boolean isGod = !thisTarget.isGod();
                thisTarget.setGod(isGod);
                thisTarget.saveConfig(false, false, false, false, false, false, true, false);
                if (isGod) {
                    player.sendMessage(ASLocalizer.format("PLAYER_IS_A_GOD", ChatColor.GRAY, ASCore.getPlayerName(target)));
                    target.sendMessage(ASLocalizer.format("YOU_ARE_A_GOD", ChatColor.GRAY));
                } else {
                    player.sendMessage(ASLocalizer.format("PLAYER_IS_NO_LONGER_GOD", ChatColor.GRAY, ASCore.getPlayerName(target)));
                    target.sendMessage(ASLocalizer.format("NO_LONGER_A_GOD", ChatColor.GRAY));
                }
            }
        } else {
            player.sendMessage(ASLocalizer.format("PLAYER_NOT_FOUND", ChatColor.RED, args[0]));
        }
    }
}
