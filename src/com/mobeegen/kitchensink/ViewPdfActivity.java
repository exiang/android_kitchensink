package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ViewPdfActivity extends Activity 
{
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_imagezoomerwebview);  
        
        WebView myWebView = (WebView) findViewById(R.id.webview1);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setSupportMultipleWindows(false);
        // exiang: this function no longer supported in new api
        // myWebView.getSettings().setPluginsEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setJavaScriptEnabled(true);
        
        myWebView.setWebViewClient(new WebViewClient()
		{
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) 
			{
				//webView.stopLoading();
				//webView.loadUrl("file:///android_asset/webViewError.html");
				Toast.makeText(getApplicationContext(), "Error! " + description, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
				// to allow url from ads that connect to market to work
				if (url != null && url.startsWith("market://")) 
				{
					view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					return true;
				}
				else if (url.startsWith("tel:"))
				{ 
					view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	                return true;
				}
				else
				{
					// return false to let the WebView handle the URL
					return false;
				}
				
            }
		 });
        

        // case 1
        // webview do not support opening pdf in app
        //myWebView.loadUrl("file:///android_asset/sample.pdf");
        
        // case 2
        // may use google doc for proxy
        //myWebView.loadUrl("https://docs.google.com/viewer?url=http://htmlsample.yeesiang.com/sample.pdf");
        myWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://htmlsample.yeesiang.com/sample.pdf");
        
        // case 3
        // try using js pdf
        // no luck, only run from web server but not local version
        //myWebView.loadUrl("file:///android_asset/mozillapdf/index.html");
        

    }
}