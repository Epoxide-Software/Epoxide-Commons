package org.epoxide.commons;

import java.util.logging.Logger;

import org.epoxide.commons.registry.Identifier;

public class EpoxideCommons {

    /**
     * The logger used by Epoxide Commons. By default this is an anonymous logger, however you
     * can use {@link #setLogger(Logger)} to change it to a new one.
     */
    private static Logger log = Logger.getAnonymousLogger();

    /**
     * The default value to use for namespaced objects like {@link Identifier}.
     */
    private static String defaultName = "epoxide";

    /**
     * Sets the logger for Epoxide Commons to use. This will create a new
     * {@link java.util.logging.Logger} using {@link Logger#getLogger(String)}.
     *
     * @param name The name for the logger.
     */
    public static void setLogger (String name) {

        setLogger(Logger.getLogger(name));
    }

    /**
     * Sets the logger for Epoxide Commons to use.
     *
     * @param logger The logger for EpoxideCommons to use.
     */
    public static void setLogger (Logger logger) {

        log = logger;
    }

    /**
     * Gets the logger for Epoxide Commons. This should only be used internally to post
     * messages. Using it for other reasons is fine though.
     *
     * @return The logger used by Epoxide Commons.
     */
    public static Logger getLogger () {

        return log;
    }

    /**
     * Gets the default name for things like namespaced objects.
     *
     * @return The default name to use for things like namespaced objects.
     */
    public static String getDefaultName () {

        return defaultName;
    }

    /**
     * Sets the default name for things like namespaced objects.
     *
     * @param name The new name for namespaced objects to use by default.
     */
    public static void setDefaultName (String name) {

        defaultName = name;
    }
}
