package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NfcActivity extends Activity
{
	protected Button nfcWriteBtn, nfcReadBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        nfcWriteBtn = (Button) findViewById(R.id.nfcWriteBtn);
        nfcWriteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), NfcWriteActivity.class)); 
            }
        });
        
        nfcReadBtn = (Button) findViewById(R.id.nfcReadBtn);
        nfcReadBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), NfcReadActivity.class)); 
            }
        });
        
	}
}
