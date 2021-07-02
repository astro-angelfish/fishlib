/*
 * Plugin Commons, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.plugincommons.inventory.control.handler;

import moe.orangemc.plugincommons.inventory.control.Button;
import org.bukkit.entity.Player;

/**
 * The handler handles the button events.
 */
public interface ButtonClickHandler extends ControlHandler {
    /**
     * Calls when a player clicks that button
     * @param whoClicked the player clicked the button
     * @param buttonClicked the button clicked.
     */
    void onClick(Player whoClicked, Button buttonClicked);
}
