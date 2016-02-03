
package com.mobeegen.kitchensink;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobeegen.Mobeegen;

public class HttpAuthJsonActivity extends Activity
{
	final static String TAG = "HttpAuthJsonActivity";
	final Activity activity = this;

	ProgressDialog loadingDialog;
	Button loopjHttpAuthJsonBtn, loopjHttpsAuthJsonBtn;
	Button loopjHttpJsonBtn, loopjHttpsJsonBtn;
	Button loopjHttpDecryptJsonBtn;
	static TextView textView1;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_httpauthjson);

		loopjHttpJsonBtn = (Button) findViewById(R.id.loopjHttpJsonBtn);
		loopjHttpJsonBtn.setOnClickListener(btnListener);

		loopjHttpsJsonBtn = (Button) findViewById(R.id.loopjHttpsJsonBtn);
		loopjHttpsJsonBtn.setOnClickListener(btnListener);

		loopjHttpAuthJsonBtn = (Button) findViewById(R.id.loopjHttpAuthJsonBtn);
		loopjHttpAuthJsonBtn.setOnClickListener(btnListener);

		loopjHttpsAuthJsonBtn = (Button) findViewById(R.id.loopjHttpsAuthJsonBtn);
		loopjHttpsAuthJsonBtn.setOnClickListener(btnListener);

		loopjHttpDecryptJsonBtn = (Button) findViewById(R.id.loopjHttpDecryptJsonBtn);
		loopjHttpDecryptJsonBtn.setOnClickListener(btnListener);

		textView1 = (TextView) findViewById(R.id.textView1);
	}

	public OnClickListener btnListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.loopjHttpJsonBtn :
				{
					AsyncHttpClient client = new AsyncHttpClient();

					client.get(
								"http://test.mobeegen.com/nonsecure/index.php",
								new AsyncHttpResponseHandler()
								{
									/**
									 * Fired when the request is started,
									 * override to handle in your own code
									 */
									@Override
									public void onStart()
									{
										loadingDialog = ProgressDialog
												.show(
														activity,
														"",
														"Loading... Please wait.",
														true);
									}

									/**
									 * Fired in all cases when the request is
									 * finished, after both success and failure,
									 * override to handle in your own code
									 */
									@Override
									public void onFinish()
									{
										loadingDialog.dismiss();
									}

									/**
									 * Fired when a request fails to complete,
									 * override to handle in your own code
									 * 
									 * @param error
									 *            the underlying cause of the
									 *            failure
									 * @param content
									 *            the response body, if any
									 */
									@Override
									public void onFailure(Throwable error,
											String content)
									{
										Log.v(TAG, "failed to grab data: "
												+ error.getMessage());
										Toast.makeText(
														activity,
														error.getMessage(),
														Toast.LENGTH_LONG)
												.show();
									}

									/**
									 * Fired when a request returns
									 * successfully, override to handle in your
									 * own code
									 * 
									 * @param content
									 *            the body of the HTTP response
									 *            from the server
									 */
									@Override
									public void onSuccess(String response)
									{
										Log.v(TAG, "response: " + response);

										try
										{
											JSONObject json = new JSONObject(
													response);
											String status = json
													.getString("status");
											String message = json
													.getString("message");
											Toast.makeText(
															activity,
															status + " msg: "
																	+ message,
															Toast.LENGTH_LONG)
													.show();
										}
										catch (Exception e)
										{
											Log.v(
													TAG,
													"failed to parse grabbed data: "
															+ e.getMessage());
											e.printStackTrace();
										}
									}
								});
					break;
				}
				case R.id.loopjHttpsJsonBtn :
				{
					org.apache.http.conn.ssl.SSLSocketFactory sfactory;
					AsyncHttpClient client = new AsyncHttpClient();
					sfactory = TrustAllSSLSocketFactory.getSocketFactory();
					client.setSSLSocketFactory(sfactory);

					client.get(
								"https://test.mobeegen.com/nonsecure/index.php",
								new AsyncHttpResponseHandler()
								{
									/**
									 * Fired when the request is started,
									 * override to handle in your own code
									 */
									@Override
									public void onStart()
									{
										loadingDialog = ProgressDialog
												.show(
														activity,
														"",
														"Loading... Please wait.",
														true);
									}

									/**
									 * Fired in all cases when the request is
									 * finished, after both success and failure,
									 * override to handle in your own code
									 */
									@Override
									public void onFinish()
									{
										loadingDialog.dismiss();
									}

									/**
									 * Fired when a request fails to complete,
									 * override to handle in your own code
									 * 
									 * @param error
									 *            the underlying cause of the
									 *            failure
									 * @param content
									 *            the response body, if any
									 */
									@Override
									public void onFailure(Throwable error,
											String content)
									{
										Log.v(TAG, "failed to grab data: "
												+ error.getMessage());
										Toast.makeText(
														activity,
														error.getMessage(),
														Toast.LENGTH_LONG)
												.show();
									}

									/**
									 * Fired when a request returns
									 * successfully, override to handle in your
									 * own code
									 * 
									 * @param content
									 *            the body of the HTTP response
									 *            from the server
									 */
									@Override
									public void onSuccess(String response)
									{
										Log.v(TAG, "response: " + response);

										try
										{
											JSONObject json = new JSONObject(
													response);
											String status = json
													.getString("status");
											String message = json
													.getString("message");
											Toast.makeText(
															activity,
															status + " msg: "
																	+ message,
															Toast.LENGTH_LONG)
													.show();
										}
										catch (Exception e)
										{
											Log.v(
													TAG,
													"failed to parse grabbed data: "
															+ e.getMessage());
											e.printStackTrace();
										}
									}
								});
					break;
				}
				case R.id.loopjHttpsAuthJsonBtn :
				{
					org.apache.http.conn.ssl.SSLSocketFactory sfactory;
					AsyncHttpClient client = new AsyncHttpClient();

					client.setBasicAuth("admin", "123456");
					sfactory = TrustAllSSLSocketFactory.getSocketFactory();
					client.setSSLSocketFactory(sfactory);

					client.get(
								"https://test.mobeegen.com/secure/index.php",
								new AsyncHttpResponseHandler()
								{
									/**
									 * Fired when the request is started,
									 * override to handle in your own code
									 */
									@Override
									public void onStart()
									{
										loadingDialog = ProgressDialog
												.show(
														activity,
														"",
														"Loading... Please wait.",
														true);
									}

									/**
									 * Fired in all cases when the request is
									 * finished, after both success and failure,
									 * override to handle in your own code
									 */
									@Override
									public void onFinish()
									{
										loadingDialog.dismiss();
									}

									/**
									 * Fired when a request fails to complete,
									 * override to handle in your own code
									 * 
									 * @param error
									 *            the underlying cause of the
									 *            failure
									 * @param content
									 *            the response body, if any
									 */
									@Override
									public void onFailure(Throwable error,
											String content)
									{
										Log.v(TAG, "failed to grab data: "
												+ error.getMessage());
										Toast.makeText(
														activity,
														error.getMessage(),
														Toast.LENGTH_LONG)
												.show();
									}

									/**
									 * Fired when a request returns
									 * successfully, override to handle in your
									 * own code
									 * 
									 * @param content
									 *            the body of the HTTP response
									 *            from the server
									 */
									@Override
									public void onSuccess(String response)
									{
										Log.v(TAG, "response: " + response);

										try
										{
											JSONObject json = new JSONObject(
													response);
											String status = json
													.getString("status");
											String message = json
													.getString("message");
											Toast.makeText(
															activity,
															status + " msg: "
																	+ message,
															Toast.LENGTH_LONG)
													.show();
										}
										catch (Exception e)
										{
											Log.v(
													TAG,
													"failed to parse grabbed data: "
															+ e.getMessage());
											e.printStackTrace();
										}
									}
								});
					break;
				}
				// end of httpsAuthJson
				case R.id.loopjHttpAuthJsonBtn :
				{
					org.apache.http.conn.ssl.SSLSocketFactory sfactory;
					AsyncHttpClient client = new AsyncHttpClient();

					client.setBasicAuth("admin", "123456");
					client.get(
								"http://test.mobeegen.com/secure/index.php",
								new AsyncHttpResponseHandler()
								{
									/**
									 * Fired when the request is started,
									 * override to handle in your own code
									 */
									@Override
									public void onStart()
									{
										loadingDialog = ProgressDialog
												.show(
														activity,
														"",
														"Loading... Please wait.",
														true);
									}

									/**
									 * Fired in all cases when the request is
									 * finished, after both success and failure,
									 * override to handle in your own code
									 */
									@Override
									public void onFinish()
									{
										loadingDialog.dismiss();
									}

									/**
									 * Fired when a request fails to complete,
									 * override to handle in your own code
									 * 
									 * @param error
									 *            the underlying cause of the
									 *            failure
									 * @param content
									 *            the response body, if any
									 */
									@Override
									public void onFailure(Throwable error,
											String content)
									{
										Log.v(TAG, "failed to grab data: "
												+ error.getMessage());
										Toast.makeText(
														activity,
														error.getMessage(),
														Toast.LENGTH_LONG)
												.show();
									}

									/**
									 * Fired when a request returns
									 * successfully, override to handle in your
									 * own code
									 * 
									 * @param content
									 *            the body of the HTTP response
									 *            from the server
									 */
									@Override
									public void onSuccess(String response)
									{
										Log.v(TAG, "response: " + response);

										try
										{
											JSONObject json = new JSONObject(
													response);
											String status = json
													.getString("status");
											String message = json
													.getString("message");
											Toast.makeText(
															activity,
															status + " msg: "
																	+ message,
															Toast.LENGTH_LONG)
													.show();
										}
										catch (Exception e)
										{
											Log.v(
													TAG,
													"failed to parse grabbed data: "
															+ e.getMessage());
											e.printStackTrace();
										}
									}
								});
					break;
				}
				// end of loopjHttpAuthJson

				// start of loopjHttpDecryptJsonBtn
				case R.id.loopjHttpDecryptJsonBtn :
				{
					AsyncHttpClient client = new AsyncHttpClient();

					client.get(
								"http://test.mobeegen.com/encrypted/index.php",
								new AsyncHttpResponseHandler()
								{
									/**
									 * Fired when the request is started,
									 * override to handle in your own code
									 */
									@Override
									public void onStart()
									{
										loadingDialog = ProgressDialog
												.show(
														activity,
														"",
														"Loading... Please wait.",
														true);
									}

									/**
									 * Fired in all cases when the request is
									 * finished, after both success and failure,
									 * override to handle in your own code
									 */
									@Override
									public void onFinish()
									{
										loadingDialog.dismiss();
									}

									/**
									 * Fired when a request fails to complete,
									 * override to handle in your own code
									 * 
									 * @param error
									 *            the underlying cause of the
									 *            failure
									 * @param content
									 *            the response body, if any
									 */
									@Override
									public void onFailure(Throwable error,
											String content)
									{
										Log.v(TAG, "failed to grab data: "
												+ error.getMessage());
										Toast.makeText(
														activity,
														error.getMessage(),
														Toast.LENGTH_LONG)
												.show();
									}

									/**
									 * Fired when a request returns
									 * successfully, override to handle in your
									 * own code
									 * 
									 * @param content
									 *            the body of the HTTP response
									 *            from the server
									 */
									@Override
									public void onSuccess(String response)
									{
										Log.v(TAG, "response: " + response);

										response = decrypt(
															response,
															"secretpassword");
										// Log.v(TAG, "decrypted: " + response);
										try
										{
											JSONObject json = new JSONObject(
													response);
											String status = json
													.getString("status");
											String message = json
													.getString("message");

											textView1.setText(message);
										}
										catch (Exception e)
										{
											Log.v(
													TAG,
													"failed to parse grabbed data: "
															+ e.getMessage());
											e.printStackTrace();
										}

									}
								});
					break;
				}
				// end of loopjHttpDecryptJsonBtn
			}
		}
	};

	public static String convertStreamToString(InputStream is) throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null)
		{
			sb.append(line);
		}

		is.close();

		return sb.toString();
	}

	public static String decrypt(String input, String key)
	{
		String decryptedData = "";

		Cipher cipher;
		try
		{
			SecretKeySpec skeySpec = new SecretKeySpec(Mobeegen.md5(key)
					.getBytes(), "AES");

			cipher = Cipher.getInstance("AES/ECB/NoPadding");

			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encryptedByteArray = Base64.decode(input.getBytes(), 0);
			byte[] decryptedByteArray;
			decryptedByteArray = cipher.doFinal(encryptedByteArray);
			decryptedData = new String(Base64.decode(decryptedByteArray, 0));
		}
		catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return decryptedData;
	}
}
