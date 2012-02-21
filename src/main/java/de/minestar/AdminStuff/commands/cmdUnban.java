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

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.ASCore;
import de.minestar.AdminStuff.ASPlayer;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdUnban extends AbstractCommand {

    public cmdUnban(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
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
        String targetName = PlayerUtils.getCorrectPlayerName(args[0]);
        if (targetName == null) {
            ChatUtils.writeError(player, pluginName, "Spieler '" + args[0] + "' existiert nicht!");
        } else {
            ASPlayer thisTarget = ASCore.getOrCreateASPlayer(targetName);
            thisTarget.setBanned(false);
            thisTarget.setTempBanned(false);
            thisTarget.setBanEndTime(0);
            thisTarget.saveConfig(false, false, false, false, true, false, false, false);
            ASCore.unbanPlayer(targetName);
            ((CraftServer) Bukkit.getServer()).getHandle().removeUserBan(targetName);
            ChatUtils.writeSuccess(player, pluginName, "Spieler '" + targetName + "' wurde entbannt!");
        }
    }
}
