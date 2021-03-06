package com.adhamenaya.microchart.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.adhamenaya.microchart.model.ChartData;
import com.adhamenaya.microchart.model.ValueColor;
import com.adhamenaya.microchart.utils.Box;

import static com.adhamenaya.microchart.model.ValueColor.NEUTRAL;

/**
 * Created by Admin on 18/12/2016.
 */

public class DeltaChart extends Chart {

    private ValueColor color;
    private String deltaDisplayValue;
    private float value1;
    private float value2;
    private float deltaValue;
    private String valueTitle1;
    private String valueTitle2;

    Box box1;
    Box box2;
    Box delta;

    public DeltaChart(Context context){
        super(context);
    }
    @Override
    protected void paintChart(Canvas canvas) {
      //  canvas.drawRect(box1.x1, box1.y1, box1.x2,box1.y2, getRectPaint());
        canvas.drawRect(delta.x1, delta.y1, delta.x2,delta.y2, getRectPaint());
      //  canvas.drawRect(box2.x1, box2.y1, box2.x2,box2.y2, getRectPaint());
    }

    @Override
    protected void prepare() {

        float deltaValue = value2 - value1;
        float maxValueWidth;
        float space = 0;
        float currentY = 0;

        // Calculate space between boxes
        int boxHeight = (int) ((mHeight / 3) * 0.9);

        // Calculate the space between the bars
        space = (mHeight / 3) - boxHeight;

        deltaValue = value1 - value2;


        if(value1 < 0 ^ value2 < 0){
         // Start from left and right
            maxValueWidth = value1 + value2;
            // Box 1
            box1 = new Box(0,currentY,Math.abs(value1),currentY+boxHeight,NEUTRAL);
            currentY+=space;
            delta = new Box(Math.abs(value1),currentY,deltaValue,currentY+boxHeight,NEUTRAL);
            currentY+=space;
            box2 = new Box(Math.abs(value1)+deltaValue,currentY,Math.abs(value1),currentY+boxHeight,NEUTRAL);

        }else if (value1 < 0 && value2 < 0){
         // Start from left
            maxValueWidth = Math.max(Math.abs(value1),Math.abs(value2));
        }else {
         // Start from right
            maxValueWidth = Math.max(value1,value2);
        }
    }

    @Override
    public void setData(ChartData data) {

    }

    public void setValues(float value1,float value2){
        this.value1 = value1;
        this.value2 = value2;
        prepare();
        invalidate();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Get measured height and width of the view
        setMeasuredDimension(getMeasurement(widthMeasureSpec, mWidth),
                getMeasurement(heightMeasureSpec, mHeight));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("---", "onDraw");

        super.onDraw(canvas);
        paintChart(canvas);
    }
}
