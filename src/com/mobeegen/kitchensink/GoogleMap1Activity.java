package com.mobeegen.kitchensink;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Overlay;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GoogleMap1Activity extends MapActivity
{
	private static final int latE6 = 37985339;
    private static final int lngE6 = 23716735;
    
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap1);  
        
        MapView mapView = (MapView) findViewById(R.id.mapview1);
        mapView.setBuiltInZoomControls(true);
        
        // create mapOverlays list of object
        List<Overlay> mapOverlays = mapView.getOverlays();
        // create drawable 
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_menu_myplaces);
        // create itemizedOverlay object
        MyGoogleMapOverlay overlayItems = new MyGoogleMapOverlay(this, drawable);
        
        GeoPoint point = new GeoPoint(latE6, lngE6);
        // create overlayItem
        OverlayItem overlayItem = new OverlayItem(point, "Hello", "I'm in Athens, Greece!");
        // add overlayItem to overlayItems
        overlayItems.addOverlay(overlayItem);
        // add overlayItems to mapOverlays
        mapOverlays.add(overlayItems);
        
        MapController mapController = mapView.getController();
        mapController.animateTo(point);
        mapController.setZoom(10);
	}
	
	@Override
	protected boolean isRouteDisplayed() 
	{
	    return true;
	}
}
