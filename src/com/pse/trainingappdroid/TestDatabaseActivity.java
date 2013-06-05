package com.pse.trainingappdroid;

import java.util.List;

import android.R.integer;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Mandatory 4 extension by
 * 
 * @author Alejandro Jorge Álvarez & Lucas Grzegorczyk
 */

public class TestDatabaseActivity extends ListActivity
{

	private CountersDataSource datasource;
	
	//SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
	 private String result, res;

	String userRecovered = LoginActivity.pref.getString("key_userName", "Not exist");
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_test_database);
		
		
		
		this.datasource = new CountersDataSource(this);
		this.datasource.open();

		List<Counter> values = this.datasource.getAllCounters();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Counter> adapter = new ArrayAdapter<Counter>(this, android.R.layout.simple_list_item_1,
				values);
		this.setListAdapter(adapter);
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view)
	{
		@SuppressWarnings("unchecked")
		ArrayAdapter<Counter> adapter = (ArrayAdapter<Counter>) this.getListAdapter();
		Counter counter = null;
		
		
		switch (view.getId())
		{
			case R.id.add:
				if (WorkoutActivity.stretchCounter == 0) {
					Toast.makeText(this, "Your record is already added", Toast.LENGTH_SHORT).show();
					break;
				}
				result= compareID(userRecovered);
				int user_count = Integer.parseInt(result);
				//Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
				counter = this.datasource.createMaxCount(WorkoutActivity.stretchCounter,user_count);
				adapter.add(counter);
				WorkoutActivity.stretchCounter = 0;
				break;
			case R.id.delete:
				if (this.getListAdapter().getCount() > 0) {
					counter = (Counter) this.getListAdapter().getItem(0);
					this.datasource.deleteCounter(counter);
					adapter.remove(counter);
				}
				break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume()
	{
		this.datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		this.datasource.close();
		super.onPause();
	}

	/**** Method allows to find the id of the user in order to remove his workout *****/
	private String compareID(String loginuser)
	{

		try {
			// Create an object of the class
			LoginDataSource userinformation = new LoginDataSource(this);

			// Open database to write indoors
			userinformation.open();

			Cursor cur = userinformation.fetchAllTodos();
			cur.moveToFirst();

			// Search in the table, if the login is equals to the username keep
			// into preference
			while (!cur.getString(1).toString().contains(loginuser)) {
				// Increment the cursor +1
				cur.moveToNext();
			}
			if (cur.getString(1).toString().contains(loginuser)) {
				res = cur.getString(0);
			}
			else {
				res = "0";
			}
			cur.close();
			// Close database
			userinformation.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return res;
	}
}
