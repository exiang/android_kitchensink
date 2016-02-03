package com.mobeegen.kitchensink;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gcm.GCMRegistrar;

public class GcmActivity extends Activity 
{
	static final String TAG = "GcmActivity";
	
	private EditText editTextRegisterID;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcm);
        
        editTextRegisterID = (EditText) findViewById(R.id.editTextRegisterID);
        
        // auto register this device
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) 
		{
			  GCMRegistrar.register(this, "961877547334");
			  regId = GCMRegistrar.getRegistrationId(this);
		} 
		else 
		{
		  Log.v(TAG, "Already registered");
		}
		
		editTextRegisterID.setText(regId);
		Log.v(TAG, "Register ID: "+regId);
		
	}

}
