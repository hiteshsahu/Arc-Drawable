package com.hiteshsahu.curvedbuttons;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Originally Created by AnderWeb (Gustavo Claramunt) on 7/10/14.
 */
public class ArcDrawable extends Drawable {

    private final Random r;
    private int left, right, top, bottom;
    private  Paint[] paints = new Paint[3];
    private HashMap<Path, Paint> pathMap = new HashMap();
    private int height;
    private int width;
    private int centerX;
    private int centerY;


    public ArcDrawable() {

         r = new Random();

        // white paint
        Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(3);
        whitePaint.setColor(Color.WHITE);
        paints[0]= whitePaint;

        // green paint
        Paint greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setColor(Color.GREEN);
        paints[1]= greenPaint;

        // red paint
        Paint redPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
        paints[2]= redPaint;
    }

    public void setCenter(int centerX, int centerY)
    {
        this.centerX =centerX;
        this.centerY =centerY;
        invalidateSelf();

    }

    @Override
    public void draw(Canvas canvas) {




        canvas.drawRect(width/2-200, height/2-400, width/2+200, height/2+400, paints[0]);

        canvas.drawRect(width/2-180, height/2-380, width/2+180, height/2+350, paints[0]);


        for (int i =0; i <width; i+=15)
        {
           // RectF rect = new RectF(centerX,centerY,centerX+i,centerY+i);
           //canvas.drawOval(rect, paints[1]);


            //paints[2].setARGB( 255,r.nextInt(255), r.nextInt(255), r.nextInt(255));
           canvas.drawCircle(centerX, centerY, centerY+i, paints[2]);

        }

        for (int i =width; i >0; i-=15)
        {
            //paints[2].setARGB( 255,r.nextInt(255), r.nextInt(255), r.nextInt(255));

            canvas.drawCircle(width-centerX,
                    centerY,
                    centerY+i, paints[1]);

        }


        //----------USE PATHS----------
        // Define and use custom  Path
       // for (Map.Entry<Path, Paint> entry : pathMap.entrySet()) {
            // Draw Path on respective Paint style
            //  canvas.drawPath(entry.getKey(),  entry.getValue());

        //}


        // -------OR use conventional Style---------
        //drawArcs(canvas);

    }


    //Same result
    private void drawArcs(Canvas canvas) {
        RectF rectF = new RectF(left, top, right, bottom);

        // method 1
        canvas.drawArc (rectF, 90, 45, true,  paints[0]);

        // method 2
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc (left, top, right, bottom, 0, 45, true, paints[1]);
        }

        // method two with stroke
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc (left, top, right, bottom, 180, 45, true,  paints[2]);
        }
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

         width = bounds.width();
         height = bounds.height();

        centerY= height/2;

        left = bounds.left;
        right = bounds.right;
        top = bounds.top;
        bottom = bounds.bottom;

        final int size = Math.min(width, height);
        final int centerX = bounds.left + (width / 2);
        final int centerY = bounds.top + (height / 2);

        pathMap.clear();
        //update pathmap using new bounds
        recreatePathMap(size, centerX, centerY);
        invalidateSelf();
    }


    private Path recreatePathMap(int size, int centerX, int centerY) {

        RectF rectF = new RectF(left, top, right, bottom);

//        // first arc
       Path arcPath = new Path();
        arcPath.moveTo(centerX,centerY);
        arcPath.arcTo (rectF, 90, 45);
        arcPath.close();
        // add to draw Map
        pathMap.put(arcPath, paints[0]);

        //second arc
        arcPath = new Path();
        arcPath.moveTo(centerX,centerY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          arcPath.arcTo (rectF, 0, 45);
        }
        arcPath.close();
        // add to draw Map
        pathMap.put(arcPath, paints[1]);

        // third arc
        arcPath = new Path();
        arcPath.moveTo(centerX,centerY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            arcPath.arcTo (rectF, 90, 180);

        }
        arcPath.close();
        // add to draw Map
        pathMap.put(arcPath, paints[2]);

        return arcPath;

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }


}