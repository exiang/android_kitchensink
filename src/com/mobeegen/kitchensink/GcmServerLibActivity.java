
package com.mobeegen.kitchensink;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobeegen.Notify;

// this demo the usage of notify.mobeegen.com server to keep track of devices
// allowing user to subscribe/unsubscribe
public class GcmServerLibActivity extends Activity
{
	static final String TAG = "GcmServerLibActivity";
	final Activity activity = this;

	protected ToggleButton toggleButtonNotification;
	ProgressDialog loadingDialog;
	AlertDialog alertDialog;

	Notify notify;

	static final String notifyProjectId = "961877547334";
	static final String notifyKey = "815412fca7024e3cab003bfc5d937b11c4b08763";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.v(TAG, "start onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gcmserver);

		toggleButtonNotification = (ToggleButton) findViewById(R.id.toggleButtonNotification);

		// init notify object
		notify = new Notify(getBaseContext(), activity,
				"com.mobeegen.kitchensink", notifyProjectId, notifyKey);

		// get device id
		Log.v(TAG, "device ID: " + notify.getDeviceId());
		Log.v(TAG, "device registered: " + notify.isDeviceGCMRegistered());

		// update the toggle button state
		toggleButtonNotification.setChecked(false);
		RequestParams params = new RequestParams();
		params.put("key", notifyKey);
		params.put("uid", notify.getDeviceId());

		AsyncHttpClient client = new AsyncHttpClient();
		client.post(
					notify.getIsRegisteredUrl(), params,
					new AsyncHttpResponseHandler()
					{
						// Fired when the request is started, override to handle
						// in your own code
						@Override
						public void onStart()
						{
							Log.v(TAG, "async onStart");
							String loadingMessage = activity.getString(R.string.notify_connecting_server);
							loadingDialog = ProgressDialog .show(activity, "", loadingMessage, true);
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
							String alertMessage = activity.getString(R.string.notify_server_connection_fail);
							alertDialog = new AlertDialog.Builder(activity)
						    .setTitle("Notification")
						    .setMessage(alertMessage)
						    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						        	dialog.cancel();
						        }
						     }).show();
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

								if (notify.isDeviceGCMRegistered() == true
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
				if (!notify.getDeviceId().equals(""))
				{
					// toggle is on
					if (toggleButtonNotification.isChecked())
					{
						Log.v(TAG, "set notification on");
						notify.onNotification();

					}
					// toggle is off
					else
					{
						Log.v(TAG, "set notification off");
						notify.offNotification();
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
