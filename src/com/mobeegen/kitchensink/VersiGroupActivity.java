package com.mobeegen.kitchensink;

import com.mobeegen.VersiActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VersiGroupActivity extends Activity 
{
	
	protected Button versiBtn;
	protected Button versiManualBtn;
	protected Button versiInsertBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versigroup);
        

		versiBtn = (Button) findViewById(R.id.versiBtn);
		versiManualBtn = (Button) findViewById(R.id.versiManualBtn);
		versiInsertBtn = (Button) findViewById(R.id.versiInsertBtn);
		

		versiBtn.setOnClickListener(btnListener);
		versiManualBtn.setOnClickListener(btnListener);
		versiInsertBtn.setOnClickListener(btnListener);
       
	}
	
	public OnClickListener btnListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{

				case R.id.versiBtn :
					Intent i = new Intent(getApplicationContext(),
							VersiActivity.class);
					i.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
					i.putExtra("forceUpdate", false);
					i.putExtra("accessKey", getResources().getString(R.string.versiAccessKey));
					i.putExtra("redirect2Class", "com.mobeegen.kitchensink.DoorActivity");
					startActivity(i);
					finish();
					break;
				case R.id.versiManualBtn :
					startActivity(new Intent(getApplicationContext(),
							VersiManualActivity.class));
					break;
				case R.id.versiInsertBtn :
					startActivity(new Intent(getApplicationContext(),
							VersiInsertActivity.class));
					break;
			}
		}
	};
}
