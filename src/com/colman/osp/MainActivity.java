package com.colman.osp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.colman.om.Recreation;
import com.colman.rest.ConfigurationManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		final Context applicationContext = this.getApplicationContext();

		final ConfigurationManager config = ConfigurationManager
				.getInstance();
		
		config.setContext(applicationContext);
		
		config.init();

		final com.colman.om.Activity activity = config.getActivity();

		LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);

		LayoutParams layoutParameter = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		for (final Recreation nextRecreation : activity.getRecreationList()) {

			final Button newButton = new Button(this);

			newButton.setText(nextRecreation.getName());

			layout.addView(newButton, layoutParameter);

			newButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {

					final Intent intent = new Intent(v.getContext(),
							SearchActivity.class);

					intent.putExtra("recreationName", nextRecreation.getName());
					
					startActivity(intent);

				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recreations, menu);
		return true;
	}

}
