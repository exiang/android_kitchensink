package com.mobeegen.kitchensink;

import com.mobeegen.kitchensink.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class HtmlTextViewActivity extends Activity
{
	TextView myTextView;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmltextview);

        myTextView = (TextView) findViewById(R.id.textView1);
		myTextView.setText(Html.fromHtml(getString(R.string.html1)));
		myTextView.setMovementMethod(new ScrollingMovementMethod());
	}
}