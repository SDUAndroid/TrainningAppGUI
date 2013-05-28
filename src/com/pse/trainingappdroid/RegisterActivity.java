package com.pse.trainingappdroid;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	EditText inputUser;
	EditText inputPassword;
	EditText inputConfirmPassword;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Find buttons
		Button buttonOk = (Button) findViewById(R.id.buttonOK);
		
		//Find edit texts
	    inputUser = (EditText)findViewById(R.id.editTextUsername);
		inputPassword = (EditText)findViewById(R.id.editTextPassword);
		inputConfirmPassword= (EditText)findViewById(R.id.EditTextConfirmPass);
		
		final LoginDataSource db = new LoginDataSource(this);
	
		//Add new user
		buttonOk.setOnClickListener(new View.OnClickListener()
		{public void onClick(View v) {
           		
        		String userName = inputUser.getText().toString();
        		String password = inputPassword.getText().toString();
        		String confirmPassword = inputConfirmPassword.getText().toString();
        		
							if (userName.isEmpty() == false && password.isEmpty() == false) {
								if (password.equals(confirmPassword)) {
									db.open();
									db.addLogin(new Login(userName, password));
									Log.d("BUTTON_OK_SIGNUP", "Account created");
									messageUser("New user created");
								}else{
									messageUser("Please verify the passwords");
								}
							}else{
								messageUser("Please fill the fields");
							}
				}
        });
		
	}
	
	public void messageUser(String message){
		Toast.makeText(this, message, 2000).show();
	}
	
}
