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
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.core.MinestarCore;
import de.minestar.core.units.MinestarPlayer;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdUnban extends AbstractCommand {

    public cmdUnban(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
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
            MinestarPlayer mPlayer = MinestarCore.getPlayer(targetName);
            Boolean b = mPlayer.getBoolean("banned");
            if (b == null || false)
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + " war nicht gebannt!");
            else {
                mPlayer.removeValue("banned", Boolean.class);
                mPlayer.removeValue("tempBann", Long.class);
                Bukkit.getOfflinePlayer(targetName).setBanned(false);
                ChatUtils.writeSuccess(player, pluginName, "Spieler '" + targetName + "' wurde entbannt!");
            }
        }
    }
}
