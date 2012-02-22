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
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.ASPlayer;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdAFK extends AbstractCommand {

    public cmdAFK(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /afk <br>
     * Toggle AFK-status
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        // ADD PLAYER, IF NOT FOUND
        ASPlayer thisPlayer = Core.getOrCreateASPlayer(player);
        boolean isAFK = !thisPlayer.isAFK();

        thisPlayer.setAFK(isAFK);
        thisPlayer.saveConfig(true, false, false, false, false, false, false);
        thisPlayer.updateNick();

        if (isAFK) {
            Bukkit.broadcastMessage(player.getDisplayName() + " ist AFK");
            PlayerUtils.sendInfo(player, pluginName, "Du bist jetzt AFK");
        } else {
            Bukkit.broadcastMessage(player.getDisplayName() + " ist wieder da");
            PlayerUtils.sendInfo(player, pluginName, "Willkommen zurueck :)");
        }
    }
}
