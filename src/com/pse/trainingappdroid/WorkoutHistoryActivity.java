package com.pse.trainingappdroid;

import org.achartengine.GraphicalView;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class WorkoutHistoryActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_history);	
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		LineGraph line = new LineGraph();

    	GraphicalView gView = line.getView(this);   	
    	
    	layout.addView(gView);
		
		
	}
	
	
}

	
	
	
	


