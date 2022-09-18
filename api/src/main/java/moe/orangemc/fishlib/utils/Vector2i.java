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

package moe.orangemc.fishlib.utils;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * A two-dimension and integer version vector
 */
@ShouldNotBeImplemented
public final class Vector2i implements Cloneable {
	private int x;
	private int y;

	/**
	 * Constructs a new 2 dimension vector
	 * @param x the x position
	 * @param y the y position
	 */
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get x position of the vector
	 * @return the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get y position of the vector
	 * @return the y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set x position of the vector
	 * @param x the x position
	 * @return the vector itself
	 */
	public Vector2i setX(int x) {
		Validate.isTrue(Double.isFinite(x), "x must be a valid number");
		this.x = x;
		return this;
	}

	/**
	 * Set y position of the vector
	 * @param y the y position
	 * @return the vector itself
	 */
	public Vector2i setY(int y) {
		Validate.isTrue(Double.isFinite(y), "y must be a valid number");
		this.y = y;
		return this;
	}

	/**
	 * Adds two positions to the vector
	 * @param x the x position
	 * @param y the y position
	 * @return the vector itself
	 */
	public Vector2i add(int x, int y) {
		Validate.isTrue(x >= 0, "x must greater than zero");
		Validate.isTrue(y >= 0, "y must greater than zero");

		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Subtracts to position from the vector
	 * @param x the x position
	 * @param y the y position
	 * @return the vector itself
	 */
	public Vector2i subtract(int x, int y) {
		Validate.isTrue(x >= 0, "x must greater than zero");
		Validate.isTrue(y >= 0, "y must greater than zero");

		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * Multiply this vector with the scale
	 * @param scale the scale
	 * @return the vector itself
	 */
	public Vector2i multiply(int scale) {
		this.x *= scale;
		this.y *= scale;
		return this;
	}

	/**
	 * Divide this vector by the scale
	 * @param scale the scale
	 * @return the vector itself
	 */
	public Vector2i divide(int scale) {
		this.x /= scale;
		this.y /= scale;
		return this;
	}

	/**
	 * Adds this vector with another vector
	 * @param another another vector to add
	 * @return the vector itself
	 */
	public Vector2i add(Vector2i another) {
		Validate.notNull(another, "Another vector to add cannot be null.");
		this.x += another.getX();
		this.y += another.getY();
		return this;
	}

	/**
	 * Subtracts this vector with another vector
	 * @param another another vector to subtract
	 * @return the vector itself
	 */
	public Vector2i subtract(Vector2i another) {
		Validate.notNull(another, "Another vector to subtract cannot be null.");
		this.x -= another.getX();
		this.y -= another.getY();
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector2i that = (Vector2i) o;
		return getX() == that.getX() && getY() == that.getY();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), "Vector2i", "By Astro angelfish");
	}

	@Override
	public String toString() {
		return "Vector2i{" + "x=" + x + ", y=" + y + '}';
	}

	public Vector2i clone() {
		return new Vector2i(x, y);
	}
}
