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

import org.bukkit.entity.Player;

import de.minestar.AdminStuff.ASCore;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdBurn extends AbstractCommand {

    public cmdBurn(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /burn <Player> <Time in seconds><br>
     * Burn a player for x seconds
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     *            split[1] is the time in seconds           
     */
    public void execute(String[] args, Player player) {

        String playerName = args[0];
        Player target = PlayerUtils.getOnlinePlayer(playerName);
        if (target == null)
            PlayerUtils.sendError(player, pluginName, "Spieler '" + playerName + "' wurde nicht gefunden!");
        else if (target.isDead() || !target.isOnline())
            PlayerUtils.sendError(player, pluginName, "Spieler '" + playerName + "' ist tot oder nicht online!");
        else {
            int ticks = 0;
            try {
                ticks = Integer.parseInt(args[1]) * 20;
            } catch (Exception e) {
                PlayerUtils.sendError(player, pluginName, args[1] + " ist keine Zahl!");
                PlayerUtils.sendInfo(player, getHelpMessage());
                return;
            }
            target.setFireTicks(ticks);
            PlayerUtils.sendSuccess(target, pluginName, "Spieler '" + playerName + "' brennt nun. Zufrieden?");
        }
    }
}
