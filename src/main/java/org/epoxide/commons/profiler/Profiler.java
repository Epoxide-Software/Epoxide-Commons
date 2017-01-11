package org.epoxide.commons.profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Profiler {

	private final Logger log;
	private final List<ProfileEntry> entries = new ArrayList<>();

	private boolean enabled;
	private ProfileEntry current;

	public Profiler(String name) {

		this(Logger.getLogger(name));
	}

	public Profiler(Logger log) {

		this.log = log;
	}

	public void start(String processName) {

		if (this.isEnabled()) {

			if (current != null) {

			}
		}
	}

	public ProfileEntry stop() {

		if (this.isEnabled()) {

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
}