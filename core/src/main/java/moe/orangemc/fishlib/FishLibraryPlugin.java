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

package moe.orangemc.fishlib;

import moe.orangemc.fishlib.service.FishLibraryService;
import moe.orangemc.fishlib.service.FishLibraryServiceImpl;
import moe.orangemc.fishlib.listener.PluginListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class FishLibraryPlugin extends JavaPlugin {
	private static FishLibraryPlugin instance;
	private static FishLibraryServiceImpl fishLibraryService;

	@Override
	public void onLoad() {
		instance = this;
		fishLibraryService = new FishLibraryServiceImpl();
		FishLibrary.setFishLibraryService(fishLibraryService);
		Bukkit.getServicesManager().register(FishLibraryService.class, fishLibraryService, this, ServicePriority.Highest);
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PluginListener(), this);
	}

	public static FishLibraryPlugin getInstance() {
		return instance;
	}

	public static FishLibraryServiceImpl getFishLibraryService() {
		return fishLibraryService;
	}
}
