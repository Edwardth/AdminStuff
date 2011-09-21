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

public class cmdClassic extends Command {

    public cmdClassic(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /classic <br>
     * Toggle the classicmode
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        // ADD PLAYER, IF NOT FOUND
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(player);
        boolean isClassic = !thisPlayer.isClassicMode();

        thisPlayer.setClassicMode(isClassic);
        thisPlayer.saveConfig(true, false, false, false, false, false, false, true);

        if (isClassic) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ASLocalizer.format("YOU_ARE_IN_CLASSICMODE", ChatColor.GRAY));
        } else {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ASLocalizer.format("YOU_ARE_NO_LONGER_IN_CLASSICMODE", ChatColor.GRAY));
        }
    }
}
