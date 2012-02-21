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

package de.minestar.AdminStuff.data;

public enum Time {

    //@formatter:off
    DAY     ("day",         0),
    TAG     ("tag",         0),
    NIGHT   ("night",   13000),
    NACHT   ("nacht",   13000);
    //@formatter:on

    private String name;
    private int dateTime;

    private Time(String name, int dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public int getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Time getTime(String timeName) {
        timeName = timeName.toLowerCase();
        for (Time t : values()) {
            if (t.getName().equals(timeName))
                return t;
        }

        return null;
    }

    public static String possibleNames() {
        Time[] times = values();
        if (times.length == 0)
            return "Es existieren keine Zeiten!";

        StringBuilder sBuilder = new StringBuilder(256);
        int i = 0;
        for (; i < times.length - 1; ++i) {
            sBuilder.append(times[i]);
            sBuilder.append(',');
        }
        sBuilder.append(times[i]);
        return sBuilder.toString();
    }
}
