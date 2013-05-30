package com.pse.trainingappdroid;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * database connection and supports adding new counters and fetching all counters.
 *
 */
public class CountersDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_MAXCOUNT, MySQLiteHelper.COLUMN_USERCOUNT };
	

  public CountersDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Counter createMaxCount(int maxCount) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_MAXCOUNT, maxCount);
    long insertId = database.insert(MySQLiteHelper.TABLE_COUNTER, null,
        values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_COUNTER,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Counter newCounter = cursorToCounter(cursor);
    cursor.close();
    return newCounter;
  }

  public void deleteCounter(Counter counter) {
    long id = counter.getId();
    System.out.println("Counter deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_COUNTER, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<Counter> getAllCounters() {
    List<Counter> counters = new ArrayList<Counter>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_COUNTER,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Counter counter = cursorToCounter(cursor);
      counters.add(counter);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return counters;
  }

  private Counter cursorToCounter(Cursor cursor) {
    Counter counter = new Counter();
    counter.setId(cursor.getLong(0));
    counter.setMaxCounter(cursor.getInt(1));
    return counter;
  }
	
	 public void deleteCounterByID(long id){ 
		    System.out.println("Counter deleted with id: " + id);
		    database.delete(MySQLiteHelper.TABLE_COUNTER, MySQLiteHelper.COLUMN_ID
		        + " = " + id, null);
		  }
	
} 

