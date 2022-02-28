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

package moe.orangemc.fishlib.utils;

import moe.orangemc.fishlib.annotation.ShouldNotBeImplemented;
import org.apache.commons.lang.Validate;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Reflection helper to simplify reflecting.
 */
@ShouldNotBeImplemented
public final class ReflectionUtil {
    private static String serverVersion = null;

    private ReflectionUtil() {
    	throw new UnsupportedOperationException();
    }

    /**
     * Invoke a method
     * @param target the object the underlying method is invoked from.
     * @param name The name of the method.
     * @param args The arguments for the method call
     * @return the result of dispatching the method represented by this object on obj with parameters args
     * @throws Exception If something wrong happened in the method call
     * @see Method#invoke(Object, Object...)
     * @see Class#getMethod(String, Class[])
     */
    public static Object invokeMethod(Object target, String name, Object ... args) throws Exception {
	    Validate.notNull(name, "name cannot be null");
	    Validate.notNull(target, "target cannot be null");

        Class<?>[] argClasses = extractArgClasses(args);

        Object result;
        try {
            result = invokeMethod(target, name, argClasses, args);
        } catch (Exception e) {
            covertArgumentClasses(argClasses);

            try {
                result = invokeMethod(target, name, argClasses, args);
            } catch (Exception ex) {
                e.addSuppressed(ex);
                throw e;
            }
        }

        return result;
    }

    /**
     * Invoke a method
     * @param target the object the underlying method is invoked from.
     * @param name the name of the method.
     * @param argClasses the argument classes for the method call
     * @param args the arguments for the method call
     * @return the result of dispatching the method represented by this object on obj with parameters args
     * @throws Exception if something wrong happened in the method call
     * @see Method#invoke(Object, Object...)
     * @see Class#getMethod(String, Class[])
     */
    public static Object invokeMethod(Object target, String name, Class<?>[] argClasses, Object ... args) throws Exception {
	    Validate.notNull(name, "name cannot be null");
	    Validate.notNull(target, "target cannot be null");

        Class<?> targetClass = target.getClass();
        return invokeMethod(targetClass, target, name, argClasses, args);
    }

    /**
     * Invoke a method
     * @param forceClass the class in which the method is.
     * @param target the object the underlying method is invoked from.
     * @param name the name of the method.
     * @param args the arguments for the method call
     * @return the result of dispatching the method represented by this object on obj with parameters args
     * @throws Exception if something wrong happened in the method call
     * @see Method#invoke(Object, Object...)
     * @see Class#getMethod(String, Class[])
     */
    public static Object invokeMethod(Class<?> forceClass, Object target, String name, Object ... args) throws Exception {
	    Validate.notNull(target, "target cannot be null");
	    Validate.notNull(forceClass, "forceClass cannot be null");
	    Validate.notNull(name, "name cannot be null");

        Class<?>[] argClasses = extractArgClasses(args);

        Object result;
        try {
            result = invokeMethod(forceClass, target, name, argClasses, args);
        } catch (Exception e) {
            covertArgumentClasses(argClasses);

            try {
                result = invokeMethod(forceClass, target, name, argClasses, args);
            } catch (Exception ex) {
                e.addSuppressed(ex);
                throw e;
            }
        }

        return result;
    }

    /**
     * Invoke a method
     * @param forceClass the class in which the method is.
     * @param target the object the underlying method is invoked from.
     * @param name the name of the method.
     * @param argClasses the argument classes for the method call
     * @param args the arguments for the method call
     * @return the result of dispatching the method represented by this object on obj with parameters args
     * @throws Exception if something wrong happened in the method call
     * @see Method#invoke(Object, Object...)
     * @see Class#getMethod(String, Class[])
     */
    public static Object invokeMethod(Class<?> forceClass, Object target, String name, Class<?>[] argClasses, Object ... args) throws Exception {
	    Validate.notNull(target, "target cannot be null");
	    Validate.notNull(forceClass, "forceClass cannot be null");
	    Validate.notNull(name, "name cannot be null");
	    Validate.notNull(argClasses, "argClasses cannot be null");

        try {
            Method method = forceClass.getMethod(name, argClasses);
            return method.invoke(target, args);
        } catch (NoSuchMethodException e) {
            try {
                Method notPublicMethod = forceClass.getDeclaredMethod(name, argClasses);
                notPublicMethod.setAccessible(true);
                return notPublicMethod.invoke(target, args);
            } catch (Exception ex) {
                e.addSuppressed(ex);
                throw e;
            }
        }
    }

