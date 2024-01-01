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

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Advanced lazy reference.
 * <br>
 * Initiates the value when {@link #get()} is called and the value is null or invalid.
 * @param <T> the type of the value
 */
public class LazyReference<T> {
	private final Supplier<T> supplier;
	private final Function<T, Boolean> validator;
	private T value;

	/**
	 * Creates a new lazy reference.
	 * @param supplier the supplier to supply the value
	 */
	public LazyReference(Supplier<T> supplier) {
		this(supplier, Objects::nonNull);
	}

	/**
	 * Creates a new lazy reference.
	 * @param supplier the supplier to supply the value
	 * @param validator the validator to validate the value
	 */
	public LazyReference(Supplier<T> supplier, Function<T, Boolean> validator) {
		this.supplier = supplier;
		this.validator = validator;
	}

	/**
	 * Gets the value.
	 * @return the value
	 */
	public T get() {
		if (validator.apply(value)) {
			value = supplier.get();
		}
		return value;
	}
}
