package com.mobeegen.kitchensink;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobeegen.Versi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class VersiManualActivity extends Activity
{
	final static String TAG = "VersiManualActivity";
	final Activity activity = this;
	
	Versi v;
	EditText etRemoteVersionCode, etVersionCode;
	Button btnGetRemoteVersionCode, btnCheckUpdate, btnCheckUpdate2;
	ProgressDialog loadingDialog;
	AlertDialog alertDialog;
	String versiAccessKey;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versimanual);
        versiAccessKey = getResources().getString(R.string.versiAccessKey);
        
        etVersionCode = (EditText)findViewById(R.id.etVersionCode);
        etRemoteVersionCode = (EditText)findViewById(R.id.etRemoteVersionCode);
        btnGetRemoteVersionCode = (Button) findViewById(R.id.btnGetRemoteVersionCode);
        btnCheckUpdate = (Button) findViewById(R.id.btnCheckUpdate);
        btnCheckUpdate2 = (Button) findViewById(R.id.btnCheckUpdate2);
        
        v = new Versi(getApplicationContext(), activity,
				"com.mobeegen.kitchensink", versiAccessKey);
        
        // v.getLocalVersionCode()
        etVersionCode.setText(String.valueOf(v.getLocalVersionCode()));

        // get remote version code
        btnGetRemoteVersionCode.setOnClickListener(new OnClickListener()
    	{
    		@Override
    		public void onClick(View view)
    		{
    			 // code to get remote versionCode
    	        String jsonApiUrl = v.getAppVersionUrl();
    	        RequestParams params = new RequestParams();
    			params.put("key", versiAccessKey);
    			
    	        AsyncHttpClient client = new AsyncHttpClient();
    			client.post(jsonApiUrl, params, new AsyncHttpResponseHandler()
    			{
    				@Override
    				public void onStart()
    				{
    					Log.v(TAG, "call onStart");
    					String loadingMessage = activity.getString(R.string.versi_msg_loading);
    					loadingDialog = ProgressDialog .show(activity, "", loadingMessage, true);
    				}
    				
    				@Override
    				public void onFinish()
    				{
    					Log.v(TAG, "call onFinish");
    					loadingDialog.dismiss();
    				}
    				
    				@Override
    				public void onSuccess(String response)
    				{
    					Log.v(TAG, "call onSuccess");
    					Log.v(TAG, "json response: "+response);
    					try
    					{
    						JSONObject json = new JSONObject(response);
    						etRemoteVersionCode.setText(json.getString("versionCode"));
    						
    					} 
    					catch (Exception e)
    					{
    						e.printStackTrace();
    					}
    				}
    			});
    		}
    	});
        
        // check for update
        // no force update, not silent
        btnCheckUpdate.setOnClickListener(new OnClickListener()
    	{
    		@Override
    		public void onClick(View view)
    		{
    	        v.checkForUpdate(false, false);
    		}
    	});
    	
        // force update, not silent
        btnCheckUpdate2.setOnClickListener(new OnClickListener()
    	{
    		@Override
    		public void onClick(View view)
    		{
    	        v.checkForUpdate(true, false);
    		}
    	});
       
	}
}
