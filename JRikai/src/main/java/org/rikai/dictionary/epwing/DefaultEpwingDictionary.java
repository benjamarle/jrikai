package org.rikai.dictionary.epwing;

import fuku.eb4j.hook.DefaultHook;

public class DefaultEpwingDictionary extends EpwingDictionary<String> {

	public DefaultEpwingDictionary(String path) {
		super(path, null);
	}

	@Override
	public void load() {
		super.load();
		this.setHook(new DefaultHook(this.getSubBook()));
	}
}
