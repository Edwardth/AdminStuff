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
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bukkit.Souli.AdminStuff.ASItem;

public class cmdStack extends Command {

    public cmdStack(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /stack <br>
     * Stack your inventory
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        ItemStack[] items = player.getInventory().getContents();
        int len = items.length;

        int affected = 0;

        for (int i = 0; i < len; i++) {
            ItemStack item = items[i];

            if ((item == null) || (item.getAmount() <= 0) || ASItem.getMaxStackSize(item.getType()) == 1) {
                continue;
            }

            if ((item.getTypeId() >= 325) && (item.getTypeId() <= 327)) {
                continue;
            }
            if (item.getAmount() < 64) {
                int needed = 64 - item.getAmount();
                for (int j = i + 1; j < len; j++) {
                    ItemStack item2 = items[j];
                    if ((item2 == null) || (item2.getAmount() <= 0) || ASItem.getMaxStackSize(item.getType()) == 1) {
                        continue;
                    }
                    if (item2.getTypeId() != item.getTypeId() || item.getDurability() != item2.getDurability()) {
                        continue;
                    }
                    if (item2.getAmount() > needed) {
                        item.setAmount(64);
                        item2.setAmount(item2.getAmount() - needed);
                        break;
                    }
                    items[j] = null;
                    item.setAmount(item.getAmount() + item2.getAmount());
                    needed = 64 - item.getAmount();
                    affected++;
                }
            }
        }
        if (affected > 0) {
            player.getInventory().setContents(items);
        }
        player.sendMessage(ChatColor.GRAY + "Items stacked.");
    }
}
