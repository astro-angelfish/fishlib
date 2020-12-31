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

package moe.orangemc.luckyfish.plugincommons.messaging.impl;

import moe.orangemc.luckyfish.plugincommons.messaging.MessageCallback;
import moe.orangemc.luckyfish.plugincommons.messaging.MessagingManager;
import moe.orangemc.luckyfish.plugincommons.messaging.TypeAdapter;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manager of bungee/player communication
 */
public final class MessagingManagerImpl implements MessagingManager {
    private final Plugin plugin;
    private final Map<String, MessageCallback<?>> registeredChannels = new HashMap<>();
    private final Map<Class<?>, TypeAdapter<?>> typeAdapterMap = new HashMap<>();
    private final BungeeMessageReceiver receiver = new BungeeMessageReceiver();

    /**
     * Constructs the manager and initializes default type adapters
     * @param plugin the plugin wants to use this manager
     */
    public MessagingManagerImpl(Plugin plugin) {
        this.plugin = plugin;

        // register default type adapters
        initTypeAdapters();
    }

    private void initTypeAdapters() {
        // int
        registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
            @Override
            public void writeToStream(Integer target, ByteArrayDataOutput bado) {
                bado.writeInt(target);
            }

            @Override
            public Integer readFromStream(ByteArrayDataInput badi) {
                return badi.readInt();
            }
        });
        // float
        registerTypeAdapter(Float.class, new TypeAdapter<Float>() {
            @Override
            public void writeToStream(Float target, ByteArrayDataOutput bado) {
                bado.writeFloat(target);
            }

            @Override
            public Float readFromStream(ByteArrayDataInput badi) {
                return badi.readFloat();
            }
        });
        // byte
        registerTypeAdapter(Byte.class, new TypeAdapter<Byte>() {
            @Override
            public void writeToStream(Byte target, ByteArrayDataOutput bado) {
                bado.writeByte(target);
            }

            @Override
            public Byte readFromStream(ByteArrayDataInput badi) {
                return badi.readByte();
            }
        });
        // double
        registerTypeAdapter(Double.class, new TypeAdapter<Double>() {
            @Override
            public void writeToStream(Double target, ByteArrayDataOutput bado) {
                bado.writeDouble(target);
            }

            @Override
            public Double readFromStream(ByteArrayDataInput badi) {
                return badi.readDouble();
            }
        });
        // long
        registerTypeAdapter(Long.class, new TypeAdapter<Long>() {
            @Override
            public void writeToStream(Long target, ByteArrayDataOutput bado) {
                bado.writeLong(target);
            }

            @Override
            public Long readFromStream(ByteArrayDataInput badi) {
                return badi.readLong();
            }
        });
        // char
        registerTypeAdapter(Character.class, new TypeAdapter<Character>() {
            @Override
            public void writeToStream(Character target, ByteArrayDataOutput bado) {
                bado.writeChar(target);
            }

            @Override
            public Character readFromStream(ByteArrayDataInput badi) {
                return badi.readChar();
            }
        });
        // boolean
        registerTypeAdapter(Boolean.class, new TypeAdapter<Boolean>() {
            @Override
            public void writeToStream(Boolean target, ByteArrayDataOutput bado) {
                bado.writeBoolean(target);
            }

            @Override
            public Boolean readFromStream(ByteArrayDataInput badi) {
                return badi.readBoolean();
            }
        });
        // short
        registerTypeAdapter(Short.class, new TypeAdapter<Short>() {
            @Override
            public void writeToStream(Short target, ByteArrayDataOutput bado) {
                bado.writeShort(target);
            }

            @Override
            public Short readFromStream(ByteArrayDataInput badi) {
                return badi.readShort();
            }
        });
        // void
        registerTypeAdapter(Void.class, new TypeAdapter<Void>() {
            @Override
            public void writeToStream(Void target, ByteArrayDataOutput bado) {

            }

            @Override
            public Void readFromStream(ByteArrayDataInput badi) {
                return null;
            }
        });
        // string
        registerTypeAdapter(String.class, new TypeAdapter<String>() {
            @Override
            public void writeToStream(String target, ByteArrayDataOutput bado) {
                bado.writeUTF(target);
            }

            @Override
            public String readFromStream(ByteArrayDataInput badi) {
                return badi.readUTF();
            }
        });
        // uuid
        registerTypeAdapter(UUID.class, new TypeAdapter<UUID>() {
            @Override
            public void writeToStream(UUID target, ByteArrayDataOutput bado) {
                bado.writeLong(target.getMostSignificantBits());
                bado.writeLong(target.getLeastSignificantBits());
            }

            @Override
            public UUID readFromStream(ByteArrayDataInput badi) {
                return new UUID(badi.readLong(), badi.readLong());
            }
        });
        // int array
        registerTypeAdapter(int[].class, new TypeAdapter<int[]>() {
            @Override
            public void writeToStream(int[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (int a : target) {
                    bado.writeInt(a);
                }
            }

            @Override
            public int[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                int[] result = new int[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readInt();
                }
                return result;
            }
        });
        // float array
        registerTypeAdapter(float[].class, new TypeAdapter<float[]>() {
            @Override
            public void writeToStream(float[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (float a : target) {
                    bado.writeFloat(a);
                }
            }

            @Override
            public float[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                float[] result = new float[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readFloat();
                }
                return result;
            }
        });
        // double array
        registerTypeAdapter(double[].class, new TypeAdapter<double[]>() {
            @Override
            public void writeToStream(double[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (double a : target) {
                    bado.writeDouble(a);
                }
            }

            @Override
            public double[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                double[] result = new double[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readDouble();
                }
                return result;
            }
        });
        // long array
        registerTypeAdapter(long[].class, new TypeAdapter<long[]>() {
            @Override
            public void writeToStream(long[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (float a : target) {
                    bado.writeFloat(a);
                }
            }

            @Override
            public long[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                long[] result = new long[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readLong();
                }
                return result;
            }
        });
        // char array
        registerTypeAdapter(char[].class, new TypeAdapter<char[]>() {
            @Override
            public void writeToStream(char[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (char a : target) {
                    bado.writeChar(a);
                }
            }

            @Override
            public char[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                char[] result = new char[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readChar();
                }
                return result;
            }
        });
        // boolean array
        registerTypeAdapter(boolean[].class, new TypeAdapter<boolean[]>() {
            @Override
            public void writeToStream(boolean[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (boolean a : target) {
                    bado.writeBoolean(a);
                }
            }

            @Override
            public boolean[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                boolean[] result = new boolean[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readBoolean();
                }
                return result;
            }
        });
        // byte array
        registerTypeAdapter(byte[].class, new TypeAdapter<byte[]>() {
            @Override
            public void writeToStream(byte[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (byte a : target) {
                    bado.writeByte(a);
                }
            }

            @Override
            public byte[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                byte[] result = new byte[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readByte();
                }
                return result;
            }
        });
        // short array
        registerTypeAdapter(short[].class, new TypeAdapter<short[]>() {
            @Override
            public void writeToStream(short[] target, ByteArrayDataOutput bado) {
                bado.writeInt(target.length);
                for (short a : target) {
                    bado.writeShort(a);
                }
            }

            @Override
            public short[] readFromStream(ByteArrayDataInput badi) {
                int length = badi.readInt();
                short[] result = new short[length];
                for (int i = 0; i < length; i ++) {
                    result[i] = badi.readShort();
                }
                return result;
            }
        });
        // default object
        registerTypeAdapter(Object.class, new TypeAdapter<Object>() {
            @Override
            public void writeToStream(Object target, ByteArrayDataOutput bado) {
                try {
                    Class<?> clazz = target.getClass();
                    Field[] fields = clazz.getDeclaredFields();

                    for (Field f : fields) {
                        f.setAccessible(true);
                        Object o = f.get(target);
                        Class<?> oc = getWrappedClass(o.getClass());
                        if (typeAdapterMap.get(getWrappedClass(oc)) != null) {
                            // bypass type parameter
                            TypeAdapter.class.getMethod("writeToStream", Object.class, ByteArrayDataOutput.class).invoke(typeAdapterMap.get(oc), o, bado);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Object readFromStream(ByteArrayDataInput badi) {
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

        if (bungeeOnly && (!Bukkit.getServer().spigot().getConfig().getConfigurationSection("settings").getBoolean("settings.bungeecord"))) {
        	throw new IllegalStateException("This channel can only be used in bungee mode but the spigot is not covered under bungee.");
        }

        registeredChannels.put(channel, callback);
        try {
            callback.getAcceptableClass().getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class "+ callback.getAcceptableClass() + " has no default constructor");
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
                    bcb.call(object);
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
