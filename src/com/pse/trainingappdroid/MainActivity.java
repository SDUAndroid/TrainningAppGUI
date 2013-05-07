package com.pse.trainingappdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;


public class MainActivity extends Activity
{

	private static final Boolean D = true;
	private static final String TAG = "MainActivity";

	private Button butStart = null;
	private SeekBar sb_threshold = null;
	
	//Config workout values 
	public static int difficulty_threshold;
	public static int timeseries;
	public static int serie;

	@Override
	protected void onStart()
	{
		super.onStart();
		if (D) {
			Log.d(TAG, "++ On Start ++");
		}
	}

	@Override
	protected void onStop()
	{
		if (D) {
			Log.d(TAG, "-- On Stop --");
		}
		super.onStop();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		if (D) {
			Log.d(TAG, "+ On Restore Instance State +");
		}
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		if (D) {
			Log.d(TAG, "- On Save Instance State -");
		}
		super.onSaveInstanceState(outState);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v)
		{

			switch (v.getId())
			{
			// Start workout connect to device instantly
				case R.id.buttonStartWorkout: {
							
					//Lets try to move the connexion to the workout activity
					//Note to the past: good idea
					MainActivity.difficulty_threshold = MainActivity.this.sb_threshold.getProgress();//Yeah
					if (D) {
						Log.d(TAG, "Click start activity-threshold--> "+MainActivity.difficulty_threshold );
					}		
					Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
					MainActivity.this.startActivity(intent);
				}
					break;

			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		if (D) {
			Log.d(TAG, "+++ On Create +++");
		}

//		// Find views
		butStart = (Button) this.findViewById(R.id.buttonStartWorkout);
		butStart.setOnClickListener(this.mOnClickListener);
		
		sb_threshold= (SeekBar) this.findViewById(R.id.seekBar_threshold);
		sb_threshold.setProgress(50);//Medium level default		
		difficulty_threshold = 50;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		this.getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent = null;
		switch (item.getItemId())
		{
			case R.id.menu_settings:
				intent = new Intent(MainActivity.this, TabsActivity.class);
				this.startActivity(intent);
				return true;
		}

		return false;
	}

}
