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

import com.adonit.AdonitSPPLibrary;;

public class Sample extends Activity {
	
	AdonitSPPLibrary mLibrary;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(button1OnClickListener);
        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(button2OnClickListener);
        
        mLibrary = new AdonitSPPLibrary(this, mMyHandler);
        mLibrary.start();
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
