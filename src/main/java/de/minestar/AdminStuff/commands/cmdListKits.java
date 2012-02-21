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

import java.util.Map.Entry;

import org.bukkit.entity.Player;

import com.bukkit.gemo.utils.UtilPermissions;

import de.minestar.AdminStuff.ASCore;
import de.minestar.AdminStuff.ASKit;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdListKits extends AbstractCommand {

    public cmdListKits(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /listkits <br>
     * List all available kits (with permissions)
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        PlayerUtils.sendInfo(player, pluginName, "Kitliste:");
        int count = 1;
        for (Entry<String, ASKit> entry : ASCore.kitList.entrySet()) {
            if (UtilPermissions.playerCanUseCommand(player, "adminstuff.commands.admin.kit." + entry.getKey()))
                PlayerUtils.sendInfo(player, "#" + count++ + " : " + entry.getKey());
        }
    }
}
