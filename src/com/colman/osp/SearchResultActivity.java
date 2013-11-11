package com.colman.osp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import com.colman.parser.Doc;

public class SearchResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		Intent intent = getIntent();
		
		final Doc doc = (Doc) intent.getSerializableExtra("time");
		
		final EditText startTime = (EditText) findViewById(R.id.startTime);
		
		startTime.setText(doc.getStart_time());
		
		final EditText endTime = (EditText) findViewById(R.id.endTime);
		
		endTime.setText(doc.getEnd_time());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
