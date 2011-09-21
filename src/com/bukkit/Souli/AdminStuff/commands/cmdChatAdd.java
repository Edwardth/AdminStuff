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

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASLocalizer;
import com.bukkit.Souli.AdminStuff.ASPlayer;

public class cmdChatAdd extends ExtendedCommand {

    public cmdChatAdd(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /chat <Player 1.. Player n><br>
     * Reply a player
     * 
     * @param player
     *            Called the command
     * @param split
     * 	          list of Players
     */
    public void execute(String[] args, Player player) {
        // ADD PLAYER, IF NOT FOUND
        ASPlayer thisASPlayer = ASCore.getOrCreateASPlayer(player);

        // ADD RECIPIENTLIST
        ArrayList<Player> recipientList = new ArrayList<Player>();
        for (String arg : args) {
            Player thisPlayer = ASCore.getPlayer(arg);
            if (thisPlayer != null) {
                recipientList.add(thisPlayer);
            }
        }

        if (recipientList.size() < 1) {
            player.sendMessage(ASLocalizer.format("NO_RECIPIENT_FOUND", ChatColor.RED));
            return;
        }

        String list = "";
        String[] recList = new String[recipientList.size()];
        for (int i = 0; i < recipientList.size(); i++) {
            recList[i] = recipientList.get(i).getName();
            list += ASCore.getPlayerName(recipientList.get(i));
            if (i < recipientList.size() - 1)
                list += ", ";
        }
        thisASPlayer.setRecipients(recList);
        player.sendMessage(ASLocalizer.format("ONLY_CHAT_TO", ChatColor.GRAY, list));
    }
}
