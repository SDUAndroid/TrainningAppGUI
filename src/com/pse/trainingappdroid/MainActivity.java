package com.pse.trainingappdroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

//import com.pse.trainingappdroid.MainActivity.BtMultiResponseReceiver;

public class MainActivity extends Activity
{

	/**
	 * BT Backend params Code by Alejandro and Lucas -- START
	 **/
	private static final Boolean D = true;
	private static final String TAG = "MainActivity";

	private EditText mET1 = null;
	private TextView tv = null;
	private SeekBar sb = null;
	private int mET1_lines = 0;

	private boolean workout = false;
	private boolean running = false;
	public static int stretchCounter = 0;

	private BtMultiResponseReceiver btMultiResponseReceiver = null;
	private IntentFilter multiFilter = null;
	private IntentFilter multiHrFilter = null;

	private static final int BT_DEVICE_1_ID = 1;
	private static final String BT_DEVICE_1_MAC = "00:06:66:49:59:0F";

	private BtConnectorThreaded btct1 = null, btct2 = null;
	private BtConnectorPolarThreaded btct3 = null;

	/**
	 * -- END
	 */

	/**
	 * BT Backend methods Code by Alejandro and Lucas -- START
	 */

//	@Override
//	protected void onStart()
//	{
//		super.onStart();
//		if (D) {
//			Log.d(TAG, "++ On Start ++");
//		}
//	}

//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		if (D) {
//			Log.d(TAG, "+ On Resume +");
//		}
//		this.registerReceiver(this.btMultiResponseReceiver, this.multiFilter);
//		this.registerReceiver(this.btMultiResponseReceiver, this.multiHrFilter);
//	}

//	@Override
//	protected void onPause()
//	{
//		super.onPause();
//		if (D) {
//			Log.d(TAG, "- On Pause -");
//		}
//		this.tv.setText("Start workout");
//		this.mET1.setText("Your streches---> 0");
//
//		this.unregisterReceiver(this.btMultiResponseReceiver);
//	}

//	@Override
//	protected void onStop()
//	{
//		if (D) {
//			Log.d(TAG, "-- On Stop --");
//		}
//		super.onStop();
//	}

//	@Override
//	protected void onDestroy()
//	{
//		super.onDestroy();
//		if (D) {
//			Log.d(TAG, "--- On Destroy ---");
//		}
//		if (this.btct1 != null) {
//			this.btct1.disconnect();
//		}
//		if (this.btct2 != null) {
//			this.btct2.disconnect();
//		}
//		if (this.btct3 != null) {
//			this.btct3.disconnect();
//		}
//	}

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

//	OnClickListener mOnClickListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v)
//		{
//
//			switch (v.getId())
//			{
//
//				case R.id.button_save: {
//					MainActivity.this.mET1.append("\nEnabled...waiting data");
//					MainActivity.this.btct1 = new BtConnectorThreaded(
//							MainActivity.this.getApplicationContext(), BT_DEVICE_1_MAC, BT_DEVICE_1_ID);
//					MainActivity.this.btct1.connect();
//				}
//					break;
//
//				case R.id.button2: {
//					if (!MainActivity.this.running) {
//						break;
//					}
//
//					Intent myIntent = new Intent(v.getContext(), TestDatabaseActivity.class);
//					MainActivity.this.startActivityForResult(myIntent, 0);
//					MainActivity.this.running = false;
//				}
//					break;
//
//				case R.id.button3: {
//
//					MainActivity.this.running = true;
//					MainActivity.this.mET1.setText("Your streches---> " + stretchCounter);
//				}
//					break;
//
//			}
//
//		}
//	};

	/**
	 * -- END
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		if (D) {
			Log.d(TAG, "+++ On Create +++");
		}

		// Setup views
		/*
		 * mET1 = (EditText) findViewById(R.id.editText1); tv = (TextView)
		 * findViewById(R.id.textView1); tv.setText("Start workout"); sb =
		 * (SeekBar) findViewById(R.id.seekBar1); mET1.setEnabled(false);
		 * ((Button) findViewById(R.id.button1))
		 * .setOnClickListener(mOnClickListener); ((Button)
		 * findViewById(R.id.button2))// Stop
		 * .setOnClickListener(mOnClickListener); ((Button)
		 * findViewById(R.id.button3))// Start
		 * .setOnClickListener(mOnClickListener);
		 */

		// Setup broadcast receiver
//		this.btMultiResponseReceiver = new BtMultiResponseReceiver();
//		this.multiFilter = new IntentFilter(BtConnectorThreaded.BT_NEW_DATA_INTENT);
//		this.multiHrFilter = new IntentFilter(BtConnectorPolarThreaded.BT_NEW_DATA_INTENT);

		/**
		 * -- END
		 */

		// setContentView(R.layout.activity_main);

		// Find button//Find buttons
		Button buttonStart = (Button) this.findViewById(R.id.buttonStart);

		buttonStart.setOnClickListener(new View.OnClickListener() {// Click in
																	// start
																	// activity
																	// should
																	// connect
																	// bt here

					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
						MainActivity.this.startActivity(intent);
					}
				});
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

	private class BtMultiResponseReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{

			int id = 0;
			String line = "";

			if (intent.hasExtra(BtConnectorThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA)) {
				line = intent.getStringExtra(BtConnectorThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA);
				id = intent.getIntExtra(BtConnectorThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA_STREAM_ID, 0);

			}
			else if (intent.hasExtra(BtConnectorPolarThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_HR_DATA)) {
				line = ""
						+ intent.getIntExtra(BtConnectorPolarThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_HR_DATA, 0);
				id = intent.getIntExtra(BtConnectorPolarThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA_STREAM_ID,
						0);
			}

			switch (id)
			{

				case BT_DEVICE_1_ID:
					MainActivity.this.mET1_lines++;
					// mET1.append("\n" + mET1_lines + ": " + line);
					// mET1.setText("\n" + mET1_lines + ": " + line);
					if (MainActivity.this.running) {
						MainActivity.this.tv.setText(this.getStrength(line));

						MainActivity.this.sb.setProgress(Integer.parseInt(line) - 42000);
					}
					break;

			}

		}

		/**
		 * @param value
		 *            from the sensor
		 * @return conversion to low,normal,hard strength
		 */
		private String getStrength(String value)
		{

			int strengthBT = Integer.parseInt(value);
			String strength = null;

			if (strengthBT < 48000) {

				strength = "More please";// low

				MainActivity.this.workout = true;
			}
			if (strengthBT > 55000) {
				strength = "Woaaah";

				if (MainActivity.this.workout) {
					stretchCounter++;
					MainActivity.this.workout = false;
					MainActivity.this.mET1.setText("Your streches---> " + stretchCounter);

				}
			}
			if ( ( strengthBT >= 48000 ) && ( strengthBT <= 55000 )) {
				strength = "Okay...";
			}

			return strength;
		}

	}

}
