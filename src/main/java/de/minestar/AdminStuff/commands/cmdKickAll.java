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

import de.minestar.AdminStuff.ASCore;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdKickAll extends AbstractExtendedCommand {

    public cmdKickAll(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /kickall<br>
     * Kick all players
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        Player[] allPlayers = Bukkit.getOnlinePlayers();
        String msg = getMessage(args);
        for (Player kPlayer : allPlayers) {
            if (!kPlayer.equals(player))
                kPlayer.kickPlayer(msg);
        }
        PlayerUtils.sendSuccess(player, pluginName, "Alle anderen Spieler wurden gekickt!");
    }

    private String getMessage(String[] args) {
        // no message was given
        if (args.length == 1)
            return "Du wurdest gekickt.";

        // create message string
        StringBuilder sBuilder = new StringBuilder(256);
        for (int i = 1; i < args.length; ++i) {
            sBuilder.append(args[i]);
            sBuilder.append(' ');
        }
        sBuilder.deleteCharAt(sBuilder.length() - 1);
        return sBuilder.toString();
    }
}
