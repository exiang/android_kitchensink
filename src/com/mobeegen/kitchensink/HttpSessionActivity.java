package com.mobeegen.kitchensink;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HttpSessionActivity extends Activity
{
	final static String TAG = "HttpSessionActivity";
	final Activity activity = this;
	
	boolean isLogin = false;
	
	ProgressDialog loadingDialog;
	AsyncHttpClient client;
	
	Button loginHttpSessionBtn, secretHttpSessionBtn, logoutHttpSessionBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpsession);

        client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        
        loginHttpSessionBtn = (Button) findViewById(R.id.loginHttpSessionBtn);
        loginHttpSessionBtn.setOnClickListener(btnListener);
        loginHttpSessionBtn.setEnabled(false);
        
        secretHttpSessionBtn = (Button) findViewById(R.id.secretHttpSessionBtn);
        secretHttpSessionBtn.setOnClickListener(btnListener);
        secretHttpSessionBtn.setEnabled(false);
        
        logoutHttpSessionBtn = (Button) findViewById(R.id.logoutHttpSessionBtn);
        logoutHttpSessionBtn.setOnClickListener(btnListener);
        logoutHttpSessionBtn.setEnabled(false);
	}
	
	@Override
	protected void onResume()
	{
		Log.v(TAG, "call onResume");
		Toast.makeText(activity, "onResume", Toast.LENGTH_SHORT).show();
		super.onResume();
		checkSession();
	}
	
	/*@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		Log.v(TAG, "call onSaveInstanceState");
		Toast.makeText(activity, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
		outState.putBoolean("isLogin", isLogin);
		Toast.makeText(activity, "saved isLogin: "+isLogin, Toast.LENGTH_SHORT).show();
		super.onSaveInstanceState(outState);
	}
	

	@Override
	protected void onRestoreInstanceState(Bundle savedState)
	{
		Log.v(TAG, "call onRestoreInstanceState");
		Toast.makeText(activity, "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
		
		super.onRestoreInstanceState(savedState);
		
		isLogin = savedState.getBoolean("isLogin");
		Toast.makeText(activity, "restored isLogin: "+isLogin, Toast.LENGTH_SHORT).show();
		checkSession();
	}*/
	
	public OnClickListener btnListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.loginHttpSessionBtn:
				{
					login();
					break;
				}
				case R.id.secretHttpSessionBtn:
				{
					accessSecret();
					break;
				}
				case R.id.logoutHttpSessionBtn:
				{
					logout();
					break;
				}
			}
		}
	};
	
	public void isLogin()
	{
		loginHttpSessionBtn.setEnabled(false);
		secretHttpSessionBtn.setEnabled(true);
		logoutHttpSessionBtn.setEnabled(true);
	}
	
	public void isLogout()
	{
		loginHttpSessionBtn.setEnabled(true);
		secretHttpSessionBtn.setEnabled(false);
		logoutHttpSessionBtn.setEnabled(false);
	}
	
	public void checkSession()
	{
		client.get("http://test.mobeegen.com/session/login.php?username=admin&password=123456", new AsyncHttpResponseHandler() 
        {
        	/**
             * Fired when the request is started, override to handle in your own code
             */
        	@Override
        	public void onStart()
        	{
        		
        	}
        	
        	 /**
             * Fired in all cases when the request is finished, after both success and failure, override to handle in your own code
             */
        	@Override
            public void onFinish() 
        	{
        	}
        	
        	/**
             * Fired when a request fails to complete, override to handle in your own code
             * @param error the underlying cause of the failure
             * @param content the response body, if any
             */
        	@Override
            public void onFailure(Throwable error, String content) 
        	{
        		Log.v(TAG, "failed to login: "+error.getMessage());
        		Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
        	}
        	
        	/**
             * Fired when a request returns successfully, override to handle in your own code
             * @param content the body of the HTTP response from the server
             */
        	@Override            
            public void onSuccess(String response) 
            {
            	Log.v(TAG, "response: "+response);

            	try
            	{                	
                	JSONObject json = new JSONObject(response);
            		String status = json.getString("status");
            		Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();

            		if(status.equals("already login"))
            		{
            			isLogin = true;
            		}
            	}
            	catch (Exception e) 
    			{
            		Log.v(TAG, "failed to parse grabbed data: "+e.getMessage());
    				e.printStackTrace();
    			}
            	
            	if(isLogin)
                {
                	isLogin();
                }
                else
                {
                	isLogout();
                }
            }
        });
		
		
	}
	
	public void login()
	{
		Log.v(TAG, "call login");
		client.get("http://test.mobeegen.com/session/login.php?username=admin&password=123456", new AsyncHttpResponseHandler() 
        {
        	/**
             * Fired when the request is started, override to handle in your own code
             */
        	@Override
        	public void onStart()
        	{
        		loadingDialog = ProgressDialog.show(activity, "", "Logging in... Please wait.", true);
        	}
        	
        	 /**
             * Fired in all cases when the request is finished, after both success and failure, override to handle in your own code
             */
        	@Override
            public void onFinish() 
        	{
        		loadingDialog.dismiss();
        	}
        	
        	/**
             * Fired when a request fails to complete, override to handle in your own code
             * @param error the underlying cause of the failure
             * @param content the response body, if any
             */
        	@Override
            public void onFailure(Throwable error, String content) 
        	{
        		Log.v(TAG, "failed to login: "+error.getMessage());
        		Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
        	}
        	
        	/**
             * Fired when a request returns successfully, override to handle in your own code
             * @param content the body of the HTTP response from the server
             */
        	@Override            
            public void onSuccess(String response) 
            {
            	Log.v(TAG, "response: "+response);

            	try
            	{                	
                	JSONObject json = new JSONObject(response);
            		String status = json.getString("status");
            		Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();

            		if(status.equals("already login") || status.equals("login success"))
            		{
            			isLogin = true;
            			isLogin();
            		}
            	}
            	catch (Exception e) 
    			{
            		Log.v(TAG, "failed to parse grabbed data: "+e.getMessage());
    				e.printStackTrace();
    			}
            }
        });
	}
	
	public void logout()
	{
		Log.v(TAG, "call logout");
		client.get("http://test.mobeegen.com/session/logout.php", new AsyncHttpResponseHandler() 
        {
        	/**
             * Fired when the request is started, override to handle in your own code
             */
        	@Override
        	public void onStart()
        	{
        		loadingDialog = ProgressDialog.show(activity, "", "Logout now... Please wait.", true);
        	}
        	
        	 /**
             * Fired in all cases when the request is finished, after both success and failure, override to handle in your own code
             */
        	@Override
            public void onFinish() 
        	{
        		loadingDialog.dismiss();
        	}
        	
        	/**
             * Fired when a request fails to complete, override to handle in your own code
             * @param error the underlying cause of the failure
             * @param content the response body, if any
             */
        	@Override
            public void onFailure(Throwable error, String content) 
        	{
        		Log.v(TAG, "failed to logout: "+error.getMessage());
        		Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
        	}
        	
        	/**
             * Fired when a request returns successfully, override to handle in your own code
             * @param content the body of the HTTP response from the server
             */
        	@Override            
            public void onSuccess(String response) 
            {
            	Log.v(TAG, "response: "+response);

            	try
            	{                	
                	JSONObject json = new JSONObject(response);
            		String status = json.getString("status");
            		Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();

            		if(status.equals("already logout") || status.equals("logout success"))
            		{
            			isLogin = false;
            			isLogout();
            		}
            	}
            	catch (Exception e) 
    			{
            		Log.v(TAG, "failed to parse grabbed data: "+e.getMessage());
    				e.printStackTrace();
    			}
            }
        });
	}
	
	
	public void accessSecret()
	{
		Log.v(TAG, "call accessSecret");
		client.get("http://test.mobeegen.com/session/secret.php", new AsyncHttpResponseHandler() 
        {
        	/**
             * Fired when the request is started, override to handle in your own code
             */
        	@Override
        	public void onStart()
        	{
        		loadingDialog = ProgressDialog.show(activity, "", "Accessing now... Please wait.", true);
        	}
        	
        	 /**
             * Fired in all cases when the request is finished, after both success and failure, override to handle in your own code
             */
        	@Override
            public void onFinish() 
        	{
        		loadingDialog.dismiss();
        	}
        	
        	/**
             * Fired when a request fails to complete, override to handle in your own code
             * @param error the underlying cause of the failure
             * @param content the response body, if any
             */
        	@Override
            public void onFailure(Throwable error, String content) 
        	{
        		Log.v(TAG, "failed to access: "+error.getMessage());
        		Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
        	}
        	
        	/**
             * Fired when a request returns successfully, override to handle in your own code
             * @param content the body of the HTTP response from the server
             */
        	@Override            
            public void onSuccess(String response) 
            {
            	Log.v(TAG, "response: "+response);

            	try
            	{                	
                	JSONObject json = new JSONObject(response);
            		String status = json.getString("status");
            		//Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();

            		if(status.equals("already login"))
            		{
            			String counter = json.getString("counter");
                		Toast.makeText(activity, counter, Toast.LENGTH_SHORT).show();
            		}
            		
            	}
            	catch (Exception e) 
    			{
            		Log.v(TAG, "failed to parse grabbed data: "+e.getMessage());
    				e.printStackTrace();
    			}
            }
        });
	}
	
}
