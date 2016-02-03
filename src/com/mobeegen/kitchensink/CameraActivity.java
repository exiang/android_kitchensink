package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CameraActivity  extends Activity {
	protected Button cam1Btn, cam2Btn, cam3Btn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cam1Btn = (Button) findViewById(R.id.cam1Btn);
        cam1Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), CameraFrontActivity.class)); 
            }
        });
        
        cam2Btn = (Button) findViewById(R.id.cam2Btn);
        cam2Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
        
        cam3Btn = (Button) findViewById(R.id.cam3Btn);
        cam3Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), CameraViewActivity.class)); 
            }
        });
	}
}
