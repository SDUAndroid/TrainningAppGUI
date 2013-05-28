package com.pse.trainingappdroid;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends Activity{
	
	
	EditText inputUser;
	EditText inputPassword;
	private boolean result;
	private boolean res;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		//Find buttons
		Button buttonLoggin = (Button) findViewById(R.id.buttonLogin);
		Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
		Button buttonForgotPass = (Button) findViewById(R.id.buttonForgotPass);
		
		inputUser = (EditText)  findViewById(R.id.editTextUsername);
		inputPassword = (EditText)  findViewById(R.id.editTextPassword);

		final LoginDataSource db = new LoginDataSource(this);
		
		final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		final Editor editor = pref.edit();
		
		buttonLoggin.setOnClickListener(new View.OnClickListener()
			{public void onClick(View v) {
	        		
	        		String userName = inputUser.getText().toString();
	        		String password = inputPassword.getText().toString();

	        		if(userName.isEmpty() == false && password.isEmpty() == false){

	        			result = compareUser(userName, password);
						  
						  if(result == true){
							  //Store the user name and the password to use them through the application
							  editor.putString("key_userName", userName); 
							  editor.putString("key_password", password); 
							  editor.commit(); 
							  
							  Intent intent = new Intent(LoginActivity.this , MainActivity.class);
				        	  startActivity(intent);
				              finish();
						  }else{
							  messageUser("Error: usename or password");
							  Log.d("BUTTON_LOGIN", "error");
						  }
	        		}else{
	        			messageUser("Please fill the fields");
	        		}
	        		}
	        });

		buttonRegister.setOnClickListener(new View.OnClickListener()
		{
        	public void onClick(View v) {
           		Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        		startActivity(intent);
        		}
        });
		
		
		buttonForgotPass.setOnClickListener(new View.OnClickListener()
		{
        	public void onClick(View v) {
           		Intent intent = new Intent(LoginActivity.this , ForgotActivity.class);
        		startActivity(intent);
        		}
        });
		
		
	}	
	
	public void messageUser(String message){
		Toast.makeText(this, message, 2000).show();
	}
	
	
	/**** Method allows to compare the id of the user *****/
	private boolean compareUser(String login, String password)
	{

		try {
			// Create an object of the class
			LoginDataSource userinformation = new LoginDataSource(this);

			// Open database to write indoors
			userinformation.open();

			Cursor cur = userinformation.fetchAllTodos();
			startManagingCursor(cur);
			// cur.moveToFirst();

			// Search in the table, if the id and the password exist or don't exist
			while (cur.moveToNext()) {
				// Compare the content of EditText with the data from the
				// database
				if (login.equals(cur.getString(1)) && password.equals(cur.getString(2))) {
					res = true;
				}
				else {
					res = false;
				}
			}
			stopManagingCursor(cur);
			// Close database
			userinformation.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return res;
	}

	
}