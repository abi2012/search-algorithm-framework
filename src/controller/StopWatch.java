package controller;

/*
 Copyright (c) 2005, Corey Goldberg

 StopWatch.java is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.
 */

/**
 * Sample usage:
 * 
 * <pre>
 * public static void main(String[] args) {
 * 	StopWatch s = new StopWatch();
 * 	s.start();
 * 	// code you want to time goes here
 * 	s.stop();
 * 	System.out.println(&quot;elapsed time in milliseconds: &quot; + s.getElapsedTime());
 * }
 * </pre>
 * <p>
 * &nbsp;
 */
public class StopWatch {

	private long startTime = 0;
	private long stopTime = 0;
	private boolean running = false;

	/**
	 * Start the stop watch. The stop watch will not stop until the {@link #stop()} method is
	 * invoked.
	 */
	public void start() {
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}

	/**
	 * Stop the stop watch.
	 */
	public void stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}

	/**
	 * Gets the elapsed time, expressed in milliseconds.
	 * 
	 * @return If the stop watch is running, then return the time elapsed so far. If the stop watch
	 *         is stopped, then return the difference between the start time and the stop time.
	 */
	public long getElapsedTime() {
		long elapsed;
		if (running) {
			elapsed = (System.currentTimeMillis() - startTime);
		} else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

	/**
	 * Gets the elapsed time, expressed in seconds.
	 * 
	 * @return If the stop watch is running, then return the time elapsed so far. If the stop watch
	 *         is stopped, then return the difference between the start time and the stop time.
	 */
	public long getElapsedTimeSecs() {
		return getElapsedTime() / 1000;
	}
}
