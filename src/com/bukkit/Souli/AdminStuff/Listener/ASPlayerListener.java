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

package com.bukkit.Souli.AdminStuff.Listener;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASPlayer;
import com.bukkit.Souli.AdminStuff.ASSpawn;
import com.gemo.utils.BlockUtils;
import com.gemo.utils.UtilPermissions;

public class ASPlayerListener extends PlayerListener {
    public static Map<String, ASPlayer> playerMap = new TreeMap<String, ASPlayer>();
    public static Map<String, ItemStack> queuedFillChest = new TreeMap<String, ItemStack>();

    /**
     * 
     * ON PLAYER MOVE
     * 
     */
    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
	if (BlockUtils.LocationEquals(event.getTo(), event.getFrom()))
	    return;

	// ADD PLAYER, IF NOT FOUND
	Player player = event.getPlayer();
	if (!ASPlayerListener.playerMap.containsKey(player.getName())) {
	    ASPlayerListener.playerMap.put(player.getName(), new ASPlayer());
	    return;
	}

	// IS PLAYER GLUED = RETURN TO GLUELOCATION
	if (ASPlayerListener.playerMap.get(player.getName()).isGlued()) {
	    event.setTo(ASPlayerListener.playerMap.get(player.getName())
		    .getGlueLocation());
	}
    }

    /**
     * 
     * ON PLAYER JOIN
     * 
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
	if (!playerMap.containsKey(event.getPlayer().getName())) {
	    playerMap.put(event.getPlayer().getName(), new ASPlayer());
	}

	// LOAD USERCONFIG
	playerMap.get(event.getPlayer().getName()).loadConfig(
		event.getPlayer().getName());

	// IS USER TEMPBANNED?
	if (playerMap.get(event.getPlayer().getName()).isTempBanned()) {
	    long endTime = playerMap.get(event.getPlayer().getName())
		    .getBanEndTime();
	    if (endTime < System.currentTimeMillis()) {
		ASPlayerListener.playerMap.get(event.getPlayer().getName())
			.setTempBanned(false);
		ASPlayerListener.playerMap.get(event.getPlayer().getName())
			.setBanEndTime(0);
		ASPlayerListener.playerMap.get(event.getPlayer().getName())
			.saveConfig(event.getPlayer().getName(), false, false,
				false, false, true, false);
		return;
	    } else {
		Date newDate = new Date(endTime + 1000);
		event.getPlayer().kickPlayer(
			"You are temporary banned until " + newDate.toString()
				+ "!");
		return;
	    }
	}

	// UPDATE NICK
	ASPlayer.updateNick(event.getPlayer().getName(),
		playerMap.get(event.getPlayer().getName()).isAFK(), playerMap
			.get(event.getPlayer().getName()).isSlapped());
    }

    /**
     * 
     * ON PLAYER KICK
     * 
     */
    @Override
    public void onPlayerKick(PlayerKickEvent event) {
	if (event.isCancelled())
	    return;
	if (playerMap.containsKey(event.getPlayer().getName())) {
	    playerMap.remove(event.getPlayer().getName());
	}
    }

    /**
     * 
     * ON PLAYER QUIT
     * 
     */
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
	if (playerMap.containsKey(event.getPlayer().getName())) {
	    playerMap.remove(event.getPlayer().getName());
	}
    }

    /**
     * 
     * ON PLAYER RESPAWN
     * 
     */
    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
	if (!playerMap.containsKey(event.getPlayer().getName())) {
	    playerMap.put(event.getPlayer().getName(), new ASPlayer());
	}
	// TELEPORT TO WORLDSPAWN
	Location loc = ASSpawn
		.getSpawn(ASCore.getMCServer().getWorlds().get(0));
	Location nloc = loc.getWorld().getHighestBlockAt(loc).getLocation();
	nloc.setYaw(loc.getYaw());
	nloc.setPitch(loc.getPitch());
	event.setRespawnLocation(nloc);

	/*
	 * ASPlayer.updateNick(event.getPlayer().getName(),
	 * playerMap.get(event.getPlayer().getName()).isAFK(), playerMap
	 * .get(event.getPlayer().getName()).isSlapped());
	 */
    }

    /**
     * 
     * ON PLAYER INTERACT
     * 
     */
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {

	// ONLY CLICKS ON A BLOCK
	if (event.getAction() != Action.LEFT_CLICK_BLOCK
		&& event.getAction() != Action.RIGHT_CLICK_BLOCK)
	    return;

	// FILL CHEST
	if (queuedFillChest.containsKey(event.getPlayer().getName())) {

	    // CLICKED ON A CHEST?
	    if (event.getClickedBlock().getTypeId() != Material.CHEST.getId()) {
		queuedFillChest.remove(event.getPlayer().getName());
		event.getPlayer().sendMessage(
			ChatColor.RED + "Chestfill cancelled.");
		return;
	    } else {
		// CANCEL EVENT
		event.setUseInteractedBlock(Event.Result.DENY);
		event.setUseItemInHand(Event.Result.DENY);
		event.setCancelled(true);

		// FILL CHEST / DOUBLECHEST
		ItemStack item = queuedFillChest.get(
			event.getPlayer().getName()).clone();
		Chest chest = (Chest) event.getClickedBlock().getState();
		fillChest(chest, item);

		Chest dChest = BlockUtils.isDoubleChest(chest.getBlock());
		if (dChest != null) {
		    fillChest(dChest, item);
		}

		// SEND MESSAGE
		event.getPlayer().sendMessage(
			ChatColor.GREEN
				+ "Chest filled with '"
				+ Material.getMaterial(item.getTypeId()).name()
					.toLowerCase() + "'.");
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
	for (int i = 0; i < chest.getInventory().getSize(); i++)
	    chest.getInventory().setItem(i, item.clone());
    }

    /**
     * 
     * ON PLAYER CHAT
     * 
     */
    @Override
    public void onPlayerChat(PlayerChatEvent event) {
	if (!playerMap.containsKey(event.getPlayer().getName())) {
	    playerMap.put(event.getPlayer().getName(), new ASPlayer());
	}
	ASPlayer thisPlayer = playerMap.get(event.getPlayer().getName());

	String nick = ASCore.getPlayerName(event.getPlayer());

	// PLAYER IS MUTED = ONLY ADMINS/MODS RECEIVE A MESSAGE
	if (thisPlayer.isMuted()) {
	    Iterator<Player> it = event.getRecipients().iterator();
	    while (it.hasNext()) {
		Player nextPlayer = it.next();
		if (nextPlayer.getName().equalsIgnoreCase(
			event.getPlayer().getName())) {
		    nextPlayer.sendMessage(ChatColor.RED + "[Muted] " + nick
			    + ChatColor.WHITE + ": " + event.getMessage());
		    continue;
		}

		if (UtilPermissions.playerCanUseCommand(nextPlayer,
			"adminstuff.chat.read.muted")) {
		    nextPlayer.sendMessage(ChatColor.RED + "[Muted] " + nick
			    + ChatColor.WHITE + ": " + event.getMessage());
		}
	    }
	    event.setCancelled(true);
	    return;
	}

	// PLAYER IS IN CHATMODE
	if (thisPlayer.getRecipients() != null) {
	    Iterator<Player> it = event.getRecipients().iterator();
	    while (it.hasNext()) {
		Player nextPlayer = it.next();
		boolean found = false;
		if (UtilPermissions.playerCanUseCommand(nextPlayer,
			"adminstuff.chat.read.all")) {
		    nextPlayer.sendMessage(ChatColor.DARK_GREEN + nick
			    + ChatColor.WHITE + ": " + event.getMessage());
		    found = true;
		}
		if (!found) {
		    for (String name : thisPlayer.getRecipients()) {
			if (name.equalsIgnoreCase(nextPlayer.getName())
				|| nextPlayer.getName().equalsIgnoreCase(
					event.getPlayer().getName())) {
			    nextPlayer.sendMessage(ChatColor.DARK_GREEN + nick
				    + ChatColor.WHITE + ": "
				    + event.getMessage());
			    break;
			}
		    }
		}
	    }
	    event.setCancelled(true);
	    return;
	}

	// CHAT IS HIDDEN = ONLY RECEIVE MESSAGES FROM OTHER PREDEFINED PLAYERS
	Iterator<Player> it = event.getRecipients().iterator();
	while(it.hasNext()) {
	    Player nextPlayer = it.next();
	    if (nextPlayer.getName().equalsIgnoreCase(
		    event.getPlayer().getName()))
		continue;

	    ASPlayer actPlayer = playerMap.get(nextPlayer.getName());
	    if (actPlayer != null) {
		if (actPlayer.isHideChat()) {
		    if (!actPlayer.isRecipient(event.getPlayer().getName())) {
			it.remove();
		    }
		}
	    }
	}
    }

}
