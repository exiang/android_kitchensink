package com.mobeegen.kitchensink;

import com.mobeegen.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class GrabUseCacheActivity extends Activity 
{
	final static String TAG = "GrabUseCacheActivity";
	WebView myWebView;
	String url;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabusecache);

        myWebView = (WebView) findViewById(R.id.webViewGrabUseCache);
        
        url = "http://htmlsample.yeesiang.com/time.php";
        grabContent(url);
        
	}
	
	public void grabContent(String url) 
	{
		// use cache
		if(cacheExists(url))
        {
			Toast.makeText(getApplicationContext(), "Using Cache. Reload after 10 sec for fresh.", Toast.LENGTH_SHORT).show();
			GrabFromCacheTask cacheTask = new GrabFromCacheTask();
			cacheTask.execute(url);
        }
		// use fresh
        else
        {
        	Toast.makeText(getApplicationContext(), "Using Fresh", Toast.LENGTH_SHORT).show();
        	GrabFromWebTask webTask = new GrabFromWebTask();
        	webTask.execute(url);
        }
		
	}
	
	public void setContent(String content)
	{
		myWebView.loadData(content, "text/html", "UTF-8");
	}

	private boolean cacheExists(String url)
	{
		return cacheExists(url, 10);
	}
	
	private boolean cacheExists(String url, int expireInSecond)
	{
		Log.v(TAG, "start cacheExists");
		
		boolean cacheFileExists = false;
		long nowTimestamp = System.currentTimeMillis();
		Log.v(TAG, "current timestamp: "+nowTimestamp);
		
		// is cache file exists?
		File cacheDir = new File (getExternalFilesDir(null) + "/cache");
		File cacheFile = new File(cacheDir, Mobeegen.base64EncodeUrl(url));
	    if (cacheFile != null && cacheFile.exists()) 
	    {
	    	long lastModifiedTimestamp = cacheFile.lastModified();
	    	Log.v(TAG, "cache file timestamp: "+lastModifiedTimestamp);
	    	
		    // check the cache is expired or not?
	    	if(nowTimestamp - lastModifiedTimestamp < (expireInSecond*1000))
	    	{
	    		cacheFileExists = true;
	    	}
	    	
	    }
	    

	    Log.v(TAG, "cache file exists: "+cacheFileExists);
		return cacheFileExists;
	}
	
	private boolean cacheContent(String url, String content)
	{
		Log.v(TAG, "cache url: "+url);
		Log.v(TAG, "cache content: "+content);
		
		// create cache file
		File cacheDir = new File (getExternalFilesDir(null) + "/cache");
		cacheDir.mkdirs();
		File cacheFile = new File(cacheDir, Mobeegen.base64EncodeUrl(url));
		
		Log.v(TAG, "cache location: "+cacheFile.getAbsolutePath());
			
		// check external storage available or not
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) 
		{
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} 
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
		{
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} 
		else 
		{
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		// write to external storage
		if(mExternalStorageAvailable && mExternalStorageWriteable)
		{
			Log.v(TAG, "external storage is available and writable");
			
			try
			{
				Log.v(TAG, "start writing cache...");
				
				FileOutputStream fos = new FileOutputStream(cacheFile);
				fos.write(content.getBytes());
				fos.close();
				
				Log.v(TAG, "finished writing cache");
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return false;
	}
	 
	private class GrabFromWebTask extends AsyncTask<String, Void, HashMap<String, String>> 
	{
	    @Override
	    protected HashMap<String, String> doInBackground(String... urls) 
	    {
	    	Log.v(TAG, "start GrabFromWebTask");
	    	
			String response = "";
			HashMap<String, String> result = new HashMap<String, String>();
			 
			for (String url : urls) 
			{
			    DefaultHttpClient client = new DefaultHttpClient();
			    HttpGet httpGet = new HttpGet(url);
		    
			    try 
			    {
			      HttpResponse execute = client.execute(httpGet);
			      InputStream content = execute.getEntity().getContent();
			
			      BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
			      String s = "";
			      
		          while ((s = buffer.readLine()) != null) 
		          {
		            response += s;
		          }
		        } 
		        catch (Exception e) 
		        {
		          e.printStackTrace();
		        }
			}
			
			result.put("response", response);
			result.put("url", url);
		    return result;
	    }
		
	    protected void onPostExecute(HashMap<String, String> result) 
	    {
	    	// write to cache if not exists
	    	if(!cacheExists(result.get("url")))
	    	{
	    		cacheContent(result.get("url"), result.get("response"));
	    	}
	    	setContent(result.get("response"));
	    }
	}
	
	
	private class GrabFromCacheTask extends AsyncTask<String, Void, HashMap<String, String>> 
	{
	    @Override
	    protected HashMap<String, String> doInBackground(String... urls) 
	    {
	    	Log.v(TAG, "start GrabFromCacheTask");
	    	
	    	StringBuilder  response = new StringBuilder();
			HashMap<String, String> result = new HashMap<String, String>();
			
			File cacheDir = new File (getExternalFilesDir(null) + "/cache");
			File cacheFile = new File(cacheDir, Mobeegen.base64EncodeUrl(url));
			
			try 
			{
			    BufferedReader br = new BufferedReader(new FileReader(cacheFile));
			    String line;

			    while ((line = br.readLine()) != null) 
			    {
			    	response.append(line);
			    	response.append('\n');
			    }
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			result.put("response", response.toString());
			result.put("url", url);
		    return result;
	    }
		
	    protected void onPostExecute(HashMap<String, String> result) 
	    {
	    	setContent(result.get("response"));
	    }
	}

}
