package org.epoxide.commons.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Level;

import org.epoxide.commons.EpoxideCommons;

/**
 * This singleton class is meant to hold all miscellaneous utility code related to reflection.
 */
public final class ReflectionUtils {

    /**
     * Utility classes, such as this one, are not meant to be instantiated. Java adds an
     * implicit public constructor to every class which does not define at lease one
     * explicitly. Hence why this constructor was added.
     */
    private ReflectionUtils () {

        throw new IllegalAccessError("Utility class");
    }

    /**
     * Attempts to construct a class using reflection. The constructor used will be determined
     * using the passed object arguments. If no constructor could be found, this will return
     * null and report the error.
     *
     * @param clazz The class to construct.
     * @param args The arguments to construct the class with.
     * @return The constructed instance, or null if it failed.
     */
    public static <T> T constructClass (Class<T> clazz, Object... args) {

        final Class<?>[] types = getTypeArray(args);
        final Constructor<T> constructor = findConstructor(clazz, types);

        if (constructor != null) {

            try {

                return constructor.newInstance(args);
            }

            catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

                EpoxideCommons.getLogger().log(Level.WARNING, "Could not construct for " + getConstructionInfo(clazz, types), e);
            }
        }

        EpoxideCommons.getLogger().log(Level.WARNING, "No constructor found for " + getConstructionInfo(clazz, types));
        return null;
    }

    /**
     * Attempts to find a class constructor from an array of classes which represent the
     * construction argument types being looked for.
     *
     * @param clazz The clazz to look through.
     * @param types The constructor argument types.
     * @return The first valid constructor, or null if none could be found.
     */
    public static <T> Constructor<T> findConstructor (Class<T> clazz, Class<?>[] types) {

        try {

            return clazz.getConstructor(types);
        }

        catch (final NoSuchMethodException e) {

            EpoxideCommons.getLogger().log(Level.WARNING, "Tried to find a constructor, but nothing was found for " + getConstructionInfo(clazz, types), e);
        }

        catch (final SecurityException e) {

            EpoxideCommons.getLogger().log(Level.WARNING, "Security violation! Tried to access " + getConstructionInfo(clazz, types), e);
        }

        return null;
    }

    /**
     * Creates an array of classes which represent the types of the passed objects. The order
     * of types is preserved.
     *
     * @param args The objects to get types for. Like the input for an unknown constructor.
     * @return An array of classes which represent the types of the passed objects.
     */
    public static Class<?>[] getTypeArray (Object... args) {

        final Class<?>[] types = new Class[args.length];

        for (int index = 0; index < types.length; index++) {

            final Object obj = args[index];
            Class<?> clazz = obj != null ? obj.getClass() : null;

            // Replaces a wrapper class like Integer with it's primitive counterpart.
            if (ClassUtils.isWrapper(clazz)) {

                clazz = ClassUtils.getPrimitive(clazz);
            }

            types[index] = clazz;
        }

        return types;
    }

    /**
     * A quick utility method for printing class and constructor arguments. Used to make
     * printing the debug info easier.
     *
     * @param clazz The class to print.
     * @param argTypes The constructor arguments to print.
     * @return A string containing the debug info.
     */
    private static String getConstructionInfo (Class<?> clazz, Class<?>[] argTypes) {

        return "Class: " + clazz.getName() + " Args: " + Arrays.toString(argTypes);
    }
}
