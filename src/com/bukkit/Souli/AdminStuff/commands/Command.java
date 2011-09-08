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

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import com.gemo.utils.UtilPermissions;

public abstract class Command {

    public final static String NO_RIGHT = "You aren't allowed to use this command!";
    public final Server server;

    private String description;
    private String syntax;
    private String arguments;
    private String node;

    public Command(String syntax, String arguments, String node, Server server) {
        this.syntax = syntax;
        this.arguments = arguments;
        if (this.arguments == null)
            arguments = "";
        this.server = server;
        this.node = node;
        this.description = "";
    }

    public void run(String[] args, Player player) {
        if (!hasRights(player)) {
            player.sendMessage(ChatColor.RED + NO_RIGHT);
            return;
        }
        if (!hasCorrectSyntax(args)) {
            player.sendMessage(ChatColor.RED + getSyntax() + " " + getArguments() + " " + getDescription());
            return;
        }
        execute(args, player);
    }

    public abstract void execute(String[] args, Player player);

    public boolean hasRights(Player player) {
        return UtilPermissions.playerCanUseCommand(player, "adminstuff." + getNode());
    }

    public boolean hasCorrectSyntax(String[] args) {
        return args.length == countArguments();
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getArguments() {
        return arguments;
    }

    public String getNode() {
        return node;
    }

    protected int countArguments() {
        int counter = 0;
        for (int i = 0; i < arguments.length(); ++i)
            if (arguments.charAt(i) == '<')
                ++counter;
        return counter;
    }
}
