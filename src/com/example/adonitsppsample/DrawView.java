package com.example.adonitsppsample;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.graphics.Path;
import android.widget.TextView;

public class DrawView extends View implements OnTouchListener {
	private static final String TAG = "DrawView";

    List<MyPath> path = new ArrayList<MyPath>();
    Paint paint = new Paint();
    //Paint paintRed = new Paint();
    int miColor = 0;
    float mfPenWidth = 3;
    private MyPath myPath;
    
	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setDither(true);
	}
	
	public void SetColor(int iColor)
	{
		miColor = iColor;
	}
	
	public void SetStrokeWidth(float fWidth)
	{
		mfPenWidth = (float)fWidth;
		Log.d(TAG, "mfPenWidth:" + String.valueOf(mfPenWidth));
	}
	
	@Override
    public void onDraw(Canvas canvas) {
		for (MyPath onePath : path) {
			switch (onePath.color) 
			{
				case 0:
					paint.setColor(Color.BLACK);
					paint.setStrokeWidth(onePath.fWidth);
					canvas.drawPath(onePath, paint);
					break;
				case 1:
					paint.setColor(Color.RED);
					paint.setStrokeWidth(onePath.fWidth);
					canvas.drawPath(onePath, paint);
					break;
				case 2:
					paint.setColor(Color.GREEN);
					paint.setStrokeWidth(onePath.fWidth);
					canvas.drawPath(onePath, paint);
					break;
				case 3:
					paint.setColor(Color.YELLOW);
					paint.setStrokeWidth(onePath.fWidth);
					canvas.drawPath(onePath, paint);
					break;
				case 4:
					paint.setColor(Color.BLUE);
					paint.setStrokeWidth(onePath.fWidth);
					canvas.drawPath(onePath, paint);
					break;
			} 
		}
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
        	myPath = new MyPath();
        	myPath.moveTo(event.getX(), event.getY());
        	myPath.fWidth = mfPenWidth;
        } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	myPath.lineTo(event.getX(), event.getY());
        	path.add(myPath);
        	myPath.color = miColor;
            myPath = new MyPath();
            myPath.moveTo(event.getX(), event.getY());
            myPath.fWidth = mfPenWidth;
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
        	myPath.lineTo(event.getX(), event.getY());
        	path.add(myPath);
        	myPath.fWidth = mfPenWidth;
        }
        invalidate();
        return true;
	}
	
	public void clear()
	{
		path.clear();
		invalidate();
	}
}

class MyPath extends Path
{
    int color;
    float fWidth;
}

