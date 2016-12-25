package org.epoxide.commons.registry;

/**
 * A basic object used to represent an identifier string. An Identifier string has two parts,
 * the first is the domain which is used to tell the origin. The second part is the name, and
 * is used to describe the thing being registered. These are usually used as keys in content
 * registries which accept registrations from external sources, or in registering resources.
 */
public class Identifier {
    
    /**
     * The domain name for the identifier.
     */
    private final String domain;
    
    /**
     * The name/id for the identifier.
     */
    private final String name;
    
    /**
     * Creates a new RegistryName using only one name value. The name will attempt to be split
     * using {@link #splitNameSafely(String)}.
     * 
     * @param name The name to be split.
     */
    public Identifier(String name) {
        
        this(splitNameSafely(name));
    }
    
    /**
     * Creates a new identifier using an array of strings. The first string is expected to be
     * the domain, and the second is the name.
     * 
     * @param strings An array of strings used to construct the identifier.
     */
    public Identifier(String... strings) {
        
        this(strings[0], strings[1]);
    }
    
    /**
     * Creates a new Identifier with the provided domain and name.
     * 
     * @param domain The domain for the identifier.
     * @param name The name of the identifier.
     */
    public Identifier(String domain, String name) {
        
        this.domain = domain;
        this.name = name;
    }
    
    /**
     * Gets the domain for the identifier.
     * 
     * @return The domain for the identifier.
     */
    public String getDomain () {
        
        return this.domain;
    }
    
    /**
     * Gets the name for the identifier.
     * 
     * @return The name for the identifier.
     */
    public String getName () {
        
        return this.name;
    }
    
    /**
     * Safely splits a string into two pieces using the domain format. If no domain is
     * specified a default name will be used. The domain is defined as the substring in front
     * of a color. The name is defined as the substring after.
     * 
     * @param name The name to split.
     * @return An array of strings which represent the domain and name for an Identifier.
     */
    private static String[] splitNameSafely (String name) {
        
        // TODO remove tinker default
        final String[] astring = new String[] { "tinker", name };
        final int seperator = name.indexOf(":");
        
        if (seperator >= 0) {
            
            astring[1] = name.substring(seperator + 1, name.length());
            
            if (seperator > 1)
                astring[0] = name.substring(0, seperator);
        }
        
        return astring;
    }
    
    @Override
    public int hashCode () {
        
        return this.domain.hashCode() + this.name.hashCode();
    }
    
    @Override
    public boolean equals (Object obj) {
        
        if (obj instanceof Identifier) {
            
            final Identifier regName = (Identifier) obj;
            return this.domain.equals(regName.domain) && this.name.equals(regName.name);
        }
        
        return false;
    }
    
    @Override
    public String toString () {
        
        return this.domain + ":" + this.name;
    }
}