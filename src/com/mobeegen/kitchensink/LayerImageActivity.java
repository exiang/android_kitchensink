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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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

public class LayerImageActivity extends Activity
{
	final static String TAG = "LayerImageActivity";
	ImageView myImageView;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layerimage);
        
        myImageView = (ImageView) findViewById(R.id.imageViewLayer);
        
        Resources r = getResources();
        Drawable[] layers = new Drawable[2];
        layers[0] = r.getDrawable(R.drawable.im_koala);
        layers[1] = r.getDrawable(R.drawable.im_overlay);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        myImageView.setImageDrawable(layerDrawable);
       
	}
}