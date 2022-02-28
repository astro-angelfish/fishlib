/*
 * FishLib, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
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

package moe.orangemc.fishlib.item;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;

import org.bukkit.Material;

/**
 * Material helper.
 */
@ShouldNotBeImplemented
public final class MaterialUtil {
	private MaterialUtil() {
		throw new AssertionError("Well you can not access this.");
	}

    /**
     * Check if the material is liquid.
     * @param material The material to be checked.
     * @return true if this material is liquid.
     */
    public static boolean isLiquid(Material material) {
		return switch(material) {
			case WATER, LAVA -> true;
			default -> false;
		};
    }

    /**
     * Check if entities can walk through this material.
     * @param material The material to be checked.
     * @return true if entities can walk through this material.
     */
    public static boolean canWalkThrough(Material material) {
	    return switch (material) {
		    // <editor-fold defaultstate="collapsed" desc="canWalkThrough">
		    case ACACIA_BUTTON, ACACIA_SAPLING, ACACIA_SIGN, ACACIA_WALL_SIGN, ACTIVATOR_RAIL, AIR, ALLIUM, ATTACHED_MELON_STEM, ATTACHED_PUMPKIN_STEM, AZURE_BLUET, BAMBOO_SAPLING, BEETROOTS, BIRCH_BUTTON, BIRCH_SAPLING, BIRCH_SIGN, BIRCH_WALL_SIGN, BLACK_BANNER, BLACK_WALL_BANNER, BLUE_BANNER, BLUE_ORCHID, BLUE_WALL_BANNER, BRAIN_CORAL_FAN, BRAIN_CORAL_WALL_FAN, BROWN_BANNER, BROWN_MUSHROOM, BROWN_WALL_BANNER, BUBBLE_CORAL_FAN, BUBBLE_CORAL_WALL_FAN, CARROTS, CAVE_AIR, CORNFLOWER, CYAN_BANNER, CYAN_WALL_BANNER, DANDELION, DEAD_BRAIN_CORAL_FAN, DEAD_BRAIN_CORAL_WALL_FAN, DEAD_FIRE_CORAL_FAN, DEAD_FIRE_CORAL_WALL_FAN, DEAD_BUBBLE_CORAL_FAN, DEAD_BUBBLE_CORAL_WALL_FAN, DEAD_HORN_CORAL_FAN, DEAD_HORN_CORAL_WALL_FAN, DEAD_TUBE_CORAL_FAN, DEAD_TUBE_CORAL_WALL_FAN, DETECTOR_RAIL, FERN, GRASS, GRAY_BANNER, GRAY_WALL_BANNER, GREEN_BANNER, GREEN_WALL_BANNER, HORN_CORAL_FAN, HORN_CORAL_WALL_FAN, JUNGLE_BUTTON, JUNGLE_SAPLING, JUNGLE_SIGN, JUNGLE_WALL_SIGN, KELP_PLANT, LIGHT_BLUE_BANNER, LIGHT_BLUE_WALL_BANNER, LIGHT_GRAY_BANNER, LEVER, LIGHT_GRAY_WALL_BANNER, MAGENTA_BANNER, MAGENTA_WALL_BANNER, MOVING_PISTON, OAK_BUTTON, OAK_SAPLING, OAK_SIGN, OAK_WALL_SIGN, ORANGE_BANNER, ORANGE_TULIP, ORANGE_WALL_BANNER, PEONY, PINK_BANNER, PINK_TULIP, PINK_WALL_BANNER, RAIL, RED_BANNER, RED_MUSHROOM, RED_TULIP, RED_WALL_BANNER, SCAFFOLDING, SEAGRASS, SPRUCE_SAPLING, SPRUCE_SIGN, SPRUCE_WALL_SIGN, SPRUCE_BUTTON, TALL_GRASS, TALL_SEAGRASS, TUBE_CORAL_FAN, TUBE_CORAL_WALL_FAN, VINE, VOID_AIR, WALL_TORCH, TORCH, YELLOW_BANNER, YELLOW_WALL_BANNER, STONE_BUTTON, FIRE_CORAL_FAN, FIRE_CORAL_WALL_FAN ->
				    // </editor-fold>
				    true;
		    default -> false;
	    };
    }

