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

package com.bukkit.Souli.AdminStuff;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gemo.utils.BlockUtils;

public class ASItem {
    public static Map<String, Integer> matList = null;
    private int TypeID = 0;
    private byte Data = (byte) 0;

    public ASItem(int TypeID) {
        this.TypeID = TypeID;
        initMatList();
    }

    public ASItem(int TypeID, byte Data) {
        this.TypeID = TypeID;
        this.Data = Data;
        initMatList();
    }

    public static void initMatList() {
        if (matList == null) {
            matList = new HashMap<String, Integer>();
            for (Material mat : Material.values()) {
                matList.put(mat.name().replace("_", "").replace(" ", "").toLowerCase(), mat.getId());
            }
        }
    }

    public int getTypeID() {
        return TypeID;
    }

    public byte getData() {
        return Data;
    }

    /**
     * 
     * STATIC FUNCTIONS FOR RETURNING ITEMS
     * 
     */

    public static int returnItemID(String itemName) {
        return BlockUtils.getItemIDFromName(itemName);
    }

    public static ItemStack getItemStack(int TypeID, int amount) {
        return getItemStack(TypeID, (byte) 0, amount);
    }

    public static ItemStack getItemStack(int TypeID, byte Data, int amount) {
        if (!isValid(TypeID, Data))
            return null;

        ItemStack item = new ItemStack(TypeID);
        if (Data != 0)
            item.setDurability(Data);

        item.setAmount(amount);

        return item;
    }

    public static ItemStack getItemStack(String name, int amount) {
        return getItemStack(name, (byte) 0, amount);
    }

    public static ItemStack getItemStack(String name, byte Data, int amount) {
        if (!isValid(name, Data))
            return null;

        ItemStack item = null;
        if (!isInteger(name)) {
            item = new ItemStack(matList.get(name.toLowerCase()));
        } else
            item = new ItemStack(Integer.valueOf(name));

        if (Data != 0)
            item.setDurability(Data);

        item.setAmount(amount);

        return item;
    }

    /**
     * 
     * STATIC FUNCTIONS FOR ITEM VALIDATION
     * 
     */

    public static boolean isValid(int TypeID) {
        return isValid(TypeID, (byte) 0);
    }

    public static String getIDPart(String input) {
        if (input.split(":").length >= 1)
            return input.split(":")[0];
        else
            return input;
    }

    public static byte getDataPart(String input) {
        try {
            return Byte.valueOf(input.split(":")[1]);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(getIDPart(input));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValid(String name) {
        return isValid(name, (byte) 0);
    }

    public static boolean isValid(String name, byte Data) {
        if (isInteger(name))
            return isValid(Integer.valueOf(name), Data);

        initMatList();
        if (!matList.containsKey(name.toLowerCase()))
            return false;

        return isValid(matList.get(name.toLowerCase()), Data);
    }

    public static boolean isValid(int TypeID, byte Data) {
        initMatList();
        Material mat = Material.getMaterial(TypeID);
        if (mat == null) {
            mat = null;
            return false;
        }

        if (Data < 0 || Data > 15)
            return false;

        if (mat.getId() == Material.WOOL.getId() || mat.getId() == Material.INK_SACK.getId()) {
            return true;
        } else if (mat.getId() == Material.LOG.getId() || mat.getId() == Material.LEAVES.getId() || mat.getId() == Material.JUKEBOX.getId() || mat.getId() == Material.SAPLING.getId() || mat.getId() == Material.LONG_GRASS.getId() || mat.getId() == Material.DEAD_BUSH.getId()) {
            if (Data > 2)
                return false;
            return true;
        } else if (mat.getId() == Material.DOUBLE_STEP.getId() || mat.getId() == Material.STEP.getId()) {
            if (Data > 3)
                return false;
            return true;
        } else if (mat.getId() == Material.CROPS.getId()) {
            if (Data > 7)
                return false;
            return true;
        } else if (mat.getId() == Material.COAL.getId()) {
            if (Data > 1)
                return false;
            return true;
        } else if (Data == 0) {
            return true;
        }
        return false;
    }

    public static int getMaxStackSize(Material mat) {
        if (mat.getId() == Material.CAKE.getId() || mat.getId() == Material.LAVA_BUCKET.getId() || mat.getId() == Material.WATER_BUCKET.getId() || mat.getId() == Material.MILK_BUCKET.getId() || mat.getId() == Material.BUCKET.getId() || mat.getId() == Material.MAP.getId() || (mat.getId() >= 267 && mat.getId() <= 286) || (mat.getId() >= 290 && mat.getId() <= 294) || (mat.getId() >= 298 && mat.getId() <= 317))
            return 1;

        return 64;
    }
}
