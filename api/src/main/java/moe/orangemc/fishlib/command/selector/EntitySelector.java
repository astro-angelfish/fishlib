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

package moe.orangemc.fishlib.command.selector;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import moe.orangemc.fishlib.command.selector.util.EntityHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.function.Predicate;

/**
 * Entity selector like vanilla one.
 *
 * Originally from <a href="https://github.com/czm23333/IndustrialWorld/blob/master/src/main/java/com/industrialworld/command/selector/EntitySelector.java">...</a>
 * with a little changes.
 * Anyway it's still my code.
 * The quality of these code is poor so I'll optimize them soon.
 * NPath=2147483647
 *
 * At least czm23333 allowed me to use these code in our project.
 */
@ShouldNotBeImplemented
public final class EntitySelector {
	private final CommandSender sender;
	private final List<Entity> selectedEntities = new LinkedList<>();
	private Location baseLoc;
	private Sort sortMethod;
	private final List<Predicate<Entity>> allPredicates = new LinkedList<>();
	private final Map<String, String> predicateStringPair = new HashMap<>();
	private int limit;

	/**
	 * Constructs an entity selector
	 * @param sender the sender executes the command
	 * @param selector the selector the sender used.
	 */
	public EntitySelector(CommandSender sender, String selector) {
		this.sender = sender;
		try {
			selectedEntities.add(Bukkit.getEntity(UUID.fromString(selector)));
		} catch (IllegalArgumentException e) {
			// uuid seems to be invalid
			Player player = Bukkit.getPlayerExact(selector);
			if (player == null) {
				// ok, now we have to analyze the selector.
				parseSelector(selector);
			} else {
				selectedEntities.add(player);
			}
		}
	}

	private void parseSelector(String selector) {
		if (!selector.startsWith("@")) {
			return;
		}
		if (sender instanceof Entity) {
			baseLoc = ((Entity) sender).getLocation();
		} else {
			baseLoc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
		}

		char target;
		try {
			target = selector.charAt(1);
		} catch (IndexOutOfBoundsException e) {
			return;
		}

		sortMethod = target == 'r' ? Sort.RANDOM : (target == 'p' ? Sort.NEAREST : Sort.ARBITRARY);

		String args = selector.substring(2).trim();

		// there is not arguments.
		if (args.isEmpty()) {
			List<Entity> entities = getEntities(sortMethod);
			if (target == 'r' || target == 'p') {
				for (Entity e : entities) {
					if (e instanceof Player) {
						selectedEntities.add(e);
						break;
					}
				}
			} else if (target == 'a') {
				entities.removeIf((e) -> !(e instanceof Player));
			}
			selectedEntities.addAll(entities);

			return;
		}
		// check selector syntax.
		if (!args.matches("^\\[(.+=[0-9a-zA-Z.\\-\\\\?!@#$%^&*()=+\\[\\];':\"|/" +
						">_<" +
						"]*,?)*(.+=[0-9a-zA-Z.\\-\\\\?!@#$%^&*()=+\\[\\];':\"|/" +
						">_<" +
						"]*)+]$")) {
			sender.sendMessage(ChatColor.RED + "Wrong syntax for selector.");
			return;
		}

		// parse selector pairs.
		String[] parsing = args.replaceAll("\\[", "").replaceAll("]", "").split(",");
		for (String predicateString : parsing) {
			String[] slices = predicateString.split("=", 2);
			predicateStringPair.put(slices[0], slices[1]); // name and value
		}

		// ok and let's start parsing.
		try {
			createPredicates();

			if (predicateStringPair.containsKey("limit")) {
				limit = Integer.parseInt(predicateStringPair.get("limit"));
			} else if (target == 'r' || target == 'p') {
				limit = 1;
			} else {
				limit = Integer.MAX_VALUE;
			}
			if (predicateStringPair.containsKey("sort")) {
				sortMethod = Sort.valueOf(predicateStringPair.get("sort").toUpperCase());
			}
		} catch (SelectorSyntaxException | IllegalArgumentException e) {
			sendWrongMessage();
		}
	}

