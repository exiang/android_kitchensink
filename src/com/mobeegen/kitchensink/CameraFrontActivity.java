package com.mobeegen.kitchensink;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

// refer to:
//  - http://android-er.blogspot.com/2010/12/add-overlay-on-camera-preview.html
//  - http://android-er.blogspot.com/2010/12/implement-takepicture-function-of.html
public class CameraFrontActivity extends Activity
{
  public final static String DEBUG_TAG = "CameraFrontActivity";
  private Camera camera;
  SurfaceView surfaceView;
  SurfaceHolder surfaceHolder;
  boolean previewing = false;
  private boolean cameraConfigured=false;
  LayoutInflater controlInflater = null;
  private int cameraId = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camerafront);
    
    surfaceView = (SurfaceView)findViewById(R.id.camerapreview);
    surfaceHolder = surfaceView.getHolder();
    surfaceHolder.addCallback(surfaceCallback);
    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    controlInflater = LayoutInflater.from(getBaseContext());
    View viewControl = controlInflater.inflate(R.layout.cameracontrol, null);
    LayoutParams layoutParamsControl = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
    this.addContentView(viewControl, layoutParamsControl);

    final Button button = (Button) findViewById(R.id.btnTakePhoto);
    button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	camera.takePicture(null, null, new CameraSdPhotoHandler(getApplicationContext()));
        }
    });
  }

  private int findFrontFacingCamera() 
  {
    int cameraId = -1;
    // Search for the front facing camera
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) 
    {
      CameraInfo info = new CameraInfo();
      Camera.getCameraInfo(i, info);
      
      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) 
      {
        Log.d(DEBUG_TAG, "Camera found");
        cameraId = i;
        break;
      }
    }
    return cameraId;
  }
  
  @Override
public void onResume() 
{
    super.onResume();
    cameraId = findFrontFacingCamera();
    if (cameraId < 0) 
    {
    	Toast.makeText(this, "No front facing camera found.", Toast.LENGTH_LONG).show();
    } 
    else 
    {
		camera = Camera.open(cameraId);
		camera.setDisplayOrientation(90);
		startPreview();
    }
  }

  @Override
  protected void onPause() 
  {
	if (camera != null && previewing) 
    {
      camera.stopPreview();
   
      camera.release();
      camera = null;
      previewing = false;
    }
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
    if (camera!=null && surfaceHolder.getSurface()!=null) 
    {
      try 
      {
        camera.setPreviewDisplay(surfaceHolder);
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
      previewing=true;
    }
  }
  
  SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() 
  {
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) 
		{
			initPreview(width, height);
		    startPreview();
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
		}
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
		}
  };
} 