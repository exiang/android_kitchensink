package com.mobeegen.kitchensink;

import com.mobeegen.kitchensink.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WebviewActivity extends Activity
{
	String startUrl = "http://www.mobeegen.com";
	
	FrameLayout webViewContainer;
	ImageView loadingView;
	WebView webView;
	
	ProgressBar loadingProgressBar;
	boolean firstLaunch = true;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        
        Bundle b = getIntent().getExtras();
        startUrl = b.getString("startUrl");
        
        webViewContainer = (FrameLayout) findViewById(R.id.webview_container);
		webView = new WebView(getApplicationContext());
		webViewContainer.addView(webView);
		
		loadingProgressBar = (ProgressBar)findViewById(R.id.progressbar_Horizontal);
		
		// initialize webview    
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.getSettings().setSupportMultipleWindows(false);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);
		// caching
		webView.getSettings().setAppCacheMaxSize(1024*1024*8);
		String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
		webView.getSettings().setAppCachePath(appCachePath);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		
		// using webChromeClient
		webView.setWebChromeClient(new WebChromeClient()
		{	
			public void onProgressChanged(WebView view, int progress) 
			{
				super.onProgressChanged(view, progress);
				loadingProgressBar.setProgress(progress);
		
				// hide the progress bar if the loading is complete
				if (progress == 100) 
				{
					loadingProgressBar.setVisibility(View.GONE);
					
					if(firstLaunch)
					{
					    webView.setVisibility(View.VISIBLE);
					}
					
					firstLaunch = false;
				} 
				else
				{
					loadingProgressBar.setVisibility(View.VISIBLE);

					if(firstLaunch)
					{
					    webView.setVisibility(View.VISIBLE);
					}
				}
			}
			
			public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) 
			{
			    callback.invoke(origin, true, false);
			}
		});
		
		// webViewClient
		webView.setWebViewClient(new WebViewClient()
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

		webView.loadUrl(startUrl);
	}
	
	@Override
	protected void onDestroy()
	{		
		super.onDestroy();
		webViewContainer.removeAllViews();
		webView.destroy();
	}
}