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
import de.minestar.AdminStuff.manager.ASPlayer;
import de.minestar.AdminStuff.manager.PlayerManager;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdBan extends AbstractExtendedCommand {

    private PlayerManager pManager;

    public cmdBan(String syntax, String arguments, String node, PlayerManager pManager) {
        super(Core.NAME, syntax, arguments, node);
        this.pManager = pManager;
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
        // player is online
        if (target != null)
            playerName = target.getName();
        else {
            // player is maybe offline?
            playerName = PlayerUtils.getOfflinePlayerName(playerName);
            // player was never on the server
            if (playerName == null) {
                playerName = args[0];
                PlayerUtils.sendError(player, pluginName, "Spieler '" + playerName + "' existiert nicht, wird dennoch praeventiv gebannt!");
            }
            // player is offline
            else
                PlayerUtils.sendInfo(player, pluginName, "Spieler '" + playerName + "' ist nicht online, wird dennoch gebannt!");
        }
        ASPlayer thisTarget = pManager.getPlayer(playerName);
        String message = target != null ? getMessage(args) : null;
        pManager.bannPlayer(thisTarget, target, message);

        PlayerUtils.sendSuccess(player, pluginName, "Spieler '" + playerName + "' wurde gebannt!");
    }

    // Create message from arguments
    private String getMessage(String[] args) {
        // if no message was given
        if (args.length == 1)
            return "Du bist gebannt!";
        else
            return ChatUtils.getMessage(args, " ", 1);
    }
}
