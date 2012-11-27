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


public class DrawView extends View implements OnTouchListener {
	private static final String TAG = "DrawView";

    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint();
    Paint paintRed = new Paint();
    int miColor = 0;
    
	public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paintRed.setColor(Color.RED);
        paintRed.setAntiAlias(true);
	}
	
	public void SetColor(int iColor)
	{
		miColor = iColor;
	}
	
	@Override
    public void onDraw(Canvas canvas) {
        for (Point point : points) {
        	if (point.color == 0) {
        		canvas.drawCircle(point.x, point.y, 5, paint);
        	} else {
        		canvas.drawCircle(point.x, point.y, 5, paintRed);
        	}
            // Log.d(TAG, "Painting: "+point);
        }
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        point.color = miColor;
        points.add(point);
        invalidate();
        Log.d(TAG, "point: " + point);
        return true;
	}
}

class Point {
    float x, y;
    int color;

    @Override
    public String toString() {
        return x + ", " + y;
    }
}

