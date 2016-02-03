
package com.mobeegen.kitchensink;

import com.mobeegen.VersiActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DoorActivity extends Activity
{
	protected Button versiGroupBtn;
	protected Button htmlInTextViewBtn;
	protected Button customButtonBtn;
	protected Button customTitleBarBtn;
	protected Button imageZoomerBtn;
	protected Button imageZoomerWebviewBtn;
	protected Button pullRefreshFeedBtn;
	protected Button jsonTwitterBtn;
	protected Button grabUseCacheBtn;
	protected Button sliderBtn;
	protected Button paramsActBtn;
	protected Button deviceInfoBtn;
	protected Button wallpaperBtn;
	protected Button googleMapBtn;
	protected Button tabBtn;
	protected Button interactiveButtonBtn;
	protected Button loadImageFromWebBtn;
	protected Button roundedCornerImageFromWebBtn;
	protected Button nfcBtn;
	protected Button gcmBtn;
	protected Button gcmServerBtn;
	protected Button gcmServerLibBtn;
	protected Button scaleImageBtn;
	protected Button playSoundBtn;
	protected Button viewPdfBtn;
	protected Button layerImageBtn;
	protected Button geocoderBtn;
	protected Button getLocBtn;
	protected Button sqlBtn;
	protected Button gridImageBtn;
	protected Button galleryLocalBtn;
	protected Button cameraBtn;
	protected Button ribbonMenuBtn;
	protected Button httpAuthJsonBtn;
	protected Button httpSessionBtn;
	protected Button googleAnalyticsBtn;
	protected Button chartBtn;
	protected Button tabularBtn;
	protected Button tabularRawHtmlBtn;
	protected Button canvasDrawBtn;
	protected Button text2SpeechBtn;
	protected Button jsonObjectArrayBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_door);

		versiGroupBtn = (Button) findViewById(R.id.versiGroupBtn);
		htmlInTextViewBtn = (Button) findViewById(R.id.htmlInTextViewBtn);
		customButtonBtn = (Button) findViewById(R.id.customButtonBtn);
		customTitleBarBtn = (Button) findViewById(R.id.customTitleBarBtn);
		imageZoomerBtn = (Button) findViewById(R.id.imageZoomerBtn);
		imageZoomerWebviewBtn = (Button) findViewById(R.id.imageZoomerWebviewBtn);

		googleMapBtn = (Button) findViewById(R.id.googleMapBtn);
		googleMapBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getApplicationContext(),
						GoogleMapActivity.class));
			}
		});

		pullRefreshFeedBtn = (Button) findViewById(R.id.pullRefreshFeedBtn);
		jsonTwitterBtn = (Button) findViewById(R.id.jsonTwitterBtn);
		grabUseCacheBtn = (Button) findViewById(R.id.grabUseCacheBtn);
		tabBtn = (Button) findViewById(R.id.tabBtn);
		sliderBtn = (Button) findViewById(R.id.sliderBtn);
		paramsActBtn = (Button) findViewById(R.id.paramsActBtn);
		deviceInfoBtn = (Button) findViewById(R.id.deviceInfoBtn);
		wallpaperBtn = (Button) findViewById(R.id.wallpaperBtn);
		interactiveButtonBtn = (Button) findViewById(R.id.interactiveBtn);
		loadImageFromWebBtn = (Button) findViewById(R.id.loadImageFromWebBtn);
		roundedCornerImageFromWebBtn = (Button) findViewById(R.id.roundedCornerImageFromWebBtn);
		nfcBtn = (Button) findViewById(R.id.nfcBtn);
		gcmBtn = (Button) findViewById(R.id.gcmBtn);
		gcmServerBtn = (Button) findViewById(R.id.gcmServerBtn);
		gcmServerLibBtn = (Button) findViewById(R.id.gcmServerLibBtn);
		scaleImageBtn = (Button) findViewById(R.id.scaleImageBtn);
		playSoundBtn = (Button) findViewById(R.id.playSoundBtn);
		viewPdfBtn = (Button) findViewById(R.id.viewPdfBtn);
		geocoderBtn = (Button) findViewById(R.id.geocoderBtn);
		getLocBtn = (Button) findViewById(R.id.getLocBtn);
		sqlBtn = (Button) findViewById(R.id.sqlBtn);
		layerImageBtn = (Button) findViewById(R.id.layerImageBtn);
		gridImageBtn = (Button) findViewById(R.id.gridImageBtn);
		galleryLocalBtn = (Button) findViewById(R.id.galleryLocalBtn);
		cameraBtn = (Button) findViewById(R.id.cameraBtn);
		ribbonMenuBtn = (Button) findViewById(R.id.ribbonMenuBtn);
		httpAuthJsonBtn = (Button) findViewById(R.id.httpAuthJsonBtn);
		httpSessionBtn = (Button) findViewById(R.id.httpSessionBtn);
		googleAnalyticsBtn = (Button) findViewById(R.id.googleAnalyticsBtn);
		chartBtn = (Button) findViewById(R.id.chartBtn);
		tabularBtn = (Button) findViewById(R.id.tabularBtn);
		tabularRawHtmlBtn = (Button) findViewById(R.id.tabularRawHtmlBtn);
		canvasDrawBtn = (Button) findViewById(R.id.canvasDrawBtn);
		text2SpeechBtn = (Button) findViewById(R.id.text2SpeechBtn);
		jsonObjectArrayBtn = (Button) findViewById(R.id.jsonObjectArrayBtn);
		
		versiGroupBtn.setOnClickListener(btnListener);
		htmlInTextViewBtn.setOnClickListener(btnListener);
		customButtonBtn.setOnClickListener(btnListener);
		customTitleBarBtn.setOnClickListener(btnListener);
		imageZoomerBtn.setOnClickListener(btnListener);
		imageZoomerWebviewBtn.setOnClickListener(btnListener);
		pullRefreshFeedBtn.setOnClickListener(btnListener);
		jsonTwitterBtn.setOnClickListener(btnListener);
		grabUseCacheBtn.setOnClickListener(btnListener);
		tabBtn.setOnClickListener(btnListener);
		sliderBtn.setOnClickListener(btnListener);
		paramsActBtn.setOnClickListener(btnListener);
		deviceInfoBtn.setOnClickListener(btnListener);
		wallpaperBtn.setOnClickListener(btnListener);
		interactiveButtonBtn.setOnClickListener(btnListener);
		loadImageFromWebBtn.setOnClickListener(btnListener);
		roundedCornerImageFromWebBtn.setOnClickListener(btnListener);
		nfcBtn.setOnClickListener(btnListener);
		gcmBtn.setOnClickListener(btnListener);
		gcmServerBtn.setOnClickListener(btnListener);
		gcmServerLibBtn.setOnClickListener(btnListener);
		scaleImageBtn.setOnClickListener(btnListener);
		playSoundBtn.setOnClickListener(btnListener);
		viewPdfBtn.setOnClickListener(btnListener);
		geocoderBtn.setOnClickListener(btnListener);
		getLocBtn.setOnClickListener(btnListener);
		sqlBtn.setOnClickListener(btnListener);
		layerImageBtn.setOnClickListener(btnListener);
		gridImageBtn.setOnClickListener(btnListener);
		galleryLocalBtn.setOnClickListener(btnListener);
		cameraBtn.setOnClickListener(btnListener);
		ribbonMenuBtn.setOnClickListener(btnListener);
		httpAuthJsonBtn.setOnClickListener(btnListener);
		httpSessionBtn.setOnClickListener(btnListener);
		googleAnalyticsBtn.setOnClickListener(btnListener);
		chartBtn.setOnClickListener(btnListener);
		tabularBtn.setOnClickListener(btnListener);
		tabularRawHtmlBtn.setOnClickListener(btnListener);
		canvasDrawBtn.setOnClickListener(btnListener);
		text2SpeechBtn.setOnClickListener(btnListener);
		jsonObjectArrayBtn.setOnClickListener(btnListener);
	}

	public OnClickListener btnListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.versiGroupBtn :
				startActivity(new Intent(getApplicationContext(),
						VersiGroupActivity.class));
				break;
				case R.id.htmlInTextViewBtn :
					startActivity(new Intent(getApplicationContext(),
							HtmlTextViewActivity.class));
					break;
				case R.id.customButtonBtn :
					startActivity(new Intent(getApplicationContext(),
							CustomButtonActivity.class));
					break;
				case R.id.customTitleBarBtn :
					startActivity(new Intent(getApplicationContext(),
							CustomTitleBarActivity.class));
					break;
				case R.id.imageZoomerBtn :
					startActivity(new Intent(getApplicationContext(),
							ImageZoomerActivity.class));
					break;
				case R.id.imageZoomerWebviewBtn :
					startActivity(new Intent(getApplicationContext(),
							ImageZoomerWebviewActivity.class));
					break;
				case R.id.pullRefreshFeedBtn :
					startActivity(new Intent(getApplicationContext(),
							PullRefreshFeedActivity.class));
					break;
				case R.id.jsonTwitterBtn :
					startActivity(new Intent(getApplicationContext(),
							ReadTwitterActivity.class));
					break;
				case R.id.grabUseCacheBtn :
					startActivity(new Intent(getApplicationContext(),
							GrabUseCacheActivity.class));
					break;
				case R.id.tabBtn :
					startActivity(new Intent(getApplicationContext(),
							RadioTabActivity.class));
					break;
				case R.id.sliderBtn :
					startActivity(new Intent(getApplicationContext(),
							SliderActivity.class));
					break;
				case R.id.paramsActBtn :
					startActivity(new Intent(getApplicationContext(),
							ParamsActActivity.class));
					break;
				case R.id.deviceInfoBtn :
					startActivity(new Intent(getApplicationContext(),
							DeviceInfoActivity.class));
					break;
				case R.id.wallpaperBtn :
					startActivity(new Intent(getApplicationContext(),
							WallpaperActivity.class));
					break;
				case R.id.interactiveBtn :
					startActivity(new Intent(getApplicationContext(),
							InteractiveButtonActivity.class));
					break;
				case R.id.loadImageFromWebBtn :
					startActivity(new Intent(getApplicationContext(),
							LoadImageFromWebActivity.class));
					break;
				case R.id.roundedCornerImageFromWebBtn :
					startActivity(new Intent(getApplicationContext(),
							RoundedCornerImageFromWebActivity.class));
					break;
				case R.id.nfcBtn :
					startActivity(new Intent(getApplicationContext(),
							NfcActivity.class));
					break;
				case R.id.gcmBtn :
					startActivity(new Intent(getApplicationContext(),
							GcmActivity.class));
					break;

				case R.id.gcmServerBtn :
					startActivity(new Intent(getApplicationContext(),
							GcmServerActivity.class));
					break;

				case R.id.gcmServerLibBtn :
					startActivity(new Intent(getApplicationContext(),
							GcmServerLibActivity.class));
					break;

				case R.id.scaleImageBtn :
					startActivity(new Intent(getApplicationContext(),
							ScaleImageActivity.class));
					break;
				case R.id.playSoundBtn :
					startActivity(new Intent(getApplicationContext(),
							PlaySoundActivity.class));
					break;
				case R.id.viewPdfBtn :
					startActivity(new Intent(getApplicationContext(),
							ViewPdfActivity.class));
					break;
				case R.id.geocoderBtn :
					startActivity(new Intent(getApplicationContext(),
							GeocoderActivity.class));
					break;
				case R.id.getLocBtn :
					startActivity(new Intent(getApplicationContext(),
							GetLocationActivity.class));
					break;
				case R.id.sqlBtn :
					startActivity(new Intent(getApplicationContext(),
							SQLActivity.class));
					break;
				case R.id.layerImageBtn :
					startActivity(new Intent(getApplicationContext(),
							LayerImageActivity.class));
					break;
				case R.id.gridImageBtn :
					startActivity(new Intent(getApplicationContext(),
							GridImageActivity.class));
					break;
				case R.id.galleryLocalBtn :
					startActivity(new Intent(getApplicationContext(),
							GalleryLocalActivity.class));
					break;
				case R.id.cameraBtn :
					startActivity(new Intent(getApplicationContext(),
							CameraActivity.class));
					break;
				case R.id.ribbonMenuBtn :
					startActivity(new Intent(getApplicationContext(),
							RibbonMenuActivity.class));
					break;
				case R.id.httpAuthJsonBtn :
					startActivity(new Intent(getApplicationContext(),
							HttpAuthJsonActivity.class));
					break;

				case R.id.httpSessionBtn :
					startActivity(new Intent(getApplicationContext(),
							HttpSessionActivity.class));
					break;

				case R.id.googleAnalyticsBtn :
					startActivity(new Intent(getApplicationContext(),
							GoogleAnalyticsActivity.class));
					break;

				case R.id.chartBtn :
					startActivity(new Intent(getApplicationContext(),
							ChartActivity.class));
					break;

				case R.id.tabularBtn :
					startActivity(new Intent(getApplicationContext(),
							TabularActivity.class));
					break;

				case R.id.tabularRawHtmlBtn :
					startActivity(new Intent(getApplicationContext(),
							TabularRawHtmlActivity.class));
					break;

				case R.id.canvasDrawBtn :
					startActivity(new Intent(getApplicationContext(),
							CanvasDrawActivity.class));
					break;
				case R.id.text2SpeechBtn :
					startActivity(new Intent(getApplicationContext(),
							Text2SpeechActivity.class));
					break;

				case R.id.jsonObjectArrayBtn :
					startActivity(new Intent(getApplicationContext(),
							JsonObjectArrayActivity.class));
					break;
			}
		}
	};
}
