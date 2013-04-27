package com.pse.trainingappdroid;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class WorkoutHistoryActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_history);
		
		
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		BarGraph bar = new BarGraph();
    	GraphicalView gView = bar.getView(this);
    	
    	layout.addView(gView);
		

		
	}
	
	
}

	
	
	
	


