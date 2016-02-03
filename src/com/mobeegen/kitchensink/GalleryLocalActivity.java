package com.mobeegen.kitchensink;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.mobeegen.kitchensink.GridImageActivity.ImageAdapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryLocalActivity extends Activity
{	
	final static String TAG = "GalleryLocalActivity";
	final Activity activity = this;
	
	int[] pThumbnailsArray = new int[6];
	protected ImageAdapter pAdapter;
	
	GridView gridView;
	
	public static Resources r;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerylocal);

        r =  getResources();
        
        gridView = (GridView) findViewById(R.id.gridview);
        pAdapter = new ImageAdapter();
		gridView.setAdapter(pAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				Toast.makeText(activity, "Gallery item "+position+" selected!", Toast.LENGTH_SHORT).show();
			}
			
		});
		
		for(int i=0; i<6; i++)
		{
			pThumbnailsArray[i] = r.getIdentifier("im_p"+(i+1),"drawable","com.mobeegen.kitchensink");
		}
	}
	
	@Override
	protected void onResume()
	{
		Log.v(TAG, "call onResume");
		super.onResume();
		
		pAdapter.notifyDataSetChanged();

	}
	
	public class ImageAdapter extends BaseAdapter 
	{
		@Override
		public int getCount() 
		{
			return pThumbnailsArray.length;
		}

		@Override
		public Object getItem(int position) 
		{
			return null;
		}

		@Override
		public long getItemId(int position) 
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			final ImageView imageView;
			if (convertView == null) 
			{
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
			} 
			else 
			{
				imageView = (ImageView) convertView;
			}

			Uri uriImageToLoad = Uri.parse("android.resource://com.mobeegen.kitchensink/"+pThumbnailsArray[position]);
			try
			{
		        Bitmap bm = this.decodeUri(uriImageToLoad);
		        imageView.setImageBitmap(bm);
		        bm = null;
		        System.gc();
		    }
		    catch(Exception e)
		    {
		        Log.v(TAG, "Remote Image Exception", e);
		    }
			

			return imageView;
		}
		
		private Bitmap decodeUri(Uri uri)
		{
		    try 
		    {
		    	InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(uri);
		    	
		        //Decode image size
		        BitmapFactory.Options o = new BitmapFactory.Options();
		        o.inJustDecodeBounds = true;
		        BitmapFactory.decodeStream(fileInputStream,null,o);

		        //The new size we want to scale to
		        final int REQUIRED_SIZE = 200;

		        //Find the correct scale value. It should be the power of 2.
		        int width_tmp=o.outWidth, height_tmp=o.outHeight;
		        int scale=1;
		        while(true)
		        {
		            if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
		                break;
		            width_tmp/=2;
		            height_tmp/=2;
		            scale*=2;
		        }

		        //Decode with inSampleSize
		        BitmapFactory.Options o2 = new BitmapFactory.Options();
		        o2.inSampleSize=scale;
		        return BitmapFactory.decodeStream(fileInputStream, null, o2);
		    } 
		    catch (FileNotFoundException e) 
		    {
		    	Log.v(TAG, "file not found: "+uri);
		    }
		    
		    return null;
		}
	}
}
