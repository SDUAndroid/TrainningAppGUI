package com.pse.trainingappdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends Activity
{

	private static final Boolean D = true;
	private static final String TAG = "MainActivity";

	private Button butStart = null;
	private SeekBar sb_threshold = null;
	private Spinner sp_time, sp_serie;

	// Config workout values
	public static int difficulty_threshold;
	public static int timeOfTheSeries;
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

					// Lets try to move the connexion to the workout activity
					// Note to the past: good idea
					MainActivity.difficulty_threshold = MainActivity.this.sb_threshold.getProgress();// Yeah
					if (D) {
						Log.d(TAG, "Click start activity-threshold--> " + MainActivity.difficulty_threshold);
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

		// // Find views
		this.butStart = (Button) this.findViewById(R.id.buttonStartWorkout);
		this.butStart.setOnClickListener(this.mOnClickListener);

		this.sb_threshold = (SeekBar) this.findViewById(R.id.seekBar_threshold);
		this.sb_threshold.setProgress(50);// Medium level default
		difficulty_threshold = 50;

		this.sp_time = (Spinner) this.findViewById(R.id.spinner1);
		this.sp_serie = (Spinner) this.findViewById(R.id.spinner2);

		this.sp_time.setOnItemSelectedListener(new SpinnerActivity());

	}

	public class SpinnerActivity extends Activity implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
		{
			// An item was selected. You can retrieve the selected item using
			MainActivity.timeOfTheSeries = Integer.parseInt(parent.getItemAtPosition(pos).toString());
			if (D) {
				Log.d(TAG, "+++ On ItemSelected +++"+ MainActivity.timeOfTheSeries);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{
			// Another interface callback
		}
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
