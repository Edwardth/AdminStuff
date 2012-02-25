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

import de.minestar.AdminStuff.manager.ASPlayer;
import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.manager.PlayerManager;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdGlue extends AbstractExtendedCommand {

    private PlayerManager pManager;

    public cmdGlue(String syntax, String arguments, String node, PlayerManager pManager) {
        super(Core.NAME, syntax, arguments, node);
        this.pManager = pManager;
    }

    @Override
    /**
     * Representing the command <br>
     * /glue <Player><br>
     * The player is now unable to move
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        gluePlayer(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        gluePlayer(args, console);
    }

    private void gluePlayer(String[] targetNames, CommandSender sender) {

        Player target = null;
        ASPlayer thisPlayer = null;
        for (String targetName : targetNames) {
            target = PlayerUtils.getOnlinePlayer(targetName);
            if (target == null)
                ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' ist tot oder offline!");
            else {
                thisPlayer = pManager.getPlayer(target);

                // player is glued -> unglue him
                if (thisPlayer.isGlued()) {
                    pManager.setGlue(thisPlayer, null);
                    ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + target.getName() + "' ist wieder frei!");
                    PlayerUtils.sendInfo(target, pluginName, "Du bist wieder frei!");
                } else {
                    pManager.setGlue(thisPlayer, target.getLocation());
                    ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + target.getName() + "' ist festgeklebt!");
                    PlayerUtils.sendInfo(target, pluginName, "Du bist festgeklebt!");
                }
            }
        }
    }
}