	private void createPredicates() throws SelectorSyntaxException {
		// score predicates
		if (predicateStringPair.containsKey("score")) {
			allPredicates.addAll(parseScorePredicate(predicateStringPair.get("score")));
		}
		// positions
		if (predicateStringPair.containsKey("x")) {
			baseLoc.setX(Double.parseDouble(predicateStringPair.get("x")));
		}
		if (predicateStringPair.containsKey("y")) {
			baseLoc.setY(Double.parseDouble(predicateStringPair.get("y")));
		}
		if (predicateStringPair.containsKey("z")) {
			baseLoc.setY(Double.parseDouble(predicateStringPair.get("z")));
		}
		// distance
		if (predicateStringPair.containsKey("distance")) {
			// conflicts with delta position
			if (predicateStringPair.containsKey("dx") ||
					predicateStringPair.containsKey("dy") ||
					predicateStringPair.containsKey("dz")) {
				throw new SelectorSyntaxException();
			}
			double distance = Double.parseDouble(predicateStringPair.get("distance"));
			allPredicates.add((entity) -> entity.getLocation().distance(baseLoc) < distance);
		}
		// delta position
		if (predicateStringPair.containsKey("dx") &&
				predicateStringPair.containsKey("dy") &&
				predicateStringPair.containsKey("dz")) {
			// conflicts with distance position.
			if (predicateStringPair.containsKey("distance")) {
				throw new SelectorSyntaxException();
			}
			parseDeltaPosition();
		}
		// team
		if (predicateStringPair.containsKey("team")) {
			parseTeamName();
		}
		// level
		if (predicateStringPair.containsKey("level")) {
			parseLevel();
		}
		// gamemode
		if (predicateStringPair.containsKey("gamemode")) {
			parseGameMode();
		}
		// name
		if (predicateStringPair.containsKey("name")) {
			parseName();
		}
		// yaw
		if (predicateStringPair.containsKey("x_rotation")) {
			parseXRotation();
		}
		// pitch
		if (predicateStringPair.containsKey("y_rotation")) {
			parseYRotation();
		}
		// scoreboard tag
		if (predicateStringPair.containsKey("tag")) {
			parseTag();
		}

		// nbt, advancements and predicate will not be supported due to bukkit doesn't support them.
		// I'm too lazy to mess with net.minecraft.server package. It's brain-melting.
	}

	private void sendWrongMessage() {
		sender.sendMessage(ChatColor.RED + "Wrong syntax for selector");
	}

	/**
	 * Get the selected entities from the entities.
	 * @return the selected entities
	 */
	public List<Entity> getSelectedEntities() {
		List<Entity> entities = getEntities(sortMethod);

		// apply predicates
		for (Predicate<Entity> predicate : allPredicates) {
			entities.removeIf((entity) -> !predicate.test(entity));
		}

		int i = 0;
		for (Entity entity : entities) {
			if (++i > limit) {
				break;
			}
			selectedEntities.add(entity);
		}
		return Collections.unmodifiableList(selectedEntities);
	}

