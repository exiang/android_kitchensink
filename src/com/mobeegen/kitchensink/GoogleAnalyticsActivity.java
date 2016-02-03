package com.mobeegen.kitchensink;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
 
public class GoogleAnalyticsActivity extends Activity
{
	final static String TAG = "GoogleAnalyticsActivity";
	protected Button googleAnalyticsAction1Btn;
	protected Button googleAnalyticsAction2Btn;
  
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleanalytics);
        
        googleAnalyticsAction1Btn = (Button) findViewById(R.id.googleAnalyticsAction1Btn);
        googleAnalyticsAction1Btn.setOnClickListener(btnListener);
        
        googleAnalyticsAction2Btn = (Button) findViewById(R.id.googleAnalyticsAction2Btn);
        googleAnalyticsAction2Btn.setOnClickListener(btnListener);
	}
	

	public OnClickListener btnListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.googleAnalyticsAction1Btn :
				{
					try 
					{
						// Category, Action, Label, Value
						EasyTracker.getTracker().trackEvent("Clicks", "Button Action 1", "clicked", null );
					}
					catch( Exception error ) 
					{
						Log.e(TAG, "ButtonClick: " + error.toString() );
					}
						
					Toast.makeText(getApplicationContext(), "Action 1 Clicked", Toast.LENGTH_SHORT).show();
					break;
				}
				case R.id.googleAnalyticsAction2Btn :
				{
					try 
					{
						// Category, Action, Label, Value
						EasyTracker.getTracker().trackEvent("Clicks", "Button Action 2", "clicked", null );
					}
					catch( Exception error ) 
					{
						Log.e(TAG, "ButtonClick: " + error.toString() );
					}
						
					Toast.makeText(getApplicationContext(), "Action 2 Clicked", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	};
	
	@Override
	public void onStart() 
	{
	    super.onStart();
	    EasyTracker.getInstance().activityStart(this); // Add this method.
	}
	
	@Override
	public void onStop() 
	{
	    super.onStop();
	    EasyTracker.getInstance().activityStop(this); // Add this method.
	}
}