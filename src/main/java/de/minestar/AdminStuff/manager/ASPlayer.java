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

package de.minestar.AdminStuff.manager;

import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.minestar.core.MinestarCore;

public class ASPlayer {

    private String playerName = "";

    private String lastSender = null;

    private boolean isAFK = false;
    private boolean isMuted = false;
    private boolean isSlapped = false;
    private boolean hideChat = false;

    private GameMode mode = GameMode.SURVIVAL;

    private Location glueLocation = null;
    private Set<String> Recipients = null;

    // load from database
    public ASPlayer(String accountName, String nickName, boolean muted, boolean banned, boolean god, GameMode mode, long tempBanned, String lastSeen, Location glueLocation) {
        this.playerName = accountName;
        this.isMuted = muted;
        this.mode = mode;
        this.glueLocation = glueLocation;
    }

    // call when a new player is creating
    protected ASPlayer(String playerName) {
        this.playerName = playerName;
    }

    // *********************** NICK **************************
    public String getNickname() {
        return MinestarCore.getPlayer(this.playerName).getNickName();
    }

    // *********************** AFK **************************
    public boolean isAFK() {
        return isAFK;
    }

    protected void setAFK(boolean isAFK) {
        this.isAFK = isAFK;
    }

    // ********************* MUTE ***************************
    public boolean isMuted() {
        return isMuted;
    }

    protected void setMuted(boolean muted) {
        this.isMuted = muted;
    }

    // ****************** LAST SENDER **********************
    public String getLastSender() {
        return lastSender;
    }

    public void setLastSender(String lastSender) {
        this.lastSender = lastSender;
    }

    public String getPlayerName() {
        return playerName;
    }

    protected void updateNick(Player player) {
//        String nick = MinestarCore.getPlayer(this.playerName).getNickName();
//        nick = nick.replace("[AFK] ", "").replace("was fished!", "");
//        if (isAFK)
//            nick = "[AFK] " + nick;
//        if (isSlapped)
//            nick = nick + "was fished!";
//        MinestarCore.getPlayer(this.playerName).setNickName(nick);
    }

    // ********************* GLUE **************************
    public boolean isGlued() {
        return glueLocation != null;
    }

    public Location getGlueLocation() {
        return glueLocation;
    }

    protected void setGlueLocation(Location glueLocation) {
        this.glueLocation = glueLocation;
    }

    // ******************** SLAP ***************************
    public boolean isSlapped() {
        return isSlapped;
    }

    protected void setSlapped(boolean isSlapped) {
        this.isSlapped = isSlapped;
    }

    // ***************** RECIPIENTS ************************
    public boolean isRecipient(String playerName) {
        return Recipients != null && Recipients.contains(playerName);
    }

    public void clearRecipients() {
        Recipients = null;
    }

    public void setRecipients(Set<String> recipients) {
        Recipients = recipients;
    }

    // ****************** HIDE CHAT ***********************
    public boolean isHideChat() {
        return hideChat;
    }

    public void setHideChat(boolean hideChat) {
        this.hideChat = hideChat;
    }

    // ****************** GAME MODE ***********************
    public GameMode getGameMode() {
        return mode;
    }

    protected void setGameMode(GameMode mode) {
        this.mode = mode;
    }
}
