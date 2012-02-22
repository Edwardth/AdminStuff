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

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.ASPlayer;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdBan extends AbstractExtendedCommand {

    public cmdBan(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /ban <Player><br>
     * Ban a single player
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        String playerName = args[0];

        Player target = PlayerUtils.getOnlinePlayer(playerName);
        if (target == null) {
            playerName = PlayerUtils.getOfflinePlayerName(playerName);
            if (playerName == null) {
                PlayerUtils.sendError(player, pluginName, "Spieler '" + args[0] + "' wurde nicht gefunden!");
                return;
            } else {
                Core.banPlayer(playerName);
                PlayerUtils.sendSuccess(player, pluginName, "Der Spieler '" + playerName + "' wurde gebannt!");
            }
        } else if (target.isOnline()) {

            playerName = target.getName();
            ASPlayer thisTarget = Core.getOrCreateASPlayer(target);
            thisTarget.setBanned(true);
            target.setBanned(true);
            thisTarget.saveConfig(false, false, false, true, false, false, false);

            Core.banPlayer(playerName);
            target.kickPlayer(getMessage(args));
            PlayerUtils.sendSuccess(player, pluginName, "Der Spieler '" + playerName + "' wurde gebannt!");
        } else
            PlayerUtils.sendError(player, pluginName, "Spieler '" + target.getName() + "' existiert, ist aber nicht online!");
    }

    // Create message from arguments
    private String getMessage(String[] args) {
        // if no message was given
        if (args.length == 1)
            return "Du bist gebannt!";

        StringBuilder sBuilder = new StringBuilder(256);
        int i = 1;
        for (; i < args.length - 1; ++i) {
            sBuilder.append(args[i]);
            sBuilder.append(' ');
        }
        sBuilder.append(args[i]);
        return sBuilder.toString();
    }
}
