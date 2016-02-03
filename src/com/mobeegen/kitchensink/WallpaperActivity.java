package com.mobeegen.kitchensink;

import java.io.IOException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class WallpaperActivity extends Activity
{
	final static String TAG = "WallpaperActivity";
	
	protected Button setWallpaperBtn;
	protected Toast toast;
	ImageView imWallpaper;
	
	Resources r;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        
        r = getResources();
        
        imWallpaper = (ImageView) findViewById(R.id.imageViewWallpaper);
        imWallpaper.setBackgroundResource(R.drawable.im_monalisa);

        
        setWallpaperBtn = (Button) findViewById(R.id.setWallpaperBtn);
        setWallpaperBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//startActivity(new Intent(getApplicationContext(), WallpaperActivity.class)); 
            	
            	WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try 
                {
                	Log.v(TAG, "desired min width: "+myWallpaperManager.getDesiredMinimumWidth());
                	Log.v(TAG, "desired min height: "+myWallpaperManager.getDesiredMinimumHeight());
                	
                	myWallpaperManager.setBitmap
                	(
                			drawableToWallpaperBitmap
            				(
            						r.getDrawable(R.drawable.im_monalisa),
            						myWallpaperManager.getDesiredMinimumWidth(), 
            						myWallpaperManager.getDesiredMinimumHeight()
            				)
            		);
                	
                	//myWallpaperManager.setResource(R.drawable.im_monalisa);
                	toast = Toast.makeText(getApplicationContext(), "Successfully set to your wallpaper", Toast.LENGTH_LONG);
                	
                } 
                catch (IOException e) 
                {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	                 
	                 toast = Toast.makeText(getApplicationContext(), "Failed to set to your wallpaper", Toast.LENGTH_LONG);
                }
                
                toast.show();
            }
        });
	}
	
	public static Bitmap drawableToWallpaperBitmap (Drawable drawable, int desiredWidth, int desiredHeight) 
	{
	    int originalWidth = drawable.getIntrinsicWidth();
	    int originalHeight = drawable.getIntrinsicHeight();
	    
	    int xStart = (int)((desiredWidth - originalWidth)/2);
	    int yStart = (int)((desiredHeight - originalHeight)/2);
	    
	    int xEnd = xStart+originalWidth; 
	    int yEnd = yStart+originalHeight;
	    
		Bitmap useThisBitmap = Bitmap.createBitmap(desiredWidth, desiredHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(useThisBitmap);
		drawable.setBounds(xStart, yStart, xEnd, yEnd);
		drawable.draw(canvas);
		
		return useThisBitmap;
	}
}
