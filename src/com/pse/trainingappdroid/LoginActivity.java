package com.pse.trainingappdroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LoginActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		//Find buttons
		Button buttonLoggin = (Button) findViewById(R.id.buttonLogin);
		Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
		Button buttonForgotPass = (Button) findViewById(R.id.buttonForgotPass);
		
		
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
		
		
		buttonLoggin.setOnClickListener(new View.OnClickListener()
		{
        	public void onClick(View v) {
           		Intent intent = new Intent(LoginActivity.this , MainActivity.class);
        		startActivity(intent);
        		finish();
        		}
        });
		
	}


}