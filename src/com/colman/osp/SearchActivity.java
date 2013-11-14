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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.colman.om.Mapping;
import com.colman.om.Recreation;
import com.colman.om.SelectQuery;
import com.colman.parser.NodeListSingleton;
import com.colman.parser.PoolManager;
import com.colman.rest.ConfigurationManager;

public class SearchActivity extends Activity {

	private List<SelectQuery> selectQueryList = new ArrayList<SelectQuery>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		final Intent intent = getIntent();

		final String recreationName = intent.getStringExtra("recreationName");

		final Context applicationContext = this.getApplicationContext();

		final ConfigurationManager config = ConfigurationManager
				.getInstance();

		config.setContext(applicationContext);
		
		config.setRecreationName(recreationName);
		
		config.init();
		
		final Recreation recreation = config.getRecreation(recreationName);

		final List<Mapping> queryParameterPrompts = recreation
				.getQueryParameterPrompt().getQueryParameterPromptList();

		final LinearLayout layout = (LinearLayout) findViewById(R.id.LinearLayout1);

		final LayoutParams layoutParameter = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		for (final Mapping nextQueryPrompt : queryParameterPrompts) {

			final Spinner newSpinner = new Spinner(this);

			final List<String> items = PoolManager.getSpinnerItems(
					applicationContext, nextQueryPrompt.getValue());

			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, items);

			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			newSpinner.setAdapter(adapter);

			newSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					final String value = newSpinner.getItemAtPosition(arg2)
							.toString();

					final Mapping nameValuePair = PoolManager.getNameValuePair(
							applicationContext, nextQueryPrompt.getValue(),
							value);

					addQuery(selectQueryList, newSpinner.hashCode(),
							nameValuePair);
				}

				private void addQuery(final List<SelectQuery> selectQueryList,
						final int hashCode, final Mapping nameValuePair) {

					boolean found = false;

					for (final SelectQuery next : selectQueryList) {
						if (next.getName() == hashCode) {
							next.setMapping(nameValuePair);
							found = true;
							break;
						}
					}

					if (!found) {
						SelectQuery newQuery = new SelectQuery();
						newQuery.setName(hashCode);
						newQuery.setMapping(nameValuePair);
						selectQueryList.add(newQuery);
					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

			layout.addView(newSpinner, layoutParameter);

		}

		// Add listener to button
		final Button searchButton = new Button(this);

		searchButton.setText("Search");
		
		layout.addView(searchButton, layoutParameter);

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {

				List<Mapping> selectQueryParameter = new ArrayList<Mapping>();

				for (final SelectQuery next : selectQueryList) {
					selectQueryParameter.add(next.getMapping());

				}
				// Potential thread
				DownLoadSchedule task = new DownLoadSchedule(v, recreation,
						selectQueryParameter);

				task.execute();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	private class DownLoadSchedule extends AsyncTask<String, Void, String> {

		private View v;

		private List<Mapping> promptParameter;

		private Recreation recreation;

		DownLoadSchedule(final View v, final Recreation recreation,
				final List<Mapping> promptParameter) {

			this.v = v;

			this.promptParameter = promptParameter;

			this.recreation = recreation;
		}

		@Override
		protected String doInBackground(String... args) {

			final List<Mapping> parameter = new ArrayList<Mapping>();

			parameter.addAll(recreation.getParameter().getMappings());

			parameter.addAll(promptParameter);

			final NodeList nodeList = PoolManager.getNodeList(
					recreation.getHost(),
					Integer.valueOf(recreation.getPort()),
					recreation.getPath(), parameter);

			NodeListSingleton.getInstance().setNodeList(nodeList);

			final List<String> breadcrumb = new ArrayList<String>();

			for (final Mapping nextMapping : promptParameter) {
				breadcrumb.add(nextMapping.getValue());
			}

			NodeListSingleton.getInstance().setBreadcrumb(breadcrumb);

			final Intent intent = new Intent(v.getContext(),
					SwimmingPoolSearchResultActivity.class);

			startActivity(intent);

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

		}
	}
}
