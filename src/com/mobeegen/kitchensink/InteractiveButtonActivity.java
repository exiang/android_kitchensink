package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InteractiveButtonActivity extends Activity
{

	protected Button callBtn;
	protected Button smsBtn;
	protected Button emailBtn;
	protected Button webBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactivebutton);
        
        callBtn = (Button) findViewById(R.id.callBtn);
        smsBtn = (Button) findViewById(R.id.smsBtn);
        emailBtn = (Button) findViewById(R.id.emailBtn);
        webBtn = (Button) findViewById(R.id.webBtn);
        
        callBtn.setOnClickListener(btnListener);
        smsBtn.setOnClickListener(btnListener);
        emailBtn.setOnClickListener(btnListener);
        webBtn.setOnClickListener(btnListener);
	}
	
	public OnClickListener btnListener = new OnClickListener() 
	{
		public void onClick (View v) 
		{
			switch (v.getId()) 
			{
				case R.id.callBtn:
				{
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:+60126130617"));
				    startActivity(intent);
					break;
				}
				case R.id.smsBtn:
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("sms:+60126130617"));
					intent.putExtra("sms_body", "Hello World from com.mobeegen.kitchensink");
				    startActivity(intent);
				    break;
				}
				case R.id.emailBtn:
				{
					Intent intent = new Intent(android.content.Intent.ACTION_SEND);
					intent.setType("plain/text");
					intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "exiang@websight.com.my", "muzani@websight.com.my"});
					intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hello World from com.mobeegen.kitchensink");
					intent.putExtra(android.content.Intent.EXTRA_TEXT, "The sink is dirty, please clean it up.. :P");
					startActivity(intent);
					break;
				}
				case R.id.webBtn:
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://websight.com.my"));
				    startActivity(intent);
				    break;
				}
			}
		}
	};
}

