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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdInvsee extends AbstractExtendedCommand {

    private Map<Player, ItemStack[]> backup = new HashMap<Player, ItemStack[]>();

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
        if (args.length == 0) {
            ItemStack[] items = backup.remove(player);
            if (items == null)
                PlayerUtils.sendError(player, pluginName, "Du hast kein Inventar zum Wiederherstellen!");
            else {
                player.getInventory().clear();
                player.getInventory().setContents(items);
            }

        } else if (args.length == 1) {
            String targetName = args[0];
            Player target = PlayerUtils.getOnlinePlayer(targetName);
            if (target == null)
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' ist tot oder nicht online!");
            else {
                Inventory inv = player.getInventory();
                // backup current inventory
                backup.put(player, inv.getContents());

                inv.clear();

                // copy inventory
                inv.setContents(target.getInventory().getContents());

                PlayerUtils.sendSuccess(player, pluginName, "Zeige dir das Inventar von  '" + targetName + "'");
            }
        } else
            PlayerUtils.sendError(player, pluginName, getHelpMessage());
    }
}
