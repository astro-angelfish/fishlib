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

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

/**
 * A factory for tropical fish buckets.
 *
 * @see TropicalFishBucketMeta
 */
public class TropicalFishBucketItemStackFactory extends AbstractItemStackFactory<TropicalFishBucketItemStackFactory> {
	protected TropicalFishBucketItemStackFactory(Material material) {
		super(material);
	}

	/**
	 * Set the pattern color of the tropical fish in the bucket.
	 * @param color The pattern color
	 * @return The factory itself
	 */
	public TropicalFishBucketItemStackFactory withPatternColor(DyeColor color) {
		this.addItemMetaPostHook("tropical_fish_bucket_pattern_color", (im) -> ((TropicalFishBucketMeta) im).setPatternColor(color));
		return this;
	}

	/**
	 * Set the body color of the tropical fish in the bucket.
	 * @param color The body color
	 * @return The factory itself
	 */
	public TropicalFishBucketItemStackFactory withBodyColor(DyeColor color) {
		this.addItemMetaPostHook("tropical_fish_bucket_body_color", (im) -> ((TropicalFishBucketMeta) im).setBodyColor(color));
		return this;
	}

	/**
	 * Set the pattern of the tropical fish in the bucket.
	 * @param pattern The pattern
	 * @return The factory itself
	 */
	public TropicalFishBucketItemStackFactory withPattern(TropicalFish.Pattern pattern) {
		this.addItemMetaPostHook("tropical_fish_bucket_pattern", (im) -> ((TropicalFishBucketMeta) im).setPattern(pattern));
		return this;
	}
}
