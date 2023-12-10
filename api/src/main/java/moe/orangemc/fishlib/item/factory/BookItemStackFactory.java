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

package moe.orangemc.fishlib.item.factory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for book items.
 *
 * @see BookMeta
 */
public class BookItemStackFactory extends AbstractItemStackFactory<BookItemStackFactory> {
	private List<String> pages = new ArrayList<>();

	protected BookItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the title of the book.
	 * @param title The title
	 * @return The factory itself
	 */
	public BookItemStackFactory withTitle(String title) {
		this.addItemMetaPostHook("book_title", (im) -> ((BookMeta) im).setTitle(title));
		return this;
	}

	/**
	 * Set the author of the book.
	 * @param author The author
	 * @return The factory itself
	 */
	public BookItemStackFactory withAuthor(String author) {
		this.addItemMetaPostHook("book_author", (im) -> ((BookMeta) im).setAuthor(author));
		return this;
	}

	/**
	 * Set the generation of the book.
	 * @param generation The generation
	 * @return The factory itself
	 */
	public BookItemStackFactory withGeneration(BookMeta.Generation generation) {
		this.addItemMetaPostHook("book_generation", (im) -> ((BookMeta) im).setGeneration(generation));
		return this;
	}

	/**
	 * Set the pages of the book.
	 * @param pages The pages
	 * @return The factory itself
	 */
	public BookItemStackFactory withPages(List<String> pages) {
		this.pages = pages;
		return this;
	}

	/**
	 * Add a page to the book.
	 * @param page The page
	 * @return The factory itself
	 */
	public BookItemStackFactory addPage(String page) {
		this.pages.add(page);
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("book_pages", (im) -> ((BookMeta) im).setPages(pages));
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("book_pages", (im) -> ((BookMeta) im).setPages(pages));
		return super.build();
	}
}
