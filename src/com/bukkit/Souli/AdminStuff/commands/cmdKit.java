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

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASLocalizer;
import com.gemo.utils.UtilPermissions;

public class cmdKit extends Command {

    public cmdKit(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /kit <Name> <br>
     * Give yourself a kit
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        // IS KIT AVAILABLE
        if (!ASCore.kitList.containsKey(args[0].toLowerCase())) {
            player.sendMessage(ASLocalizer.format("KIT_NOT_FOUND", ChatColor.RED, args[0]));
            return;
        }

        // CHECK PERMISSIONS
        if (!UtilPermissions.playerCanUseCommand(player, "adminstuff.commands.admin.kit." + args[0].toLowerCase())) {
            player.sendMessage(ASLocalizer.format("KIT_NOT_ALLOWED", ChatColor.RED));
            return;
        }

        ASCore.kitList.get(args[0].toLowerCase()).giveKit(player);
        player.sendMessage(ASLocalizer.format("KIT_GIVE", ChatColor.GRAY, args[0]));
    }
}
