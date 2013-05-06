package com.pse.trainingappdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WorkoutActivity extends Activity implements OnClickListener {

	private Button saveBut;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);

		// Add onclick to our save button
		saveBut = ((Button) findViewById(R.id.button_save));
		saveBut.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==saveBut){
			//TODO IF WORKOUT IS FINISHED! GO TO
			Intent myIntent = new Intent(v.getContext(),
					TestDatabaseActivity.class);
			startActivityForResult(myIntent, 0);
		}
	}
}
