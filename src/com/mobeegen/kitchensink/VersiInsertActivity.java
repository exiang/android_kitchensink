package com.mobeegen.kitchensink;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobeegen.Versi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class VersiInsertActivity extends Activity
{
	final static String TAG = "VersiInsertActivity";
	final Activity activity = this;
	
	Versi v;
	
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versiinsert);
        
        String packageCode = "com.mobeegen.kitchensink";
        String accessKey = getResources().getString(R.string.versiAccessKey);
        boolean forceUpdate = false;
        boolean silentMode = true;
        
        Versi versi = new Versi(getApplicationContext(), activity,
				packageCode, accessKey);
		versi.checkForUpdate(forceUpdate, silentMode);
        
	}
}
