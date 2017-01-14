package org.epoxide.commons.registry;

/**
 * A default implementation of {@link IRegisterable}. It handles the basic
 * getting/setting behavior while allowing other implementations to also exist.
 * If you don't need additional logic, simply have Entity/Item/Block which
 * extend from this class.
 */
public class Registerable<T> implements IRegisterable<T> {

	/**
	 * The identifier for the registerable object.
	 */
	private Identifier identifier;

	@Override
	public T setIdentifier(Identifier identifier) {

		this.identifier = identifier;
		return (T) this;
	}

	@Override
	public Identifier getIdentifier() {

		return this.identifier;
	}
}