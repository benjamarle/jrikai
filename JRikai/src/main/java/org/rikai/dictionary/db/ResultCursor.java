package org.rikai.dictionary.db;

public interface ResultCursor {

	boolean next();

	String getValue(String columnName);

	void close();

}