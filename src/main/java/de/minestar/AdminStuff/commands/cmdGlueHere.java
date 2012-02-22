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

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.ASPlayer;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdGlueHere extends AbstractExtendedCommand {

    public cmdGlueHere(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /gluehere <Player><br>
     * Glues the player at the position the command  caller is looking
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        Player target = null;
        ASPlayer thisPlayer = null;
        for (String targetName : args) {
            target = PlayerUtils.getOnlinePlayer(targetName);
            if (target == null)
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' ist tot oder offline!");
            else {
                thisPlayer = Core.getOrCreateASPlayer(target);
                // toogle glue mode
                thisPlayer.setGlued(!thisPlayer.isGlued());

                if (thisPlayer.isGlued()) {
                    Location glueLocation = player.getLastTwoTargetBlocks(null, 50).get(0).getLocation();
                    thisPlayer.setGlueLocation(glueLocation);
                    PlayerUtils.sendSuccess(player, pluginName, "Spieler '" + target.getName() + "' ist festgeklebt!");
                    PlayerUtils.sendInfo(target, pluginName, "Du bist festgeklebt!");
                } else {
                    PlayerUtils.sendSuccess(player, pluginName, "Spieler '" + target.getName() + "' ist wieder frei!");
                    PlayerUtils.sendInfo(target, pluginName, "Du bist wieder frei!");
                }
                thisPlayer.saveConfig(false, false, false, true, false, false, false, false);
            }
        }
    }
}
