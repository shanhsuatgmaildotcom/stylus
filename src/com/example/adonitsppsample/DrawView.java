package com.example.adonitsppsample;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class DrawView extends SurfaceView implements SurfaceHolder.Callback 
{
	private DrawingThread _thread;	
	private MyPath mMyPath;
	//private List _graphics = new ArrayList();
	List<MyPath> _graphics = new ArrayList<MyPath>();
	private Paint mPaint;
	int miColor = 0;
    float mfPenWidth = 3;
    Canvas mCanvas = null;

	public DrawView(Context context) 
	{
	    super(context);
		getHolder().addCallback(this);
		_thread = new DrawingThread(getHolder(), this);
		mPaint = new Paint();
	    mPaint.setDither(true);
	    mPaint.setColor(0xFFFF0000);
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(3);
	}
	
	public void SetColor(int iColor)
	{
		miColor = iColor;
	}
	
	public void SetStrokeWidth(float fWidth)
	{
		mfPenWidth = (float)fWidth;
		//Log.d(TAG, "mfPenWidth:" + String.valueOf(mfPenWidth));
	}
	
	public void clear()
	{
		synchronized (_thread.getSurfaceHolder()) {
			_graphics.clear();
			//this.invalidate();
			this.onDraw(mCanvas);
		}
	}

	@Override
	public boolean onTouchEvent (MotionEvent event) 
	{
		synchronized (_thread.getSurfaceHolder()) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				mMyPath = new MyPath();
				mMyPath.moveTo(event.getX(), event.getY());
				mMyPath.fWidth = mfPenWidth;
			} else if(event.getAction() == MotionEvent.ACTION_MOVE) {
				mMyPath.lineTo(event.getX(), event.getY());
				mMyPath.color = miColor;
				_graphics.add(mMyPath);
				mMyPath = new MyPath();
				mMyPath.fWidth = mfPenWidth;
				mMyPath.moveTo(event.getX(), event.getY());
			} else if(event.getAction() == MotionEvent.ACTION_UP) {
				mMyPath.lineTo(event.getX(), event.getY());
				mMyPath.fWidth = mfPenWidth;
				_graphics.add(mMyPath);
			}
			return true;
		}
	}

	@Override
	public void onDraw(Canvas canvas) 
	{
		mCanvas = canvas;
		for (MyPath onePath : _graphics) {
			switch (onePath.color) 
			{
			case 0:
				mPaint.setColor(Color.BLACK);
				mPaint.setStrokeWidth(onePath.fWidth);
				canvas.drawPath(onePath, mPaint);
				break;
			case 1:
				mPaint.setColor(Color.RED);
				mPaint.setStrokeWidth(onePath.fWidth);
				canvas.drawPath(onePath, mPaint);
				break;
			case 2:
				mPaint.setColor(Color.GREEN);
				mPaint.setStrokeWidth(onePath.fWidth);
				canvas.drawPath(onePath, mPaint);
				break;
			case 3:
				mPaint.setColor(Color.YELLOW);
				mPaint.setStrokeWidth(onePath.fWidth);
				canvas.drawPath(onePath, mPaint);
				break;
			case 4:
				mPaint.setColor(Color.BLUE);
				mPaint.setStrokeWidth(onePath.fWidth);
				canvas.drawPath(onePath, mPaint);
				break;
			} 
		}
	}


	public void surfaceCreated(SurfaceHolder holder) 
	{
		// TODO Auto-generated method stub
		_thread.setRunning(true);
		_thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		 // TODO Auto-generated method stub	
		 boolean retry = true;
         _thread.setRunning(false);
         while (retry) {
             try {
                 _thread.join();
                 retry = false;
             } catch (InterruptedException e) {
                 // we will try it again and again...
             }
         }
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
	 // TODO Auto-generated method stub
	}
}

class DrawingThread extends Thread {
     private SurfaceHolder _surfaceHolder;
     private DrawView _panel;
     private boolean _run = false;

     public DrawingThread(SurfaceHolder surfaceHolder, DrawView panel) 
     {
         _surfaceHolder = surfaceHolder;
         _panel = panel;
     }

     public void setRunning(boolean run) 
     {
         _run = run;
     }

     public SurfaceHolder getSurfaceHolder() 
     {
         return _surfaceHolder;
     }

     @Override
     public void run() 
     {
         Canvas c;
         while (_run) {
             c = null;
             try {
                 c = _surfaceHolder.lockCanvas(null);
                 synchronized (_surfaceHolder) {
                     _panel.onDraw(c);
                 }
             } finally {
                 // do this in a finally so that if an exception is thrown
                 // during the above, we don't leave the Surface in an
                 // inconsistent state
                 if (c != null) {
                     _surfaceHolder.unlockCanvasAndPost(c);
                 }
             }
         }
     }
}

class MyPath extends Path
{
    int color = 1;
    float fWidth;
}

