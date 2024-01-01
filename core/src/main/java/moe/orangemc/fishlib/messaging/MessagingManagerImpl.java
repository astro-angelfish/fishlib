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

package moe.orangemc.fishlib.messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Manager of bungee/player communication
 */
public final class MessagingManagerImpl implements MessagingManager {
	private final Plugin plugin;
	private final Map<String, MessageCallback<?>> registeredChannels = new HashMap<>();
	private final List<String> bungeeOnlyChannels = new ArrayList<>();
	private final Map<Class<?>, TypeAdapter<?>> typeAdapterMap = new HashMap<>();
	private final BungeeMessageReceiver receiver = new BungeeMessageReceiver();

	/**
	 * Constructs the manager and initializes default type adapters
	 *
	 * @param plugin the plugin wants to use this manager
	 */
	public MessagingManagerImpl(Plugin plugin) {
		this.plugin = plugin;

		// register default type adapters
		initTypeAdapters();
	}

	private void initTypeAdapters() {
		// int
		registerTypeAdapter(Integer.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Integer target, ByteArrayDataOutput output) {
				output.writeInt(target);
			}

			@Override
			public Integer readFromStream(ByteArrayDataInput input) {
				return input.readInt();
			}
		});
		// float
		registerTypeAdapter(Float.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Float target, ByteArrayDataOutput output) {
				output.writeFloat(target);
			}

			@Override
			public Float readFromStream(ByteArrayDataInput input) {
				return input.readFloat();
			}
		});
		// byte
		registerTypeAdapter(Byte.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Byte target, ByteArrayDataOutput output) {
				output.writeByte(target);
			}

			@Override
			public Byte readFromStream(ByteArrayDataInput input) {
				return input.readByte();
			}
		});
		// double
		registerTypeAdapter(Double.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Double target, ByteArrayDataOutput output) {
				output.writeDouble(target);
			}

			@Override
			public Double readFromStream(ByteArrayDataInput input) {
				return input.readDouble();
			}
		});
		// long
		registerTypeAdapter(Long.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Long target, ByteArrayDataOutput output) {
				output.writeLong(target);
			}

			@Override
			public Long readFromStream(ByteArrayDataInput input) {
				return input.readLong();
			}
		});
		// char
		registerTypeAdapter(Character.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Character target, ByteArrayDataOutput output) {
				output.writeChar(target);
			}

			@Override
			public Character readFromStream(ByteArrayDataInput input) {
				return input.readChar();
			}
		});
		// boolean
		registerTypeAdapter(Boolean.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Boolean target, ByteArrayDataOutput output) {
				output.writeBoolean(target);
			}

			@Override
			public Boolean readFromStream(ByteArrayDataInput input) {
				return input.readBoolean();
			}
		});
		// short
		registerTypeAdapter(Short.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Short target, ByteArrayDataOutput output) {
				output.writeShort(target);
			}

			@Override
			public Short readFromStream(ByteArrayDataInput input) {
				return input.readShort();
			}
		});
		// void
		registerTypeAdapter(Void.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Void target, ByteArrayDataOutput output) {

			}

			@Override
			public Void readFromStream(ByteArrayDataInput input) {
				return null;
			}
		});
		// string
		registerTypeAdapter(String.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(String target, ByteArrayDataOutput output) {
				output.writeUTF(target);
			}

			@Override
			public String readFromStream(ByteArrayDataInput input) {
				return input.readUTF();
			}
		});
		// uuid
		registerTypeAdapter(UUID.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(UUID target, ByteArrayDataOutput output) {
				output.writeLong(target.getMostSignificantBits());
				output.writeLong(target.getLeastSignificantBits());
			}

			@Override
			public UUID readFromStream(ByteArrayDataInput input) {
				return new UUID(input.readLong(), input.readLong());
			}
		});
		// int array
		registerTypeAdapter(int[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(int[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (int a : target) {
					output.writeInt(a);
				}
			}

			@Override
			public int[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				int[] result = new int[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readInt();
				}
				return result;
			}
		});
		// float array
		registerTypeAdapter(float[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(float[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (float a : target) {
					output.writeFloat(a);
				}
			}

			@Override
			public float[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				float[] result = new float[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readFloat();
				}
				return result;
			}
		});
		// double array
		registerTypeAdapter(double[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(double[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (double a : target) {
					output.writeDouble(a);
				}
			}

			@Override
			public double[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				double[] result = new double[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readDouble();
				}
				return result;
			}
		});
		// long array
		registerTypeAdapter(long[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(long[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (float a : target) {
					output.writeFloat(a);
				}
			}

			@Override
			public long[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				long[] result = new long[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readLong();
				}
				return result;
			}
		});
		// char array
		registerTypeAdapter(char[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(char[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (char a : target) {
					output.writeChar(a);
				}
			}

			@Override
			public char[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				char[] result = new char[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readChar();
				}
				return result;
			}
		});
		// boolean array
		registerTypeAdapter(boolean[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(boolean[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (boolean a : target) {
					output.writeBoolean(a);
				}
			}

			@Override
			public boolean[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				boolean[] result = new boolean[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readBoolean();
				}
				return result;
			}
		});
		// byte array
		registerTypeAdapter(byte[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(byte[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (byte a : target) {
					output.writeByte(a);
				}
			}

			@Override
			public byte[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				byte[] result = new byte[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readByte();
				}
				return result;
			}
		});
		// short array
		registerTypeAdapter(short[].class, new TypeAdapter<>() {
			@Override
			public void writeToStream(short[] target, ByteArrayDataOutput output) {
				output.writeInt(target.length);
				for (short a : target) {
					output.writeShort(a);
				}
			}

			@Override
			public short[] readFromStream(ByteArrayDataInput input) {
				int length = input.readInt();
				short[] result = new short[length];
				for (int i = 0; i < length; i++) {
					result[i] = input.readShort();
				}
				return result;
			}
		});
		// default object
		registerTypeAdapter(Object.class, new TypeAdapter<>() {
			@Override
			public void writeToStream(Object target, ByteArrayDataOutput output) {
				try {
					Class<?> clazz = target.getClass();
					Field[] fields = clazz.getDeclaredFields();

					for (Field f : fields) {
						f.setAccessible(true);
						Object o = f.get(target);
						Class<?> oc = getWrappedClass(o.getClass());
						if (typeAdapterMap.get(getWrappedClass(oc)) != null) {
							// bypass type parameter
							TypeAdapter.class.getMethod("writeToStream", Object.class, ByteArrayDataOutput.class).invoke(typeAdapterMap.get(oc), o, output);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public Object readFromStream(ByteArrayDataInput input) {
				throw new UnsupportedOperationException("Unsupported type java.lang.Object");
			}
		});
	}

	@Override
	public <T> void registerTypeAdapter(Class<T> target, TypeAdapter<T> typeAdapter) {
		Validate.notNull(target, "target cannot be null");
		Validate.notNull(typeAdapter, "typeAdapter cannot be null");

		if (typeAdapterMap.containsKey(target)) {
			throw new IllegalArgumentException("Class " + target + " is already registered.");
		}

		typeAdapterMap.put(target, typeAdapter);
	}

	@Override
	public <T> void registerChannel(String channel, MessageCallback<T> callback, boolean bungeeOnly) {
		Validate.notNull(channel, "channel cannot be null");
		Validate.notNull(callback, "callback cannot be null");

		if (isChannelRegistered(channel)) {
			throw new IllegalArgumentException("Channel: " + channel + " is already registered");
		}

		if (bungeeOnly) {
			if ((!Bukkit.getServer().spigot().getConfig().getConfigurationSection("settings").getBoolean("bungeecord"))) {
				throw new SecurityException("This channel can only be used in bungee mode but the spigot is not covered under bungee.");
			}
			bungeeOnlyChannels.add(channel);
		}

		registeredChannels.put(channel, callback);
		try {
			callback.getAcceptableClass().getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Class " + callback.getAcceptableClass() + " has no default constructor");
		}

		Bukkit.getMessenger().registerIncomingPluginChannel(plugin, channel, receiver);
		Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, channel);
	}

	@Override
	public void sendMessage(String channel, Player targetPlayer, Object messageObject) {
		Validate.notNull(channel, "channel cannot be null");
		Validate.notNull(targetPlayer, "targetPlayer cannot be null");
		Validate.notNull(messageObject, "messageObject cannot be null");

		if (!isChannelRegistered(channel)) {
			throw new IllegalArgumentException("Channel: " + channel + " is not registered");
		}

		ByteArrayDataOutput bado = ByteStreams.newDataOutput();
		Class<?> oc = getWrappedClass(messageObject.getClass());
		TypeAdapter<?> objectAdapter = typeAdapterMap.get(oc);

		try {
			objectAdapter.getClass().getMethod("writeToStream", Object.class, ByteArrayDataOutput.class).invoke(objectAdapter, messageObject, bado);
			targetPlayer.sendPluginMessage(plugin, channel, bado.toByteArray());
		} catch (Exception e) {
			plugin.getLogger().warning("Failed to send message to target player: " + targetPlayer.getName() + " in channel: " + channel);
			e.printStackTrace();
		}
	}

	private Class<?> getWrappedClass(Class<?> type) {
		if (type == int.class) {
			return Integer.class;
		} else if (type == float.class) {
			return Float.class;
		} else if (type == byte.class) {
			return Byte.class;
		} else if (type == double.class) {
			return Double.class;
		} else if (type == long.class) {
			return Long.class;
		} else if (type == char.class) {
			return Character.class;
		} else if (type == boolean.class) {
			return Boolean.class;
		} else if (type == short.class) {
			return Short.class;
		} else if (type == void.class) {
			return Void.class;
		}
		return type;
	}

	@Override
	public boolean isChannelRegistered(String channel) {
		Validate.notNull(channel, "channel cannot be null");
		return registeredChannels.containsKey(channel);
	}

	private class BungeeMessageReceiver implements PluginMessageListener {
		@SuppressWarnings("unchecked")
		private <T> void process(String channel, Player player, byte[] message) {
			try {
				MessageCallback<T> bcb = (MessageCallback<T>) registeredChannels.get(channel);
				if (bcb == null) {
					return;
				}

				Class<T> clazz = bcb.getAcceptableClass();

				ByteArrayDataInput badi = ByteStreams.newDataInput(message);

				Constructor<T> cstr = clazz.getDeclaredConstructor();
				cstr.setAccessible(true);
				T object = cstr.newInstance();

				Field[] fields = clazz.getDeclaredFields();
				for (Field f : fields) {
					f.setAccessible(true);
					f.set(object, typeAdapterMap.get(getWrappedClass(f.getType())).readFromStream(badi));
				}

				try {
					if (bungeeOnlyChannels.contains(channel)) {
						bcb.call(null, object);
					} else {
						bcb.call(player, object);
					}
				} catch (Exception e) {
					MessagingManagerImpl.this.plugin.getLogger().warning("Failed while processing message from channel " + channel);
					e.printStackTrace();
				}
			} catch (Exception e) {
				MessagingManagerImpl.this.plugin.getLogger().warning("Failed to read data from bungee");
				e.printStackTrace();
			}
		}

		@Override
		public void onPluginMessageReceived(String channel, Player player, byte[] message) {
			this.process(channel, player, message);
		}
	}
}
