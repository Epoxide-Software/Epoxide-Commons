package org.epoxide.commons.registry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.epoxide.commons.collections.BiMap;

/**
 * A bidirectional registry which uses string based identifiers as the keys. Each key is an
 * Identifier object which is often represented as a string. Identifier strings have two parts,
 * the first is the domain and is used to define the owner of the registered value, and the
 * second is the name of the value being registered. This is primarily useful when there is a
 * possibility of plug-ins or modifications registering things with your registry.
 */
public class NamedRegistry<V> implements Iterable<V>, Serializable {

    /**
     * An array which holds a cache of all registered values. This cache should only ever be
     * modified internally.
     */
    private Object[] valueCache;

    /**
     * A bidirectional map which contains all the identifiers and registered values.
     */
    private final BiMap<Identifier, V> values = new BiMap<>();

    /**
     * Gets the Identifier for a registered value. This can be null.
     *
     * @param value The value to get the Identifier for.
     * @return The Identifier that the value was registered using. Can be null if it was not
     *         registered.
     */
    public Identifier getIdentifier (V value) {

        return this.values.getKey(value);
    }

    /**
     * Gets all of the identifiers currently registered.
     *
     * @return A collection of all identifiers that have been used.
     */
    public Set<Identifier> getIdentifiers () {

        return this.values.keySet();
    }

    /**
     * Gets a List of all registered identifiers that use the specified domain.
     *
     * @param domain The domain to look for.
     * @return A List of all registered identifiers using the passed domain.
     */
    public List<Identifier> getIdentifiers (String domain) {

        final List<Identifier> results = new ArrayList<>();

        for (final Identifier id : this.values.keySet()) {
            if (id.getDomain().equals(domain)) {
                results.add(id);
            }
        }
        
        return results;
    }

    /**
     * Gets a value from the registry at random. If the registry is empty null will be returned
     * instead.
     *
     * @param random An instance of Random to use for getting the value.
     * @return A randomly selected value from the value cache, null if cache is empty.
     */
    @SuppressWarnings("unchecked")
    public V getRandomValue (Random random) {

        final Object[] values = this.getValueCache();
        return values.length == 0 ? null : (V) values[random.nextInt(values.length)];
    }

    /**
     * Gets a value from the registry at random, but limited to entries which use the passed
     * domain.
     *
     * @param random An instance of Random to use for getting the value.
     * @param domain The domain to limit the pool to.
     * @return A randomly selected value from the cache. Null if cache is empty.
     */
    public V getRandomValue (Random random, String domain) {

        final List<V> values = this.getValues(domain);
        return values.isEmpty() ? null : values.get(random.nextInt(values.size()));
    }

    /**
     * Gets the value registered with the passed identifier.
     *
     * @param identifier The identifier to search for.
     * @return The value that was found. Can be null.
     */
    public V getValue (Identifier identifier) {

        return this.values.get(identifier);
    }

    /**
     * Gets the value registered with the passed identifier string.
     *
     * @param identifier The identifier to search for.
     * @return The value that was found. Can be null.
     */
    public V getValue (String identifier) {

        return this.getValue(new Identifier(identifier));
    }

    /**
     * Gets the value registered with the passed identifier components.
     *
     * @param domain The domain of the identifier.
     * @param name The name of the identifier.
     * @return The value that was found. Can be null.
     */
    public V getValue (String domain, String name) {

        return this.getValue(new Identifier(domain, name));
    }

    /**
     * Provides access to the value cache for the registry. If it does not exist, it will be
     * generated.
     *
     * @return An array of all the cached values.
     */
    public Object[] getValueCache () {

        if (this.valueCache == null) {
            this.valueCache = this.values.values().toArray(new Object[this.values.size()]);
        }

        return this.valueCache;
    }

    /**
     * Gets a List of all registered values.
     *
     * @return A List of all registered values.
     */
    public List<V> getValues () {

        return new ArrayList<>(this.values.values());
    }

    /**
     * Gets a List of all values registered using the passed domain.
     *
     * @param domain The domain to limit to
     * @return A List of all registered values using the passed domain.
     */
    public List<V> getValues (String domain) {

        final List<V> values = new ArrayList<>();

        for (final Entry<Identifier, V> entry : this.values.entrySet()) {
            if (entry.getKey().getDomain().equals(domain)) {
                values.add(entry.getValue());
            }
        }
        
        return values;
    }

    /**
     * Checks if a domain has been used to register something.
     *
     * @param domain The target domain.
     * @return Whether or not the domain was found.
     */
    public boolean hasDomain (String domain) {

        for (final Entry<Identifier, V> entry : this.values.entrySet()) {
            if (entry.getKey().getDomain().equals(domain)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Checks if an identifier has already been used in the registry.
     *
     * @param identifier The Identifier to search for.
     * @return Whether or not the identifier has been used.
     */
    public boolean hasIdentifier (Identifier identifier) {

        return this.values.containsKey(identifier);
    }

    /**
     * Registers a value in the registry using the passed identifier as the key. Null and
     * duplicate entries are not allowed.
     *
     * @param identifier The identifier to use for the value.
     * @param value The value to register.
     * @return The value being registered, for convenience.
     */
    public V registerValue (Identifier identifier, V value) {

        if (this.values.containsKey(identifier)) {
            this.valueCache = null;
        }
        this.values.put(identifier, value);
        return value;
    }

    /**
     * Registers a value in the registry using the passed identifier info as the key. Null and
     * duplicate entries are not allowed.
     *
     * @param domain The domain to use for the identifier.
     * @param name The name to use for the identifier.
     * @param value The value to register.
     * @return The value being registered, for convenience.
     */
    public V registerValue (String domain, String name, V value) {

        return this.registerValue(new Identifier(domain, name), value);
    }

    /**
     * Registers a value in the registry using the passed string as the identifier key. Null
     * and duplicate entries are not allowed.
     *
     * @param identifier The identifier to register the entry under.
     * @param value The value to register.
     * @return The value being registered, for convenience.
     */
    public V registerValue (String identifier, V value) {

        return this.registerValue(new Identifier(identifier), value);
    }

    /**
     * Registers a value with the registry. The key is pulled from the IRegisterable data. Null
     * and duplicate entries are not allowed.
     *
     * @param value The value being registered. Must be IRegisterable and V at the same time.
     * @return The value being registered, for convenience.
     */
    public V registerValue (IRegisterable<V> value) {

        return this.registerValue(value.getIdentifier(), (V) value);
    }

    @Override
    public Iterator<V> iterator () {

        return this.values.values().iterator();
    }
}