package com.mobeegen.kitchensink;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;;

public class MyGoogleMapOverlay extends ItemizedOverlay<OverlayItem> 
{
	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	private Context context;

	public MyGoogleMapOverlay(Context context, Drawable defaultMarker) 
	{
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}

	@Override
	protected OverlayItem createItem(int i) 
	{
		return mapOverlays.get(i);
	}

	@Override
	public int size() 
	{
		return mapOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) 
	{
		 mapOverlays.add(overlay);
		 this.populate();
	}
	
	public void clear()
	{
		mapOverlays.clear();
		this.populate();
	}

	protected boolean onTap(int index) 
	{
		OverlayItem item = mapOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	};
} 
