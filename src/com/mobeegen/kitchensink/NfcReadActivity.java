package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NfcReadActivity extends Activity
{
	protected Button nfcWriteBtn, nfcReadBtn;
	final static String TAG = "NfcReadActivity";
	NdefMessage msgs[];
	
	TextView nfcContentTextView;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.v(TAG, "start onCreate");
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcread);
        
        nfcContentTextView = (TextView) findViewById(R.id.textViewNfcContent);
        
        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) 
        {
        	Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            NdefRecord cardRecord = msg.getRecords()[0];
            String message = new String(cardRecord.getPayload());
            displayCard(message);
        }
        
        Log.v(TAG, "end onCreate");
	}
	
	private void displayCard(String message) 
	{
		nfcContentTextView.setText(message);
	}
}
