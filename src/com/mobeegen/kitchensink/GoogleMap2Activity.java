package com.mobeegen.kitchensink;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MyLocationOverlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GoogleMap2Activity extends MapActivity
{
	final static String TAG = "GoogleMap2Activity";
	
	private MapController mapController;
	private MapView mapView;
	
	private LocationManager locationManager;
	private MyLocationOverlay myLocationOverlay;
	private MyGoogleMapOverlay overlayItems;
	
	List<Overlay> mapOverlays;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap2);  
        
        mapView = (MapView) findViewById(R.id.mapview2);
        mapView.setBuiltInZoomControls(true);
        
        // create mapOverlays list of object
        mapOverlays = mapView.getOverlays();
        // create drawable 
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_menu_myplaces);
        // create itemizedOverlay object
        overlayItems = new MyGoogleMapOverlay(this, drawable);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        
        if (!gpsEnabled) 
        {
        	Log.v(TAG, "GPS NOT enabled");
        	enableLocationSettings();
        }
        else
        {
        	Log.v(TAG, "GPS enabled");
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoUpdateHandler());
        	
        	myLocationOverlay = new MyLocationOverlay(this, mapView);
            mapView.getOverlays().add(myLocationOverlay);
            myLocationOverlay.runOnFirstFix(new Runnable() 
            {
            	public void run() 
            	{
            		mapView.getController().animateTo(myLocationOverlay.getMyLocation());
            	}
            }); 
        }
		
        mapController = mapView.getController();
        mapController.setZoom(14);

        
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
	    return false;
	}
	
	public class GeoUpdateHandler implements LocationListener 
	{
		@Override
		public void onLocationChanged(Location location) 
		{
			Log.v(TAG, "onLocationChanged");
			
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			
			Log.v(TAG, "lat: "+lat+"; lng: "+lng);
			Toast.makeText(getApplicationContext(), "lat: "+lat+"; lng: "+lng, Toast.LENGTH_SHORT).show();
			
			GeoPoint point = new GeoPoint(lat, lng);
			setMarker(point);
			mapController.animateTo(point);
		}

		@Override
		public void onProviderDisabled(String provider) 
		{
		}

		@Override
		public void onProviderEnabled(String provider) 
		{
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
		}
	}

	private void setMarker(GeoPoint point) 
	{
		Log.v(TAG, "setMarker");
			
		//clear all 
		overlayItems.clear();
		
        OverlayItem overlayItem = new OverlayItem(point, "", "");
        // add overlayItem to overlayItems
        overlayItems.addOverlay(overlayItem);
        // add overlayItems to mapOverlays
        mapOverlays.add(overlayItems);
	}
	
	private void createMarker(GeoPoint point) 
	{
		Log.v(TAG, "createMarker");
			
        OverlayItem overlayItem = new OverlayItem(point, "", "");
        // add overlayItem to overlayItems
        overlayItems.addOverlay(overlayItem);
        // add overlayItems to mapOverlays
        mapOverlays.add(overlayItems);
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();
	}
	
	private void enableLocationSettings() 
	{
	    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    startActivity(settingsIntent);
	}
	
}

