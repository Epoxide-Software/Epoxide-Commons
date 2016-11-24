package xyz.epoxide.commons.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Map which works both ways. This allows you to do things like getting a
 * key from it's value, or deleting an entry by using it's value instead of it's key.
 */
public class BiMap<K, V> implements Map<K, V> {
    
    /**
     * The backbone map which handles all of the normal map pairings.
     */
    private final Map<K, V> map;
    
    /**
     * An inverted map which contains inverse pairings of {@link #map}
     */
    private final Map<V, K> inverted;
    
    /**
     * Constructs a new bidirectional map using two empty HashMaps.
     */
    public BiMap() {
        
        this(new HashMap<K, V>(), new HashMap<V, K>());
    }
    
    /**
     * Constructs a new bidirectional map using an existing map as the backbone. The inverted
     * map will be created automatically.
     * 
     * @param map The map to populate the BiMap with.
     */
    public BiMap(Map<K, V> map) {
        
        this(map, invertMap(map));
    }
    
    /**
     * Constructs a new bidirectional map using predefined Maps for the default and inverted
     * maps. This constructor will not verify if the two maps passed are actually legitimate so
     * use at own discretion.
     * 
     * @param map A map containing the backbone pairings for the map.
     * @param inverted A map containing the inverse pairings of the backbone map.
     */
    public BiMap(Map<K, V> map, Map<V, K> inverted) {
        
        this.map = map;
        this.inverted = inverted;
    }
    
    /**
     * Creates a new BiMap where the backbone map and the inverse map have been switched.
     * 
     * @return A BiMap with the backbone map and inverse map switched around.
     */
    public BiMap<V, K> getInverse () {
        
        return new BiMap<V, K>(this.inverted, this.map);
    }
    
    /**
     * Creates an inverted copy of any Map. The keys of the resulting Map should be equal to
     * the values of the Map passed.
     * 
     * @param map The map to create an inverted copy of.
     * @return A Map which represents an inverted copy of the Map that was passed.
     */
    public static <K, V> Map<V, K> invertMap (Map<K, V> map) {
        
        final HashMap<V, K> reversed = new HashMap<V, K>();
        
        for (final Map.Entry<K, V> entry : map.entrySet())
            reversed.put(entry.getValue(), entry.getKey());
        
        return reversed;
    }
    
    /**
     * Attempts to retrieve a key from the {@link #map} by looking up the value in the
     * {@link #inverted} map.
     * 
     * @param value The value to get the key for.
     * @return The resulting key for the value passed, or null.
     */
    public K getKey (V value) {
        
        return this.inverted.get(value);
    }
    
    /**
     * Attempts to remove a pair from the map by value rather than key. This is done by using
     * the value as a key for the {@link #inverted} map.
     * 
     * @param value The value to remove.
     * @return The key of the value that was removed.
     */
    public K removeValue (V value) {
        
        if (this.inverted.containsKey(value)) {
            
            this.map.remove(this.inverted.get(value));
            return this.inverted.remove(value);
        }
        
        return null;
    }
    
    @Override
    public void clear () {
        
        this.map.clear();
        this.inverted.clear();
    }
    
    @Override
    public boolean containsKey (Object key) {
        
        return this.map.containsKey(key);
    }
    
    @Override
    public boolean containsValue (Object value) {
        
        return this.map.containsValue(value);
    }
    
    @Override
    public Set<Entry<K, V>> entrySet () {
        
        return Collections.unmodifiableSet(this.map.entrySet());
    }
    
    @Override
    public V get (Object key) {
        
        return this.map.get(key);
    }
    
    @Override
    public boolean isEmpty () {
        
        return this.map.isEmpty();
    }
    
    @Override
    public Set<K> keySet () {
        
        return Collections.unmodifiableSet(this.map.keySet());
    }
    
    @Override
    public V put (K key, V value) {
        
        this.map.put(key, value);
        this.inverted.put(value, key);
        return value;
    }
    
    @Override
    public void putAll (Map<? extends K, ? extends V> other) {
        
        for (final Entry<? extends K, ? extends V> entry : other.entrySet())
            this.put(entry.getKey(), entry.getValue());
    }
    
    @Override
    public V remove (Object key) {
        
        if (this.map.containsKey(key)) {
            
            this.inverted.remove(this.map.get(key));
            return this.map.remove(key);
        }
        
        return null;
    }
    
    @Override
    public int size () {
        
        return this.map.size();
    }
    
    @Override
    public Collection<V> values () {
        
        return Collections.unmodifiableCollection(this.map.values());
    }
}