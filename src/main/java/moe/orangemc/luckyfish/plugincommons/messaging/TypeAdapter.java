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

package moe.orangemc.luckyfish.plugincommons.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

/**
 * The type adapter
 * inspired by Gson
 */
public interface TypeAdapter<T> {
    /**
     * Writes an object to byte array stream
     * @param target the object to be written
     * @param bado the output stream being written to
     */
    void writeToStream(T target, ByteArrayDataOutput bado);

    /**
     * Reads an object from byte array stream
     * @param badi the input stream being read
     * @return the object read.
     */
    T readFromStream(ByteArrayDataInput badi);
}
