package com.colman.osp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.colman.parser.NodeListSingleton;
import com.colman.parser.PoolManager;

public class SwimmingPoolSearchResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_swimming_pool_search_result);

		final NodeList nodeList = NodeListSingleton.getInstance().getNodeList();

		final List<String> breadcrumbList = NodeListSingleton.getInstance()
				.getBreadcrumb();

		final TextView breadcrumb = (TextView) findViewById(R.id.breadcrumb);

		final StringBuilder strBuilder = new StringBuilder();

		for (final String next : breadcrumbList) {
			strBuilder.append(next);
			strBuilder.append(" > ");
		}
		breadcrumb.setText(strBuilder.toString());

		final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

		for (int i = nodeList.getLength() - 1; i >= 0; i--) {

			final TableRow newRow = new TableRow(this);

			final TextView startTimeTextView = new TextView(this);

			final Node node = nodeList.item(i);

			final List<String> startTime = PoolManager.getAttributeValue(node,
					"start_time");

			startTimeTextView.setText(startTime.get(0));

			TextView endTimeTextView = new TextView(this);

			final List<String> endTime = PoolManager.getAttributeValue(node,
					"end_time");

			endTimeTextView.setText(endTime.get(0));

			final TextView blankTextView = new TextView(this);

			blankTextView.setText("		to		");

			newRow.addView(startTimeTextView);

			newRow.addView(blankTextView);

			newRow.addView(endTimeTextView);

			tableLayout.addView(newRow);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result, menu);
		return true;
	}

}
