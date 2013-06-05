package com.pse.trainingappdroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Mandatory 4 extension by
 * 
 * @author Alejandro Jorge Álvarez & Lucas Grzegorczyk
 * 
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_COUNTER = "counter";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_MAXCOUNT = "maxCount";
	public static final String COLUMN_USERCOUNT = "userCount";
	
	public static final String TABLE_LOGIN = "login";
	public static final String COLUMN_IDUSER = "idUser";
	public static final String COLUMN_USER = "user";
	public static final String COLUMN_PASSWORD = "password";

	private static final String DATABASE_NAME = "counter.db";
	private static final int DATABASE_VERSION = 2;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_COUNTER + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_MAXCOUNT + " text not null, "
			+ COLUMN_USERCOUNT + " integer, FOREIGN KEY(" + COLUMN_USERCOUNT + ") REFERENCES " + TABLE_LOGIN + "(" + COLUMN_IDUSER + "));";

	private static final String DATABASE_CREATE_LOGIN = "create table "
			+ TABLE_LOGIN + "(" + COLUMN_IDUSER+ " integer primary key autoincrement, " +
			COLUMN_USER+ " text not null," +COLUMN_PASSWORD+ " text not null);";
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		database.execSQL(DATABASE_CREATE_LOGIN);
		Log.d("Insert table: ", "DATABASE_CREATE"); 
		Log.d("Insert table: ", "DATABASE_CREATE_LOGIN"); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTER);
		onCreate(db);
	}
}