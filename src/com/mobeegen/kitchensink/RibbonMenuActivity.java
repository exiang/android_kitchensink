package com.mobeegen.kitchensink;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;

public class RibbonMenuActivity extends Activity implements iRibbonMenuCallback 
{	
	final static String TAG = "RibbonMenuActivity";
	public Menu menu;
	private RibbonMenuView rbmView;
	Button triggerRibbonMenuBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        
        // ribbon menu must be use together with window bar
        // if it is hidden, then it will crash
        // it also affected the onCreateOptionsMenu, when hidden, this function is unstable
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_ribbonmenu);
        
        rbmView = (RibbonMenuView) findViewById(R.id.ribbonMenuView1);
        rbmView.setMenuClickCallback(this);
        // notice that ribbon menu do not support sub menu item
        rbmView.setMenuItems(R.menu.menu_ribbon);
        
        // ribbon menu must be use together with window bar
        // so u may enable this line
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // or you may use a button to trigger it
        triggerRibbonMenuBtn = (Button) findViewById(R.id.triggerRibbonMenuBtn);
        triggerRibbonMenuBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				rbmView.toggleMenu();
			}
		});
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) 
	{
        Log.v(TAG, "call Preparing options menu");
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
		Log.v(TAG, "call onCreateOptionsMenu");
		
		super.onCreateOptionsMenu(menu);
		
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_ribbon, menu);
        return true;
    }
	    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Log.v(TAG, "call onOptionsItemSelected");
		int id = item.getItemId();
		
		// to make sure the ribbon menu shown when u click on the icon on action bar
		if (id == android.R.id.home) 
		{
			rbmView.toggleMenu();
			return true;
		
		} 
		else 
		{		
			RibbonMenuItemClick(id);
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void RibbonMenuItemClick(int itemId) 
	{
		// TODO Auto-generated method stub
		Log.v(TAG, "call RibbonMenuItemClick");
		Log.v(TAG, "menu id selected: "+itemId);
		
		try
		{
			MenuItem item = menu.findItem(itemId);
			Toast.makeText(this, "menu item selected: "+item.toString(), Toast.LENGTH_LONG).show();
		}
		catch(Exception e)
		{
			Log.v(TAG, "RibbonMenuItemClick failed to get menu item");
		}
	}
	
	// add this code block to prevent the default menu from showing up
	@Override
	public boolean onPreparePanel(int featureId, View view, Menu menu){
		return false;
	}

}
