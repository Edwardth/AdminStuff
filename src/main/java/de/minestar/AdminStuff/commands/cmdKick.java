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
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdKick extends AbstractExtendedCommand {

    public cmdKick(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /kick <Player><br>
     * Kick a single player
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        String targetName = args[0];
        String msg = getMessage(args);

        Player target = PlayerUtils.getOnlinePlayer(targetName);
        if (target == null)
            PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
        else if (!target.isOnline())
            PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' ist nicht online!");
        else {
            Core.getOrCreateASPlayer(target);
            target.kickPlayer(msg);
            PlayerUtils.sendSuccess(player, pluginName, "Spieler '" + targetName + "' wurde gekickt!");
        }
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
