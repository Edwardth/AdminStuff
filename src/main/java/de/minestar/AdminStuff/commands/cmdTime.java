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

import de.minestar.AdminStuff.ASCore;
import de.minestar.AdminStuff.data.Time;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdTime extends AbstractCommand {

    public cmdTime(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /time day|night <br>
     * This changes the worldtime
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the time
     */
    public void execute(String[] args, Player player) {
        Time time = Time.getTime(args[0]);
        if (time == null) {
            PlayerUtils.sendError(player, pluginName, "Falscher Paramater! Benutze einen der folgenden:");
            PlayerUtils.sendInfo(player, Time.possibleNames());
        } else {
            player.getWorld().setTime(time.getDateTime());
            PlayerUtils.sendSuccess(player, pluginName, "Zeit in deiner Welt gesetzt auf " + time);
        }
    }
}
