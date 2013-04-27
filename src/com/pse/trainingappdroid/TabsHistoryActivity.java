package com.pse.trainingappdroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TabsHistoryActivity extends Activity {
	
	CheckedTextView date;
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_history);
		
		
		ListView list = (ListView)findViewById(R.id.maliste);
		ArrayAdapter<String> tableau = new ArrayAdapter<String>(
		list.getContext(), R.layout.maliste);
		for (int i=0; i<40; i++) {
		tableau.add("coucou " + i);
		}
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
