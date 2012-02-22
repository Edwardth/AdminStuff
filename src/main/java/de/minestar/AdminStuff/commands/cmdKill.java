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
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdKill extends AbstractCommand {

    public cmdKill(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /kill <Player><br>
     * Kills the Player and clears the inventory
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        // kill yourself
        if (args.length == 0)
            killPlayer(player, player);
        else
            killPlayers(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        if (args.length == 0)
            ConsoleUtils.printError(pluginName, "If you want kill yourself, press ALT+F4");
        else
            killPlayers(args, console);
    }

    private void killPlayers(String[] targetNames, CommandSender sender) {
        Player target = null;
        for (String targetName : targetNames) {
            target = PlayerUtils.getOnlinePlayer(targetName);
            if (target == null)
                ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                ChatUtils.writeError(sender, pluginName, "Spieler '" + target.getName() + "' ist tot oder nicht online!");
            else
                killPlayer(target, sender);
        }
    }

    private void killPlayer(Player player, CommandSender sender) {
        player.getInventory().clear();
        player.damage(100);
        ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + player.getName() + "' wurde getoetet!");
    }
}
