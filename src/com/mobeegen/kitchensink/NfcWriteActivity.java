
package com.mobeegen.kitchensink;

import java.io.IOException;
import java.nio.charset.Charset;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NfcWriteActivity extends Activity
{
	NfcAdapter mNfcAdapter;
	private boolean mInWriteMode;
	private Button mWriteTagButton;
	private EditText editTextNfcData;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfcwrite);

		// grab our NFC Adapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		mWriteTagButton = (Button) findViewById(R.id.nfcWriteConfirmBtn);
		mWriteTagButton.setOnClickListener(btnListener);

		// muzani: if no NFC support, disable
		if (mNfcAdapter == null || !mNfcAdapter.isEnabled())
		{
			mWriteTagButton.setEnabled(false);
		}

		editTextNfcData = (EditText) findViewById(R.id.editTextNfcData);
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		// muzani: if no NFC support, do nothing
		if (mNfcAdapter != null && mNfcAdapter.isEnabled())
		{
			disableWriteMode();
		}
	}

	@Override
	public void onNewIntent(Intent intent)
	{
		if (mInWriteMode)
		{
			mInWriteMode = false;

			// write to newly scanned tag
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeTag(tag);
		}
	}

	public OnClickListener btnListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			if (v.getId() == R.id.nfcWriteConfirmBtn)
			{
				// hide soft keyboard
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						0);

				displayMessage("Touch and hold tag against phone to write.");
				enableWriteMode();
			}
		}
	};

	@TargetApi(14)
	private void enableWriteMode()
	{
		mInWriteMode = true;

		// set up a PendingIntent to open the app when a tag is scanned
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass())
						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter[] filters = new IntentFilter[]{tagDetected};

		mNfcAdapter
				.enableForegroundDispatch(this, pendingIntent, filters, null);
	}

	@TargetApi(14)
	private void disableWriteMode()
	{
		mNfcAdapter.disableForegroundDispatch(this);
	}

	@TargetApi(14)
	private boolean writeTag(Tag tag)
	{
		// record to launch Play Store if app is not installed
		NdefRecord appRecord = NdefRecord
				.createApplicationRecord("com.mobeegen.kitchensink");

		// record that contains our custom "retro console" game data, using
		// custom MIME_TYPE
		byte[] payload = getRandomConsole().getBytes();
		byte[] mimeBytes = MimeType.NFC_DEMO.getBytes(Charset
				.forName("US-ASCII"));
		NdefRecord cardRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		NdefMessage message = new NdefMessage(new NdefRecord[]{cardRecord,
				appRecord});

		try
		{
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			if (ndef != null)
			{
				ndef.connect();

				if (!ndef.isWritable())
				{
					displayMessage("Read-only tag.");
					return false;
				}

				// work out how much space we need for the data
				int size = message.toByteArray().length;
				if (ndef.getMaxSize() < size)
				{
					displayMessage("Tag doesn't have enough free space.");
					return false;
				}

				ndef.writeNdefMessage(message);
				displayMessage("Tag written successfully.");
				return true;
			} else
			{
				// attempt to format tag
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null)
				{
					try
					{
						format.connect();
						format.format(message);
						displayMessage("Tag written successfully!\nClose this app and scan tag.");
						return true;
					} catch (IOException e)
					{
						displayMessage("Unable to format tag to NDEF.");
						return false;
					}
				} else
				{
					displayMessage("Tag doesn't appear to support NDEF format.");
					return false;
				}
			}
		} catch (Exception e)
		{
			displayMessage("Failed to write tag");
		}

		return false;
	}

	private String getRandomConsole()
	{
		return editTextNfcData.getText().toString();
	}

	private void displayMessage(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
				.show();
	}
}