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

package de.minestar.AdminStuff.Listener;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.bukkit.gemo.utils.BlockUtils;
import com.bukkit.gemo.utils.UtilPermissions;

import de.minestar.AdminStuff.ASCore;
import de.minestar.AdminStuff.ASPlayer;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class ASPlayerListener implements Listener {
    private static Map<String, ASPlayer> playerMap = new TreeMap<String, ASPlayer>();
    public static Map<String, ItemStack> queuedFillChest = new TreeMap<String, ItemStack>();

    /**
     * 
     * ON PLAYER MOVE
     * 
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (BlockUtils.LocationEquals(event.getTo(), event.getFrom()))
            return;

        // ADD PLAYER, IF NOT FOUND
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(event.getPlayer());

        // IS PLAYER GLUED = RETURN TO GLUELOCATION
        if (thisPlayer.isGlued() && thisPlayer.getGlueLocation() != null)
            event.setTo(thisPlayer.getGlueLocation());
    }

    // ON PLAYER PRELOGIN
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        String name = event.getName().toLowerCase();
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(event.getName());
        // IS USER BANNED IN TXT?
        if (ASCore.bannedPlayers.containsKey(name) || thisPlayer.isBanned()) {
            event.disallow(Result.KICK_BANNED, "Du bist gebannt!");
            return;
        }

        if (thisPlayer.isTempBanned()) {
            long endTime = thisPlayer.getBanEndTime();
            if (endTime <= System.currentTimeMillis()) {
                thisPlayer.setTempBanned(false);
                thisPlayer.setBanEndTime(0);
                thisPlayer.saveConfig(false, false, false, false, true, false, false, false);
            } else {
                event.disallow(Result.KICK_BANNED, ("Du bist temporaer gebannt bis " + new Date(endTime + 1000)));
                return;
            }
        }
    }
    /**
     * 
     * ON PLAYER JOIN
     * 
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(player);

        // IS USER BANNED IN TXT?
        if (ASCore.bannedPlayers.containsKey(player.getName().toLowerCase()) || thisPlayer.isBanned()) {
            player.kickPlayer("Du bist gebannt!");
            return;
        }

        // IS USER TEMPBANNED?

        // UPDATE NICK
        thisPlayer.updateNick();
        thisPlayer.updateLastSeen();
    }

    /**
     * 
     * ON PLAYER KICK
     * 
     */
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.isCancelled())
            return;
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(event.getPlayer());
        thisPlayer.updateLastSeen();
        thisPlayer.saveConfig(true, true, true, true, true, true, true, true);
        playerMap.remove(event.getPlayer().getName().toLowerCase());
    }

    /**
     * 
     * ON PLAYER QUIT
     * 
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(event.getPlayer());
        thisPlayer.updateLastSeen();
        thisPlayer.saveConfig(true, true, true, true, true, true, true, true);
        playerMap.remove(event.getPlayer().getName().toLowerCase());
    }

    /**
     * 
     * ON PLAYER INTERACT
     * 
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // ONLY CLICKS ON A BLOCK
        if (!event.hasBlock())
            return;

        Player player = event.getPlayer();
        // FILL CHEST
        if (queuedFillChest.containsKey(player.getName())) {

            // CLICKED ON A CHEST?
            if (event.getClickedBlock().getTypeId() != Material.CHEST.getId()) {
                queuedFillChest.remove(player.getName());
                PlayerUtils.sendError(player, ASCore.NAME, "Chestfill abgebrochen!");
                return;
            } else {
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);

                // FILL CHEST / DOUBLECHEST
                ItemStack item = queuedFillChest.get(event.getPlayer().getName()).clone();
                Chest chest = (Chest) event.getClickedBlock().getState();
                fillChest(chest, item);

                Chest dChest = BlockUtils.isDoubleChest(chest.getBlock());
                if (dChest != null)
                    fillChest(dChest, item);

                // SEND MESSAGE
                PlayerUtils.sendSuccess(player, ASCore.NAME, "Kiste wurde mit '" + item.getType().name() + "' gefuellt!");
                queuedFillChest.remove(event.getPlayer().getName());
            }
            return;
        }
    }

    /**
     * 
     * FILL CHEST
     * 
     * @param chest
     *            chestblock to be filled
     * @param item
     *            item to fill the chest with
     * 
     */
    public void fillChest(Chest chest, ItemStack item) {
        if (item.getTypeId() == Material.AIR.getId()) {
            chest.getInventory().clear();
            return;
        }
        for (int i = 0; i < chest.getInventory().getSize(); i++)
            chest.getInventory().addItem(item);
    }

    /**
     * 
     * ON PLAYER CHAT
     * 
     */
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(event.getPlayer());

        // Player is muted -> only ops and player with right permission can read
        if (thisPlayer.isMuted()) {
            String nick = ASCore.getPlayerName(event.getPlayer());
            String message = ChatColor.RED + "[STUMM] " + nick + ChatColor.WHITE + ": " + event.getMessage();
            Player current = null;

            Iterator<Player> i = event.getRecipients().iterator();
            while (i.hasNext()) {
                current = i.next();
                if (UtilPermissions.playerCanUseCommand(current, "adminstuff.chat.read.muted"))
                    PlayerUtils.sendBlankMessage(current, message);

            }
            event.setCancelled(true);
        }
    }
    /**
     * @return the playerMap
     */
    public static Map<String, ASPlayer> getPlayerMap() {
        return playerMap;
    }

    /**
     * @param playerMap
     *            the playerMap to set
     */
    public static void setPlayerMap(Map<String, ASPlayer> playerMap) {
        ASPlayerListener.playerMap = playerMap;
    }

}
