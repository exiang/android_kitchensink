package com.mobeegen.kitchensink;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;

public class RadioTabActivity extends Activity {
	
	protected ViewFlipper vf;
	private Context mContext;
	RadioButton rb0, rb1, rb2;
	RadioGroup rg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiotabbing);
        mContext = this;
        
        rb0 = (RadioButton) findViewById(R.id.rad_tab1); 
        rb1 = (RadioButton) findViewById(R.id.rad_tab2); 
        rb2 = (RadioButton) findViewById(R.id.rad_tab3); 
        rg = (RadioGroup) findViewById(R.id.radio_tabber);
        vf = (ViewFlipper) findViewById(R.id.main_frame);
        
        rb0.setChecked(true);
        vf.setDisplayedChild(0);
        
        rb0.setOnClickListener(listener);
        rb1.setOnClickListener(listener);
        rb2.setOnClickListener(listener);
    }
    
    private OnClickListener listener = new OnClickListener() {
    	public void onClick(View v) {
    		int selectedTab = vf.getDisplayedChild();
    		switch (v.getId()) {
    		case R.id.rad_tab1:
    			if (selectedTab != 0) {
					vf.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
					vf.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
	    			vf.setDisplayedChild(0);
				}				
    			break;
    		case R.id.rad_tab2:
    			if (selectedTab != 1) {
	    			vf.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
					vf.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
	    			vf.setDisplayedChild(1);
    			}
    			break;
    		case R.id.rad_tab3:
    			if (selectedTab != 2) {
	    			vf.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
					vf.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
	    			vf.setDisplayedChild(2);
    			}
    			break;
    		}
    	}
    };

    
}
