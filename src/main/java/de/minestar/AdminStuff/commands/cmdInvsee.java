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
import org.bukkit.inventory.ItemStack;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.manager.ASPlayer;
import de.minestar.AdminStuff.manager.PlayerManager;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdInvsee extends AbstractExtendedCommand {

    private PlayerManager pManager;

    public cmdInvsee(String syntax, String arguments, String node, PlayerManager pManager) {
        super(Core.NAME, syntax, arguments, node);
        this.pManager = pManager;
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
        if (args.length == 0)
            restoreInventory(player);
        else if (args.length == 1)
            seeInventory(player, args[0]);
        else
            PlayerUtils.sendError(player, pluginName, getHelpMessage());
    }

    private void seeInventory(Player player, String targetName) {
        Player target = PlayerUtils.getOnlinePlayer(targetName);
        if (target == null)
            PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
        else if (target.isDead() || !target.isOnline())
            PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' ist tot oder nicht online!");
        else {
            ASPlayer thisPlayer = pManager.getPlayer(target);
            Inventory inv = player.getInventory();
            // backup players inventory
            pManager.backupInventory(thisPlayer, inv.getContents());

            inv.clear();

            // copy inventory
            inv.setContents(target.getInventory().getContents());

            PlayerUtils.sendSuccess(player, pluginName, "Zeige dir das Inventar von  '" + targetName + "'");
        }
    }

    private void restoreInventory(Player player) {
        ASPlayer thisPlayer = pManager.getPlayer(player);
        // CLEAR INVENTORY
        player.getInventory().clear();
        // RESTORE INVENTORY
        ItemStack[] inv = pManager.restoreInventory(thisPlayer);
        player.getInventory().addItem(inv);
        PlayerUtils.sendSuccess(player, pluginName, "Inventar wiederhergestellt");

    }
}
