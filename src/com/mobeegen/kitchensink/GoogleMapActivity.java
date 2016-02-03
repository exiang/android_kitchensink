package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GoogleMapActivity extends Activity
{
	protected Button googleMap1Btn, googleMap2Btn, googleMap3Btn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

        googleMap1Btn = (Button) findViewById(R.id.googleMap1Btn);
        googleMap1Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), GoogleMap1Activity.class)); 
            }
        });
        
        googleMap2Btn = (Button) findViewById(R.id.googleMap2Btn);
        googleMap2Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), GoogleMap2Activity.class)); 
            }
        });
        
        googleMap3Btn = (Button) findViewById(R.id.googleMap3Btn);
        googleMap3Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), GoogleMap3Activity.class)); 
            }
        });
	}
}
