package com.mobeegen.kitchensink;

import android.content.Context;
import android.content.Intent;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.mobeegen.Mobeegen;
import com.mobeegen.NotificationActivity;
import com.mobeegen.Notify;

public class GCMIntentService extends GCMBaseIntentService 
{
	private static final boolean sandbox = false;
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() 
    {
    	super("961877547334");
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        //displayMessage(context, getString(R.string.gcm_registered));
        //ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        //displayMessage(context, getString(R.string.gcm_unregistered));
        if (GCMRegistrar.isRegisteredOnServer(context)) 
        {
            //ServerUtilities.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        
        String message = intent.getStringExtra("message");
        generateNotification(context, message); 
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
       
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }
    
    

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) 
    {
    	Log.v(TAG, "called generateNotification");
    	
    	// if is production system (not sandbox) but the message is a sandbox message
    	//  discard it
    	if(!sandbox && Notify.isSandboxMessage(message))
    	{
    		Log.v(TAG, "Received a sandbox message in sandbox off mode");
    	}
    	else
    	{
	        int icon = R.drawable.ic_launcher;
	        long when = System.currentTimeMillis();
	        NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);
	       
	        String title = context.getString(R.string.app_name);
	        message = Notify.formatMessage(message, sandbox);
	        
			// Shows detail notification page
			Intent notificationIntent = new Intent(context,
					NotificationActivity.class);
			notificationIntent.putExtra("msg", message);
			notificationIntent.putExtra("sandbox", sandbox);
	
			// // set intent so it does not start a new activity
			// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			// | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(
																context, 0,
																notificationIntent,
																PendingIntent.FLAG_UPDATE_CURRENT);
			
			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					context);
			builder.setSmallIcon(icon);
			builder.setContentTitle(title);
			builder.setContentText(message);
			builder.setContentIntent(intent);
			builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
	
			Notification notification = builder.build();
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(0, notification);
    	}
    }

}
