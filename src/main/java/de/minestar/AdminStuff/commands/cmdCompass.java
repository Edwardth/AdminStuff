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

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.data.Direction;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdCompass extends AbstractCommand {

    public cmdCompass(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /compass <br>
     * Show the compassdir
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {

        int r = (int) getCorrectedYaw(player.getLocation());
        Direction dir = Direction.getDirection(r);
        PlayerUtils.sendInfo(player, pluginName, "Kompass: " + dir.name());
    }

    private float getCorrectedYaw(Location location) {
        float angle = (location.getYaw() - 90.0F) % 360.0F;
        if (angle < 0.0F) {
            angle += 360.0F;
        }
        return angle;
    }
}
