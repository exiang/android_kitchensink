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

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadImageFromWebActivity extends Activity
{
	final static String TAG = "LoadImageFromWebActivity";
	ImageView myImageView;
	ProgressBar spinner;
	//String imageUrl = "http://htmlsample.yeesiang.com/ubuntu.png";
	String imageUrl = "http://htmlsample.yeesiang.com/apple.png";
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadimagefromweb);
        
        myImageView = (ImageView) findViewById(R.id.imageViewLoadImageFromWeb);
        spinner = (ProgressBar) findViewById(R.id.progressBarLoadImageFromWeb);
        
        File cacheDir = new File (getExternalFilesDir(null) + "/cache");
		cacheDir.mkdirs();
        Log.v(TAG, "cacheDir: "+cacheDir.getAbsolutePath());
        
        // Get singletone instance of ImageLoader
        ImageLoader imageLoader = ImageLoader.getInstance();
        // Initialize ImageLoader with configuration. Do it once.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
	        .memoryCacheExtraOptions(480, 800)
	        .threadPoolSize(5)
	        .threadPriority(Thread.MIN_PRIORITY + 2)
	        .denyCacheImageMultipleSizesInMemory()
	        .offOutOfMemoryHandling()
	        .discCache(new UnlimitedDiscCache(cacheDir)) // You can pass your own disc cache implementation
	        .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
	        .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
	        .enableLogging()
	        .build();
        
        imageLoader.init(config);
        
        // Creates display image options for custom display task (all options are optional)
        DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
        .cacheInMemory()
        .cacheOnDisc()
        .imageScaleType(ImageScaleType.POWER_OF_2)
        .build();
        
        // Load and display image asynchronously
        imageLoader.displayImage(imageUrl, myImageView, displayOptions, new ImageLoadingListener() 
        {
            @Override
            public void onLoadingStarted() 
            {
            	spinner.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingFailed(FailReason failReason) 
            {
            	spinner.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onLoadingComplete(Bitmap loadedImage) 
            {
            	spinner.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onLoadingCancelled() 
            {
                // Do nothing
            }
        });
	}
}