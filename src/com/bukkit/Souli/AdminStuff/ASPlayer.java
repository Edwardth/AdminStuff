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

package com.bukkit.Souli.AdminStuff;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.config.Configuration;

import com.bukkit.Souli.AdminStuff.Listener.ASPlayerListener;

public class ASPlayer {
    private Map<Integer, ASItem> unlimitedList = new HashMap<Integer, ASItem>();
    private Location homeLocation = null;
    private boolean isAFK = false;
    private boolean isMuted = false;
    private boolean isGlued = false;
    private boolean isSlapped = false;
    private boolean isBanned = false;
    private boolean isTempBanned = false;
    private long banEndTime = 0;
    private Location glueLocation = null;
    private String lastSender = null;
    private String[] Recipients = null;
    private String nickname = "";
    private ItemStack[] invBackUp = new ItemStack[36];

    public ASPlayer() {
    }

    /**
     * LOAD PLAYERCONFIG
     * 
     * @param playerName
     */
    public void loadConfig(String playerName) {
	new File("plugins/AdminStuff/userdata/").mkdirs();
	Configuration config = new Configuration(new File(
		"plugins/AdminStuff/userdata/" + playerName + ".yml"));

	config.load();

	setGlued(config.getBoolean("glue.isGlued", false));
	setAFK(config.getBoolean("isAFK", false));
	setMuted(config.getBoolean("isMuted", false));
	setBanned(config.getBoolean("isBanned", false));
	setTempBanned(config.getBoolean("isTempBanned", false));
	setBanEndTime(Long.valueOf(config.getString("banEndTime", "0")));
	setNickname(config.getString("Nickname", ""));
	// LOAD GLUE
	if (isGlued()) {
	    World world = ASCore.getMCServer().getWorld(
		    config.getString("glue.Worldname", null));
	    if (world != null) {
		glueLocation = new Location(world, config.getInt("glue.X", 0),
			config.getInt("glue.Y", 127),
			config.getInt("glue.Z", 0),
			config.getInt("glue.Yaw", 0), config.getInt(
				"glue.Pitch", 0));
	    } else {
		glueLocation = null;
	    }
	} else {
	    glueLocation = null;
	}

	// LOAD HOME
	World world = ASCore.getMCServer().getWorld(
		config.getString("home.Worldname", ""));
	if (world != null) {
	    homeLocation = new Location(world, config.getDouble("home.X", 0),
		    config.getDouble("home.Y", 127d), config.getDouble(
			    "home.Z", 0), Float.valueOf(""
			    + config.getDouble("home.Yaw", 0d)),
		    Float.valueOf("" + config.getDouble("home.Pitch", 0d)));
	} else {
	    homeLocation = null;
	}
    }

    /**
     * SAVE PLAYERDATA TO A FILE
     */
    public void saveConfig(String playerName, boolean saveHome,
	    boolean saveAFK, boolean saveMute, boolean saveUnlimited,
	    boolean saveGlue, boolean saveBan, boolean saveNick) {
	new File("plugins/AdminStuff/userdata/").mkdirs();
	Configuration config = new Configuration(new File(
		"plugins/AdminStuff/userdata/" + playerName + ".yml"));
	config.load();
	if (saveHome) {
	    if (homeLocation != null) {
		config.setProperty("home.X", homeLocation.getX());
		config.setProperty("home.Y", homeLocation.getY());
		config.setProperty("home.Z", homeLocation.getZ());
		config.setProperty("home.Pitch", homeLocation.getPitch());
		config.setProperty("home.Yaw", homeLocation.getYaw());
		config.setProperty("home.Worldname", homeLocation.getWorld()
			.getName());
	    }
	}
	if (saveBan) {
	    config.setProperty("isBanned", isBanned);
	    config.setProperty("isTempBanned", isTempBanned);
	    config.setProperty("banEndTime", String.valueOf(getBanEndTime()));
	}
	
	if (saveNick) {
	    config.setProperty("Nickname", nickname);
	}	

	if (saveAFK)
	    config.setProperty("isAFK", isAFK);

	if (saveMute)
	    config.setProperty("isMuted", isMuted);

	if (saveUnlimited) {
	    ArrayList<Integer> list = new ArrayList<Integer>();
	    for (int val : unlimitedList.keySet())
		list.add(val);
	    config.setProperty("unlimited", list);
	}

	if (saveGlue) {
	    config.setProperty("glue.isGlued", isGlued);
	    if (glueLocation != null) {
		config.setProperty("glue.X", glueLocation.getBlockX());
		config.setProperty("glue.Y", glueLocation.getBlockY());
		config.setProperty("glue.Z", glueLocation.getBlockZ());
		config.setProperty("glue.Pitch", glueLocation.getPitch());
		config.setProperty("glue.Yaw", glueLocation.getYaw());
		config.setProperty("glue.Worldname", glueLocation.getWorld()
			.getName());
	    }
	}
	config.save();
    }

    /**
     * TOGGLE UNLIMITED ITEM
     * 
     * @param TypeID
     * @return true, if added (false, if removed)
     */
    public boolean toggleUnlimitedItem(int TypeID) {
	if (!hasUnlimitedItem(TypeID)) {
	    // ADD ITEM
	    unlimitedList.put(TypeID, new ASItem(TypeID));
	    return true;
	} else {
	    // REMOVE ITEM
	    unlimitedList.remove(TypeID);
	    return false;
	}
    }

    /**
     * 
     * @param TypeID
     * @return true, if found
     */
    public boolean hasUnlimitedItem(int TypeID) {
	return unlimitedList.containsKey(TypeID);
    }

    /**
     * HASHOMELOCATION
     * 
     * @return true, if home is set
     */
    public boolean hasHomeLocation() {
	return getHomeLocation() != null;
    }

    /**
     * SET HOMELOCATION
     */
    public void setHomeLocation(Location newHome) {
	this.homeLocation = newHome;
    }

    /**
     * GET HOMELOCATION
     * 
     * @return the HomeLocation
     */
    public Location getHomeLocation() {
	return this.homeLocation;
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
    public static void updateNick(String playerName, boolean isAFK,
	    boolean isSlapped) {
	Player player = ASCore.getPlayer(playerName);
	if (player == null)
	    return;

	ASPlayer thisPlayer = ASPlayerListener.playerMap.get(player.getName());
	
	String nick = player.getName();
	if (!thisPlayer.getNickname().equalsIgnoreCase("")) {
	    nick = thisPlayer.getNickname();
	}
	nick = nick.replace("[AFK] ", "");
	nick = nick.replace(" was fished!", "");
	if (isAFK)
	    nick = "[AFK] " + nick;

	if (isSlapped)
	    nick = nick + " was fished!";

	player.setDisplayName(nick);
    }

    /**
     * GET UNLIMITED LIST
     * 
     * @return the unlimitedList
     */
    public Map<Integer, ASItem> getUnlimitedList() {
	return unlimitedList;
    }

    /**
     * SET UNLIMITED LIST
     * 
     * @param unlimitedList
     *            the unlimitedList to set
     */
    public void setUnlimitedList(Map<Integer, ASItem> unlimitedList) {
	this.unlimitedList = unlimitedList;
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

    /**
     * @return the recipients
     */
    public String[] getRecipients() {
	return Recipients;
    }

    /**
     * @param recipients
     *            the recipients to set
     */
    public void setRecipients(String[] recipients) {
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
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
