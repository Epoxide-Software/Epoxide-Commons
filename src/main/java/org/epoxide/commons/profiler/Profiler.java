package org.epoxide.commons.profiler;

public class Profiler {

	private final ProfileEntry main;
	
	private boolean enabled;
	private ProfileEntry current;

	public Profiler(String profileName) {

		this.main = new ProfileEntry("main");
		this.current = this.main;
	}

	public void start(String processName) {

		if (this.isEnabled()) {

			ProfileEntry entry = new ProfileEntry(processName);
			
			if (this.current != null)
				this.current.addSubEntry(entry);
			
			this.current = entry;
			this.current.setTime(System.nanoTime());
		}
	}

	public ProfileEntry stop() {

		if (this.isEnabled()) {

			if (current == null)
				return null;
			
			this.current.setTime(System.nanoTime() - this.current.getTime());
			this.current.setComplete(true);

			ProfileEntry ending = this.current;
			
			if (this.current.hasParent())
				this.current = this.current.getParent();
			
			return ending;
		}

		return null;
	}

	public Profiler setEnabled(boolean enabled) {

		this.enabled = enabled;
		return this;
	}

	public boolean isEnabled() {

		return this.enabled;
	}
	
	public ProfileEntry getCurrentEntry() {
		
		return this.current;
	}
}