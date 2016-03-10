package org.rikai.dictionary;

public interface Dictionary<T extends AbstractEntry> {

	Entries<T> query(String q);

	void load();

	boolean isLoaded();

	void close();

}
