
package com.mobeegen.kitchensink;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class GetLocationActivity extends Activity
{
	LocationManager mLocationManager;
	TextView tv;
	Location mLocation;
	Criteria criteria;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_text);

		// Initialize
		tv = (TextView) findViewById(R.id.tv1);
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Set the criteria of what to look for
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		// Shows all possible ways the app can get location
		List<String> providers = mLocationManager.getProviders(true);
		StringBuilder mSB = new StringBuilder("Providers:\n");
		for (int i = 0; i < providers.size(); i++)
		{
			mLocationManager.requestLocationUpdates(providers.get(i), 5000,
					2.0f, new LocationListener()
					{

						@Override
						public void onLocationChanged(Location location)
						{

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
						public void onStatusChanged(String provider,
								int status, Bundle extras)
						{

						}

					});
			mSB.append(providers.get(i)).append(": \n");
			mLocation = mLocationManager.getLastKnownLocation(providers.get(i));
			if (mLocation != null)
			{
				mSB.append(mLocation.getLatitude()).append(" , ");
				mSB.append(mLocation.getLongitude()).append("\n");
			} else
			{
				mSB.append("Location can not be found");
			}
			tv.setText(mSB.toString());
		}

	}

	// This finds the best provider form the last known location. May crash
	// without a listener.
	void showRecentLocation()
	{
		String locationprovider = mLocationManager.getBestProvider(criteria,
				true);
		mLocation = mLocationManager.getLastKnownLocation(locationprovider);
		tv.setText("Last location\nlat: " + mLocation.getLatitude()
				+ "\nlongitude: " + mLocation.getLongitude());
	}

}
