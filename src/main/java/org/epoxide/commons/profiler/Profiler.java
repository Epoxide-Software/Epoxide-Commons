package org.epoxide.commons.profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Profiler {

	private final Logger log;
	private final List<ProfileEntry> entries = new ArrayList<>();
	
	private boolean enabled;
	
	public Profiler (String name) {
		
		this (Logger.getLogger(name));
	}
	
	public Profiler (Logger log) {
		
		this.log = log;
	}
	
	public Profiler setEnabled(boolean enabled) {
		
		this.enabled = enabled;
		return this;
	}
	
	public boolean isEnabled() {
		
		return this.enabled;
	}
	
	public static class ProfileEntry {
		
		private final String name;
		private Long time;
		private boolean ended;
		
		public ProfileEntry (String name) {
			
			this.name = name;
		}
	}
}