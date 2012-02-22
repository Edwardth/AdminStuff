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

import com.bukkit.gemo.utils.UtilPermissions;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.ASKit;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdKit extends AbstractCommand {

    public cmdKit(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /kit <Name> <br>
     * Give yourself a kit
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        if (args.length == 1) {
            ASKit kit = Core.kitList.get(args[0]);
            if (kit == null)
                PlayerUtils.sendError(player, pluginName, "Das Kit '" + args[0] + "' wurde nicht gefunden!");
            else
                giveKit(player, kit, args[0]);
        } else
            giveKits(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        if (args.length == 1)
            ConsoleUtils.printError(pluginName, "You cannot give you any items, you have already every item!");
        else
            giveKits(args, console);
    }

    private boolean canUseKit(String kitName, CommandSender sender) {
        return (sender instanceof ConsoleCommandSender) || UtilPermissions.playerCanUseCommand((Player) sender, "adminstuff.commands.admin.kit." + kitName);
    }

    private void giveKits(String[] args, CommandSender sender) {
        String kitName = args[0];
        ASKit kit = Core.kitList.get(kitName);
        if (kit == null) {
            ChatUtils.writeError(sender, pluginName, "Das Kit '" + kitName + "' wurde nicht gefunden!");
            return;
        }
        if (!canUseKit(kitName, sender)) {
            ChatUtils.writeError(sender, pluginName, "Du kannst das Kit '" + kitName + "' nicht benutzen!");
            return;
        }
        Player target = null;
        for (int i = 1; i < args.length; ++i) {
            target = PlayerUtils.getOnlinePlayer(args[i]);
            if (target == null)
                ChatUtils.writeError(sender, pluginName, "Spieler '" + args[i] + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                ChatUtils.writeError(sender, pluginName, "Spieler '" + target.getName() + "' ist tot oder nicht online!");
            else {
                giveKit(target, kit, kitName);
                ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + target.getName() + "' hat das Kit '" + kitName + "' erhalten");
            }
        }
    }

    private void giveKit(Player target, ASKit kit, String kitName) {
        kit.giveKit(target);
        PlayerUtils.sendInfo(target, pluginName, "Du hast Kit '" + kitName + "' erhalten!");
    }
}