    /**
     * Returns the value of target's field.
     * @param forceClass the class in which the field is.
     * @param target object from which the represented field's value is to be extracted
     * @param name the field name
     * @return the value of the represented field in object obj; primitive values are wrapped in an appropriate object before being returned
     * @throws Exception if something wrong happened.
     * @see Field#get(Object)
     * @see Class#getField(String)
     */
    public static Object getField(Class<?> forceClass, Object target, String name) throws Exception {
	    Validate.notNull(forceClass, "forceClass cannot be null");
	    Validate.notNull(name, "name cannot be null");

        try {
            Field field = forceClass.getField(name);
            return field.get(target);
        } catch (NoSuchFieldException e) {
            try {
                Field notPublicField = forceClass.getDeclaredField(name);
                notPublicField.setAccessible(true);
                return notPublicField.get(target);
            } catch (Exception ex) {
                e.addSuppressed(ex);
                throw e;
            }
        }
    }

    /**
     * Returns the value of target's field.
     * @param target object from which the represented field's value is to be extracted
     * @param name the field name
     * @return the value of the represented field in object obj; primitive values are wrapped in an appropriate object before being returned
     * @throws Exception if something wrong happened.
     * @see Field#get(Object)
     * @see Class#getField(String)
     */
    public static Object getField(Object target, String name) throws Exception {
	    Validate.notNull(target, "target cannot be null");
	    Validate.notNull(name, "name cannot be null");

        Class<?> targetClass = target.getClass();
        return getField(targetClass, target, name);
    }

    /**
     * Creates a new object with provided classes and arguments
     * @param className The class name of the object.
     * @param args the arguments for the constructor call
     * @return a new object created by calling the constructor this object represents
     * @throws Exception if something wrong happened
     * @see Constructor#newInstance(Object...)
     * @see Class#getConstructor(Class[])
     */
    public static Object newInstance(String className, Object ... args) throws Exception {
	    Validate.notNull(className, "className cannot be null");

        Class<?>[] argClasses = extractArgClasses(args);

        try {
            return newInstance(className, argClasses, args);
        } catch (Exception e) {
            covertArgumentClasses(argClasses);
            try {
                return newInstance(className, argClasses, args);
            } catch (Exception ex) {
                e.addSuppressed(ex);
                throw e;
            }
        }
    }

    /**
     * Creates a new object with provided classes and arguments
     * @param clazz class of the object.
     * @param args the arguments for the constructor call
     * @return a new object created by calling the constructor this object represents
     * @throws Exception if something wrong happened
     * @see Constructor#newInstance(Object...)
     * @see Class#getConstructor(Class[])
     */
    public static Object newInstance(Class<?> clazz, Object ... args) throws Exception {
	    Validate.notNull(clazz, "clazz cannot be null");

        Class<?>[] argClasses = extractArgClasses(args);
        try {
            return newInstance(clazz, argClasses, args);
        } catch (Exception e) {
            covertArgumentClasses(argClasses);
            try {
                return newInstance(clazz, argClasses, args);
            } catch (Exception ex) {
                e.addSuppressed(ex);
                throw e;
            }
        }
    }

    /**
     * Creates a new object with provided classes and arguments
     * @param argClasses the argument classes for the constructor call
     * @param args the arguments for the constructor call
     * @return a new object created by calling the constructor this object represents
     * @throws Exception if something wrong happened
     * @see Constructor#newInstance(Object...)
     * @see Class#getConstructor(Class[])
     */
    public static Object newInstance(String className, Class<?>[] argClasses, Object ... args) throws Exception {
	    Validate.notNull(className, "className cannot be null");
	    Validate.notNull(argClasses, "argClasses cannot be null");

        return newInstance(Class.forName(className), argClasses, args);
    }

