package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParamsActActivity extends Activity
{
	protected Button googleBtn;
	protected Button yahooBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramsact);
        
        googleBtn = (Button) findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(), WebviewActivity.class);
            	i.putExtra("startUrl", "http://www.google.com");
            	startActivity(i);
            }
        });
        
        yahooBtn = (Button) findViewById(R.id.yahooBtn);
        yahooBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getApplicationContext(), WebviewActivity.class);
            	i.putExtra("startUrl", "http://www.yahoo.com");
            	startActivity(i);
            }
        });

	}
}
