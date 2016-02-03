package com.mobeegen.kitchensink;

import com.artfulbits.aiCharts.ChartView;
import com.artfulbits.aiCharts.Base.ChartArea;
import com.artfulbits.aiCharts.Base.ChartAxisScale;
import com.artfulbits.aiCharts.Base.ChartLegend;
import com.artfulbits.aiCharts.Base.ChartLegend.LayoutMode;
import com.artfulbits.aiCharts.Base.ChartPalette;
import com.artfulbits.aiCharts.Base.ChartPoint;
import com.artfulbits.aiCharts.Base.ChartSeries;
import com.artfulbits.aiCharts.Base.ChartLayoutElement.Alignment;
import com.artfulbits.aiCharts.Base.ChartLayoutElement.Dock;
import com.artfulbits.aiCharts.Types.ChartPieType;
import com.artfulbits.aiCharts.Types.ChartTypes;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View.OnTouchListener;

public class AiChartPieActivity extends Activity
{
	ChartView chartView;
	ChartLegend chartLegend;
	ScaleGestureDetector scalingDetector;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aichartpie);

        chartView = new ChartView(this);
        chartLegend = new ChartLegend();
		
        ChartSeries series = new ChartSeries("PieChart", ChartTypes.Pie);
        String[] labels = 
		{ 
			"Africa", "Asia", "Europe",
			"Latin America", "Northern America", "Oceania",
			"Babylon", "Alantis" , "Moon", "Mars"
		};
        
        for(int i = 0; i < 10; i++)
		{
        	ChartPoint point = series.getPoints().addXY(i, 100 * i);
        	point.setLabel(labels[i]);
		}
        
        series.setShowLabel(false);
        series.setAttribute(ChartPieType.LABEL_STYLE,
				ChartPieType.LabelStyle.OutsideColumn);
		
        //chartView.setBackgroundResource(android.R.drawable.);
        chartView.setBackgroundColor(Color.rgb(100, 100, 100));
        chartView.getSeries().add(series);
        chartView.getAreas().add(new ChartArea());
        chartView.setPalette(new ChartPalette().Rainbow);
        
        chartLegend.setAlignment(Alignment.Near);
        chartLegend.setDock(Dock.Top);
        chartLegend.setLayoutMode(LayoutMode.Auto);
        chartLegend.setSpacing(20);
        chartLegend.setVisible(true);
        chartView.getLegends().add(chartLegend);
        
		
		setContentView(chartView);
    }
}