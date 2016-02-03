package com.mobeegen.kitchensink;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ImageZoomerWebviewActivity extends Activity 
{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagezoomerwebview);  
       
        WebView myWebView = (WebView) findViewById(R.id.webview1);
        myWebView.loadUrl("file:///android_asset/im_verybig2_c.png");
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);

    }
}