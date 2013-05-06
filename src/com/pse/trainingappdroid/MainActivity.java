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
import com.pse.trainingappdroid.BtConnectorPolarThreaded;
import com.pse.trainingappdroid.BtConnectorThreaded;
import com.pse.trainingappdroid.R;
import com.pse.trainingappdroid.TestDatabaseActivity;
//import com.pse.trainingappdroid.MainActivity.BtMultiResponseReceiver;

public class MainActivity extends Activity{
	
	/** BT Backend params
	 * 	Code by Alejandro and Lucas
	 * -- START
	 * **/
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
	 * BT Backend methods
	 * Code by Alejandro and Lucas
	 * -- START
	 */

	@Override
	protected void onStart() {
		super.onStart();
		if (D)
			Log.d(TAG, "++ On Start ++");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (D)
			Log.d(TAG, "+ On Resume +");
		registerReceiver(btMultiResponseReceiver, multiFilter);
		registerReceiver(btMultiResponseReceiver, multiHrFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (D)
			Log.d(TAG, "- On Pause -");
		tv.setText("Start workout");
		mET1.setText("Your streches---> 0");

		unregisterReceiver(btMultiResponseReceiver);
	}

	@Override
	protected void onStop() {
		if (D)
			Log.d(TAG, "-- On Stop --");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (D)
			Log.d(TAG, "--- On Destroy ---");
		if (btct1 != null)
			btct1.disconnect();
		if (btct2 != null)
			btct2.disconnect();
		if (btct3 != null)
			btct3.disconnect();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (D)
			Log.d(TAG, "+ On Restore Instance State +");
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (D)
			Log.d(TAG, "- On Save Instance State -");
		super.onSaveInstanceState(outState);
	}
	
	OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.button_save: {
				mET1.append("\nEnabled...waiting data");
				btct1 = new BtConnectorThreaded(getApplicationContext(),
						BT_DEVICE_1_MAC, BT_DEVICE_1_ID);
				btct1.connect();
			}
				break;

			case R.id.button2: {
				if (!running) break;
				
				Intent myIntent = new Intent(v.getContext(),
						TestDatabaseActivity.class);
				startActivityForResult(myIntent, 0);
				running = false;
			}
				break;

			case R.id.button3: {

				running = true;
				mET1.setText("Your streches---> "+stretchCounter);
			}
				break;

			}

		}
	};
	
	/**
	 * -- END
	 */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/**
		 * BT backend code
		 * Code by Alejandro and Lucas
		 * -- START
		 */
		if (D)
			Log.d(TAG, "+++ On Create +++");

		setContentView(R.layout.activity_main);

		// Setup views
		/*
		mET1 = (EditText) findViewById(R.id.editText1);
		tv = (TextView) findViewById(R.id.textView1);
		tv.setText("Start workout");
		sb = (SeekBar) findViewById(R.id.seekBar1);

		mET1.setEnabled(false);

		((Button) findViewById(R.id.button1))
				.setOnClickListener(mOnClickListener);
		((Button) findViewById(R.id.button2))// Stop
				.setOnClickListener(mOnClickListener);
		((Button) findViewById(R.id.button3))// Start
				.setOnClickListener(mOnClickListener);
		*/

		// Setup broadcast receiver
		btMultiResponseReceiver = new BtMultiResponseReceiver();
		multiFilter = new IntentFilter(BtConnectorThreaded.BT_NEW_DATA_INTENT);
		multiHrFilter = new IntentFilter(
				BtConnectorPolarThreaded.BT_NEW_DATA_INTENT);
		
		/**
		 * -- END
		 */
		
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
	 
		private class BtMultiResponseReceiver extends BroadcastReceiver {

			@Override
			public void onReceive(Context context, Intent intent) {

				int id = 0;
				String line = "";

				if (intent
						.hasExtra(BtConnectorThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA)) {
					line = intent
							.getStringExtra(BtConnectorThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA);
					id = intent
							.getIntExtra(
									BtConnectorThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA_STREAM_ID,
									0);

				} else if (intent
						.hasExtra(BtConnectorPolarThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_HR_DATA)) {
					line = ""
							+ intent.getIntExtra(
									BtConnectorPolarThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_HR_DATA,
									0);
					id = intent
							.getIntExtra(
									BtConnectorPolarThreaded.BT_NEW_DATA_INTENT_EXTRA_BT_DATA_STREAM_ID,
									0);
				}

				switch (id) {

				case BT_DEVICE_1_ID:
					mET1_lines++;
					// mET1.append("\n" + mET1_lines + ": " + line);
					// mET1.setText("\n" + mET1_lines + ": " + line);
					if (running) {
						tv.setText(this.getStrength(line));

						sb.setProgress(Integer.parseInt(line) - 42000);
					}
					break;

				}

			}

			/**
			 * 
			 * @param value
			 *            from the sensor
			 * @return conversion to low,normal,hard strength
			 */
			private String getStrength(String value) {

				int strengthBT = Integer.parseInt(value);
				String strength = null;

				if (strengthBT < 48000) {

					strength = "More please";// low

					workout = true;
				}
				if (strengthBT > 55000) {
					strength = "Woaaah";

					if (workout) {
						stretchCounter++;
						workout = false;
						mET1.setText("Your streches---> "+stretchCounter);

					}
				}
				if (strengthBT >= 48000 && strengthBT <= 55000) {
					strength = "Okay...";
				}

				return strength;
			}

		}
	
}
