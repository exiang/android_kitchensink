package com.mobeegen.kitchensink;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GoogleMap3Activity extends MapActivity
{
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap3);  
        
        MapView mapView = (MapView) findViewById(R.id.mapview3);
        mapView.setBuiltInZoomControls(true);
        
        MapController mapController = mapView.getController();
        mapController.setZoom(10);
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
	    return true;
	}
}
