package com.pse.trainingappdroid;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//Find button//Find buttons
		Button buttonStart = (Button) findViewById(R.id.buttonStart);
		
		
		buttonStart.setOnClickListener(new View.OnClickListener()
		{
        	public void onClick(View v) {
           		Intent intent = new Intent(MainActivity.this , WorkoutActivity.class);
        		startActivity(intent);
        		}
        });
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	 public boolean onOptionsItemSelected(MenuItem item) {
         Intent intent = null;
         switch (item.getItemId()) {
            case R.id.menu_settings:
               intent = new Intent(MainActivity.this,TabsActivity.class);
               startActivity(intent);
               return true;
           }
      
      return false;
}
	
}
