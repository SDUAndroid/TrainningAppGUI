package com.pse.trainingappdroid;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity
{

	EditText inputUser;
	EditText inputPassword;
	EditText inputConfirmPassword;
	private boolean exist_user, res;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		// Find buttons
		Button buttonOk = (Button) findViewById(R.id.buttonOK);

		// Find edit texts
		inputUser = (EditText) findViewById(R.id.editTextUsername);
		inputPassword = (EditText) findViewById(R.id.editTextPassword);
		inputConfirmPassword = (EditText) findViewById(R.id.EditTextConfirmPass);

		final LoginDataSource db = new LoginDataSource(this);

		// Add new user
		buttonOk.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v)
			{

				String userName = inputUser.getText().toString();
				String password = inputPassword.getText().toString();
				String confirmPassword = inputConfirmPassword.getText().toString();

				if (userName.isEmpty() == false && password.isEmpty() == false) {
					exist_user = compareUser_exist(userName);
					if (exist_user == true) {
						messageUser("Username already exists");
					}
					else if (password.equals(confirmPassword)) {
						db.open();
						db.addLogin(new Login(userName, password));
						Log.d("BUTTON_OK_SIGNUP", "Account created");
						messageUser("New user created");
					}
					else {
						messageUser("Please verify the passwords");
					}
				}
				else {
					messageUser("Please fill the fields");
				}
			}
		});

	}

	/**** Method allows to find the id of the user in order to remove his workout *****/
	private boolean compareUser_exist(String loginuser)
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
				res = true;
			}
			else {
				res = false;
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

	public void messageUser(String message)
	{
		Toast.makeText(this, message, 2000).show();
	}

}
