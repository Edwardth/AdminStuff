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

package de.minestar.AdminStuff;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.minestar.minestarlibrary.utils.PlayerUtils;

public class ASPlayer {

    private String playerName = "";
    private boolean isAFK = false;
    private boolean isMuted = false;
    private boolean isGlued = false;
    private boolean isSlapped = false;
    private boolean isBanned = false;
    private boolean isTempBanned = false;
    private boolean hideChat = false;
    private boolean isGod = false;
    private boolean classicMode = false;
    private long banEndTime = 0;
    private Location glueLocation = null;
    private String lastSender = null;
    private Set<String> Recipients = null;
    private String nickname = "";
    private ItemStack[] invBackUp = new ItemStack[36];
    private String lastSeen = "NEVER";

    public ASPlayer(String playerName) {
        this.playerName = playerName;
        this.loadConfig();
    }

    public ASPlayer(Player player) {
        this.playerName = player.getName();
        this.loadConfig();
    }

    /**
     * UPDATE LAST SEEN
     */
    public void updateLastSeen() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
        SimpleDateFormat hourFormat = new SimpleDateFormat("k:m:s");
        setLastSeen(dateFormat.format(new Date()) + " - " + hourFormat.format(new Date()));
    }

    /**
     * GET LAST SEEN
     */
    public String getLastSeen() {
        return lastSeen;
    }

    /**
     * SET LAST SEEN
     */
    public void setLastSeen(String lastSeen) {
        if (lastSeen == null)
            lastSeen = "War niemals da";

        this.lastSeen = lastSeen;
    }

    /**
     * IS RECIPIENT
     */
    public boolean isRecipient(String playerName) {
        return Recipients != null && Recipients.contains(playerName);
    }

    /**
     * SAVE INVENTORY
     * 
     * @param toSave
     *            Inventory to save
     */
    public void saveInventory(Inventory toSave) {
        invBackUp = new ItemStack[36];
        for (int i = 0; i < toSave.getSize(); i++) {
            if (toSave.getItem(i) != null && toSave.getItem(i).getTypeId() > 0) {
                getInvBackUp()[i] = toSave.getItem(i).clone();
            }
        }
    }

    /**
     * LOAD PLAYERCONFIG
     * 
     * @param playerName
     */
    public void loadConfig() {
        try {
            new File("plugins/AdminStuff/userdata/").mkdirs();
            YamlConfiguration config = new YamlConfiguration();
            if (new File("plugins/AdminStuff/userdata/" + this.playerName.toLowerCase() + ".yml").exists()) {
                config.load(new File("plugins/AdminStuff/userdata/" + this.playerName.toLowerCase() + ".yml"));

                setGod(config.getBoolean("isGod", false));
                setGlued(config.getBoolean("glue.isGlued", false));
                setAFK(config.getBoolean("isAFK", false));
                setMuted(config.getBoolean("isMuted", false));
                setBanned(config.getBoolean("isBanned", false));
                setTempBanned(config.getBoolean("isTempBanned", false));
                setBanEndTime(Long.valueOf(config.getString("banEndTime", "0")));
                setNickname(config.getString("Nickname", ""));
                setClassicMode(config.getBoolean("classicMode", false));
                setLastSeen(config.getString("lastSeen"));

                // LOAD GLUE
                if (isGlued()) {
                    World world = Bukkit.getWorld(config.getString("glue.Worldname", null));
                    if (world != null)
                        glueLocation = new Location(world, config.getInt("glue.X", 0), config.getInt("glue.Y", 127), config.getInt("glue.Z", 0), config.getInt("glue.Yaw", 0), config.getInt("glue.Pitch", 0));
                    else
                        glueLocation = null;

                } else {
                    glueLocation = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameMode BooleanToGameMode(boolean inClassic) {
        return (inClassic ? GameMode.CREATIVE : GameMode.SURVIVAL);
    }

    /**
     * SAVE PLAYERDATA TO A FILE
     */
    public void saveConfig(boolean saveAFK, boolean saveMute, boolean saveGlue, boolean saveBan, boolean saveNick, boolean saveGod, boolean saveClassic) {
        // TODO: Always call this method when a change is done IN this class
        new File("plugins/AdminStuff/userdata/").mkdirs();
        YamlConfiguration config = new YamlConfiguration();
        try {
            // config.load(new File("plugins/AdminStuff/userdata/" +
            // this.playerName.toLowerCase() + ".yml"));

            config.set("lastSeen", getLastSeen());

            if (saveBan) {
                config.set("isBanned", isBanned);
                config.set("isTempBanned", isTempBanned);
                config.set("banEndTime", String.valueOf(getBanEndTime()));
            }

            if (saveNick) {
                config.set("Nickname", nickname);
            }

            if (saveAFK)
                config.set("isAFK", isAFK);

            if (saveClassic)
                config.set("classicMode", classicMode);

            if (saveMute)
                config.set("isMuted", isMuted);

            if (saveGlue) {
                config.set("glue.isGlued", isGlued);
                if (glueLocation != null) {
                    config.set("glue.X", glueLocation.getBlockX());
                    config.set("glue.Y", glueLocation.getBlockY());
                    config.set("glue.Z", glueLocation.getBlockZ());
                    config.set("glue.Pitch", glueLocation.getPitch());
                    config.set("glue.Yaw", glueLocation.getYaw());
                    config.set("glue.Worldname", glueLocation.getWorld().getName());
                }
            }

            if (saveGod) {
                config.set("isGod", isGod);
            }

            config.save(new File("plugins/AdminStuff/userdata/" + this.playerName.toLowerCase() + ".yml"));
        } catch (Exception e) {

        }
    }

    /**
     * @return the isAFK
     */
    public boolean isAFK() {
        return isAFK;
    }

    /**
     * @param isAFK
     *            the isAFK to set
     */
    public void setAFK(boolean isAFK) {
        this.isAFK = isAFK;
    }

    /**
     * @return the muted
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * @param muted
     *            the muted to set
     */
    public void setMuted(boolean muted) {
        this.isMuted = muted;
    }

    /**
     * @return the lastSender
     */
    public String getLastSender() {
        return lastSender;
    }

    /**
     * @param lastSender
     *            the lastSender to set
     */
    public void setLastSender(String lastSender) {
        this.lastSender = lastSender;
    }

    /**
     * @return the invBackUp
     */
    public ItemStack[] getInvBackUp() {
        return invBackUp;
    }

    /**
     * @param invBackUp
     *            the invBackUp to set
     */
    public void setInvBackUp(ItemStack[] invBackUp) {
        this.invBackUp = invBackUp;
    }

    /**
     * UPDATE NICKNAME
     * 
     * @param playerName
     * @param isAFK
     */
    public void updateNick() {
        Player player = PlayerUtils.getOnlinePlayer(playerName);
        if (player == null)
            return;

        String nick = Core.getPlayerName(player);
        if (!getNickname().equalsIgnoreCase("")) {
            nick = getNickname();
        }
        nick = nick.replace("[AFK] ", "").replace("was fished!", "");
        if (isAFK)
            nick = "[AFK] " + nick;

        if (isSlapped)
            nick = nick + "was fished!";

        player.setDisplayName(nick);
    }

    /**
     * IS GLUED
     * 
     * @return the isGlued
     */
    public boolean isGlued() {
        return isGlued;
    }

    /**
     * SET GLUED
     * 
     * @param isGlued
     *            the isGlued to set
     */
    public void setGlued(boolean isGlued) {
        if (!isGlued)
            this.glueLocation = null;
        this.isGlued = isGlued;
    }

    /**
     * GET GLUELOCATION
     * 
     * @return the glueLocation
     */
    public Location getGlueLocation() {
        return glueLocation;
    }

    /**
     * SET GLUELOCATION
     * 
     * @param glueLocation
     *            the glueLocation to set
     */
    public void setGlueLocation(Location glueLocation) {
        this.glueLocation = glueLocation;
    }

    /**
     * IS SLAPPED
     * 
     * @return the isSlapped
     */
    public boolean isSlapped() {
        return isSlapped;
    }

    /**
     * SET SLAPPED
     * 
     * @param isSlapped
     *            the isSlapped to set
     */
    public void setSlapped(boolean isSlapped) {
        this.isSlapped = isSlapped;
    }

    public void clearRecipients() {
        Recipients = null;
    }

    /**
     * @param recipients
     *            the recipients to set
     */
    public void setRecipients(Set<String> recipients) {
        Recipients = recipients;
    }

    /**
     * @return the isBanned
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * @return the isTempBanned
     */
    public boolean isTempBanned() {
        return isTempBanned;
    }

    /**
     * @param isTempBanned
     *            the isTempBanned to set
     */
    public void setTempBanned(boolean isTempBanned) {
        this.isTempBanned = isTempBanned;
    }

    /**
     * @return the banEndTime
     */
    public long getBanEndTime() {
        return banEndTime;
    }

    /**
     * @param banEndTime
     *            the banEndTime to set
     */
    public void setBanEndTime(long banEndTime) {
        this.banEndTime = banEndTime;
    }

    /**
     * @param isBanned
     *            the isBanned to set
     */
    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     *            the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the hideChat
     */
    public boolean isHideChat() {
        return hideChat;
    }

    /**
     * @param hideChat
     *            the hideChat to set
     */
    public void setHideChat(boolean hideChat) {
        this.hideChat = hideChat;
    }

    /**
     * @return the isGod
     */
    public boolean isGod() {
        return isGod;
    }

    /**
     * @param isGod
     *            the isGod to set
     */
    public void setGod(boolean isGod) {
        this.isGod = isGod;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName
     *            the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the classicMode
     */
    public boolean isClassicMode() {
        return classicMode;
    }

    /**
     * @param classicMode
     *            the classicMode to set
     */
    public void setClassicMode(boolean classicMode) {
        this.classicMode = classicMode;
    }
}
