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

package de.minestar.AdminStuff.commands;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.ASCore;
import de.minestar.AdminStuff.ASPlayer;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdClassic extends AbstractExtendedCommand {

    public cmdClassic(String syntax, String arguments, String node) {
        super(ASCore.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /classic <br>
     * Toggle the classicmode
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        if (args.length == 0)
            changeMode(player, player.getName());
        else
            changeMode(player, args);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        if (args.length == 0)
            ConsoleUtils.printError(pluginName, "You are God, why do you want to activate classic mode?");
        else
            changeMode(console, args);

    }
    private void changeMode(CommandSender sender, String... targetNames) {

        Player target = null;
        ASPlayer thisPlayer = null;
        boolean isClassic = false;
        for (String targetName : targetNames) {
            target = PlayerUtils.getOnlinePlayer(targetName);
            if (target == null) {
                ChatUtils.writeError(target, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden oder ist offline!");
                return;
            }

            // ADD PLAYER, IF NOT FOUND
            thisPlayer = ASCore.getOrCreateASPlayer(targetName);
            isClassic = thisPlayer.isClassicMode();

            // Flip modus
            thisPlayer.setClassicMode(!isClassic);
            thisPlayer.saveConfig(true, false, false, false, false, false, false, true);

            if (isClassic) {
                target.setGameMode(GameMode.CREATIVE);
                ChatUtils.writeSuccess(target, pluginName, "Du bist nun im Creative Modus!");
            } else {
                ChatUtils.writeSuccess(target, pluginName, "Du bist nun im Survival Modus!");
                target.setGameMode(GameMode.SURVIVAL);
            }
        }
    }
}
