package org.epoxide.commons.reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This singleton class is meant to hold all miscellaneous utility code related to classes.
 */
public final class ClassUtils {

    /**
     * Utility classes, such as this one, are not meant to be instantiated. Java adds an
     * implicit public constructor to every class which does not define at lease one
     * explicitly. Hence why this constructor was added.
     */
    private ClassUtils () {

        throw new IllegalAccessError("Utility class");
    }

    /**
     * A map linking primitive classes such as int.class to their wrappers, such as
     * Integer.class.
     */
    private final static Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = createPrimitiveToWrapperMap();

    /**
     * A map linking wrapper classes such as Integer.class to their primitive variants, such as
     * int.class.
     */
    private final static Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE = PRIMITIVE_TO_WRAPPER.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    /**
     * Checks if a class is a valid wrapper class, within the scope of this system.
     *
     * @param clazz The class to check.
     * @return Whether or not the class passed is a wrapper class.
     */
    public static boolean isWrapper (Class<?> clazz) {

        return WRAPPER_TO_PRIMITIVE.containsKey(clazz);
    }

    /**
     * Checks if a class is a valid primitive class, within the scope of this system.
     * void.class for example is not primitve.
     *
     * @param clazz The class to check.
     * @return Whether or not the class passed is a primitive class.
     */
    public static boolean isPrimitive (Class<?> clazz) {

        return PRIMITIVE_TO_WRAPPER.containsKey(clazz);
    }

    /**
     * Gets the wrapper class for a primitive class.
     *
     * @param primitiveClass The primitive class. Like int.class
     * @return The wrapper class associated with the primitive class, or null if none exist.
     */
    public static Class<?> getWrapper (Class<?> primitiveClass) {

        return PRIMITIVE_TO_WRAPPER.get(primitiveClass);
    }

    /**
     * Gets the primitive class for a primitive wrapper class.
     *
     * @param wrapperClass The wrapper class. Like Integer.class.
     * @return The primitive class associated with the wrapper class, or null if none exist.
     */
    public static Class<?> getPrimitive (Class<?> wrapperClass) {

        return WRAPPER_TO_PRIMITIVE.get(wrapperClass);
    }

    /**
     * Creates a map which links primitive classes with their wrapper counterparts. This is
     * only used to initially generate {@link #PRIMITIVE_TO_WRAPPER} and
     * {@link #WRAPPER_TO_PRIMITIVE}.
     *
     * @return The basic class map.
     */
    private static Map<Class<?>, Class<?>> createPrimitiveToWrapperMap () {

        final Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(boolean.class, Boolean.class);
        map.put(byte.class, Byte.class);
        map.put(short.class, Short.class);
        map.put(char.class, Character.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(float.class, Float.class);
        map.put(double.class, Double.class);
        return map;
    }
}
