package xyz.epoxide.commons;

import java.util.logging.Logger;

public class Constants {

    /**
     * An anonymous logger used by Epoxide Commons for logger output.
     */
    public static final Logger LOG = Logger.getAnonymousLogger();

    /**
     * The current version of the library being used. This follows a major.release.build
     * structure. The major number represents the current iteration of the project. The release
     * number is used to track when a build has features not included in previous builds and is
     * not fully backwards compatible. The buled number is set by the build server and
     * represents how many builds of the project there have been.
     */
    public static final String VERSION = "0.0.0.0";
}