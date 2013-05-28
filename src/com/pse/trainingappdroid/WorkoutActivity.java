package com.pse.trainingappdroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class WorkoutActivity extends Activity implements OnClickListener
{

	private static final Boolean D = true;
	private static final String TAG = "WorkoutActivity";
	private static final int REST_TIME = 10000;
	// Sensor values
	public static int LOW_VALUE = 51500;// 46800
	public static int MED_VALUE = 57500;// 52800
	private int sensorLow_value;
	private int sensorHigh_value;

	public static int stretchCounter = 0;
	private Button saveBut;
	private SeekBar sb_strenght;
	private TextView textView_strenght;
	private TextView textView_stretches;
	private TextView textView_time;

	private boolean workout;
	private boolean running;

	private CountDownTimer countdown;

	private BtMultiResponseReceiver btMultiResponseReceiver = null;
	private IntentFilter multiFilter = null;
	private IntentFilter multiHrFilter = null;

	private static final int BT_DEVICE_1_ID = 1;
	private static final String BT_DEVICE_1_MAC = "00:06:66:49:59:0F";

	private BtConnectorThreaded btct1 = null, btct2 = null;
	private BtConnectorPolarThreaded btct3 = null;

	private int seriesCounter;
	private MediaPlayer mp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_workout);

		this.seriesCounter = 0;
		this.connectStuff();
		// this.beginCountdown();

		// use the seekbar from the main
		this.setSensorValues(MainActivity.difficulty_threshold);
		// Add onclick to our save button invisible!! make it visible when
		// countdown ends
		this.saveBut = ( (Button) this.findViewById(R.id.button_save_workout) );
		this.saveBut.setOnClickListener(this);
		this.saveBut.setVisibility(View.GONE);

		this.sb_strenght = (SeekBar) this.findViewById(R.id.seekBar_strenght);

		this.textView_strenght = (TextView) this.findViewById(R.id.textView_strenght);
		this.textView_stretches = (TextView) this.findViewById(R.id.textView_countdown);
		this.textView_time = (TextView) this.findViewById(R.id.textView_time);

		this.mp = MediaPlayer.create(this, R.raw.bip);

	}

	// backend methods
	@Override
	protected void onResume()
	{
		this.seriesCounter = 0;
		super.onResume();
		if (D) {
			Log.d(TAG, "+ On Resume +");
		}
		this.registerReceiver(this.btMultiResponseReceiver, this.multiFilter);
		this.registerReceiver(this.btMultiResponseReceiver, this.multiHrFilter);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		WorkoutActivity.stretchCounter = 0;
		if (D) {
			Log.d(TAG, "--- On Destroy ---");
		}
		if (this.btct1 != null) {
			this.btct1.disconnect();
		}
		if (this.btct2 != null) {
			this.btct2.disconnect();
		}
		if (this.btct3 != null) {
			this.btct3.disconnect();
		}
	}

	@Override
	protected void onPause()

	{
		super.onPause();
		if (D) {
			Log.d(TAG, "- On Pause -");
		}

		this.unregisterReceiver(this.btMultiResponseReceiver);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if (v == this.saveBut) {

			Intent myIntent = new Intent(v.getContext(), TestDatabaseActivity.class);
			this.startActivityForResult(myIntent, 0);

		}
	}

	/**
	 * Set the strength value with the threshold 0-100
	 * 
	 * @param difficulty_threshold
	 */
	private void setSensorValues(int difficulty_threshold)
	{
		int lowest_value_low = LOW_VALUE;
		int lowest_value_high = MED_VALUE;
		int thresholdLow = difficulty_threshold * 30;
		int thresholdHigh = difficulty_threshold * 40;

		// set values
		this.sensorLow_value = lowest_value_low + thresholdLow;
		this.sensorHigh_value = lowest_value_high + thresholdHigh;

		if (D) {
			Log.d(TAG, "Sensor threshold values---" + this.sensorHigh_value + "---" + this.sensorLow_value);
		}
		if (D) {
			Log.d(TAG, "Sensor low/med defaults---" + this.LOW_VALUE + "---" + this.MED_VALUE);
		}

	}

	private void connectStuff()
	{

		// Setup broadcast receiver
		this.btMultiResponseReceiver = new BtMultiResponseReceiver();
		this.multiFilter = new IntentFilter(BtConnectorThreaded.BT_NEW_DATA_INTENT);
		this.multiHrFilter = new IntentFilter(BtConnectorPolarThreaded.BT_NEW_DATA_INTENT);

		// connect to device, flag as running workout
		this.btct1 = new BtConnectorThreaded(this.getApplicationContext(), BT_DEVICE_1_MAC, BT_DEVICE_1_ID);
		this.btct1.connect();

		// WORKOUT yes but NOT RUNNING YET
		this.workout = true;
		this.running = false;

	}

	private void beginCountdown()
	{
		if (!WorkoutActivity.this.running && WorkoutActivity.this.workout) {// When
		// connects

			WorkoutActivity.this.running = true;
			this.mp.start();

			this.countdown = new CountDownTimer(MainActivity.timeOfTheSeries * 1000, 1000) {

				public void onTick(long millisUntilFinished)
				{
					WorkoutActivity.this.textView_time.setText("" + millisUntilFinished / 1000);

				}

				public void onFinish()
				{
					WorkoutActivity.this.textView_time.setText("0");
					seriesCounter++;

					if (D) {
						Log.d(TAG, "+ On FINISH + " + MainActivity.serie);
					}
					// STOP ALL
					if (MainActivity.serie.equalsIgnoreCase("Soft training")) {// Just
					// 1
					// repeat
						WorkoutActivity.this.saveBut.setVisibility(View.VISIBLE);
					}
					if (MainActivity.serie.equalsIgnoreCase("Standard boy") && seriesCounter < 2) {// 2

						// textView_strenght.setText("Have a small break");

						// repeat
						CountDownTimer countdown1 = new CountDownTimer(REST_TIME, 1000) {

							public void onTick(long millisUntilFinished)
							{
								textView_strenght.setText("Have a small break " + millisUntilFinished / 1000
										+ "'' left");
								if (D) {
									Log.d(TAG, "- On tick -");
								}
							}

							public void onFinish()
							{
								WorkoutActivity.this.running = false;
								mp.start();
								WorkoutActivity.this.textView_time.setText("" + MainActivity.timeOfTheSeries);

								if (D) {
									Log.d(TAG, "+ On FINISH + " + MainActivity.serie
											+ WorkoutActivity.this.running);
								}
							}

						};

						countdown1.start();
					}
					else if (MainActivity.serie.equalsIgnoreCase("Standard boy")) {
						WorkoutActivity.this.saveBut.setVisibility(View.VISIBLE);
					}
					if (MainActivity.serie.equalsIgnoreCase("INTENSE 300") && seriesCounter < 3) {// 3
					// repeat
					// textView_strenght.setText("Have a small break");
					// repeat
						CountDownTimer countdown2 = new CountDownTimer(REST_TIME, 1000) {

							public void onTick(long millisUntilFinished)
							{
								// WorkoutActivity.this.textView_time.setText(""
								// + millisUntilFinished / 1000);
								textView_strenght.setText("Have a small break " + millisUntilFinished / 1000
										+ "'' left");
								if (D) {
									Log.d(TAG, "- On tick -");
								}
							}

							public void onFinish()
							{
								WorkoutActivity.this.running = false;
								mp.start();
								WorkoutActivity.this.textView_time.setText("" + MainActivity.timeOfTheSeries);

								if (D) {
									Log.d(TAG, "+ On FINISH + " + seriesCounter);
								}
							}

						};

						countdown2.start();
					}
					else if (MainActivity.serie.equalsIgnoreCase("INTENSE 300")) {
						WorkoutActivity.this.saveBut.setVisibility(View.VISIBLE);
					}

					// WorkoutActivity.this.workout = false;

				}
			}.start();

			if (D) {
				Log.d(TAG, "+ Countdown created +");
			}
		}
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

				case BT_DEVICE_1_ID:// Work with stretch sensor bt data here

					WorkoutActivity.this.beginCountdown();
					// WorkoutActivity.this.workout = true;
					//
					try {
						if (Integer.parseInt(WorkoutActivity.this.textView_time.getText().toString()) > 0) {

							WorkoutActivity.this.textView_strenght.setText(this.getStrength(line));
							WorkoutActivity.this.sb_strenght.setProgress(Integer.parseInt(line) - 44500);// 43000
							WorkoutActivity.this.textView_stretches.setText("" + stretchCounter);
							if (D) {
								Log.d(TAG, "Sensor on the go values---" + line);
							}
						}

					} catch (NumberFormatException e) {
						e.printStackTrace();
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
			// 48000 //55000
			if (strengthBT < WorkoutActivity.this.sensorLow_value) {

				strength = "Stretch it!";// low

				WorkoutActivity.this.workout = true;
			}
			if (strengthBT > WorkoutActivity.this.sensorHigh_value) {
				strength = "Woaaah!!!";

				if (WorkoutActivity.this.workout) {
					stretchCounter++;

					WorkoutActivity.this.workout = false;

				}
			}
			if ( ( strengthBT >= WorkoutActivity.this.sensorLow_value )
					&& ( strengthBT <= WorkoutActivity.this.sensorHigh_value )) {
				strength = "Keep going!!";
			}

			return strength;
		}

	}

}
