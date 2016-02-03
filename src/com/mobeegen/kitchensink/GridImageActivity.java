package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GridImageActivity extends Activity  {
	final static String TAG = "GridImageActivity";
	
	Bitmap bitmapOriginal;
	int xTotal = 5, yTotal = 5;
	Bitmap[] bitmapArray;
	GridView gridViewGridImage;
	ImageView imageViewGridImageDebug;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridimage);
        
        gridViewGridImage = (GridView) findViewById(R.id.gridViewGridImage);

        bitmapOriginal = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.im_monalisa);
        bitmapArray = cutBitmap(bitmapOriginal);
        
        gridViewGridImage.setNumColumns(xTotal);
        gridViewGridImage.setHorizontalSpacing(1);
        gridViewGridImage.setVerticalSpacing(1);
        gridViewGridImage.setVerticalScrollBarEnabled(false);
        
        ImageAdapter imageAdapter = new ImageAdapter(this);
        imageAdapter.setImages(bitmapArray);
        gridViewGridImage.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
        
	}
	
	public Bitmap[] cutBitmap(Bitmap source)
	{
		Bitmap[] bitmapArray = new Bitmap[xTotal*yTotal];
		
		int i,j,x,y,w,h;
		int k=0;
		int width=source.getWidth();
		int height=source.getHeight();
		
		for(i=0; i<yTotal; i++)
		{
		   for(j=0; j<xTotal; j++)
		   {
			   // x, y, width, height
			   x = (j*width)/xTotal;
			   y = (i*height)/yTotal;
			   w = width/xTotal;
			   h = height/yTotal;
			   
			   Log.v(TAG, "i:"+i+" j:"+j+" k: "+k+" x:"+x+" y:"+y+" w:"+w+" h:"+h);
			   
			   bitmapArray[k] = Bitmap.createBitmap(source, x, y, w, h);
			   k++;
		   }
		}
		
		return bitmapArray;
	}
	
	 public class ImageAdapter extends BaseAdapter 
	 {
	        private Context mContext;
	        private Bitmap[] pics;

	        public ImageAdapter(Context c) 
	        {
	            mContext = c;
	        }

	        public int getCount() 
	        {
	            return pics.length;
	        }

	        public Object getItem(int position) {return null;}

	        public long getItemId(int position) {return 0;}

	        // create a new ImageView for each item referenced by the Adapter
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) 
	        {
	            ImageView imageView;
	            if (convertView == null) 
	            {  // if it's not recycled, initialize some attributes
	                imageView = new ImageView(mContext);
	                //You can set some params here
	            } 
	            else 
	            {
	                imageView = (ImageView) convertView;
	            }

	            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	            imageView.setImageBitmap(pics[position]);
	            return imageView;
	        }
	        
	        
	        public void setImages(Bitmap[] bitmapArray)
	        {
	            pics = bitmapArray;
	        }
	    }
}
