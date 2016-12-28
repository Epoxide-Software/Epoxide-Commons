package org.epoxide.commons.registry;

/**
 * This interface can be implemented to make registering more streamlined. The purpose of this
 * interface is to shift the setting of the registry name to the object being registered,
 * rather than the code registering said object. It also allows for all of the implementing
 * type to tell what it's name/id is in the registry. A default implementation is provided with
 * the Registerable class.
 */
public interface IRegisterable<T> {
    
    /**
     * Sets the identifier for the registerable object.
     * 
     * @param identifier The identifier to use.
     * @return The object having it's identifier set, for convenience.
     */
    T setIdentifier (Identifier identifier);
    
    /**
     * Gets the identifier for the registerable object.
     * 
     * @return The identifier for the object.
     */
    Identifier getIdentifier ();

    /**
     * A default implementation of IRegisterable. It handles the basic getting/setting behavior
     * while allowing other implementations to also exist. If you don't need additional logic,
     * simply have Entity/Item/Block which extend from this class.
     */
    class Registerable<T> implements IRegisterable<T> {
        
        /**
         * The identifier for the registerable object.
         */
        private Identifier identifier;
        
        @Override
        public T setIdentifier (Identifier identifier) {
            
            this.identifier = identifier;
            return (T) this;
        }
        
        @Override
        public Identifier getIdentifier () {
            
            return this.identifier;
        }
    }
}