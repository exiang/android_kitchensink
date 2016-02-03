
package com.mobeegen.kitchensink;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.google.android.gcm.GCMRegistrar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

// this demo the usage of notify.mobeegen.com server to keep track of devices
// allowing user to subscribe/unsubscribe
public class GcmServerActivity extends Activity
{
	static final String TAG = "GcmServerActivity";
	final Activity activity = this;

	protected ToggleButton toggleButtonNotification;
	ProgressDialog loadingDialog;

	static final String notifyProjectId = "961877547334";
	static final String notifyPackage = "com.mobeegen.kitchensink";
	static final String notifyPlatform = "android";
	static final String notifyKey = "815412fca7024e3cab003bfc5d937b11c4b08763";
	static final String notifyServerUrl = "http://notify.mobeegen.com";

	String deviceId;
	boolean deviceGCMRegistered;

	public String getRegisterUrl()
	{
		// sample
		// http://notify.mobeegen.com?package=com.mobeegen.kitchensink&platform=android&action=register

		String result = notifyServerUrl + "/api.php?package=" + notifyPackage
				+ "&platform=" + notifyPlatform + "&action=register";
		Log.v(TAG, "getRegisterUrl: " + result);
		return result;
	}

	public String getUnregisterUrl()
	{
		// sample
		// http://notify.mobeegen.com?package=com.mobeegen.kitchensink&platform=android&action=unregister

		String result = notifyServerUrl + "/api.php?package=" + notifyPackage
				+ "&platform=" + notifyPlatform + "&action=unregister";
		Log.v(TAG, "getUnregisterUrl: " + result);
		return result;
	}

	public String getIsRegisteredUrl()
	{
		// sample
		// http://notify.mobeegen.com?package=com.mobeegen.kitchensink&platform=android&action=isRegistered

		String result = notifyServerUrl + "/api.php?package=" + notifyPackage
				+ "&platform=" + notifyPlatform + "&action=isRegistered";
		Log.v(TAG, "getIsRegisteredUrl: " + result);
		return result;
	}

	public String getDeviceId()
	{
		String result = "";

		// check is it registered
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		result = GCMRegistrar.getRegistrationId(this);

		// if empty, is not registered
		if (result.equals(""))
		{
			Log.v(TAG, "device is not registered");

			// register the device
			GCMRegistrar.register(this, notifyProjectId);
			result = GCMRegistrar.getRegistrationId(this);
		}

		return result;
	}

	public boolean isDeviceGCMRegistered()
	{
		boolean result = false;
		String deviceId = "";

		// check is it registered
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		deviceId = GCMRegistrar.getRegistrationId(this);

		if (!deviceId.equals(""))
		{
			result = true;
		}

		return result;
	}

	private void onNotification()
	{
		RequestParams params = new RequestParams();
		params.put("key", notifyKey);
		params.put("uid", deviceId);

		AsyncHttpClient client = new AsyncHttpClient();
		client.post(getRegisterUrl(), params, new AsyncHttpResponseHandler()
		{
			// Fired when the request is started, override to handle in your own
			// code
			@Override
			public void onStart()
			{
				Log.v(TAG, "async onStart");
				loadingDialog = ProgressDialog
						.show(activity, "", "Registering... Please wait.", true);
			}

			// Fired in all cases when the request is finished, after both
			// success and failure, override to handle in your own code
			@Override
			public void onFinish()
			{
				Log.v(TAG, "async onFinish");
				loadingDialog.dismiss();
			}

			@Override
			public void onFailure(Throwable error, String content)
			{
				Log.v(TAG, "async onFailure");
			}

			@Override
			public void onSuccess(String response)
			{
				Log.v(TAG, "async onSuccess");
				Log.v(TAG, "response: " + response);

				GCMRegistrar.register(activity, notifyProjectId);
			}
		});
	}

	private void offNotification()
	{
		RequestParams params = new RequestParams();
		params.put("key", notifyKey);
		params.put("uid", deviceId);

		AsyncHttpClient client = new AsyncHttpClient();
		client.post(getUnregisterUrl(), params, new AsyncHttpResponseHandler()
		{
			// Fired when the request is started, override to handle in your own
			// code
			@Override
			public void onStart()
			{
				Log.v(TAG, "async onStart");
				loadingDialog = ProgressDialog
						.show(
								activity, "", "Unregistering... Please wait.",
								true);
			}

			// Fired in all cases when the request is finished, after both
			// success and failure, override to handle in your own code
			@Override
			public void onFinish()
			{
				Log.v(TAG, "async onFinish");
				loadingDialog.dismiss();
			}

			@Override
			public void onFailure(Throwable error, String content)
			{
				Log.v(TAG, "async onFailure");
			}

			@Override
			public void onSuccess(String response)
			{
				Log.v(TAG, "async onSuccess");
				Log.v(TAG, "response: " + response);

				// GCMRegistrar.unregister(activity);
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.v(TAG, "start onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gcmserver);

		toggleButtonNotification = (ToggleButton) findViewById(R.id.toggleButtonNotification);

		// get device id
		deviceId = getDeviceId();
		deviceGCMRegistered = isDeviceGCMRegistered();
		Log.v(TAG, "device ID: " + deviceId);

		// update the toggle button state
		toggleButtonNotification.setChecked(false);
		RequestParams params = new RequestParams();
		params.put("key", notifyKey);
		params.put("uid", deviceId);

		AsyncHttpClient client = new AsyncHttpClient();
		client.post(
					getIsRegisteredUrl(), params,
					new AsyncHttpResponseHandler()
					{
						// Fired when the request is started, override to handle
						// in your own code
						@Override
						public void onStart()
						{
							Log.v(TAG, "async onStart");
							loadingDialog = ProgressDialog
									.show(
											activity, "",
											"Checking... Please wait.", true);
						}

						// Fired in all cases when the request is finished,
						// after both success and failure, override to handle in
						// your own code
						@Override
						public void onFinish()
						{
							Log.v(TAG, "async onFinish");
							loadingDialog.dismiss();
						}

						@Override
						public void onFailure(Throwable error, String content)
						{
							Log.v(TAG, "async onFailure");
						}

						@Override
						public void onSuccess(String response)
						{
							Log.v(TAG, "async onSuccess");
							Log.v(TAG, "response: " + response);

							try
							{
								JSONObject json = new JSONObject(response);
								Log.v(
										TAG,
										"status? " + json.getString("status"));
								Log.v(
										TAG,
										"is registered? "
												+ json.getString("isRegistered"));

								if (isDeviceGCMRegistered() == true
										&& json.getString("status")
												.equals("success")
										&& json.getString("isRegistered")
												.equals("1"))
								{
									Log.v(
											TAG,
											"set toggleButtonNotification checked");
									toggleButtonNotification.setChecked(true);
								}

							}
							catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});

		// when toggle
		toggleButtonNotification.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// if device id is ready
				if (!deviceId.equals(""))
				{
					// toggle is on
					if (toggleButtonNotification.isChecked())
					{
						Log.v(TAG, "notification on");
						onNotification();

					}
					// toggle is off
					else
					{
						Log.v(TAG, "notification off");
						offNotification();
					}
				}
				// device id not ready yet...
				else
				{
					Log.v(TAG, "device id is empty, cant proceed");
				}
			}
		});

	}

}
