package com.example.adonitsppsample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.adonit.AdonitSPPLibrary;

public class Sample extends Activity {
	DrawView drawView;
	boolean mbConnect = true;
	AdonitSPPLibrary mLibrary;
	Switch mConnectSwitch;
	TextView mColorView;
	Button mClearButton;
	TextView mPressureTextView;
	TextView mButton1TextView;
	TextView mButton2TextView;
	TextView mBatteryTextView;
	//TextView mStrokeWidthTextView;
	int miColorIndex = 0;
	boolean mbButton1 = false;
	boolean mbButton2 = false;
	int miPressure = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE | Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_sample);

        mConnectSwitch = (Switch)findViewById(R.id.switch1);
        mConnectSwitch.setChecked(mbConnect);
        mConnectSwitch.setOnCheckedChangeListener(mConnectListener);
        mClearButton = (Button)findViewById(R.id.buttonClear);
        mClearButton.setOnClickListener(buttonClearOnClickListener);
        
        mColorView = (TextView)findViewById(R.id.ColorView);
        mPressureTextView = (TextView)findViewById(R.id.textPressure);
        mButton1TextView = (TextView)findViewById(R.id.textButton1);
        mButton2TextView = (TextView)findViewById(R.id.textButton2);
        mBatteryTextView = (TextView)findViewById(R.id.textBattery);
        //mStrokeWidthTextView = (TextView)findViewById(R.id.textStrokeWidth);
        
        mLibrary = new AdonitSPPLibrary(this, mMyHandler);
        mLibrary.start();
        
        drawView = new DrawView(this);
        MarginLayoutParams ml = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ml.setMargins(300, 0, 0, 0);
		drawView.setLayoutParams(ml);
		LinearLayout ll = (LinearLayout)findViewById(R.id.LayoutDraw);
        ll.addView(drawView);
    }
    
    private OnCheckedChangeListener mConnectListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
				mLibrary.resume();
			} else {
				mLibrary.pause();
			}
		}
    };
    
    private OnClickListener buttonClearOnClickListener = new OnClickListener() { 
        public void onClick(View v) {
        	drawView.clear();
        }
    };
    
    private void changeColor(int iIndex)
    {
    	miColorIndex = iIndex;
    	switch(iIndex) {
			case 0:
				drawView.SetColor(0);
				mColorView.setBackgroundColor(Color.BLACK);
				break;
			case 1:
				drawView.SetColor(1);
				mColorView.setBackgroundColor(Color.RED);
				break;
			case 2:
				drawView.SetColor(2);
				mColorView.setBackgroundColor(Color.GREEN);
				break;
			case 3:
				drawView.SetColor(3);
				mColorView.setBackgroundColor(Color.YELLOW);
				break;
			case 4:
				drawView.SetColor(4);
				mColorView.setBackgroundColor(Color.BLUE);
				break;
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sample, menu);
        menu.add(0, 0, 0, "Black");
        menu.add(0, 1, 0, "Red");
        menu.add(0, 2, 0, "Green");
        menu.add(0, 3, 0, "Yellow");
        menu.add(0, 4, 0, "Blue");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	super.onOptionsItemSelected(item);
    	changeColor(item.getItemId());
    	return true;
    }
    
    Handler mMyHandler = new Handler()
    {
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{	
				case AdonitSPPLibrary.UPDATE:
					Bundle bundle = msg.getData();
					Log.d("AdonitSPPSample", String.valueOf(bundle.getInt("pressure")) + " " + String.valueOf(bundle.getBoolean("button1")) + " " + String.valueOf(bundle.getBoolean("button2")));
					miPressure = bundle.getInt("pressure");
					if (miPressure <= 50) {
						drawView.SetStrokeWidth(3);
						//mStrokeWidthTextView.setText("3");
					} else if (miPressure > 50 && miPressure <= 100) {
						drawView.SetStrokeWidth(5);
						//mStrokeWidthTextView.setText("5");
					} else if (miPressure > 100 && miPressure <= 150) {
						drawView.SetStrokeWidth(7);
						//mStrokeWidthTextView.setText("7");
					} else if (miPressure > 150 && miPressure <= 200) {
						drawView.SetStrokeWidth(9);
						//mStrokeWidthTextView.setText("9");
					} else if (miPressure > 200 && miPressure <= 255) {
						drawView.SetStrokeWidth(11);
						//mStrokeWidthTextView.setText("11");
					}
					mPressureTextView.setText(String.valueOf(miPressure));
					mButton1TextView.setText(String.valueOf(bundle.getBoolean("button1")));
					mButton2TextView.setText(String.valueOf(bundle.getBoolean("button2")));
					mBatteryTextView.setText(String.valueOf(bundle.getInt("battery")));
					if (!mbButton1 && bundle.getBoolean("button1"))
					{
						int iColorIndex = miColorIndex + 1;
						if (iColorIndex > 4) {
							iColorIndex = 0;
						}
						changeColor(iColorIndex);
					}
					if (!mbButton2 && bundle.getBoolean("button2"))
					{
						int iColorIndex = miColorIndex - 1;
						if (iColorIndex < 0) {
							iColorIndex = 4;
						}
						changeColor(iColorIndex);
					}
					mbButton1 = bundle.getBoolean("button1");
					mbButton2 = bundle.getBoolean("button2");
					break;
			}
		}
    };
}
