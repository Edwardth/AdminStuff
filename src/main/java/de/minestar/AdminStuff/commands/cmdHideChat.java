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

import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.core.MinestarCore;
import de.minestar.core.units.MinestarPlayer;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdHideChat extends AbstractCommand {

    public cmdHideChat(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /hidechat <br>
     * Toggle HideChat-Status
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {

        MinestarPlayer mPlayer = MinestarCore.getPlayer(player);
        Boolean hideChat = mPlayer.getBoolean("adminstuff.hidechat");
        if (hideChat != null && !hideChat)
            hideChat = true;
        else
            hideChat = false;
        mPlayer.setBoolean("adminstuff.hidechat", hideChat);

        if (hideChat)
            PlayerUtils.sendSuccess(player, pluginName, "Der Chat ist jetzt verborgen!");
        else
            PlayerUtils.sendSuccess(player, pluginName, "Der Chat ist wieder sichtbar!");
    }
}
