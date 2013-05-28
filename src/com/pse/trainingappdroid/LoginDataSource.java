package com.pse.trainingappdroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class LoginDataSource
{
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;

	  public LoginDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }


	  //Adding new login
	  public void addLogin(Login login) {
	 
	  SQLiteDatabase db = dbHelper.getWritableDatabase();	  
		  
	  ContentValues values = new ContentValues();
	  values.put(MySQLiteHelper.COLUMN_USER, login.getUser()); 
	  values.put(MySQLiteHelper.COLUMN_PASSWORD, login.getPassword()); 
	 
	  // Inserting Row
	  db.insert(MySQLiteHelper.TABLE_LOGIN, null, values);
	  db.close();
      }
	    
	  //Getting single login
	  Login getLogin(String user) {
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGIN, new String[] { MySQLiteHelper.COLUMN_IDUSER,
			  MySQLiteHelper.COLUMN_USER, MySQLiteHelper.COLUMN_PASSWORD }, MySQLiteHelper.COLUMN_USER + "=?",
	                new String[] { String.valueOf(user) }, null, null, null, null);
	        if (cursor != null)
	            cursor.moveToFirst();
	        	Login login = new Login(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2));
	        return login;
	    }
	  
	   //Deleting single contact
	  public void deleteLogin(Login login) {
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    db.delete(MySQLiteHelper.TABLE_LOGIN, MySQLiteHelper.COLUMN_IDUSER + " = ?",
	            new String[] { String.valueOf(login.getIdUser()) });
	    db.close();
	  }
	  
	  //Data query recover
	  public Cursor fetchAllTodos(){
		return database.query(MySQLiteHelper.TABLE_LOGIN, new String[] 
				{MySQLiteHelper.COLUMN_IDUSER, MySQLiteHelper.COLUMN_USER, MySQLiteHelper.COLUMN_PASSWORD}, null, null, null,null, null);
	  }

}
