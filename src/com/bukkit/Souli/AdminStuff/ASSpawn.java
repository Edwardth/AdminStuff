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
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class ASSpawn {
    public static HashMap<String, Location> spawns = new HashMap<String, Location>();

    /**
     * SET SPAWN
     * 
     * @param world
     * @param loc
     */
    public static void setSpawn(World world, Location loc) {
        spawns.put(world.getName(), loc);
        world.setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        saveSpawnFile(world, loc);
    }

    /**
     * GET SPAWN
     * 
     * @param world
     * @return Location of the spawn
     */
    public static Location getSpawn(World world) {
        if (!spawns.containsKey(world.getName()))
            return world.getSpawnLocation();

        Location loc = world.getSpawnLocation();
        loc.setYaw(spawns.get(world.getName()).getYaw());
        loc.setPitch(spawns.get(world.getName()).getPitch());
        return loc;
    }

    /**
     * SAVE SPAWNFILE
     * 
     * @param world
     * @param loc
     */
    public static void saveSpawnFile(World world, Location loc) {
        new File("plugins/AdminStuff/spawns/").mkdirs();
        Configuration config = new Configuration(new File("plugins/AdminStuff/spawns/" + world.getName() + ".yml"));
        config.setProperty("X", loc.getBlockX());
        config.setProperty("Y", loc.getBlockY());
        config.setProperty("Z", loc.getBlockZ());
        config.setProperty("Pitch", loc.getPitch());
        config.setProperty("Yaw", loc.getYaw());
        config.save();
    }

    /**
     * LOAD ALL SPAWNFILES
     */
    public static void loadAllSpawns() {
        File folder = new File("plugins/AdminStuff/spawns/");
        folder.mkdirs();

        File[] files = folder.listFiles();

        for (File file : files) {
            if (!file.isFile())
                continue;
            if (file.getName().endsWith(".yml")) {
                loadSpawnFile(file.getName().replace(".yml", ""));
            }
        }
        ASCore.log.printInfo(spawns.size() + " worldspawns loaded.");
    }

    /**
     * LOAD SINGLE SPAWNFILE
     * 
     * @param worldName
     */
    public static void loadSpawnFile(String worldName) {
        new File("plugins/AdminStuff/spawns/").mkdirs();
        Configuration config = new Configuration(new File("plugins/AdminStuff/spawns/" + worldName + ".yml"));
        config.load();

        World world = ASCore.getMCServer().getWorld(worldName);
        if (world == null)
            world = ASCore.getMCServer().getWorlds().get(0);

        Location loc = new Location(world,
                config.getInt("X", 0),
                config.getInt("Y", 127),
                config.getInt("Z", 0),
                config.getInt("Yaw", 0),
                config.getInt("Pitch", 0));

        spawns.put(worldName, loc);
    }
}
