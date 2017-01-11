package org.epoxide.commons.profiler;

import java.util.logging.Logger;

public class Profiler {

	private final Logger log;
	
	private boolean enabled;
	
	public Profiler (String name) {
		
		this (Logger.getLogger(name));
	}
	
	public Profiler (Logger log) {
		
		this.log = log;
	}
	
	public void setEnabled(boolean enabled) {
		
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		
		return this.enabled;
	}
}