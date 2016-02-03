package com.mobeegen.kitchensink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import com.loopj.android.http.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ReadTwitterActivity extends Activity
{
	JSONArray jsonArray;
	ListView listView1;
	ProgressDialog loadingDialog;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readtwitter);
        listView1 = (ListView) findViewById(R.id.listView1);
        
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.twitter.com/1/statuses/user_timeline.json?trim_user=true&screen_name=websightmy&count=10&exclude_replies=true", new AsyncHttpResponseHandler() 
        {
        	/**
             * Fired when the request is started, override to handle in your own code
             */
        	@Override
        	public void onStart()
        	{
        		loadingDialog = ProgressDialog.show(ReadTwitterActivity.this, "", "Loading... Please wait.", true);
        	}
        	
        	 /**
             * Fired in all cases when the request is finished, after both success and failure, override to handle in your own code
             */
        	@Override
            public void onFinish() 
        	{
        		loadingDialog.dismiss();
        	}
        	
        	/**
             * Fired when a request fails to complete, override to handle in your own code
             * @param error the underlying cause of the failure
             * @param content the response body, if any
             */
        	@Override
            public void onFailure(Throwable error, String content) { }
        	
        	/**
             * Fired when a request returns successfully, override to handle in your own code
             * @param content the body of the HTTP response from the server
             */
        	@Override            
            public void onSuccess(String response) 
            {
            	Log.v("json", "response: "+response);
            	
            	try
            	{
            		jsonArray = new JSONArray(response);
            		int length = jsonArray.length();

            		Log.v("json", "entries: "+length);
            		
            		List<String> listContents = new ArrayList<String>(length);
            		
            		for (int i = 0; i < length; i++) 
	    			{
	    	        	JSONObject e = jsonArray.getJSONObject(i);
	    	        	Log.v(ReadTwitterActivity.class.getName(), e.getString("text"));
	    	        	listContents.add(Html.fromHtml(e.getString("text")).toString());
	    			}

            		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, listContents);
            		listView1.setAdapter(arrayAdapter); 
            	}
            	catch (Exception e) 
    			{
    				e.printStackTrace();
    			}
            }
        });
	}
}
