
package com.mobeegen.kitchensink;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Text2SpeechActivity extends Activity
		implements
			TextToSpeech.OnInitListener
{
	final static String TAG = "Text2SpeechActivity";
	final Activity activity = this;

	Button btnSpeak;
	EditText enteredText;

	private TextToSpeech tts;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text2speech);

		tts = new TextToSpeech(this, this);

		// get a reference to the button element listed in the XML layout
		btnSpeak = (Button) findViewById(R.id.btnSpeak);

		enteredText = (EditText) findViewById(R.id.etEnterTTS);
		btnSpeak.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				String words = enteredText.getText().toString();
				speakOut(words);
			}
		});
	}

	@Override
	public void onDestroy()
	{
		// Don't forget to shutdown tts!
		if (tts != null)
		{
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status)
	{

		if (status == TextToSpeech.SUCCESS)
		{

			// int result = tts.setLanguage(Locale.US);
			int result = tts.setLanguage(Locale.CHINESE);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED)
			{
				Log.e(
						"TTS",
						"Chinese Language is not supported, auto revert back to US English");
				tts.setLanguage(Locale.US);
			}

			btnSpeak.setEnabled(true);

		}
		else
		{
			Log.e("TTS", "Initilization Failed!");
		}

	}

	private void speakOut(String text)
	{

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

}
