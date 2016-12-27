package org.epoxide.commons;

import java.util.logging.Logger;

public class EpoxideCommons {
    
    /**
     * The current version of the library being used. This follows a major.release.build
     * structure. The major number represents the current iteration of the project. The release
     * number is used to track when a build has features not included in previous builds and is
     * not fully backwards compatible. The buled number is set by the build server and
     * represents how many builds of the project there have been.
     */
    public static final String VERSION = "0.0.0";
    
    /**
     * The logger used by Epoxide Commons. By default this is an anonymous logger, however you
     * can use {@link #setLogger(Logger)} to change it to a new one.
     */
    private static Logger log = Logger.getAnonymousLogger();
    
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
}
