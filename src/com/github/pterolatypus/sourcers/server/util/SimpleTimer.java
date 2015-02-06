package com.github.pterolatypus.sourcers.server.util;

public class SimpleTimer {

	private long cachedTime;

	public SimpleTimer() {
		reset();
	}

	public void reset() {
		cachedTime = System.currentTimeMillis();
	}

	public long elapsed() {
		return System.currentTimeMillis() - cachedTime;
	}
}
