package org.epoxide.commons.registry;

import org.epoxide.commons.reflection.ReflectionUtils;

/**
 * An implementation of IRegisterable, intended for registering class references. This is
 * intended for situations where you are using the registered Class to construct new Objects,
 * rather than registering a specific instance.
 */
public class RegisterableClass<T> extends Registerable<T> {

    /**
     * The contained class reference.
     */
    private final Class<? extends T> clazz;

    /**
     * Base constructor for a RegisterableClass.
     *
     * @param clazz The contained class.
     */
    public RegisterableClass (Class<? extends T> clazz) {

        this.clazz = clazz;
    }

    /**
     * Gets the contained class reference. The name of this method is to prevent overriding
     * {@link java.lang.Object#getClass()}.
     *
     * @return The contained class reference.
     */
    public Class<? extends T> getContainedClass () {

        return this.clazz;
    }

    /**
     * Attempts to construct the contained class using arbitrary arguments. Arguments only
     * support primitives.
     *
     * @param args The arguments used to construct the class.
     * @return The constructed instance.
     */
    public T construct (Object... args) {

        return ReflectionUtils.constructClass(this.getContainedClass(), args);
    }
}