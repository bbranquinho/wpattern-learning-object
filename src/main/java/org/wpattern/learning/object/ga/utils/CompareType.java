package org.wpattern.learning.object.ga.utils;

public enum CompareType {

	EQUAL(0),

	LOWER(-1),

	GREATER(1);

	private final int code;

	private CompareType(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

}
