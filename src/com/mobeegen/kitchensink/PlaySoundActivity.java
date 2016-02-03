package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlaySoundActivity extends Activity
{
	protected Button playMp3Btn;
	protected Button playWavBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsound);
        
        playMp3Btn =(Button) findViewById(R.id.playMp3Btn);
        playWavBtn =(Button) findViewById(R.id.playWavBtn);
        
        playMp3Btn.setOnClickListener(btnListener);
        playWavBtn.setOnClickListener(btnListener);
	}
	
	public OnClickListener btnListener = new OnClickListener() {
		public void onClick (View v) 
		{
			switch (v.getId()) 
			{
				case R.id.playMp3Btn:
				{
					MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.camera_shutter);
					mediaPlayer.start(); // no need to call prepare(); create() does that for you
					break;
				}
				
				case R.id.playWavBtn:
				{
					MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.computer_talking);
					mediaPlayer.start(); // no need to call prepare(); create() does that for you
					break;
				}
			}
		}
	};
}
