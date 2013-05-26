package com.pse.trainingappdroid;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabsMeActivity extends Activity {
	
	private TextView Tv_lastworkout;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_me);
		this.Tv_lastworkout = (TextView) this.findViewById(R.id.textView1);
		
		//Get all the workouts
		List<Counter> list;
		CountersDataSource datasource;
		datasource = new CountersDataSource(this);
		datasource.open();
		list = datasource.getAllCounters();
		//Show the latest
		Tv_lastworkout.setText(""+list.get(list.size()-1));
	}

}
