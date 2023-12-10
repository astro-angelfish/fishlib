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
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for banners.
 *
 * @see BannerMeta
 */
public class BannerItemStackFactory extends AbstractItemStackFactory<BannerItemStackFactory> {
	private List<Pattern> patterns = new ArrayList<>();

	protected BannerItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Add a pattern to the banner.
	 * @param pattern The pattern to add
	 * @return The factory itself
	 */
	public BannerItemStackFactory addPattern(Pattern pattern) {
		patterns.add(pattern);
		return this;
	}

	/**
	 * Set the patterns of the banner.
	 * @param patterns The patterns
	 * @return The factory itself
	 */
	public BannerItemStackFactory withPatterns(List<Pattern> patterns) {
		this.patterns = patterns;
		return this;
	}

	@Override
	public ItemStackFactory toGeneric() {
		return super.toGeneric().addItemMetaPostHook("banner_pattern", (im) -> ((BannerMeta) im).setPatterns(patterns));
	}

	@Override
	public ItemStack build() {
		this.addItemMetaPostHook("banner_pattern", (im) -> ((BannerMeta) im).setPatterns(patterns));
		return super.build();
	}
}
