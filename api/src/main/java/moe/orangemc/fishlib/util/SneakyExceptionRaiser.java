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

/**
 * A utility class to raise checked exceptions without throws declaration because always check a checked exception is annoying.
 * <br>
 * It abuses generics to bypass the compiler.
 * <br>
 * Abusing this is not recommended, but it is useful in some cases like I/O operations.
 */
public final class SneakyExceptionRaiser {
	private SneakyExceptionRaiser() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Raises a throwable.
	 * @param exception the throwable to raise
	 * @return never returns
	 * @param <T> the type of the throwable
	 * @param <R> the return type
	 * @throws T the throwable
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Throwable, R> R raise(Throwable exception) throws T {
		throw (T) exception;
	}

	/**
	 * Calls a callable that may throw a throwable.
	 * @param callableAnyThrow the callable
	 * @return the result
	 * @param <T> the type of the result
	 */
	public static <T> T anyCall(ThrowingCallable<T> callableAnyThrow) {
		try {
			return callableAnyThrow.call();
		} catch (Throwable e) {
			return raise(e);
		}
	}

	/**
	 * Calls a callable that may throw a throwable.
	 * @param supplier the supplier that may throw a throwable
	 * @param consumer the consumer
	 * @param <T> the {@link AutoCloseable} type
	 */
	public static <T extends AutoCloseable> void autoClosableCall(ThrowingSupplier<T> supplier, ThrowingConsumer<T> consumer) {
		try (T t = supplier.get()) {
			consumer.accept(t);
		} catch (Throwable t) {
			raise(t);
		}
	}

	/**
	 * Calls a runnable that may throw a throwable.
	 * @param action the runnable
	 */
	public static void voidCall(ThrowingRunnable action) {
		try {
			action.run();
		} catch (Throwable e) {
			raise(e);
		}
	}
}
