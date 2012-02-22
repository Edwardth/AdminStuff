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

import java.util.Arrays;
import java.util.StringTokenizer;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.ASPlayer;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdTempBan extends AbstractCommand {

    private static final String[] examples = {"1d30h40m", "1d30h", "1d40m", "30h40m", "1d", "30h", "40m"};

    public cmdTempBan(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /tempban <Player> <Time><br>
     * Temporary ban a single player
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        tempBann(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        tempBann(args, console);
    }

    private void tempBann(String[] args, CommandSender sender) {
        Player target = PlayerUtils.getOnlinePlayer(args[0]);
        ASPlayer thisTarget = null;
        String targetName = null;
        // player is not online
        if (target == null) {
            targetName = PlayerUtils.getOfflinePlayerName(args[0]);
            // player doesn't exist in offline player
            if (targetName == null) {
                ChatUtils.writeInfo(sender, pluginName, "Es existiert kein Spieler '" + args[0] + "'! Er wurde vorrausschend dennoch gebannt!");
                thisTarget = Core.getOrCreateASPlayer(args[0].toLowerCase());
            }
            // found offline player
            else
                thisTarget = Core.getOrCreateASPlayer(targetName);
        }
        // player is online
        else {
            thisTarget = Core.getOrCreateASPlayer(target);
            targetName = target.getName();
        }

        int[] dates = parseString(" " + args[1].toLowerCase(), sender);
        // an error occured
        if (dates == null)
            return;

        thisTarget.setTempBanned(true);
        thisTarget.setBanEndTime(System.currentTimeMillis() + (24 * 60 * 60 * 1000 * dates[0]) + (60 * 60 * 1000 * dates[1]) + (60 * 1000 * dates[2]));
        thisTarget.saveConfig(false, false, false, false, true, false, false, false);
        String message = "gebannt fuer " + dates[0] + " Tage, " + dates[1] + " Stunden, " + dates[2] + " Minuten!";

        if (target != null)
            target.kickPlayer("Du bist " + message);
        ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + targetName + "' ist " + message);
    }

    private int[] parseString(String date, CommandSender sender) {
        // days, hours, minutes
        int[] result = new int[3];
        try {
            // split the string at 'h' OR 'm' OR 'd' and remaine the delimiter
            StringTokenizer st = new StringTokenizer(date, "[d,h,m]", true);
            // parsed integer
            int i = 0;
            // date identifier
            char c = 0;
            // parse string
            while (st.hasMoreTokens()) {
                i = Integer.parseInt(st.nextToken());
                c = st.nextToken().charAt(0);
                // assign date
                fillDates(result, c, i);
            }

        } catch (Exception e) {
            ChatUtils.writeError(sender, pluginName, "Falsche Syntax! Beispiele: ");
            showExamples(sender);
            return null;
        }
        // when all numbers are zero or negative
        if (result[0] < 1 && result[1] < 1 && result[2] < 1) {
            ChatUtils.writeError(sender, pluginName, Arrays.toString(result) + "sind ungueltige Eingabe! Eine Zahl muss wenigstens positiv ungleich null sein!");
            showExamples(sender);
            return null;
        }
        return result;
    }

    private void fillDates(int[] result, char c, int i) {

        switch (c) {
            case 'd' :
                result[0] = i;
                break;
            case 'h' :
                result[1] = i;
                break;
            case 'm' :
                result[2] = i;
                break;
        }
    }

    private void showExamples(CommandSender sender) {
        for (String example : examples)
            ChatUtils.writeInfo(sender, example);
    }
}
