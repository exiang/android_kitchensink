package com.mobeegen.kitchensink;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DeviceInfoActivity extends Activity
{
	EditText editTextAndroidID;
	EditText editTextMACAddress;
	EditText editTextBluetoothID;
	EditText editTextSIMSerial;
	EditText editTextPhoneNumber;
	
	String androidID = "";
	String MACAddress = "";
	String bluetoothID = "";
	String SIMSerial = "";
	String phoneNumber = "";
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        androidID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID); 
        editTextAndroidID = (EditText) findViewById(R.id.editTextAndroidID);
        editTextAndroidID.setText(androidID);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        MACAddress = wifiManager.getConnectionInfo().getMacAddress();
        editTextMACAddress = (EditText) findViewById(R.id.editTextMACAddress);
        editTextMACAddress.setText(MACAddress);
        
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothID = btAdapter.getAddress();
        editTextBluetoothID = (EditText) findViewById(R.id.editTextBluetoothID);
        editTextBluetoothID.setText(bluetoothID);
        
        TelephonyManager tManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        SIMSerial = tManager.getDeviceId();
        editTextSIMSerial = (EditText) findViewById(R.id.editTextSIMSerial);
        editTextSIMSerial.setText(SIMSerial);

        phoneNumber = tManager.getLine1Number();
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextPhoneNumber.setText(phoneNumber);
	}
}
