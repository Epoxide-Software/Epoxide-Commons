package org.epoxide.commons.profiler;

import java.util.ArrayList;
import java.util.List;

public class ProfileEntry {

	private String name;
	private long time;
	private boolean completed;
	private List<ProfileEntry> subEntries = new ArrayList<ProfileEntry>();
	private ProfileEntry parent;

	public ProfileEntry(String name) {

		this.name = name;
	}

	public String getName() {

		return this.name;
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
		entry.setParent(this);
	}
	
	public List<ProfileEntry> getSubEntries() {
		
		return this.subEntries;
	}
	
	public ProfileEntry getParent() {
		
		return this.parent;
	}
	
	public void setParent(ProfileEntry parent) {
		
		this.parent = parent;
		this.name = parent.getName() + "#" + this.getName();
	}
	
	public boolean hasParent() {
		
		return this.getParent() != null;
	}
	
	@Override
	public String toString () {
		
		return this.getName() + " - " + this.getTime() + "NS " + (this.isComplete() ? "Completed" : "Incomplete");
	}
}