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
import org.bukkit.inventory.Inventory;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdInvsee extends AbstractCommand {

    public cmdInvsee(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /invsee<br>
     * Show your own inventory
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        String targetName = args[0];
        Player target = PlayerUtils.getOnlinePlayer(targetName);
        if (target == null)
            PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
        else if (target.isDead() || !target.isOnline())
            PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' ist tot oder nicht online!");
        else {
            Inventory inv = player.getInventory();

            inv.clear();

            // copy inventory
            inv.setContents(target.getInventory().getContents());

            PlayerUtils.sendSuccess(player, pluginName, "Zeige dir das Inventar von  '" + targetName + "'");
        }
    }
}
