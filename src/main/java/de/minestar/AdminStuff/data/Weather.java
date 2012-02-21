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

public enum Weather {

    //@formatter:off
    SUN     ("sun",     false,  false),
    SONNE   ("sonne",   false,  false),
    RAIN    ("rain",    true,   false),
    REGEN   ("regen",   true,   false),
    STORM   ("storm", true,   true),
    STURM   ("sturm",   true,   true),

    // CUSTOM
    STRANGE ("strange", false,  true),
    SELTSAM ("seltsam", false,  true);
    //@formatter:on

    private String name;
    private boolean isStorming;
    private boolean isThundering;

    private Weather(String name, boolean isStorming, boolean isThundering) {
        this.name = name;
        this.isStorming = isStorming;
        this.isThundering = isThundering;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public boolean isStorming() {
        return isStorming;
    }

    public boolean isThundering() {
        return isThundering;
    }

    public static Weather getWeather(String weatherName) {
        weatherName = weatherName.toLowerCase();
        for (Weather weather : values())
            if (weather.getName().equals(weatherName))
                return weather;
        return null;
    }

    public static String possibleNames() {
        Weather[] weather = values();
        if (weather.length == 0)
            return "Es existiert kein Wetter oO";

        StringBuilder sBuilder = new StringBuilder(256);
        int i = 0;
        for (; i < weather.length - 1; ++i) {
            sBuilder.append(weather[i]);
            sBuilder.append(',');
        }
        sBuilder.append(weather[i]);
        return sBuilder.toString();
    }
}
