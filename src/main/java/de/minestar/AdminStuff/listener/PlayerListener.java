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

package de.minestar.AdminStuff.listener;

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

import de.minestar.AdminStuff.manager.ASPlayer;
import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.manager.PlayerManager;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class PlayerListener implements Listener {

    private PlayerManager pManager;
    public static Map<String, ItemStack> queuedFillChest = new TreeMap<String, ItemStack>();

    public PlayerListener(PlayerManager pManager) {
        this.pManager = pManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (BlockUtils.LocationEquals(event.getTo(), event.getFrom()))
            return;

        ASPlayer target = pManager.getPlayer(event.getPlayer());
        if (target.isGlued())
            event.setTo(target.getGlueLocation());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        String name = event.getName().toLowerCase();
        ASPlayer thisPlayer = pManager.getPlayer(name);

        // IS USER BANNED IN TXT?
        if (pManager.isPermBanned(name) || thisPlayer.isBanned()) {
            event.disallow(Result.KICK_BANNED, "Du bist gebannt!");
            return;
        }

        if (thisPlayer.isTempBanned())
            event.disallow(Result.KICK_BANNED, ("Du bist temporaer gebannt bis " + new Date(thisPlayer.getBanEndTime() + 1000)));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ASPlayer thisPlayer = pManager.getPlayer(player);

        // IS USER BANNED IN TXT OR TEMPBANNED
        if (pManager.isPermBanned(player.getName()) || thisPlayer.isBanned()) {
            player.kickPlayer("Du bist gebannt!");
            return;
        }
        // UPDATE NICK
        pManager.updateLastSeen(thisPlayer);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.isCancelled())
            return;
        ASPlayer thisPlayer = pManager.getPlayer(event.getPlayer());
        pManager.updateLastSeen(thisPlayer);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ASPlayer thisPlayer = pManager.getPlayer(event.getPlayer());
        pManager.updateLastSeen(thisPlayer);
    }

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
                PlayerUtils.sendError(player, Core.NAME, "Chestfill abgebrochen!");
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
                PlayerUtils.sendSuccess(player, Core.NAME, "Kiste wurde mit '" + item.getType().name() + "' gefuellt!");
                queuedFillChest.remove(event.getPlayer().getName());
            }
            return;
        }
    }

    private void fillChest(Chest chest, ItemStack item) {
        if (item.getTypeId() == Material.AIR.getId()) {
            chest.getInventory().clear();
            return;
        }
        for (int i = 0; i < chest.getInventory().getSize(); i++)
            chest.getInventory().addItem(item);
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        ASPlayer thisPlayer = pManager.getPlayer(event.getPlayer());

        // Player is muted -> only ops and player with right permission can read
        if (thisPlayer.isMuted()) {
            event.setCancelled(true);

            String nick = thisPlayer.getNickname();
            String message = ChatColor.RED + "[STUMM] " + nick + ChatColor.WHITE + ": " + event.getMessage();
            Player current = null;

            Iterator<Player> i = event.getRecipients().iterator();
            while (i.hasNext()) {
                current = i.next();
                if (UtilPermissions.playerCanUseCommand(current, "adminstuff.chat.read.muted"))
                    PlayerUtils.sendBlankMessage(current, message);
            }
        }
    }
}
