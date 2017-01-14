package org.epoxide.commons.profiler;

/**
 * A simple profiler for timing your code. The provided system will allow you to time a
 * process, along with sub processes.
 */
public class Profiler {

    private final ProfileEntry main;

    private boolean enabled;
    private ProfileEntry current;

    public Profiler (String profileName) {

        this.main = new ProfileEntry("main");
        this.current = this.main;
        this.main.setTime(System.nanoTime());
    }

    public void start (String processName) {

        if (this.isEnabled()) {

            final ProfileEntry entry = new ProfileEntry(processName);

            if (this.current != null)
                this.current.addSubEntry(entry);

            this.current = entry;
            this.current.setTime(System.nanoTime());
        }
    }

    public ProfileEntry stop () {

        if (this.isEnabled()) {

            if (this.current == null)
                return null;

            this.current.setTime(System.nanoTime() - this.current.getTime());
            this.current.setComplete(true);

            final ProfileEntry ending = this.current;

            if (this.current.hasParent())
                this.current = this.current.getParent();

            return ending;
        }

        return null;
    }

    public void addNote (String note) {

        this.current.addNote(note);
    }

    public Profiler setEnabled (boolean enabled) {

        this.enabled = enabled;
        return this;
    }

    public boolean isEnabled () {

        return this.enabled;
    }

    public ProfileEntry getCurrentEntry () {

        return this.current;
    }

    public ProfileEntry getMain () {

        return this.main;
    }
}