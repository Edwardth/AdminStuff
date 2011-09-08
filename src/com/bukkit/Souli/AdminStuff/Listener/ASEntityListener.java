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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.bukkit.Souli.AdminStuff.ASCore;
import com.bukkit.Souli.AdminStuff.ASPlayer;

public class ASEntityListener extends EntityListener {
    /**
     * 
     * ON ENTITY DAMAGE
     * 
     */
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled())
            return;

        // ONLY PLAYERS
        if (!(event.getEntity() instanceof Player))
            return;

        // ADD PLAYER, IF NOT FOUND
        Player player = (Player) event.getEntity();
        ASPlayer thisPlayer = ASCore.getOrCreateASPlayer(player);

        // IS PLAYER GOD = NO DAMAGE
        if (thisPlayer.isGod()) {
            event.setDamage(0);
            event.setCancelled(true);
            return;
        }
    }

}
