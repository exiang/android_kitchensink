
package com.mobeegen.kitchensink;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PullRefreshFeedActivity extends ListActivity
{
	private LinkedList<String> mListItems;
	PullToRefreshListView ptrList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pullrefreshfeed);

		ptrList = (PullToRefreshListView) findViewById(R.id.list);

		// Set a listener to be invoked when the list should be refreshed.
		ptrList.setOnRefreshListener(new OnRefreshListener<ListView>()
		{
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				ptrList.setLastUpdatedLabel(DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL));

				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});

		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListItems);

		setListAdapter(adapter);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]>
	{

		@Override
		protected String[] doInBackground(Void... params)
		{
			// Simulates a background job.
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			mListItems.addFirst("Added after refresh...");

			// Call onRefreshComplete when the list has been refreshed.
			ptrList.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats",
			"Abertam", "Abondance", "Ackawi", "Acorn", "Adelost",
			"Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
			"Aisy Cendre", "Allgauer Emmentaler"};

}
