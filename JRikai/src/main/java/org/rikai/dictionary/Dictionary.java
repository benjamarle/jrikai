package org.rikai.dictionary;

public interface Dictionary {

	Entries query(String q);
	
	void load();
	
	boolean isLoaded();

}