    /**
     * Creates a new object with provided classes and arguments
     * @param clazz class of the object.
     * @param argClasses the argument classes for the constructor call
     * @param args the arguments for the constructor call
     * @return a new object created by calling the constructor this object represents
     * @throws Exception if something wrong happened
     * @see Constructor#newInstance(Object...)
     * @see Class#getConstructor(Class[])
     */
    public static Object newInstance(Class<?> clazz, Class<?>[] argClasses, Object ... args) throws Exception {
	    Validate.notNull(clazz, "clazz cannot be null");
	    Validate.notNull(argClasses, "argClasses cannot be null");

        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor(argClasses);
        } catch (Exception e) {
            try {
                constructor = clazz.getDeclaredConstructor(argClasses);
            } catch (Exception ex) {
                e.addSuppressed(ex);
                throw e;
            }
        }

        return constructor.newInstance(args);
    }

    /**
     * Get the version of the bukkit server.
     * @return version of the bukkit server.
     */
    public static String getServerVersion() {
        return serverVersion;
    }

    private static void covertArgumentClasses(Class<?>[] argClasses) {
        for (int i = 0; i < argClasses.length; i ++) {
            Class<?> clazz = argClasses[i];
            if (clazz == Float.class) {
                argClasses[i] = float.class;
            }
            if (clazz == Integer.class) {
                argClasses[i] = int.class;
            }
            if (clazz == Double.class) {
                argClasses[i] = double.class;
            }
            if (clazz == Boolean.class) {
                argClasses[i] = boolean.class;
            }
            if (clazz == Short.class) {
                argClasses[i] = short.class;
            }
            if (clazz == Byte.class) {
                argClasses[i] = byte.class;
            }
            if (clazz == Character.class) {
                argClasses[i] = char.class;
            }
        }
    }

	/**
	 * Gets an enum constant from class.
	 * @param clazz class of the enum
	 * @param constant constant name
	 * @return the enum constant
	 * @throws Exception if no constant found or class found.
	 */
	public static Enum<?> getEnum(Class<?> clazz, String constant) throws Exception {
		Validate.notNull(clazz, "clazz cannot be null");
		Validate.notNull(constant, "constant cannot be null");

		return getEnum(clazz.getName(), constant);
	}

	/**
	 * Gets an enum constant from class and enum name.
	 * @param clazz class of the enum
	 * @param enumname name of the enum
	 * @param constant constant name
	 * @return the enum constant
	 * @throws Exception if no constant found or class found.
	 */
	public static Enum<?> getEnum(Class<?> clazz, String enumname, String constant) throws Exception {
		Validate.notNull(clazz, "clazz cannot be null");
		Validate.notNull(enumname, "enumname cannot be null");
		Validate.notNull(constant, "constant cannot be null");

		return getEnum(clazz.getName(), enumname, constant);
	}

	/**
	 * Gets an enum constant from class name.
	 * @param className class of the enum
	 * @param constant constant name
	 * @return the enum constant
	 * @throws Exception if no constant found or class found.
	 */
	public static Enum<?> getEnum(String className, String constant) throws Exception {
		Validate.notNull(className, "className cannot be null");
		Validate.notNull(constant, "constant cannot be null");

		return fetchEnumConstant(constant, Class.forName(className));
	}

	/**
	 * Gets an enum constant from class name.
	 * @param className class of the enum
	 * @param enumname name of the enum
	 * @param constant constant name
	 * @return the enum constant
	 * @throws Exception if no constant found or class found.
	 */
	public static Enum<?> getEnum(String className, String enumname, String constant) throws Exception {
		Validate.notNull(className, "className cannot be null");
		Validate.notNull(enumname, "enumname cannot be null");
		Validate.notNull(constant, "constant cannot be null");

		return getEnum(className + "$" + enumname, constant);
	}

	private static Enum<?> fetchEnumConstant(String constantName, Class<?> c) throws Exception {
		Enum<?>[] constants = (Enum<?>[]) c.getEnumConstants();
		for (Enum<?> constant : constants) {
			if (constant.name().equalsIgnoreCase(constantName)) {
				return constant;
			}
		}
		throw new Exception("Enum constant not found " + constantName);
	}

	private static Class<?>[] extractArgClasses(Object ... args) {
        Class<?>[] argClasses = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            argClasses[i] = arg.getClass();
        }
        return argClasses;
    }

    static {
        try {
            serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
        } catch (Exception ignored) {
        }
    }
}
