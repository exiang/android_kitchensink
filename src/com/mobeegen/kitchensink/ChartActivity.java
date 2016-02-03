package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChartActivity extends Activity
{
	protected Button firstChartBtn;
	protected Button acChartPieBtn;
	protected Button acChartBarBtn;
	protected Button acChartMultiBarBtn;
	protected Button aiChartPieBtn;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        firstChartBtn = (Button) findViewById(R.id.firstChartBtn);
        firstChartBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), FirstChartActivity.class)); 
            }
        });
        
        acChartPieBtn = (Button) findViewById(R.id.acChartPieBtn);
        acChartPieBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), AcChartPieActivity.class)); 
            }
        });
        
        acChartBarBtn = (Button) findViewById(R.id.acChartBarBtn);
        acChartBarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), AcChartBarActivity.class)); 
            }
        });
        
        acChartMultiBarBtn = (Button) findViewById(R.id.acChartMultiBarBtn);
        acChartMultiBarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), AcChartMultiBarActivity.class)); 
            }
        });

        aiChartPieBtn = (Button) findViewById(R.id.aiChartPieBtn);
        aiChartPieBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(getApplicationContext(), AiChartPieActivity.class)); 
            }
        });
	}
}