	private List<Predicate<Entity>> parseScorePredicate(String scorePredicateRaw) throws SelectorSyntaxException {
		if (Bukkit.getScoreboardManager() == null) {
			return new LinkedList<>(); // not supported.
		}

		String predicateString = scorePredicateRaw.substring(1, scorePredicateRaw.length() - 1);
		String[] scorePredicates = predicateString.split(",");
		List<Predicate<Entity>> predicates = new LinkedList<>();

		for (String scorePredicate : scorePredicates) {
			String[] nameValuePair = scorePredicate.split("=");
			String name = nameValuePair[0];
			String value = nameValuePair[1];

			try {
				int scoreVal = Integer.parseInt(value);

				predicates.add((entity) -> {
					Objective o = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(name);
					if (o == null) {
						return false;
					}

					return o.getScore(EntityHelper.getEntityName(entity)).getScore() > scoreVal;
				});
			} catch (NumberFormatException e) {
				// maybe there is a range defined in the predicate
				int scoreMinVal = Integer.MIN_VALUE;
				int scoreMaxVal = Integer.MAX_VALUE;

				String[] predictionPair = value.split("\\.{2}");
				boolean empty = true;
				if (!predictionPair[0].isEmpty()) {
					empty = false;
					try {
						scoreMinVal = Integer.parseInt(predictionPair[0]);
					} catch (Exception ex) {
						throw new SelectorSyntaxException();
					}
				}
				if (!predictionPair[1].isEmpty()) {
					empty = false;
					try {
						scoreMaxVal = Integer.parseInt(predictionPair[1]);
					} catch (Exception ex) {
						throw new SelectorSyntaxException();
					}
				}

				if (empty) {
					throw new SelectorSyntaxException();
				}
				int finalScoreMinVal = scoreMinVal;
				int finalScoreMaxVal = scoreMaxVal;
				predicates.add((entity) -> {
					Objective o = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(name);
					if (o == null) {
						return false;
					}

					return o.getScore(EntityHelper.getEntityName(entity)).getScore() > finalScoreMinVal && o.getScore(EntityHelper.getEntityName(
							entity)).getScore() < finalScoreMaxVal;
				});
			}
		}

		return predicates;
	}
	private void parseTag() {
		String tag = predicateStringPair.get("tag");

		boolean inverted = false;
		if (tag.startsWith("!")) {
			inverted = true;
			tag = tag.substring(1);
		}

		boolean finalInverted = inverted;
		String finalTag = tag;
		allPredicates.add((entity) -> {
			if (finalInverted) {
				return !entity.getScoreboardTags().contains(finalTag);
			} else {
				return entity.getScoreboardTags().contains(finalTag);
			}
		});
	}
	private void parseYRotation() {
		int rotationMin;
		int rotationMax;
		try {
			rotationMin = rotationMax = Integer.parseInt(predicateStringPair.get("y_rotation"));
		} catch (NumberFormatException e) {
			String[] region = predicateStringPair.get("y_rotation").split("\\.{2}");
			rotationMin = Integer.parseInt(region[0]);
			rotationMax = Integer.parseInt(region[1]);
		}

		final int rotationYMin = rotationMin;
		final int rotationYMax = rotationMax;

		allPredicates.add((entity) -> {
			double yaw = entity.getLocation().getYaw();
			if (rotationYMin > rotationYMax) {
				return yaw > rotationYMin || yaw < rotationYMax;
			} else {
				return yaw < rotationYMax && yaw > rotationYMin;
			}
		});
	}
	private void parseXRotation() {
		int rotationMin;
		int rotationMax;
		try {
			rotationMin = rotationMax = Integer.parseInt(predicateStringPair.get("x_rotation"));
		} catch (NumberFormatException e) {
			String[] region = predicateStringPair.get("x_rotation").split("\\.{2}");
			rotationMin = Integer.parseInt(region[0]);
			rotationMax = Integer.parseInt(region[1]);
		}

		final int rotationXMin = rotationMin;
		final int rotationXMax = rotationMax;

		allPredicates.add((entity) -> entity.getLocation().getPitch() >= rotationXMin &&
				entity.getLocation().getPitch() <= rotationXMax);
	}
	private void parseName() {
		String name = predicateStringPair.get("name");

		boolean inverted = name.startsWith("!");
		if (inverted) {
			name = name.substring(1);
		}

		String finalName = name;
		allPredicates.add((entity) -> {
			String en = EntityHelper.getEntityName(entity);
			if (inverted) {
				return !en.equals(finalName);
			} else {
				return en.equals(finalName);
			}
		});
	}
	private void parseGameMode() {
		String gm = predicateStringPair.get("gamemode");

		boolean inverted = gm.startsWith("!");
		if (inverted) {
			gm = gm.substring(1);
		}

		GameMode gameMode = GameMode.valueOf(gm);

		allPredicates.add((entity) -> {
			if (!(entity instanceof Player)) {
				return false;
			}

			if (inverted) {
				return ((Player) entity).getGameMode() != gameMode;
			} else {
				return ((Player) entity).getGameMode() == gameMode;
			}
		});
	}
	private void parseLevel() {
		int levelMin;
		int levelMax;
		try {
			levelMin = levelMax = Integer.parseInt(predicateStringPair.get("level"));
		} catch (NumberFormatException e) {
			String[] region = predicateStringPair.get("level").split("\\.{2}");
			levelMin = Integer.parseInt(region[0]);
			levelMax = Integer.parseInt(region[1]);
		}

		int finalLevelMin = levelMin;
		int finalLevelMax = levelMax;
		allPredicates.add((entity) -> {
			if (!(entity instanceof Player)) {
				return false;
			}
			Player p = (Player) entity;

			return p.getLevel() <= finalLevelMax && p.getLevel() >= finalLevelMin;
		});
	}
	private void parseTeamName() {
		String teamName = predicateStringPair.get("team");
		boolean inverted = teamName.startsWith("!");
		if (inverted) {
			teamName = teamName.substring(1);
		}

		String finalTeamName = teamName;
		allPredicates.add((entity) -> {
			Team teamIn;
			if (Bukkit.getScoreboardManager() == null) {
				return true; // no supported
			}
			teamIn = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(finalTeamName);
			boolean result;
			if (teamIn == null) {
				result = finalTeamName.isEmpty();
			} else {
				result = teamIn.hasEntry(EntityHelper.getEntityName(entity));
			}
			if (inverted) {
				return !result;
			} else {
				return result;
			}
		});
	}
	private void parseDeltaPosition() {
		double expectedDeltaX = Double.parseDouble(predicateStringPair.get("dx"));
		double expectedDeltaY = Double.parseDouble(predicateStringPair.get("dy"));
		double expectedDeltaZ = Double.parseDouble(predicateStringPair.get("dz"));
		Vector baseVec = baseLoc.toVector();

		allPredicates.add((entity) -> {
			Vector entityVec = entity.getLocation().toVector();
			entityVec.subtract(baseVec);
			// bypasses the "-" wisely.
			double dxCheck = entityVec.getX() / expectedDeltaX;
			double dyCheck = entityVec.getY() / expectedDeltaY;
			double dzCheck = entityVec.getZ() / expectedDeltaZ;

			return dxCheck >= 0 && dxCheck <= 1 &&
					dyCheck >= 0 && dyCheck <= 1 &&
					dzCheck >= 0 && dzCheck <= 1;
		});
	}

	private List<Entity> getEntities(Sort sortMethod) {
		if (baseLoc.getWorld() == null) {
			return new LinkedList<>();
		}

		List<Entity> e = new LinkedList<>(baseLoc.getWorld().getEntities());
		switch (sortMethod) {
			case NEAREST:
				e.sort(Comparator.comparingDouble(en -> baseLoc.distance(en.getLocation())));
				break;
			case FURTHEST:
				e.sort((en1, en2) -> (int) Math.ceil(baseLoc.distance(en2.getLocation()) -
						baseLoc.distance(en1.getLocation())));
				break;
			case RANDOM:
				Collections.shuffle(e);
				break;
			default:
				break;
		}
		return e;
	}

	private enum Sort {
		NEAREST, FURTHEST, RANDOM, ARBITRARY
	}
}
