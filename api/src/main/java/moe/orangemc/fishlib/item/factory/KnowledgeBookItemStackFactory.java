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
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.KnowledgeBookMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for knowledge books.
 *
 * @see KnowledgeBookMeta
 */
public class KnowledgeBookItemStackFactory extends AbstractItemStackFactory<KnowledgeBookItemStackFactory> {
	private List<NamespacedKey> recipes = new ArrayList<>();

	protected KnowledgeBookItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the recipes of the knowledge book.
	 *
	 * @param recipes The recipes
	 * @return The factory itself
	 */
	public KnowledgeBookItemStackFactory withRecipes(List<NamespacedKey> recipes) {
		this.recipes = recipes;
		return this;
	}

	/**
	 * Add a recipe to the knowledge book.
	 *
	 * @param recipe The recipe to add
	 * @return The factory itself
	 */
	public KnowledgeBookItemStackFactory addRecipe(NamespacedKey recipe) {
		this.recipes.add(recipe);
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("knowloedge_book_recipes", (im) -> ((KnowledgeBookMeta) im).setRecipes(recipes));
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("knowloedge_book_recipes", (im) -> ((KnowledgeBookMeta) im).setRecipes(recipes));
		return super.build();
	}
}
