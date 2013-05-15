package com.pse.trainingappdroid;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TabsHistoryActivity extends Activity
{

	CheckedTextView date;

	public static List<Counter> listOfCounters;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_history);
		
		CountersDataSource datasource;
		datasource = new CountersDataSource(this);
		datasource.open();

		listOfCounters = datasource.getAllCounters();
		
	//___
		ListView list = (ListView)findViewById(R.id.maliste);
		ArrayAdapter<Counter> tableau = new ArrayAdapter<Counter>(
		list.getContext(), R.layout.maliste, listOfCounters);
		
//		for (int i = 0; i<list.getCount();i++){
//			tableau.add(new Counter(1,""));
//		}
//		for (int i=0; i<40; i++) {
//		tableau.add("coucou " + i);
//		}
		
		list.setAdapter(tableau);
		
		
		list.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
						Intent intent = new Intent(TabsHistoryActivity.this , WorkoutHistoryActivity.class);
						startActivity(intent);
				}
			});		
		
		
		
		
		
		
		
		
		
		
		
	}

}
