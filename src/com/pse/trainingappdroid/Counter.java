package com.pse.trainingappdroid;

/**
 * The class we want to save in the database
 *
 */
public class Counter {
	private long id;
	private int maxCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMaxCounter() {
		return maxCount;
	}

	public void setMaxCounter(int counter) {
		this.maxCount = counter;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return "In your "+id+" try you did--> "+maxCount+"";
	}
}