package org.epoxide.commons.profiler;

import java.util.ArrayList;
import java.util.List;

public class ProfileEntry {

	private final String name;
	private long time;
	private boolean completed;
	private List<ProfileEntry> subEntries = new ArrayList<ProfileEntry>();
	private ProfileEntry parent;

	public ProfileEntry(String name) {

		this.name = name;
	}

	public String getName() {

		return (this.getParent() != null) ? this.getParent().getName() + "#" + this.name: this.name;
	}
	
	public long getTime() {
		
		return this.time;
	}
	
	public void setTime(long time) {
		
		this.time = time;
	}

	public boolean isComplete() {
		
		return this.completed;
	}
	
	public void setComplete(boolean complete) {
		
		this.completed = complete;
	}
	
	public void addSubEntry(ProfileEntry entry) {
		
		subEntries.add(entry);
	}
	
	public List<ProfileEntry> getSubEntries() {
		
		return this.subEntries;
	}
	
	public ProfileEntry getParent() {
		
		return this.parent;
	}
	
	public void setParent(ProfileEntry parent) {
		
		this.parent = parent;
	}
	
	public boolean hasParent() {
		
		return this.getParent() != null;
	}
}