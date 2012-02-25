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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ASPlayer {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private String playerName = "";
    // displayname without any prefix
    private String nickName = "";

    private String lastSender = null;

    private String lastSeen = "Niemals";

    private boolean isAFK = false;
    private boolean isMuted = false;
    private boolean isSlapped = false;
    private boolean isBanned = false;
    private boolean hideChat = false;
    private boolean isGod = false;

    private long banEndTime = 0;

    private GameMode mode = GameMode.SURVIVAL;

    private Location glueLocation = null;
    private Set<String> Recipients = null;
    private ItemStack[] invBackup = new ItemStack[36];

    // load from database
    public ASPlayer(String accountName, String nickName, boolean muted, boolean banned, boolean god, GameMode mode, long tempBanned, String lastSeen, Location glueLocation) {
        this.playerName = accountName;
        this.nickName = nickName;
        this.isMuted = muted;
        this.isBanned = banned;
        this.mode = mode;
        this.banEndTime = tempBanned;
        // check time ban expire
        isTempBanned();
        this.lastSeen = lastSeen;
        this.glueLocation = glueLocation;
    }

    // call when a new player is creating
    protected ASPlayer(String playerName) {
        this.playerName = playerName;
    }

    // ******************* LAST SEEN ************************
    public String getLastSeen() {
        return lastSeen;
    }

    protected void updateLastSeen() {
        lastSeen = dateFormat.format(new Date());
    }

    // **************** INVENTORY BACKUP ********************
    protected void saveInventory(ItemStack[] content) {
        invBackup = content;
    }

    protected ItemStack[] getInvBackUp() {
        ItemStack[] temp = invBackup;
        invBackup = null;
        return temp;
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

    // ****************** NICK NAME ************************
    public String getNickname() {
        return nickName;
    }

    public String getPlayerName() {
        return playerName;
    }

    protected void setNickname(String nickname) {
        this.nickName = nickname;
    }

    protected void updateNick(Player player) {

        String nick = null;
        // when player has a changed display name
        if (!getNickname().isEmpty())
            nick = getNickname();
        else
            nick = player.getDisplayName();
        nick = nick.replace("[AFK] ", "").replace("was fished!", "");
        if (isAFK)
            nick = "[AFK] " + nick;
        if (isSlapped)
            nick = nick + "was fished!";

        player.setDisplayName(nick);
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

    // ******************* BANN ****************************
    public boolean isTempBanned() {
        // is not temp banned
        if (banEndTime == 0L)
            return false;
        // check expired temp banned
        if (banEndTime <= System.currentTimeMillis()) {
            banEndTime = 0L;
            return false;
        } else
            return true;
    }

    public long getBanEndTime() {
        return banEndTime;
    }

    protected void setBanEndTime(long banEndTime) {
        this.banEndTime = banEndTime;
    }

    public boolean isBanned() {
        return isBanned;
    }

    protected void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    // ****************** HIDE CHAT ***********************
    public boolean isHideChat() {
        return hideChat;
    }

    public void setHideChat(boolean hideChat) {
        this.hideChat = hideChat;
    }

    // ****************** GOD MODE ************************
    public boolean isGod() {
        return isGod;
    }

    protected void setGod(boolean isGod) {
        this.isGod = isGod;
    }

    // ****************** GAME MODE ***********************
    public GameMode getGameMode() {
        return mode;
    }

    protected void setGameMode(GameMode mode) {
        this.mode = mode;
    }
}
