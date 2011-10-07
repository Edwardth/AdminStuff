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
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.gemo.utils.UtilPermissions;

public class cmdHelp extends Command {
    private static final String DESCRIPTION = "description";
    private static final String PERMISSION = "permission";
    private static final String PERMISSIONS = "permissions";

    public cmdHelp(String syntax, String arguments, String node, Server server) {
        super(syntax, arguments, node, server);
    }

    @Override
    /**
     * Representing the command <br>
     * /help<br>
     * Show the help
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        int page = 1;
        List<String> lines = getHelpLines(player);
        int start = (page - 1) * 9;
        int pages = lines.size() / 9 + (lines.size() % 9 > 0 ? 1 : 0);

        player.sendMessage(ChatColor.AQUA + "---------[ LIST OF COMMANDS " + page + "/" + pages + "] ---------");
        for (int i = start; (i < lines.size()) && (i < start + 9); i++) {
            player.sendMessage((String) lines.get(i));
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getHelpLines(Player player) {
        List<String> retval = new ArrayList<String>();
        boolean reported = false;
        String pluginName = "";
        for (Plugin p : ASCore.getMCServer().getPluginManager().getPlugins()) {
            try {
                PluginDescriptionFile desc = p.getDescription();
                if (UtilPermissions.playerCanUseCommand(player, "adminstuff.commands.help." + desc.getName().toLowerCase())) {
                    final HashMap<String, HashMap<String, Object>> cmds = (HashMap<String, HashMap<String, Object>>) desc.getCommands();

                    for (Entry<String, HashMap<String, Object>> k : cmds.entrySet()) {
                        final HashMap<String, Object> value = k.getValue();
                        if (value.containsKey(PERMISSION) && value.get(PERMISSION) instanceof String && !(value.get(PERMISSION).equals(""))) {
                            if (UtilPermissions.playerCanUseCommand(player, (String) value.get(PERMISSION))) {
                                retval.add(ChatColor.RED + k.getKey() + ChatColor.GRAY + ": " + value.get(DESCRIPTION));
                            }
                        } else if (value.containsKey(PERMISSION) && value.get(PERMISSION) instanceof List && !((List<Object>) value.get(PERMISSION)).isEmpty()) {
                            boolean enabled = false;
                            for (Object o : (List<Object>) value.get(PERMISSION)) {
                                if (o instanceof String && UtilPermissions.playerCanUseCommand(player, (String) o)) {
                                    enabled = true;
                                    break;
                                }
                            }
                            if (enabled) {
                                retval.add(ChatColor.RED + k.getKey() + ChatColor.GRAY + ": " + value.get(DESCRIPTION));
                            }
                        } else if (value.containsKey(PERMISSIONS) && value.get(PERMISSIONS) instanceof String && !(value.get(PERMISSIONS).equals(""))) {
                            if (UtilPermissions.playerCanUseCommand(player, (String) value.get(PERMISSIONS))) {
                                retval.add(ChatColor.RED + k.getKey() + ChatColor.GRAY + ": " + value.get(DESCRIPTION));
                            }
                        } else if (value.containsKey(PERMISSIONS) && value.get(PERMISSIONS) instanceof List && !((List<Object>) value.get(PERMISSIONS)).isEmpty()) {
                            boolean enabled = false;
                            for (Object o : (List<Object>) value.get(PERMISSIONS)) {
                                if (o instanceof String && UtilPermissions.playerCanUseCommand(player, (String) o)) {
                                    enabled = true;
                                    break;
                                }
                            }
                            if (enabled) {
                                retval.add(ChatColor.RED + k.getKey() + ChatColor.GRAY + ": " + value.get(DESCRIPTION));
                            }
                        } else if (UtilPermissions.playerCanUseCommand(player, "adminstuff.user.help." + pluginName)) {
                            retval.add(ChatColor.RED + k.getKey() + ChatColor.GRAY + ": " + value.get(DESCRIPTION));
                        }
                    }
                }
            } catch (NullPointerException ex) {
            } catch (Exception ex) {
                if (!reported) {
                    ASCore.log.printWarning("Error getting help for:" + pluginName);
                    ex.printStackTrace();
                }
                reported = true;
            }
        }
        return retval;
    }
}
