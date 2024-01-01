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

package moe.orangemc.fishlib.util;

import java.util.function.Function;

/**
 * Math utilities that {@link java.lang.Math} does not provide.
 */
public final class MathUtil {
	private MathUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if the given number is in the range.
	 * @param toCheck the number to check
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return true if the number is in the range
	 */
	public static boolean isInRange(int toCheck, int min, int max) {
		return toCheck >= min && toCheck <= max;
	}

	/**
	 * Performs bisection to find the root of the given function.
	 * <br>
	 * The tolerance of the error is 1e-6.
	 *
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param function the function
	 */
	public static double performBisection(double min, double max, Function<Double, Double> function) {
		return performBisection(min, max, function, 1e-6);
	}

	/**
	 * Performs bisection to find the root of the given function.
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param function the function
	 * @param epsilon the maximum tolerance
	 */
	public static double performBisection(double min, double max, Function<Double, Double> function, double epsilon) {
		double mid = (min + max) / 2;
		while (max - min > epsilon) {
			mid = (min + max) / 2;
			double result = function.apply(mid);
			if (result > 0) {
				max = mid;
			} else if (result < 0) {
				min = mid;
			} else {
				return mid;
			}
		}
		return mid;
	}
}
