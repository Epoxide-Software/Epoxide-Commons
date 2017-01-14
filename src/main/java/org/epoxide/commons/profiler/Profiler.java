package org.epoxide.commons.profiler;

import org.epoxide.commons.EpoxideCommons;

/**
 * A simple tool for profiling code. The system revolves around {@link ProfilerEntry} which is
 * an object which represents a given task being profiled. A new entry is created every time
 * {@link #start(String)} is called. Sub entries are supported, so you can create a new entry
 * while another one is being profiled, and it will be added as a sub entry. Once the task
 * being profiled is done remember to call {@link #stop()} to stop the current process. You
 * should call the stop method once for every time the start method was called, to ensure all
 * sub entries have been marked as complete.
 *
 * Please note that this system also includes the ability to enable/disable the profiler
 * arbitrarily. This is to allow profiling code to be included in production code with a
 * negligible performance impact. While profiling production code is not advised, it may be
 * useful for applications with a debug mode.
 *
 * @author Tyler Hancock (Darkhax)
 */
public class Profiler {
    
    /**
     * The main entry for the profiler. All other entries by the profiler will be sub entities
     * of this.
     */
    private final ProfilerEntry main;
    
    /**
     * The state of the profiler. When disabled (false) no new entries will be created. Allows
     * for profiler code to be slipped into production code with minimal performance impact.
     */
    private boolean enabled;
    
    /**
     * The entry that is currently in progress.
     */
    private ProfilerEntry current;
    
    /**
     * Constructor for a profiler. This should be used when you want to create your profiler.
     *
     * @param profileName The name for the profiler. This will be included in the {@link #main}
     *        entry.
     */
    public Profiler (String profileName) {
        
        this.main = new ProfilerEntry("main");
        this.main.setTime(System.nanoTime());
        this.current = this.main;
    }
    
    /**
     * Starts profiling a new entry. Make sure that you call {@link #stop()} when the entry is
     * done.
     *
     * @param processName The name of the thing you are profiling.
     */
    public void start (String processName) {
        
        if (this.isEnabled()) {
            
            final ProfilerEntry entry = new ProfilerEntry(processName);
            
            if (this.current != null)
                this.current.addSubEntry(entry);
            
            this.current = entry;
            this.current.setTime(System.nanoTime());
        }
    }
    
    /**
     * Stops the current profile entry. This will mark it as complete and update the time to
     * reflect the duration. This will also switch {@link #current} with the parent of the
     * current process.
     *
     * @return The ProfilerEntry that was stopped.
     */
    public ProfilerEntry stop () {
        
        if (this.isEnabled()) {
            
            if (this.current == null)
                EpoxideCommons.getLogger().warning("An attempt was made to stop profiling a process, but there are no current processes being profiled for " + this.main.getName());
            
            this.current.setTime(System.nanoTime() - this.current.getTime());
            this.current.setComplete(true);
            
            final ProfilerEntry ending = this.current;
            
            if (this.current.hasParent())
                this.current = this.current.getParent();
            
            return ending;
        }
        
        return null;
    }
    
    /**
     * Adds a note to the current profiler entry.
     *
     * @param note The note to add.
     */
    public void note (String note) {
        
        this.current.addNote(note);
    }
    
    /**
     * Updates the enabled status of the profiler.
     *
     * @param enabled Whether the profiler should be enabled.
     * @return The same profiler instance, provided for quality of life.
     */
    public Profiler setEnabled (boolean enabled) {
        
        this.enabled = enabled;
        return this;
    }
    
    /**
     * Checks if the profiler is enabled.
     *
     * @return Whether or not the profiler was enabled.
     */
    public boolean isEnabled () {
        
        return this.enabled;
    }
    
    /**
     * Gets the current entry being profiled.
     *
     * @return The current entry being profiled.
     */
    public ProfilerEntry getCurrentEntry () {
        
        return this.current;
    }
    
    /**
     * Gets the main profiler entry. This should be the first entry created when constructed.
     *
     * @return The main profiler entry.
     */
    public ProfilerEntry getMain () {
        
        return this.main;
    }
}