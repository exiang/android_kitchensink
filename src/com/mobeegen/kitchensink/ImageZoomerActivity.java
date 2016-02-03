package com.mobeegen.kitchensink;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageZoomerActivity extends Activity 
{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        
        TouchImageView img = new TouchImageView(this);
        InputStream inputStream = null;
    
        try 
        {
        	// image cannot too large or else memory run out and give error
        	inputStream = getAssets().open("im_verybig2_c.png");        	
            Bitmap veryBigImg = BitmapFactory.decodeStream(inputStream);
	        img.setImageBitmap(veryBigImg);
	        img.setScrollbarFadingEnabled(true);
	        img.setMaxZoom(10f);
	        setContentView(img);
        } 
        catch (IOException e1) 
        {
            e1.printStackTrace();
        }
        
    }
}