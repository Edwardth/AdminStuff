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
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdNickname extends AbstractExtendedCommand {

    public cmdNickname(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /nickname <Name> <br>
     * Set the nickname
     * 
     * @param player
     *            Called the command
     * @param split
     * 		  split[0] is the nickname
     */
    public void execute(String[] args, Player player) {
        // change own nickname
        if (args.length == 1)
            changeNickname(args[0], player, player);
        // change others nickname
        else
            changeNickname(args[0], args[1], player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        if (args.length == 1)
            ConsoleUtils.printError(pluginName, "You cannot change the nickname of the console!");
        else
            changeNickname(args[0], args[1], console);
    }

    private void changeNickname(String targetName, String name, CommandSender sender) {
        Player target = PlayerUtils.getOnlinePlayer(targetName);
        if (target == null)
            ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
        else if (!target.isOnline())
            ChatUtils.writeError(sender, pluginName, "Spieler '" + target.getName() + "' ist nicht online!");
        else
            changeNickname(name, target, sender);
    }

    private void changeNickname(String name, Player player, CommandSender sender) {
//        ASPlayer thisPlayer = pManager.getPlayer(player);
//        pManager.updateNickName(thisPlayer, name, player);
        MinestarCore.getPlayer(player).setNickName(name);
        PlayerUtils.sendInfo(player, pluginName, "Dein Nickname ist jetzt '" + name + "'!");
        ChatUtils.writeSuccess(sender, pluginName, "Der Nickname von '" + player.getName() + "' ist '" + name + "'!");
    }
}
