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

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASLocalizer;
import com.bukkit.Souli.AdminStuff.ASPlayer;

public class cmdUnban extends Command {

    public cmdUnban(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /unban <Player><br>
     * Unban a single player
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        File playerFile = new File("plugins/AdminStuff/userdata/" + args[0].toLowerCase() + ".yml");
        if (playerFile.exists()) {
            ASPlayer unbanned = ASCore.getOrCreateASPlayer(args[0]);
            unbanned.setBanned(false);
            unbanned.setTempBanned(false);
            unbanned.setBanEndTime(0);
            unbanned.saveConfig(false, false, false, false, true, false, false, false);
        }
        ASCore.unbanPlayer(args[0]);
        player.sendMessage(ASLocalizer.format("UNBAN_PLAYER", ChatColor.GRAY, args[0]));
        ((CraftServer) ASCore.getMCServer()).getHandle().b(args[0]);
    }
}
