package com.mobeegen.kitchensink;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

// refering to:
// https://github.com/commonsguy/cw-advandroid/blob/master/Camera/Preview/src/com/commonsware/android/camera/PreviewDemo.java

public class CameraViewActivity extends Activity
{
	private SurfaceView preview=null;
	private SurfaceHolder previewHolder=null;
	private Camera camera=null;
	private boolean inPreview=false;
	private boolean cameraConfigured=false;
  
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraview);

        preview=(SurfaceView)findViewById(R.id.preview);
        previewHolder=preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@Override
	public void onResume() 
	{
	    super.onResume();
	    
	    camera=Camera.open();
	    camera.setDisplayOrientation(90);
	    startPreview();
	  }
	    
	  @Override
	  public void onPause() 
	  {
	    if (inPreview) 
	    {
	      camera.stopPreview();
	    }
	    
	    camera.release();
	    camera=null;
	    inPreview=false;
	          
	    super.onPause();
	  }
	  
	  private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) 
	  {
	    Camera.Size result=null;
	    
	    for (Camera.Size size : parameters.getSupportedPreviewSizes()) 
	    {
	      if (size.width<=width && size.height<=height) 
	      {
	        if (result==null) 
	        {
	          result=size;
	        }
	        else 
	        {
	          int resultArea=result.width*result.height;
	          int newArea=size.width*size.height;
	          
	          if (newArea>resultArea) 
	          {
	            result=size;
	          }
	        }
	      }
	    }
	    
	    return(result);
	  }
	  
	  private void initPreview(int width, int height) 
	  {
	    if (camera!=null && previewHolder.getSurface()!=null) 
	    {
	      try 
	      {
	        camera.setPreviewDisplay(previewHolder);
	      }
	      catch (Throwable t) 
	      {
	        Log.e("PreviewDemo-surfaceCallback",
	              "Exception in setPreviewDisplay()", t);
	        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
	      }

	      if (!cameraConfigured) 
	      {
	        Camera.Parameters parameters = camera.getParameters();
	        Camera.Size size = getBestPreviewSize(width, height, parameters);
	        
	        //parameters.set("orientation", "portrait");
	        
	        
	        if (size!=null) 
	        {
	          parameters.setPreviewSize(size.width, size.height);
	          camera.setParameters(parameters);
	          cameraConfigured=true;
	        }
	      }
	    }
	  }
	  
	  private void startPreview() 
	  {
	    if (cameraConfigured && camera!=null) 
	    {
	      camera.startPreview();
	      inPreview=true;
	    }
	  }
	  
	  SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() 
	  {
	    public void surfaceCreated(SurfaceHolder holder) {
	      // no-op -- wait until surfaceChanged()
	    }
	    
	    public void surfaceChanged(SurfaceHolder holder,
	                               int format, int width,
	                               int height) {
	      initPreview(width, height);
	      startPreview();
	    }
	    
	    public void surfaceDestroyed(SurfaceHolder holder) {
	      // no-op
	    }
	  };
}