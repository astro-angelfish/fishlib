/*
 * Plugin Commons, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.luckyfish.plugincommons.item;

import org.bukkit.Material;

/**
 * Material helper.
 */
public final class MaterialUtil {
	private MaterialUtil() {
		throw new UnsupportedOperationException();
	}

    /**
     * Check if the material is liquid.
     * @param material The material to be checked.
     * @return true if this material is liquid.
     */
    public static boolean isLiquid(Material material) {
        switch (material) {
            case WATER:
            case LAVA:
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if entities can walk through this material.
     * @param material The material to be checked.
     * @return true if entities can walk through this material.
     */
    public static boolean canWalkThrough(Material material) {
        switch (material) {
            // <editor-fold defaultstate="collapsed" desc="canWalkThrough">
            case ACACIA_BUTTON:
            case ACACIA_SAPLING:
            case ACACIA_SIGN:
            case ACACIA_WALL_SIGN:
            case ACTIVATOR_RAIL:
            case AIR:
            case ALLIUM:
            case ATTACHED_MELON_STEM:
            case ATTACHED_PUMPKIN_STEM:
            case AZURE_BLUET:
            case BAMBOO_SAPLING:
            case BEETROOTS:
            case BIRCH_BUTTON:
            case BIRCH_SAPLING:
            case BIRCH_SIGN:
            case BIRCH_WALL_SIGN:
            case BLACK_BANNER:
            case BLACK_WALL_BANNER:
            case BLUE_BANNER:
            case BLUE_ORCHID:
            case BLUE_WALL_BANNER:
            case BRAIN_CORAL_FAN:
            case BRAIN_CORAL_WALL_FAN:
            case BROWN_BANNER:
            case BROWN_MUSHROOM:
            case BROWN_WALL_BANNER:
            case BUBBLE_CORAL_FAN:
            case BUBBLE_CORAL_WALL_FAN:
            case CARROTS:
            case CAVE_AIR:
            case CORNFLOWER:
            case CYAN_BANNER:
            case CYAN_WALL_BANNER:
            case DANDELION:
            case DEAD_BRAIN_CORAL_FAN:
            case DEAD_BRAIN_CORAL_WALL_FAN:
            case DEAD_FIRE_CORAL_FAN:
            case DEAD_FIRE_CORAL_WALL_FAN:
            case DEAD_BUBBLE_CORAL_FAN:
            case DEAD_BUBBLE_CORAL_WALL_FAN:
            case DEAD_HORN_CORAL_FAN:
            case DEAD_HORN_CORAL_WALL_FAN:
            case DEAD_TUBE_CORAL_FAN:
            case DEAD_TUBE_CORAL_WALL_FAN:
            case DETECTOR_RAIL:
            case FERN:
            case GRASS:
            case GRAY_BANNER:
            case GRAY_WALL_BANNER:
            case GREEN_BANNER:
            case GREEN_WALL_BANNER:
            case HORN_CORAL_FAN:
            case HORN_CORAL_WALL_FAN:
            case JUNGLE_BUTTON:
            case JUNGLE_SAPLING:
            case JUNGLE_SIGN:
            case JUNGLE_WALL_SIGN:
            case KELP_PLANT:
            case LIGHT_BLUE_BANNER:
            case LIGHT_BLUE_WALL_BANNER:
            case LIGHT_GRAY_BANNER:
            case LEVER:
            case LIGHT_GRAY_WALL_BANNER:
            case MAGENTA_BANNER:
            case MAGENTA_WALL_BANNER:
            case MOVING_PISTON:
            case OAK_BUTTON:
            case OAK_SAPLING:
            case OAK_SIGN:
            case OAK_WALL_SIGN:
            case ORANGE_BANNER:
            case ORANGE_TULIP:
            case ORANGE_WALL_BANNER:
            case PEONY:
            case PINK_BANNER:
            case PINK_TULIP:
            case PINK_WALL_BANNER:
            case RAIL:
            case RED_BANNER:
            case RED_MUSHROOM:
            case RED_TULIP:
            case RED_WALL_BANNER:
            case SCAFFOLDING:
            case SEAGRASS:
            case SPRUCE_SAPLING:
            case SPRUCE_SIGN:
            case SPRUCE_WALL_SIGN:
            case SPRUCE_BUTTON:
            case TALL_GRASS:
            case TALL_SEAGRASS:
            case TUBE_CORAL_FAN:
            case TUBE_CORAL_WALL_FAN:
            case VINE:
            case VOID_AIR:
            case WALL_TORCH:
            case TORCH:
            case YELLOW_BANNER:
            case YELLOW_WALL_BANNER:
            case STONE_BUTTON:
            case FIRE_CORAL_FAN:
            case FIRE_CORAL_WALL_FAN:
                // </editor-fold>
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if this material can make up restone circuits.
     * @param material The material to be checked.
     * @return true if this material can make up redstone circuits.
     */
    public static boolean isRedstoneBlock(Material material) {
        switch (material) {
            // <editor-fold defaultstate="collapsed" desc="isRedstoneBlock">
            case REDSTONE_WIRE:
            case REDSTONE_WALL_TORCH:
            case REDSTONE_LAMP:
            case OBSERVER:
            case PISTON:
            case PISTON_HEAD:
            case SLIME_BLOCK:
            case HONEY_BLOCK:
            case STICKY_PISTON:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case OAK_BUTTON:
            case SPRUCE_BUTTON:
            case STONE_BUTTON:
            case REPEATER:
            case COMPARATOR:
            case TNT:
            case POWERED_RAIL:
            case DETECTOR_RAIL:
            case DAYLIGHT_DETECTOR:
            case DROPPER:
            case DISPENSER:
            case HOPPER:
            case TRIPWIRE:
            case TRIPWIRE_HOOK:
                // </editor-fold>
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if this material can replaced by water or lava
     * @param material the material to be checked
     * @return true if this material can replaced by liquid
     */
    public static boolean canReplaceByLiquid(Material material) {
        switch (material) {
            // <editor-fold defaultstate="collapsed" desc="canReplaceByLiquid">
            case AIR:
            case CAVE_AIR:
            case BLACK_CARPET:
            case BLUE_CARPET:
            case BROWN_CARPET:
            case CYAN_CARPET:
            case GRAY_CARPET:
            case GREEN_CARPET:
            case LIGHT_BLUE_CARPET:
            case LIGHT_GRAY_CARPET:
            case LIME_CARPET:
            case MAGENTA_CARPET:
            case ORANGE_CARPET:
            case PINK_CARPET:
            case PURPLE_CARPET:
            case RED_CARPET:
            case WHITE_CARPET:
            case YELLOW_CARPET:
            case ROSE_BUSH:
            case DANDELION:
            case CORNFLOWER:
            case TORCH:
            case STONE_BUTTON:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case OAK_BUTTON:
            case SPRUCE_BUTTON:
            case ORANGE_TULIP:
            case PINK_TULIP:
            case POTTED_ORANGE_TULIP:
            case POTTED_PINK_TULIP:
            case POTTED_RED_TULIP:
            case POTTED_WHITE_TULIP:
            case RED_TULIP:
            case WHITE_TULIP:
            case POTTED_ACACIA_SAPLING:
            case POTTED_ALLIUM:
            case POTTED_AZURE_BLUET:
            case POTTED_BAMBOO:
            case POTTED_BIRCH_SAPLING:
            case POTTED_BLUE_ORCHID:
            case POTTED_BROWN_MUSHROOM:
            case POTTED_CACTUS:
            case POTTED_CORNFLOWER:
            case POTTED_DANDELION:
            case POTTED_DARK_OAK_SAPLING:
            case POTTED_DEAD_BUSH:
            case POTTED_FERN:
            case POTTED_JUNGLE_SAPLING:
            case POTTED_LILY_OF_THE_VALLEY:
            case POTTED_OAK_SAPLING:
            case POTTED_OXEYE_DAISY:
            case POTTED_POPPY:
            case POTTED_RED_MUSHROOM:
            case POTTED_SPRUCE_SAPLING:
            case POTTED_WITHER_ROSE:
            case ACACIA_SAPLING:
            case BAMBOO_SAPLING:
            case BIRCH_SAPLING:
            case DARK_OAK_SAPLING:
            case JUNGLE_SAPLING:
            case OAK_SAPLING:
            case SPRUCE_SAPLING:
            case AZURE_BLUET:
            case ALLIUM:
            case DEAD_BUSH:
            case FERN:
            case LARGE_FERN:
            case LILY_OF_THE_VALLEY:
            case LILY_PAD:
            case OXEYE_DAISY:
            case POPPY:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case WITHER_ROSE:
            case TALL_GRASS:
            case SUNFLOWER:
            case LILAC:
            case PEONY:
            case CREEPER_HEAD:
            case CREEPER_WALL_HEAD:
            case DRAGON_HEAD:
            case DRAGON_WALL_HEAD:
            case PLAYER_HEAD:
            case PLAYER_WALL_HEAD:
            case ZOMBIE_HEAD:
            case ZOMBIE_WALL_HEAD:
            case SKELETON_SKULL:
            case SKELETON_WALL_SKULL:
            case WITHER_SKELETON_SKULL:
            case WITHER_SKELETON_WALL_SKULL:
            case WHEAT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case SWEET_BERRY_BUSH:
            case REDSTONE_TORCH:
            case REDSTONE_WIRE:
            case LEVER:
            case COMPARATOR:
            case REPEATER:
            case TRIPWIRE_HOOK:
            case TRIPWIRE:
            case RAIL:
            case ACTIVATOR_RAIL:
            case DETECTOR_RAIL:
            case POWERED_RAIL:
            case COBWEB:
            case NETHER_WART:
            case ATTACHED_MELON_STEM:
            case ATTACHED_PUMPKIN_STEM:
            case MELON_STEM:
            case PUMPKIN_STEM:
            case COCOA:
            case END_ROD:
            case SNOW:
            case VINE:
                // </editor-fold>
                return true;
            default:
                return false;
        }
    }
}
