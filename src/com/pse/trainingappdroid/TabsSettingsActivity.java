package com.pse.trainingappdroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TabsSettingsActivity extends Activity
{
	//sometimes the sensor gets higher default values ~50k++ and sometimes lower 46k aprox
	private Button adaptBut;
	private boolean option = true;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_settings);

		adaptBut = (Button) this.findViewById(R.id.buttonAdapt);
		adaptBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				if (option) {
					WorkoutActivity.LOW_VALUE = 47800;
					WorkoutActivity.MED_VALUE = 53800;
					option = false;
					Toast.makeText(TabsSettingsActivity.this, "Adapted to lower values", Toast.LENGTH_SHORT).show();
				}
				else {
					WorkoutActivity.LOW_VALUE = 51500;
					WorkoutActivity.MED_VALUE = 57500;
					option = true;
					Toast.makeText(TabsSettingsActivity.this, "Adapted to higher values", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

}
