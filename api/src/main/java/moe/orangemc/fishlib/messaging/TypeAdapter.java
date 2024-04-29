/*
 * FishLib, a Bukkit development library
 * Copyright (C) Astro angelfish
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.fishlib.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import moe.orangemc.fishlib.annotation.CanImplement;

/**
 * The type adapter
 * inspired by Gson
 * <br>
 * Default adapted types:
 * <ul>
 *     <li>{@link String}</li>
 *     <li>int</li>
 *     <li>long</li>
 *     <li>double</li>
 *     <li>float</li>
 *     <li>byte</li>
 *     <li>short</li>
 *     <li>boolean</li>
 *     <li>char</li>
 *     <li>byte[]</li>
 *     <li>int[]</li>
 *     <li>long[]</li>
 *     <li>double[]</li>
 *     <li>float[]</li>
 *     <li>short[]</li>
 *     <li>boolean[]</li>
 *     <li>char[]</li>
 *     <li>{@link java.util.UUID}</li>
 * </ul>
 *
 * @param <T> the type to be adapted
 */
@CanImplement
public interface TypeAdapter<T> {
	/**
	 * Writes an object to byte array stream
	 *
	 * @param target the object to be written
	 * @param output   the output stream being written to
	 */
	void writeToStream(T target, ByteArrayDataOutput output);

	/**
	 * Reads an object from byte array stream
	 *
	 * @param input the input stream being read
	 * @return the object read.
	 */
	T readFromStream(ByteArrayDataInput input);
}
