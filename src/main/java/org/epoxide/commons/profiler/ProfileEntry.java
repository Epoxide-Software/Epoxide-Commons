package org.epoxide.commons.profiler;

import java.util.ArrayList;
import java.util.List;

public class ProfileEntry {

    private String name;
    private long time;
    private boolean completed;
    private ProfileEntry parent;
    private final boolean isMain;
    private final List<ProfileEntry> subEntries = new ArrayList<>();
    private final List<String> notes = new ArrayList<>();

    public ProfileEntry (String name) {
        
        this (name, false);
    }
    
    public ProfileEntry (String name, boolean isMain) {

        this.name = name;
        this.isMain = isMain;
    }

    public String getName () {

        return this.name;
    }

    public long getTime () {

        return this.time;
    }

    public void setTime (long time) {

        this.time = time;
    }

    public boolean isComplete () {

        return this.completed;
    }

    public void setComplete (boolean complete) {

        this.completed = complete;
    }

    public void addSubEntry (ProfileEntry entry) {

        this.subEntries.add(entry);
        entry.setParent(this);
    }

    public List<ProfileEntry> getSubEntries () {

        return this.subEntries;
    }

    public ProfileEntry getParent () {

        return this.parent;
    }

    public void setParent (ProfileEntry parent) {

        this.parent = parent;
        this.name = parent.getName() + "#" + this.getName();
    }

    public boolean hasParent () {

        return this.getParent() != null;
    }

    public void addNote (String note) {

        this.notes.add(note);
    }

    public List<String> getNotes () {

        return this.notes;
    }

    public boolean isMain() {
        
        return this.isMain;
    }
    
    @Override
    public String toString () {

        return this.getName() + " - " + this.getTime() + "NS " + (this.isComplete() ? "Completed" : "Incomplete");
    }
}