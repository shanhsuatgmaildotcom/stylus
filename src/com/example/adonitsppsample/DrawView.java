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

public class DrawView extends View implements OnTouchListener {
	private static final String TAG = "DrawView";

    List<MyPath> path = new ArrayList<MyPath>();
    Paint paint = new Paint();
    Paint paintRed = new Paint();
    int miColor = 0;
    private MyPath myPath;
    
	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint.setDither(true);
        paint.setColor(0xFF000000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setDither(true);
        
        paintRed.setColor(0xFFFF0000);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeJoin(Paint.Join.ROUND);
        paintRed.setStrokeCap(Paint.Cap.ROUND);
        paintRed.setStrokeWidth(3);
	}
	
	public void SetColor(int iColor)
	{
		miColor = iColor;
	}
	
	@Override
    public void onDraw(Canvas canvas) {
        /*for (Point point : points) {
        	if (point.color == 0) {
        		canvas.drawCircle(point.x, point.y, 5, paint);
        	} else {
        		canvas.drawCircle(point.x, point.y, 5, paintRed);
        	}
            // Log.d(TAG, "Painting: "+point);
        }*/
		for (MyPath onePath : path) {
			switch (onePath.color) 
			{
				case 0:
					canvas.drawPath(onePath, paint);
					break;
				case 1:
					canvas.drawPath(onePath, paintRed);
					break;
			} 
		}
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		/*Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        point.color = miColor;
        points.add(point);
        invalidate();
        Log.d(TAG, "point: " + point);*/
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
        	myPath = new MyPath();
        	myPath.moveTo(event.getX(), event.getY());
        } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	myPath.lineTo(event.getX(), event.getY());
        	path.add(myPath);
        	myPath.color = miColor;
            myPath = new MyPath();
            myPath.moveTo(event.getX(), event.getY());
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
        	myPath.lineTo(event.getX(), event.getY());
        	path.add(myPath);
        }
        invalidate();
        return true;
	}
}

class MyPath extends Path
{
    int color;
}

