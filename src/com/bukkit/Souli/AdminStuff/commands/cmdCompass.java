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

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class cmdCompass extends Command {

    public cmdCompass(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
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
        String dir = "";
        int r = (int) this.getCorrectedYaw(player.getLocation());
        if (r < 23) {
            dir = "N";
        } else {

            if (r < 68) {
                dir = "NE";
            } else {
                if (r < 113) {
                    dir = "E";
                } else {

                    if (r < 158) {
                        dir = "SE";
                    } else {
                        if (r < 203) {
                            dir = "S";
                        } else {
                            if (r < 248) {
                                dir = "SW";
                            } else {
                                if (r < 293) {
                                    dir = "W";
                                } else {
                                    if (r < 338)
                                        dir = "NW";
                                    else
                                        dir = "N";
                                }
                            }
                        }
                    }
                }
            }
        }
        player.sendMessage(ChatColor.GRAY + "Compass: " + dir);
    }

    public float getCorrectedYaw(Location location) {
        float angle = (location.getYaw() - 90.0F) % 360.0F;
        if (angle < 0.0F) {
            angle += 360.0F;
        }
        return angle;
    }
}
