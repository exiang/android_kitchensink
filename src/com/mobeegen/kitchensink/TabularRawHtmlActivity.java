
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
import com.mobeegen.ui.tabular.Tabular;

public class TabularRawHtmlActivity extends Activity
{
	final static String TAG = "TabularRawHtmlActivity";
	Resources r;

	protected LinearLayout linearLayoutTabular;
	protected WebView webView;

	ToggleButton toggleButtonOverview, toggleButtonResponsive;

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

		tabular = new Tabular("Raw HTML Table (for rowspan & colspan)");
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
		Table table1 = new Table("Actual vs Budget Sales");

		// let the api handle <table> tag
		StringBuilder stringBuilder = new StringBuilder();

		// this demo the multi row with colspan table header and also the
		// sortable feature driven by tabular.js
		// notice that all column that need to be sorted have to be inside thead
		stringBuilder.append("<thead>");
		stringBuilder
				.append("<tr><th colspan=\"2\"></th><th colspan=\"2\">Actual</th><th colspan=\"2\">Forcast</th></tr>");
		stringBuilder
				.append("<tr><th>No</th><th>Item</th><th>Sales Unit</th><th>Sales Amount</th><th>Sales Unit</th><th>Sales Amount</th></tr>");
		stringBuilder.append("</thead>");

		// the table body
		stringBuilder.append("<tbody>");
		stringBuilder
				.append("<tr><td>1</td><td>Sun Flower</td><td>234</td><td>$300.00</td><td>300</td><td>$324.00</td></tr>");
		stringBuilder
				.append("<tr><td>2</td><td>Rose</td><td>634</td><td>$644</td><td>700</td><td>$800.00</td></tr>");
		stringBuilder
				.append("<tr><td>3</td><td>Lily</td><td>1123</td><td>$3500.00</td><td>1200</td><td>$4000.00</td></tr>");
		stringBuilder.append("</tbody>");

		table1.setRawHtml(stringBuilder.toString());

		tabular.addTable(table1);

		String htmlBuffer = tabular.toHtml();
		Log.v(TAG, "html: " + htmlBuffer);

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

		reloadWebview();

		// webView.reload();
		// linearLayoutTabular.addView(tabular .getWebView(getBaseContext(),
		// "file:///android_asset/"));;

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
