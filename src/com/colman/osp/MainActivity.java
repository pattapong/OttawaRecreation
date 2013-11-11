package com.colman.osp;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.colman.parser.Doc;
import com.colman.parser.PoolManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Context applicationContext = this.getApplicationContext();
		
		// Set day
		final Spinner daySpinner = (Spinner) findViewById(R.id.day);

		List<String> days = PoolManager.getAllDays(applicationContext);

		ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, days);

		dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		daySpinner.setAdapter(dayAdapter);
		
		// Set pool
		final Spinner spinner = (Spinner) findViewById(R.id.poolName);

		List<String> pools = PoolManager.getAllPools(applicationContext);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, pools);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);

		// Set type
		final Spinner typeSpinner = (Spinner) findViewById(R.id.swimType);

		List<String> types = PoolManager.getAllTypes(applicationContext);

		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, types);

		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		typeSpinner.setAdapter(typeAdapter);

		// Add listener to button
		final Button searchButton = (Button) findViewById(R.id.searchButton);

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String day = daySpinner.getSelectedItem().toString();
				
				final String poolName = spinner.getSelectedItem().toString();

				final String swimType = typeSpinner.getSelectedItem()
						.toString();

				// Potential thread
				DownLoadSchedule task = new DownLoadSchedule(v);

				task.execute(new String[] { poolName, swimType, day });

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class DownLoadSchedule extends AsyncTask<String, Void, String> {

		private View v;

		DownLoadSchedule(View v) {
			this.v = v;
		}

		@Override
		protected String doInBackground(String... args) {
			
			final NodeList schedule = PoolManager.getSchedule(args[0], args[1], args[2]);

			final Doc newDoc = getDoc(schedule);
			
			final Intent intent = new Intent(v.getContext(),
					SearchResultActivity.class);

			intent.putExtra("time", newDoc);

			startActivity(intent);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

		}
	}

	
	public Doc getDoc(NodeList schedule){
		
//		List<String> day = PoolManager.getAttributeValue(schedule.item(0),
//				"day");
		
		List<String> start_time = PoolManager.getAttributeValue(schedule.item(0),
		"start_time");

		List<String> end_time = PoolManager.getAttributeValue(schedule.item(0),
		"end_time");
		
		Doc newDoc = new Doc();
		
		newDoc.setStart_time(start_time.get(0));
		
		newDoc.setEnd_time(end_time.get(0));
		
		return newDoc;
	}
}
