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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.minestar.AdminStuff.manager.ASPlayer;
import de.minestar.AdminStuff.manager.PlayerManager;

public class EntityListener implements Listener {

    private PlayerManager pManager;

    public EntityListener(PlayerManager pManager) {
        this.pManager = pManager;
    }
    /**
     * 
     * ON ENTITY DAMAGE
     * 
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player))
            return;

        // ADD PLAYER, IF NOT FOUND
        ASPlayer thisPlayer = pManager.getPlayer((Player) event.getEntity());

        // IS PLAYER GOD = NO DAMAGE
        if (thisPlayer.isGod()) {
            event.setDamage(0);
            event.setCancelled(true);
            return;
        }
    }
}
