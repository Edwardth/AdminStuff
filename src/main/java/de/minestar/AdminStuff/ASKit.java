/*
 * Copyright (C) 2011 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.AdminStuff;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ASKit {
    public HashMap<String, ItemStack> kitList = new HashMap<String, ItemStack>();

    public void giveKit(Player player) {
        for (ItemStack item : kitList.values()) {
            player.getInventory().addItem(item);
        }
    }

    public boolean hasItem(ItemStack item) {
        return kitList.containsKey(item.getTypeId() + "_" + item.getData());
    }

    public void addItem(ItemStack item) {
        kitList.put(item.getTypeId() + "_" + item.getData(), item);
    }

    public void removeItem(ItemStack item) {
        kitList.remove(item.getTypeId() + "_" + item.getData());
    }
}
