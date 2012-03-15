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

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.core.MinestarCore;
import de.minestar.core.units.MinestarPlayer;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdSeen extends AbstractCommand {

    public cmdSeen(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /seen <Player><br>
     * Show the LastSeen-Date
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        getSeen(args[0], player);

    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        getSeen(args[0], console);
    }

    private void getSeen(String targetName, CommandSender sender) {
        String correct = PlayerUtils.getCorrectPlayerName(targetName);
        if (correct == null)
            ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
        else {
            MinestarPlayer mPlayer = MinestarCore.getPlayer(correct);
            String seen = mPlayer.getString("adminstuff.lastseen");
            if (seen == null) {
                seen = "Niemals";
                mPlayer.setString("adminstuff.lastseen", seen);
            }
            ChatUtils.writeInfo(sender, pluginName, "Spieler '" + correct + "' wurde zuletzt " + seen + " gesehen!");
        }
    }
}
