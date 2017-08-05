package org.epoxide.commons.profiler;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to represent a task/process in the profiler. All of the base data is held within an
 * object of this type.
 *
 * @author Tyler Hancock (Darkhax)
 */
public class ProfilerEntry {

    /**
     * The name of the entry.
     */
    private String name;

    /**
     * The time of the entry. While {@link #isComplete()} returns false, this will be the start
     * time. When completed this instead represents duration time.
     */
    private long time;

    /**
     * The completion flag for the entry.
     */
    private boolean completed;

    /**
     * The parent profiler entry. This will be null for the main entry.
     */
    private ProfilerEntry parent;

    /**
     * The list of all sub entries.
     */
    private final List<ProfilerEntry> subEntries = new ArrayList<>();

    /**
     * The list of notes which have been added to this entry.
     */
    private final List<String> notes = new ArrayList<>();

    /**
     * Constructor for a profiler entry. Only handles the name. The rest of the logic is done
     * in {@link Profiler}.
     *
     * @param name The name of the entry.
     */
    public ProfilerEntry (String name) {

        this.name = name;
    }

    /**
     * Gets the name of the profiler entry.
     *
     * @return The name of the profiler entry.
     */
    public String getName () {

        return this.name;
    }

    /**
     * Gets the time for the profiler entry. The meaning of this value changes based on the
     * result of {@link #isComplete()}. When the entry is not complete, this will be the start
     * time, and when it is true it will be the total duration time.
     *
     * @return The time value for the entry.
     */
    public long getTime () {

        return this.time;
    }

    /**
     * Sets the time value to an arbitrary value.
     *
     * @param time The long to use for the time value.
     */
    public void setTime (long time) {

        this.time = time;
    }

    /**
     * Checks if the entry has been completed. This will bet set to true after
     * {@link Profiler#stop()} has been called while this entry is the current entry.
     *
     * @return Whether or not the entry has been completed.
     */
    public boolean isComplete () {

        return this.completed;
    }

    /**
     * Sets the completed value to an arbitrary state.
     *
     * @param complete The new boolean to use for the completed state.
     */
    public void setComplete (boolean complete) {

        this.completed = complete;
    }

    /**
     * Adds a sub entry to this entry. This will also make this entry the parent of the the sub
     * entry.
     *
     * @param entry The sub entry to add.
     */
    public void addSubEntry (ProfilerEntry entry) {

        this.subEntries.add(entry);
        entry.setParent(this);
    }

    /**
     * Gets a list of all sub entries. This will never be null, but it can be empty.
     *
     * @return The list of all sub entries.
     */
    public List<ProfilerEntry> getSubEntries () {

        return this.subEntries;
    }

    /**
     * Gets the parent for the entry. If this is null, the entry is likely the main entry for
     * the profiler.
     *
     * @return The parent entry.
     */
    public ProfilerEntry getParent () {

        return this.parent;
    }

    /**
     * Sets the parent entry for the sub entry. This will also prefix the name of the entry
     * with the name of the parent entry.
     *
     * @param parent The entry to set as the parent.
     */
    public void setParent (ProfilerEntry parent) {

        this.parent = parent;
        this.name = parent.getName() + "#" + this.getName();
    }

    /**
     * Checks if this entry has a parent.
     *
     * @return Whether or not the entry has a parent.
     */
    public boolean hasParent () {

        return this.getParent() != null;
    }

    /**
     * Adds a note to the entry.
     *
     * @param note The note to add.
     */
    public void addNote (String note) {

        this.notes.add(note);
    }

    /**
     * Gets a list of notes for the entry. This will never be null, but it can be empty.
     *
     * @return A list of notes for the entry.
     */
    public List<String> getNotes () {

        return this.notes;
    }

    @Override
    public String toString () {

        return this.getName() + " - " + this.getTime() + "NS " + (this.isComplete() ? "Completed" : "Incomplete");
    }
}