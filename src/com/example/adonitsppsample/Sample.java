package com.example.adonitsppsample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.adonit.AdonitSPPLibrary;

public class Sample extends Activity {
	DrawView drawView;
	AdonitSPPLibrary mLibrary;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE | Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_sample);
        
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(button1OnClickListener);
        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(button2OnClickListener);
        //setContentView(R.layout.activity_sample);
        
        mLibrary = new AdonitSPPLibrary(this, mMyHandler);
        mLibrary.start();

        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }
    
    private OnClickListener button1OnClickListener = new OnClickListener() { 
        public void onClick(View v) {
        	mLibrary.start();
        }
    };
    
    private OnClickListener button2OnClickListener = new OnClickListener() { 
        public void onClick(View v) {
        	mLibrary.stop();
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
    			break;
    		case 1:
    			drawView.SetColor(1);
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
