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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.adonit.AdonitSPPLibrary;

public class Sample extends Activity {
	DrawView drawView;
	AdonitSPPLibrary mLibrary;
	TextView mColorView;
	Button mClearButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE | Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_sample);

        mClearButton = (Button)findViewById(R.id.buttonClear);
        mClearButton.setOnClickListener(buttonClearOnClickListener);
        
        mColorView = (TextView)findViewById(R.id.ColorView);
        
        mLibrary = new AdonitSPPLibrary(this, mMyHandler);
        mLibrary.start();
        
        drawView = new DrawView(this);
        MarginLayoutParams ml = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ml.setMargins(300, 0, 0, 0);
		drawView.setLayoutParams(ml);
		LinearLayout ll = (LinearLayout)findViewById(R.id.LayoutDraw);
        ll.addView(drawView);
    }
    
    private OnClickListener buttonClearOnClickListener = new OnClickListener() { 
        public void onClick(View v) {
        	drawView.clear();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sample, menu);
        menu.add(0, 0, 0, "Black");
        menu.add(0, 1, 0, "Red");
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	super.onOptionsItemSelected(item);
    	switch(item.getItemId()) {
    		case 0:
    			drawView.SetColor(0);
    			mColorView.setBackgroundColor(Color.BLACK);
    			break;
    		case 1:
    			drawView.SetColor(1);
    			mColorView.setBackgroundColor(Color.RED);
    			break;
    	}
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
					break;
			}
		}
    };
}
