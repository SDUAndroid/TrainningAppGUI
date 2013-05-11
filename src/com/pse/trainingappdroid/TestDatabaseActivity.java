package com.pse.trainingappdroid;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Mandatory 4 extension by
 * 
 * @author Alejandro Jorge Álvarez & Lucas Grzegorczyk
 */

public class TestDatabaseActivity extends ListActivity {
	private CountersDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_test_database);

		this.datasource = new CountersDataSource(this);
		this.datasource.open();

		List<Counter> values = this.datasource.getAllCounters();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Counter> adapter = new ArrayAdapter<Counter>(this,
				android.R.layout.simple_list_item_1, values);
		this.setListAdapter(adapter);
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Counter> adapter = (ArrayAdapter<Counter>) this
				.getListAdapter();
		Counter counter = null;
		switch (view.getId()) {
		case R.id.add:

			if (WorkoutActivity.stretchCounter == 0) {
				Toast.makeText(this, "Your record is already added", Toast.LENGTH_SHORT).show();
				break;
			}
			counter = this.datasource
					.createMaxCount(WorkoutActivity.stretchCounter);
			adapter.add(counter);
			WorkoutActivity.stretchCounter = 0;
			break;
		case R.id.delete:
			if (this.getListAdapter().getCount() > 0) {
				counter = (Counter) this.getListAdapter().getItem(0);
				this.datasource.deleteCounter(counter);
				adapter.remove(counter);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		this.datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		this.datasource.close();
		super.onPause();
	}

}