    /**
     * Check if this material can make up restone circuits.
     * @param material The material to be checked.
     * @return true if this material can make up redstone circuits.
     */
    public static boolean isRedstoneBlock(Material material) {
	    return switch (material) {
		    // <editor-fold defaultstate="collapsed" desc="isRedstoneBlock">
		    case REDSTONE_WIRE, REDSTONE_WALL_TORCH, REDSTONE_LAMP, OBSERVER, PISTON, PISTON_HEAD, SLIME_BLOCK, HONEY_BLOCK, STICKY_PISTON, ACACIA_BUTTON, BIRCH_BUTTON, DARK_OAK_BUTTON, JUNGLE_BUTTON, OAK_BUTTON, SPRUCE_BUTTON, STONE_BUTTON, REPEATER, COMPARATOR, TNT, POWERED_RAIL, DETECTOR_RAIL, DAYLIGHT_DETECTOR, DROPPER, DISPENSER, HOPPER, TRIPWIRE, TRIPWIRE_HOOK ->
				    // </editor-fold>
				    true;
		    default -> false;
	    };
    }

    /**
     * Check if this material can replaced by water or lava
     * @param material the material to be checked
     * @return true if this material can replaced by liquid
     */
    public static boolean canReplacedByLiquid(Material material) {
	    return switch (material) {
		    // <editor-fold defaultstate="collapsed" desc="canReplaceByLiquid">
		    case AIR, CAVE_AIR, BLACK_CARPET, BLUE_CARPET, BROWN_CARPET, CYAN_CARPET, GRAY_CARPET, GREEN_CARPET, LIGHT_BLUE_CARPET, LIGHT_GRAY_CARPET, LIME_CARPET, MAGENTA_CARPET, ORANGE_CARPET, PINK_CARPET, PURPLE_CARPET, RED_CARPET, WHITE_CARPET, YELLOW_CARPET, ROSE_BUSH, DANDELION, CORNFLOWER, TORCH, STONE_BUTTON, ACACIA_BUTTON, BIRCH_BUTTON, DARK_OAK_BUTTON, JUNGLE_BUTTON, OAK_BUTTON, SPRUCE_BUTTON, ORANGE_TULIP, PINK_TULIP, POTTED_ORANGE_TULIP, POTTED_PINK_TULIP, POTTED_RED_TULIP, POTTED_WHITE_TULIP, RED_TULIP, WHITE_TULIP, POTTED_ACACIA_SAPLING, POTTED_ALLIUM, POTTED_AZURE_BLUET, POTTED_BAMBOO, POTTED_BIRCH_SAPLING, POTTED_BLUE_ORCHID, POTTED_BROWN_MUSHROOM, POTTED_CACTUS, POTTED_CORNFLOWER, POTTED_DANDELION, POTTED_DARK_OAK_SAPLING, POTTED_DEAD_BUSH, POTTED_FERN, POTTED_JUNGLE_SAPLING, POTTED_LILY_OF_THE_VALLEY, POTTED_OAK_SAPLING, POTTED_OXEYE_DAISY, POTTED_POPPY, POTTED_RED_MUSHROOM, POTTED_SPRUCE_SAPLING, POTTED_WITHER_ROSE, ACACIA_SAPLING, BAMBOO_SAPLING, BIRCH_SAPLING, DARK_OAK_SAPLING, JUNGLE_SAPLING, OAK_SAPLING, SPRUCE_SAPLING, AZURE_BLUET, ALLIUM, DEAD_BUSH, FERN, LARGE_FERN, LILY_OF_THE_VALLEY, LILY_PAD, OXEYE_DAISY, POPPY, BROWN_MUSHROOM, RED_MUSHROOM, WITHER_ROSE, TALL_GRASS, SUNFLOWER, LILAC, PEONY, CREEPER_HEAD, CREEPER_WALL_HEAD, DRAGON_HEAD, DRAGON_WALL_HEAD, PLAYER_HEAD, PLAYER_WALL_HEAD, ZOMBIE_HEAD, ZOMBIE_WALL_HEAD, SKELETON_SKULL, SKELETON_WALL_SKULL, WITHER_SKELETON_SKULL, WITHER_SKELETON_WALL_SKULL, WHEAT, CARROTS, POTATOES, BEETROOTS, SWEET_BERRY_BUSH, REDSTONE_TORCH, REDSTONE_WIRE, LEVER, COMPARATOR, REPEATER, TRIPWIRE_HOOK, TRIPWIRE, RAIL, ACTIVATOR_RAIL, DETECTOR_RAIL, POWERED_RAIL, COBWEB, NETHER_WART, ATTACHED_MELON_STEM, ATTACHED_PUMPKIN_STEM, MELON_STEM, PUMPKIN_STEM, COCOA, END_ROD, SNOW, VINE ->
				    // </editor-fold>
				    true;
		    default -> false;
	    };
    }
}
