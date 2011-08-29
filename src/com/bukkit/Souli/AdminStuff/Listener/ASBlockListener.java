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

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ASBlockListener extends BlockListener {

    /**
     * 
     * ON BLOCK PLACE
     * 
     */
    @Override
    public void onBlockPlace(BlockPlaceEvent event) {
	if (event.isCancelled())
	    return;

	Player player = event.getPlayer();
	if (ASPlayerListener.playerMap.get(player.getName()).hasUnlimitedItem(
		event.getBlockPlaced().getTypeId())) {
	    player.getItemInHand().setAmount(
		    player.getItemInHand().getAmount() + 1);
	}
    }

}
