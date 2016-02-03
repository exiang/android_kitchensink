package com.mobeegen.kitchensink;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SliderActivity extends Activity implements OnTouchListener {
	
	private static final int SWIPE_MIN_DISTANCE = 100;
	private static final int TAP_MAX_DISTANCE = 30;
	private static final int DOWNTIME_MAX = 300;
	private static final int SWIPETIME_MAX = 870;
	
	//private GestureDetector mGestureDetector;
	private ViewFlipper vf;
	private Context mContext;
	private TextView descText, mainText, headerText;
	private float downXValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Initialize();
    }

    private void Initialize() 
    {
    	mContext = this;
		descText = (TextView) findViewById (R.id.text_desc);
		mainText = (TextView) findViewById (R.id.text_main);
		headerText = (TextView) findViewById (R.id.header);
		vf = (ViewFlipper) findViewById(R.id.viewflipper);
		
		vf.addView(addImageView(R.drawable.hotel));
		vf.addView(addImageView(R.drawable.slide_blue));
		vf.addView(addImageView(R.drawable.slide_green));
		vf.addView(addImageView(R.drawable.slide_red));
		
        vf.setOnTouchListener((OnTouchListener) this);
	}
    
    View addImageView(int resId) {
		ImageView iv = new ImageView(this);
		iv.setImageResource(resId);

		return iv;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// Get the action that was done on this touch event
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                // store the X value when the user's finger was pressed down
                downXValue = event.getX();
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                // Get the X value when the user released his/her finger
                float currentX = event.getX();
                float horizontalMovement = currentX - downXValue;
                long timePressed = event.getEventTime() - event.getDownTime();
                mainText.setText("Horizontal Movement: " + horizontalMovement + 
                		"\nTime pressed: " + timePressed);

                // Left to Right
                if (horizontalMovement > SWIPE_MIN_DISTANCE && timePressed < SWIPETIME_MAX)
                {
                	descText.setText("Swipe Right");
                	vf.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
					vf.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
                    vf.showNext();
                    break;
                }

                // Right to Left
                if (horizontalMovement < -SWIPE_MIN_DISTANCE && timePressed < SWIPETIME_MAX)
                {
                	descText.setText("Swipe Left");
                	vf.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
					vf.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    vf.showPrevious();
                    break;
                }
                
                // Check for tap
                if (Math.abs(horizontalMovement) < TAP_MAX_DISTANCE && timePressed < DOWNTIME_MAX)
                {
                	descText.setText("Tap");
                	break;
                }
                
                else
                	descText.setText("");
                
                break;
            }
        }

        return true;
	}
}
