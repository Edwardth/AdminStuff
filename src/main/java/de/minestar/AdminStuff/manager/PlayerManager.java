/*
 * Copyright (C) 2012 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.AdminStuff.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.database.DatabaseHandler;

public class PlayerManager {

    private Map<String, ASPlayer> players;
    private DatabaseHandler dbHandler;

    public PlayerManager(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
        players = dbHandler.loadPlayer();
    }

    /**
     * Get player when player is existing or create a new one
     * 
     * @param player
     *            The target
     * @return Always a ASPlayer object!
     */
    public ASPlayer getPlayer(Player player) {
        return getPlayer(player.getName());
    }

    /**
     * Get player when player is existing or create a new one
     * 
     * @param playerName
     *            The targets name, can be capitalized
     * @return Always a ASPlayer object!
     */
    public ASPlayer getPlayer(String playerName) {
        // search for existing ASPlayer
        String temp = playerName.toLowerCase();
        ASPlayer target = players.get(temp);
        // ASPlayer already exists
        if (target != null)
            return target;
        else
            // create a new player and store him in database
            return addPlayer(playerName, temp);
    }

    // add player to map and database
    private ASPlayer addPlayer(String accountName, String playerName) {
        ASPlayer player = new ASPlayer(accountName);
        players.put(playerName, player);
        dbHandler.addPlayer(accountName);
        return player;
    }

    // ******************************************************
    // *************** BLOCK COUNTING ***********************
    // ******************************************************

    private Map<String, Location[]> blockCountSel = new HashMap<String, Location[]>();

    public boolean isInSelectionMode(Player player) {
        return blockCountSel.containsKey(player.getName().toLowerCase());
    }

    public void setSelectionMode(Player player, boolean isIn) {
        if (isIn)
            blockCountSel.put(player.getName().toLowerCase(), new Location[2]);
        else
            blockCountSel.remove(player.getName().toLowerCase());
    }

    public void setSelectedBlock(Player player, Block target, boolean leftClick) {
        String pName = player.getName().toLowerCase();
        Location[] corners = blockCountSel.get(pName);
        if (corners != null) {
            if (leftClick)
                corners[0] = target.getLocation();
            else
                corners[1] = target.getLocation();
        }
        blockCountSel.put(pName, corners);
    }

    public Location[] getSelectedBlocks(Player player) {
        return blockCountSel.get(player.getName().toLowerCase());
    }

    // ******************************************************
    // ************** COMMON USER MODIFACTION ***************
    // ******************************************************

    // glue or unglue the player and store it in database
    public void setGlue(ASPlayer target, Location loc) {
        target.setGlueLocation(loc);
        dbHandler.saveGlueLocation(target.getPlayerName(), loc);
    }

    public void setAFK(ASPlayer target, boolean isAFK, Player player) {
        target.setAFK(isAFK);
        target.updateNick(player);
    }

    public void updateNickName(ASPlayer target, String name, Player player) {
//        target.setNickname(name);
//        target.updateNick(player);
//        dbHandler.saveDisplayName(target.getPlayerName(), name);
    }

    public void setGodMode(ASPlayer target, boolean isGod) {
        target.setGod(isGod);
        dbHandler.saveGod(target.getPlayerName(), isGod);
    }

    public void setGameMode(ASPlayer target, Player player, GameMode mode) {
        target.setGameMode(mode);
        player.setGameMode(mode);
        dbHandler.saveGameMode(target.getPlayerName(), mode);
    }

    public void setMuted(ASPlayer target, boolean isMuted) {
        target.setMuted(isMuted);
        dbHandler.saveMuted(target.getPlayerName(), isMuted);
    }

    public void setSlapped(ASPlayer target, Player player, boolean isSlapped) {
        target.setSlapped(isSlapped);
        target.updateNick(player);
    }
}
