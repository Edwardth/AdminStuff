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

import de.minestar.AdminStuff.ASItem;
import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.listener.PlayerListener;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdFillChest extends AbstractCommand {

    public cmdFillChest(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /fillchest <ItemID or Name>[:SubID] <br>
     * Fill a chest with a specified item
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the item name
     */
    public void execute(String[] args, Player player) {
        String ID = ASItem.getIDPart(args[0]);
        byte Data = ASItem.getDataPart(args[0]);
        ItemStack item = ASItem.getItemStack(ID, Data, 64);
        if (item != null) {
            PlayerListener.queuedFillChest.put(player.getName(), item);
            PlayerUtils.sendInfo(player, pluginName, "Klicke auf eine Kiste um diese mit '" + item.getType().name() + "' zu fuellen!");
        } else {
            PlayerUtils.sendError(player, pluginName, "'" + args[0] + "' wurde nicht gefunden");
            PlayerUtils.sendInfo(player, getHelpMessage());
        }
    }
}
