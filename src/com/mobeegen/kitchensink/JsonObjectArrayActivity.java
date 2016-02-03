
package com.mobeegen.kitchensink;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobeegen.Mobeegen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JsonObjectArrayActivity extends Activity
{
	final static String TAG = "JsonObjectArrayActivity";
	final Activity activity = this;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blank);
		
		AsyncHttpClient client = new AsyncHttpClient();

		client.get
		(
			// exiang: switch between these 2 urls to test, no matter which you use should get the result 'white'
			// "http://test.mobeegen.com/json/json_object_array.php", /*array*/
			"http://test.mobeegen.com/json/json_object_array1.php", /* object*/
			new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart()
				{
				
				}
	
				@Override
				public void onFinish()
				{
				}
	
				@Override
				public void onFailure(Throwable error,
						String content)
				{
					Log.v(TAG, "failed to grab data: "
							+ error.getMessage());
					Toast.makeText(
									activity,
									error.getMessage(),
									Toast.LENGTH_LONG)
							.show();
				}
	
				@Override
				public void onSuccess(String response)
				{
					Log.v(TAG, "response: " + response);
	
					try
					{
						/*
						  	{
								data: 
								{
									colours: 
									{
										name: "white"
									}
								}
							}
						 */
						JSONObject json = new JSONObject(response);
						JSONObject data = json.getJSONObject("data");
						
						// is array
						//JSONArray colours = data.getJSONArray("colours");
						
						// start is object
						/*
						JSONArray colours = null;
						if(data.has("colours"))
						{
							Log.v(TAG, "colours detected");
							
							// is array
							if(data.optJSONArray("colours") != null)
							{
								Log.v(TAG, "colours is array");
								colours = data.getJSONArray("colours");
							}
							// is object
							else if(data.optJSONObject("colours") != null)
							{
								Log.v(TAG, "colours is object");
								// transform into array
								//data.accumulate("colours", data.getJSONObject("colours"));
								//colours.put(data.optJSONObject("colours"));
								//colours = data.getJSONArray("colours");
								String coloursString = "["+(data.getJSONObject("colours")).toString()+"]";
								colours = new JSONArray(coloursString);
								
							}
							
						}*/
						// end if object
						
						// use build in function to force any object into array
						JSONArray colours = util.Helper.forceJsonArray(data, "colours");
						
						Log.v(TAG, Integer.toString(colours.length()));
						Log.v(TAG, colours.toString());
						
						JSONObject color1 = (JSONObject) colours.get(0);
						String color = color1.getString("name");
						
						Toast.makeText(
								activity,
								color,
								Toast.LENGTH_LONG)
						.show();
						
						
					}
					catch (Exception e)
					{
						Log.v(
								TAG,
								"failed to parse grabbed data: "
										+ e.getMessage());
						e.printStackTrace();
					}
				}
			}
		);

	}


}
