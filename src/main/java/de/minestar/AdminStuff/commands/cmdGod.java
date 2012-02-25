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
import de.minestar.AdminStuff.manager.ASPlayer;
import de.minestar.AdminStuff.manager.PlayerManager;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdGod extends AbstractExtendedCommand {

    private PlayerManager pManager;

    public cmdGod(String syntax, String arguments, String node, PlayerManager pManager) {
        super(Core.NAME, syntax, arguments, node);
        this.pManager = pManager;
    }

    @Override
    /**
     * Representing the command <br>
     * /god <br>
     * Toggle God-status
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        // make himself a god
        if (args.length == 0)
            changeGodMode(player, player);
        else
            changeGodMode(player, args);
    }
    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        if (args.length == 0)
            ConsoleUtils.printError(pluginName, "You are God, why do you want to activate god mode?");
        else
            changeGodMode(console, args);
    }

    // function to search the players
    private void changeGodMode(CommandSender sender, String... targetNames) {
        Player target = null;
        for (String targetName : targetNames) {
            target = PlayerUtils.getOnlinePlayer(targetName);
            if (target == null)
                ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' ist tot oder nicht online!");
            else
                changeGodMode(sender, target);
        }
    }

    // function to make them god
    private void changeGodMode(CommandSender sender, Player target) {
        // ADD PLAYER, IF NOT FOUND
        ASPlayer thisPlayer = pManager.getPlayer(target);
        boolean isGod = !thisPlayer.isGod();
        pManager.setGodMode(thisPlayer, isGod);
        if (isGod) {
            ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + target.getName() + "' ist jetzt unsterblich!");
            PlayerUtils.sendInfo(target, pluginName, "Unsterblichkeit aktiviert!");
        } else {
            ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + target.getName() + "' ist wieder sterblich!");
            PlayerUtils.sendInfo(target, pluginName, "Unsterblichkeit deaktiviert!");
        }
    }
}
