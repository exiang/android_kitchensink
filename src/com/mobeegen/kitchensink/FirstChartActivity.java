package com.mobeegen.kitchensink;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class FirstChartActivity extends Activity // implements OnTouchListener
{

    private XYPlot mySimpleXYPlot;	
	//private PointF minXY;
	//private PointF maxXY;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstchart);

        // initialize our XYPlot reference:
        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
		//mySimpleXYPlot.setOnTouchListener(this);

        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                Color.rgb(200, 200, 0));                  // fill color (none)

        // add a new series' to the xyplot:
        mySimpleXYPlot.addSeries(series1, series1Format);

        // same as above:
        mySimpleXYPlot.addSeries(series2,
                new LineAndPointFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100), Color.rgb(0, 200, 200)));

        // reduce the number of range labels
        mySimpleXYPlot.setTicksPerRangeLabel(3);

        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        mySimpleXYPlot.disableAllMarkup();
        
        //mySimpleXYPlot.calculateMinMaxVals();
		//minXY=new PointF(mySimpleXYPlot.getCalculatedMinX().floatValue(),mySimpleXYPlot.getCalculatedMinY().floatValue());
		//maxXY=new PointF(mySimpleXYPlot.getCalculatedMaxX().floatValue(),mySimpleXYPlot.getCalculatedMaxY().floatValue());

        
    }

	/*static final int NONE = 0;
	static final int ONE_FINGER_DRAG = 1;
	static final int TWO_FINGERS_DRAG = 2;
	int mode = NONE;

	PointF firstFinger;
	float lastScrolling;
	float distBetweenFingers;
	float lastZooming;

	
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: // Start gesture
			firstFinger = new PointF(event.getX(), event.getY());
			mode = ONE_FINGER_DRAG;
			break;
		case MotionEvent.ACTION_UP: 
		case MotionEvent.ACTION_POINTER_UP:
			//When the gesture ends, a thread is created to give inertia to the scrolling and zoom 
			Timer t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						while(Math.abs(lastScrolling)>1f || Math.abs(lastZooming-1)<1.01){ 
						lastScrolling*=.8;
						scroll(lastScrolling);
						lastZooming+=(1-lastZooming)*.2;
						zoom(lastZooming);
						mySimpleXYPlot.setDomainBoundaries(minXY.x, maxXY.x, BoundaryMode.AUTO);
						try {
							mySimpleXYPlot.postRedraw();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// the thread lives until the scrolling and zooming are imperceptible
					}
					}
				}, 0);

		case MotionEvent.ACTION_POINTER_DOWN: // second finger
			distBetweenFingers = spacing(event);
			// the distance check is done to avoid false alarms
			if (distBetweenFingers > 5f) {
				mode = TWO_FINGERS_DRAG;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == ONE_FINGER_DRAG) {
				PointF oldFirstFinger=firstFinger;
				firstFinger=new PointF(event.getX(), event.getY());
				lastScrolling=oldFirstFinger.x-firstFinger.x;
				scroll(lastScrolling);
				lastZooming=(firstFinger.y-oldFirstFinger.y)/mySimpleXYPlot.getHeight();
				if (lastZooming<0)
					lastZooming=1/(1-lastZooming);
				else
					lastZooming+=1;
				zoom(lastZooming);
				mySimpleXYPlot.setDomainBoundaries(minXY.x, maxXY.x, BoundaryMode.AUTO);
				mySimpleXYPlot.redraw();

			} else if (mode == TWO_FINGERS_DRAG) {
				float oldDist =distBetweenFingers; 
				distBetweenFingers=spacing(event);
				lastZooming=oldDist/distBetweenFingers;
				zoom(lastZooming);
				mySimpleXYPlot.setDomainBoundaries(minXY.x, maxXY.x, BoundaryMode.AUTO);
				mySimpleXYPlot.redraw();
			}
			break;
		}

		return true;
	}
	
	private void zoom(float scale) 
	{
		float domainSpan = maxXY.x	- minXY.x;
		float domainMidPoint = maxXY.x		- domainSpan / 2.0f;
		float offset = domainSpan * scale / 2.0f;
		minXY.x=domainMidPoint- offset;
		maxXY.x=domainMidPoint+offset;
	}

	private void scroll(float pan) 
	{
		float domainSpan = maxXY.x	- minXY.x;
		float step = domainSpan / mySimpleXYPlot.getWidth();
		float offset = pan * step;
		minXY.x+= offset;
		maxXY.x+= offset;
	}

	private float spacing(MotionEvent event) 
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}*/
}