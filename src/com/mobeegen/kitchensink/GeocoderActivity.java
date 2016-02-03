
package com.mobeegen.kitchensink;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GeocoderActivity extends Activity
{
	LocationManager mLocationManager;
	EditText etAddr, etLong, etLat;
	Button btAddr, btCoor, btHere;
	Location mLocation;
	StringBuilder mSB;
	Criteria criteria;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geocoder);

		// Initialize
		etAddr = (EditText) findViewById(R.id.address);
		etLong = (EditText) findViewById(R.id.longitude);
		etLat = (EditText) findViewById(R.id.latitude);
		btAddr = (Button) findViewById(R.id.get_address);
		btCoor = (Button) findViewById(R.id.get_coordinate);
		btHere = (Button) findViewById(R.id.get_here);
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Set the criteria of what to look for
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String locationprovider = mLocationManager.getBestProvider(criteria,
				true);
		mLocation = mLocationManager.getLastKnownLocation(locationprovider);

		btHere.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				etLat.setText(Double.toString(mLocation.getLatitude()));
				etLong.setText(Double.toString(mLocation.getLongitude()));
			}
		});

		btAddr.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				List<Address> addresses;
				try
				{
					NumberFormat nf = NumberFormat.getInstance();
					Double latitude = nf.parse(etLat.getText().toString())
							.doubleValue();
					Double longitude = nf.parse(etLong.getText().toString())
							.doubleValue();

					Geocoder mGC = new Geocoder(GeocoderActivity.this,
							Locale.ENGLISH);
					addresses = mGC.getFromLocation(latitude, longitude, 1);
					if (addresses.size() > 0)
					{
						Address currentAddr = addresses.get(0);
						mSB = new StringBuilder();
						for (int i = 0; i < currentAddr
								.getMaxAddressLineIndex(); i++)
						{
							mSB.append(currentAddr.getAddressLine(i)).append(
									"\n");
						}
						etAddr.setText(mSB.toString());
					}

				} catch (IOException e)
				{
					etAddr.setText(e.getMessage());
				} catch (ParseException e)
				{
					etAddr.setText(e.getMessage());
				}
			}
		});

		btCoor.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				List<Address> addresses;
				try
				{
					Geocoder gc = new Geocoder(GeocoderActivity.this);
					addresses = gc.getFromLocationName(etAddr.getText()
							.toString(), 1);
					if (addresses.size() > 0)
					{
						Address x = addresses.get(0);
						etLat.setText(Double.toString(x.getLatitude()));
						etLong.setText(Double.toString(x.getLongitude()));
					}

				} catch (IOException e)
				{
					etAddr.setText(e.getMessage());
				}

			}
		});

	}
}
