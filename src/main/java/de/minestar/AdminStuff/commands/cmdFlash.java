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

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.ASCore;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdFlash extends AbstractExtendedCommand {

    //@formatter:off
    private static final String[] messages = {
        "Eat my Shorts, %s",
        "%s This...is...SPARTA!",
        "%s! SO EIN Feuerball!",
        "%s wurde geblitzt"
    };
    //@formatter:on

    private static final Random rand = new Random();

    public cmdFlash(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /flash<br>
     * Strikes a lightning at the last pointed location
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        if (args.length == 0) {
            Location strikeLocation = player.getLastTwoTargetBlocks(null, 50).get(0).getLocation();
            if (strikeLocation != null)
                player.getWorld().strikeLightning(strikeLocation);
        } else
            flashPlayer(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        if (args.length == 0)
            ConsoleUtils.printError(pluginName, "You can't unleash a flash at the position your are sitting!");
        else
            flashPlayer(args, console);
    }

    private void flashPlayer(String[] targetNames, CommandSender sender) {

        Player target = null;
        for (String name : targetNames) {
            target = PlayerUtils.getOnlinePlayer(name);
            if (target == null)
                ChatUtils.writeError(sender, pluginName, "Spieler '" + name + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                ChatUtils.writeError(sender, pluginName, "Spieler '" + target.getName() + "' ist tot oder offline!");
            else {
                target.getInventory().clear();
                target.getWorld().strikeLightning(target.getLocation());
                target.damage(9001);
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + getRandomMessage(target.getName()));
            }
        }
    }

    private String getRandomMessage(String targetName) {
        return String.format(messages[rand.nextInt(messages.length)], targetName);
    }
}
