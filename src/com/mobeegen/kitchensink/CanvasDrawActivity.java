
package com.mobeegen.kitchensink;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CanvasDrawActivity extends Activity
{
	Button btnCanvasDrawSave;
	Button btnCanvasDrawClear;
	Button btnCanvasDrawUpload;
	MyDrawView d;

	RelativeLayout canvasContainer;
	ProgressDialog loadingDialog;

	static String TAG = "CanvasDrawActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canvasdraw);

		LinearLayout layout = (LinearLayout) findViewById(R.id.ActivityCanvasDrawLayout);
		canvasContainer = (RelativeLayout) findViewById(R.id.CanvasDrawContainerLayout);

		d = new MyDrawView(this);
		d.setBackgroundColor(Color.WHITE);
		d.requestFocus();
		canvasContainer.addView(d);
		canvasContainer.setDrawingCacheEnabled(true);

		btnCanvasDrawClear = (Button) findViewById(R.id.btnCanvasDrawClear);
		btnCanvasDrawSave = (Button) findViewById(R.id.btnCanvasDrawSave);
		btnCanvasDrawUpload = (Button) findViewById(R.id.btnCanvasDrawUpload);

		btnCanvasDrawClear.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				d.clear();
				canvasContainer.setDrawingCacheEnabled(false);
				canvasContainer.setDrawingCacheEnabled(true);
			}
		});

		btnCanvasDrawSave.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				try
				{
					long unixTime = System.currentTimeMillis() / 1000L;
					Bitmap b = canvasContainer.getDrawingCache();
					String filename = "kitchensink.canvasdraw."+unixTime+".jpg";
					b.compress(
								Bitmap.CompressFormat.JPEG,
								100,
								new FileOutputStream(new File(Environment
										.getExternalStorageDirectory(),filename
										)));
					
					Toast.makeText(getApplicationContext(), "Store at "+Environment.getExternalStorageDirectory().getPath()+"/"+filename+" Done!", Toast.LENGTH_LONG).show();
				}
				catch (Exception e)
				{
					Log.e("Error", e.toString());
				}
			}
		});
		
		btnCanvasDrawUpload.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				try
				{
					Bitmap bitmap = canvasContainer.getDrawingCache();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				    //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
				    byte[] b = baos.toByteArray();
				    String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
				    
				    Log.v(TAG, "bitmap base64 stream:"+imageEncoded);
				    
				    AsyncHttpClient client = new AsyncHttpClient();
				    RequestParams params = new RequestParams();
				    params.put("img", imageEncoded); 
			        client.post("http://www.yeesiang.com/test/mobileUpload/receive.php", params, new AsyncHttpResponseHandler() 
			        {
			        	/**
			             * Fired when the request is started, override to handle in your own code
			             */
			        	@Override
			        	public void onStart()
			        	{
			        		loadingDialog = ProgressDialog.show(CanvasDrawActivity.this, "", "Uploading... Please wait.", true);
			        	}
			        	
			        	 /**
			             * Fired in all cases when the request is finished, after both success and failure, override to handle in your own code
			             */
			        	@Override
			            public void onFinish() 
			        	{
			        		loadingDialog.dismiss();
			        	}
			        	
			        	/**
			             * Fired when a request fails to complete, override to handle in your own code
			             * @param error the underlying cause of the failure
			             * @param content the response body, if any
			             */
			        	@Override
			            public void onFailure(Throwable error, String content) { }
			        	
			        	/**
			             * Fired when a request returns successfully, override to handle in your own code
			             * @param content the body of the HTTP response from the server
			             */
			        	@Override            
			            public void onSuccess(String response) 
			            {
			            	Toast.makeText(getApplicationContext(), "Upload Done!", Toast.LENGTH_LONG).show();
			            }
			        });
				    
				}
				catch (Exception e)
				{
					Log.e("Error", e.toString());
				}
			}
		});
	}
}
