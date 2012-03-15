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
import org.bukkit.inventory.ItemStack;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.ASItem;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdItem extends AbstractExtendedCommand {

    public cmdItem(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /i <ItemID or Name>[:SubID] <Amount> <br>
     * This gives the player a specified itemstack
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the item name
     *            split[1] is the itemamount
     */
    public void execute(String[] args, Player player) {
        String ID = ASItem.getIDPart(args[0]);
        int amount = 64;
        if (args.length == 2) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (Exception e) {
                PlayerUtils.sendError(player, ID, args[1] + " ist keine Zahl! Itemanzahl auf Eins gesetzt!");
            }
            if (amount < 1) {
                PlayerUtils.sendError(player, pluginName, args[1] + "ist kleiner als Eins! Itemanzahl auf Eins gesetzt!");
                amount = 1;
            }
        }
        byte data = ASItem.getDataPart(args[0]);
        ItemStack item = ASItem.getItemStack(ID, amount);
        if (item == null) {
            PlayerUtils.sendError(player, pluginName, "'" + args[0] + "' wurde nicht gefunden");
            return;
        }
        item.setDurability(data);
        player.getInventory().addItem(item);

        // when item has a sub id
        String itemName = item.getType().name() + (data == 0 ? "" : ":" + data);

        PlayerUtils.sendInfo(player, "Du erhaelst " + amount + " mal " + itemName);
        ConsoleUtils.printInfo(pluginName, "ITEM: " + player.getName() + " himself : " + amount + " x " + itemName);
    }
}
