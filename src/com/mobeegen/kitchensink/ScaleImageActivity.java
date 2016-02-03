package com.mobeegen.kitchensink;

import java.io.File;

import com.mobeegen.kitchensink.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ScaleImageActivity extends Activity
{
	final static String TAG = "ScaleImageActivity";
	ImageView myImageView;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaleimage);
        
        myImageView = (ImageView) findViewById(R.id.imageViewScale);
       
	}
	
	public void onStart()
	{
		super.onStart();
		
		
	}
	
	@TargetApi(11)
	@Override
	public void onWindowFocusChanged(boolean hasFocus) 
	{
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		//Here you can get the size!
		
		int originalWidth = myImageView.getDrawable().getIntrinsicWidth();
		int originalHeight = myImageView.getDrawable().getIntrinsicHeight();
		float ratio = (float) originalWidth / originalHeight;
		
		Log.v(TAG, "image original width: "+originalWidth);
		Log.v(TAG, "image original height: "+originalHeight);		
		Log.v(TAG, "image original ratio: "+ratio);
		  
		int newWidth = myImageView.getWidth();
		Log.v(TAG, "imageview width: "+newWidth);
		float diff = (float) newWidth / originalWidth;
		int newHeight = (int) Math.round(originalHeight * diff);
		
		Log.v(TAG, "imageview height: "+newHeight);
		myImageView.setLayoutParams(
                new FrameLayout.LayoutParams(
                		FrameLayout.LayoutParams.WRAP_CONTENT, 
                		FrameLayout.LayoutParams.WRAP_CONTENT)); 
		myImageView.getLayoutParams().width = newWidth;
		myImageView.getLayoutParams().height = newHeight;
		
	}
}