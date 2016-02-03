package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomTitleBarActivity extends Activity
{
	protected Button customTitleBar1Btn;
	protected Button customTitleBar2Btn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customtitlebar);

        customTitleBar1Btn = (Button) findViewById(R.id.customTitleBar1Btn);
        customTitleBar1Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), CustomTitleBar1Activity.class)); 
            }
        });
        
        customTitleBar2Btn = (Button) findViewById(R.id.customTitleBar2Btn);
        customTitleBar2Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), CustomTitleBar2Activity.class)); 
            }
        });

	}
}
