package com.example.adonitsppsample;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class DrawView extends SurfaceView implements SurfaceHolder.Callback 
{
	private DrawingThread _thread;	
	private Path path;
	private ArrayList _graphics = new ArrayList();
	private Paint mPaint;

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

	@Override
	public boolean onTouchEvent (MotionEvent event) 
	{
		synchronized (_thread.getSurfaceHolder()) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				path = new Path();
				path.moveTo(event.getX(), event.getY());
			} else if(event.getAction() == MotionEvent.ACTION_MOVE) {
				path.lineTo(event.getX(), event.getY());
				_graphics.add(path);
				path = new Path();
				path.moveTo(event.getX(), event.getY());
			} else if(event.getAction() == MotionEvent.ACTION_UP) {
				path.lineTo(event.getX(), event.getY());
				_graphics.add(path);
			}
			return true;
		}
	}

	@Override
	public void onDraw(Canvas canvas) 
	{
		for (Path path : _graphics) {
			canvas.drawPath(path, mPaint);
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
    int color;
    float fWidth;
}

