
package com.mobeegen.kitchensink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.mobeegen.ui.tabular.Table;
import com.mobeegen.ui.tabular.TableHeader;
import com.mobeegen.ui.tabular.TableRow;
import com.mobeegen.ui.tabular.Tabular;

public class TabularActivity extends Activity
{
	final static String TAG = "TabularActivity";
	Resources r;

	ToggleButton toggleButtonOverview, toggleButtonResponsive;
	protected LinearLayout linearLayoutTabular;
	protected WebView webView;

	Tabular tabular;
	boolean responsiveView = true;
	boolean overviewMode = true;

	@SuppressLint({"NewApi", "SetJavaScriptEnabled"})
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabular);

		r = getResources();

		linearLayoutTabular = (LinearLayout) findViewById(R.id.linearLayoutTabular);
		// webView = new WebView(this);
		// webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT));
		webView = (WebView) findViewById(R.id.webViewTabular);

		tabular = new Tabular("Results");
		tabular.setResponsiveView(responsiveView);
		tabular.addCssFilePath("master/test.css");

		toggleButtonOverview = (ToggleButton) findViewById(R.id.toggleButtonOverview);
		toggleButtonOverview.setChecked(overviewMode);
		toggleButtonOverview.setText("Overview");
		toggleButtonOverview.setTextOn("Overview");
		toggleButtonOverview.setTextOff("Overview");
		toggleButtonOverview.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// toggle
				overviewMode = !overviewMode;
				toggleButtonOverview.setChecked(overviewMode);

				webView.getSettings().setUseWideViewPort(overviewMode);
				webView.getSettings().setLoadWithOverviewMode(overviewMode);
				reloadWebview();
			}
		});

		toggleButtonResponsive = (ToggleButton) findViewById(R.id.toggleButtonResponsive);
		toggleButtonResponsive.setChecked(responsiveView);
		toggleButtonResponsive.setText("Responsive");
		toggleButtonResponsive.setTextOn("Responsive");
		toggleButtonResponsive.setTextOff("Responsive");
		toggleButtonResponsive.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// toggle
				responsiveView = !responsiveView;
				toggleButtonResponsive.setChecked(responsiveView);
				tabular.setResponsiveView(responsiveView);
				reloadWebview();
			}
		});

		// create table
		Table table1 = new Table("Movies Ranking in History");
		// create header
		TableHeader th1 = new TableHeader("Rank", TableHeader.PRIORITY_2);
		th1.setClasses("red");
		table1.addHeader(th1);

		// advance way
		TableHeader th2 = new TableHeader("Movie Title",
				TableHeader.PRIORITY_PERSIST);
		// th2.setClasses("blue");
		th2.setClasses("movie");
		th2.setPersist();
		table1.addHeader(th2);

		// quick way
		table1.addHeader(new TableHeader("Year", TableHeader.PRIORITY_3));
		table1.addHeader(new TableHeader("Rating", TableHeader.PRIORITY_1));
		table1.addHeader(new TableHeader("Reviews", TableHeader.PRIORITY_2));
		table1.addHeader(new TableHeader("Like", TableHeader.PRIORITY_4));
		table1.addHeader(new TableHeader("Comment", TableHeader.PRIORITY_4));
		table1.addHeader(new TableHeader("Share", TableHeader.PRIORITY_5));

		// create row
		// quick way
		table1.addRow(new TableRow("1", "Citizen Kane", "1941", "100%", "74",
				"200", "3030", "523234"));
		table1.addRow(new TableRow("2", "Casablanca", "1942", "97%", "64",
				"23200", "73030", "34535"));
		table1.addRow(new TableRow("3", "The Godfather", "1972", "97%", "87",
				"9568", "237235", "21146"));

		// advance way
		TableRow tr4 = new TableRow();
		tr4.setCells(
						"4", "Gone with the Wind", "1939", "96%", "87", "24",
						"34636", "12345");
		tr4.setClasses("green");
		table1.addRow(tr4);

		tabular.addTable(table1);

		webView.setVerticalFadingEdgeEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);

		if (overviewMode)
		{
			webView.getSettings().setUseWideViewPort(true);
			webView.getSettings().setLoadWithOverviewMode(true);
		}

		webView.getSettings().setSupportZoom(true);
		if (Build.VERSION.SDK_INT >= 11)
		{
			webView.getSettings().setBuiltInZoomControls(true);
			webView.getSettings().setDisplayZoomControls(false);
		}
		// linearLayoutTabular.addView(webView);

		// webView.reload();
		// linearLayoutTabular.addView(tabular .getWebView(getBaseContext(),
		// "file:///android_asset/"));;
		reloadWebview();
	}

	public void reloadWebview()
	{
		String htmlBuffer = tabular.toHtml();
		Log.v(TAG, "html: " + htmlBuffer);
		webView.setBackgroundColor(Color.parseColor("#aaaaaa"));
		webView.loadDataWithBaseURL(
									"file:///android_asset/", htmlBuffer,
									"text/html", "UTF-8", null);
	}
}
