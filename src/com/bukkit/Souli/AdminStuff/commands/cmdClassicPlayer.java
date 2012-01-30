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
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASLocalizer;
import com.bukkit.Souli.AdminStuff.ASPlayer;

public class cmdClassicPlayer extends Command {

    public cmdClassicPlayer(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /classic <Player><br>
     * Toggle the classicmode for another player
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        Player target = ASCore.getPlayer(args[0]);
        if (target != null) {
            ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(target);
            boolean isClassic = !thisPlayer.isClassicMode();
            thisPlayer.setClassicMode(isClassic);
            thisPlayer.saveConfig(true, false, false, false, false, false, false, true);

            if (isClassic) {
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(ASLocalizer.format("YOU_ARE_IN_CLASSICMODE", ChatColor.GRAY));
                player.sendMessage(ASLocalizer.format("X_IS_IN_CLASSICMODE", ChatColor.GRAY, ASCore.getPlayerName(target)));
            } else {
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(ASLocalizer.format("YOU_ARE_NO_LONGER_IN_CLASSICMODE", ChatColor.GRAY));
                player.sendMessage(ASLocalizer.format("X_IS_NO_LONGER_IN_CLASSICMODE", ChatColor.GRAY, ASCore.getPlayerName(target)));
            }
        } else {
            player.sendMessage(ASLocalizer.format("PLAYER_NOT_FOUND", ChatColor.RED, args[0]));
        }
    }
}
