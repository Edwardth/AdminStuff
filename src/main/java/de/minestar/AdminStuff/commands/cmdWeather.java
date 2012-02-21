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

import org.bukkit.World;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.ASCore;
import de.minestar.AdminStuff.data.Weather;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdWeather extends AbstractCommand {

    public cmdWeather(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
    }

    /**
     * Representing the command <br>
     * /weather <Weather> <br>
     * This changes the worldweather
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the weather
     */
    @Override
    public void execute(String[] args, Player player) {
        Weather weather = Weather.getWeather(args[0]);
        if (weather == null) {
            PlayerUtils.sendError(player, pluginName, "Falscher Paramater! Benutze einen der folgenden:");
            PlayerUtils.sendInfo(player, Weather.possibleNames());
        } else {
            World world = player.getWorld();
            world.setStorm(weather.isStorming());
            world.setThundering(weather.isThundering());
            PlayerUtils.sendSuccess(player, pluginName, "Wetter in deiner Welt gesetzt auf: '" + weather + "'");
        }
    }
}
