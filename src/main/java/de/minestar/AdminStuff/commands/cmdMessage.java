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

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.core.MinestarCore;
import de.minestar.core.units.MinestarPlayer;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdMessage extends AbstractExtendedCommand {

    public cmdMessage(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /msg <Player> <Message><br>
     * Message a single player
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {

        String targetName = args[0];
        Player target = PlayerUtils.getOnlinePlayer(targetName);
        if (target == null) {
            PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
            return;
        }
        // the forever alone guy
        if (target.equals(player)) {
            PlayerUtils.sendMessage(player, ChatColor.BLACK, pluginName, "Keine Freunde? Schreib doch Leif_Ericsson an :)");
            return;
        }
        if (!target.isOnline()) {
            PlayerUtils.sendError(player, pluginName, "Spieler '" + target.getName() + "' ist tot oder nicht online!");
            return;
        }

        MinestarPlayer mPlayer = MinestarCore.getPlayer(player);
        MinestarPlayer mTarget = MinestarCore.getPlayer(target);

        String message = "] : " + ChatColor.GRAY + ChatUtils.getMessage(args, " ", 1);
        PlayerUtils.sendBlankMessage(player, ChatColor.GOLD + "[ me -> " + mTarget.getNickName() + message);
        PlayerUtils.sendBlankMessage(target, ChatColor.GOLD + "[ " + mPlayer.getNickName() + " -> me" + message);

        mPlayer.setString("adminstuff.lastsender", target.getName());
        mTarget.setString("adminstuff.lastsender", player.getName());

        Boolean isAFK = mTarget.getBoolean("adminstuff.afk");
        if (isAFK != null && isAFK == true)
            PlayerUtils.sendInfo(player, "Spieler ist AFK");
    }
}
