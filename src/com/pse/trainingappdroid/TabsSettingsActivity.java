package com.pse.trainingappdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TabsSettingsActivity extends Activity
{

	// sometimes the sensor gets higher default values ~50k++ and sometimes
	// lower 46k aprox
	private Button adaptBut;
	private boolean option = true;
	private long res, result;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_settings);

		// Find buttons
		Button buttonRemove = (Button) findViewById(R.id.buttonRemove);
		Button buttonPersonalData = (Button) findViewById(R.id.buttonPersonal);
		adaptBut = (Button) this.findViewById(R.id.buttonAdapt);

		final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0
																									// -
																									// for
																									// private
																									// mode

		final LoginDataSource db = new LoginDataSource(this);
		final CountersDataSource dbCounter = new CountersDataSource(this);
		final Editor editor = pref.edit();

		adaptBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				if (option) {
					WorkoutActivity.LOW_VALUE = 47800;
					WorkoutActivity.MED_VALUE = 53800;
					option = false;
					Toast.makeText(TabsSettingsActivity.this, "Adapted to lower values", Toast.LENGTH_SHORT)
							.show();
				}
				else {
					WorkoutActivity.LOW_VALUE = 51500;
					WorkoutActivity.MED_VALUE = 57500;
					option = true;
					Toast.makeText(TabsSettingsActivity.this, "Adapted to higher values", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		buttonRemove.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v)
			{

				String userRecovered = pref.getString("key_userName", "Not exist");
				Login login = new Login();
				result = compareID(userRecovered);

				if (result != 0) {
					dbCounter.open();
					dbCounter.deleteCounterByID(result);
					dbCounter.close();
				}

				db.open();
				login = db.getLogin(userRecovered);
				db.open();
				db.deleteLogin(login);
				Log.d("BUTTON_REMOVE", "user removed");
				Log.d("BUTTON_REMOVE", login._user);

				Intent intent = new Intent(TabsSettingsActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();

				editor.clear();
				editor.commit(); // commit changes
			}
		});

		// Display the data of the current user
		buttonPersonalData.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v)
			{

				AlertDialog alertDialog = new AlertDialog.Builder(TabsSettingsActivity.this).create();
				String userRecovered = pref.getString("key_userName", "error");
				String passwordRecovered = pref.getString("key_password", "error");

				// Setting Dialog Title
				alertDialog.setTitle("Personal data");

				// Setting Dialog Message
				alertDialog.setMessage("User name: " + userRecovered + "\n" + "Password: "
						+ passwordRecovered);

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.logo_login);

				// Setting OK Button
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which)
					{
					}
				});

				// Showing Alert Message
				alertDialog.show();
			}
		});

	}

	/**** Method allows to find the id of the user in order to remove his workout *****/
	private long compareID(String loginuser)
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
				res = cur.getLong(0);
			}
			else {
				res = 0;
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
