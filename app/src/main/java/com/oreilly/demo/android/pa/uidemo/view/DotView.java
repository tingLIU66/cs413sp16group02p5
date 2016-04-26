package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.model.LiveMonster;


/**
 * I see spots!
 *
 * @author <a href="mailto:android@callmeike.net">Blake Meike</a>
 */
public class DotView extends View {

   private volatile Dots dots;

   // private volatile Dot dot;
    private volatile LiveMonster monsters;
  //  private Dots dots = new Dots();
    /**
     * @param context the rest of the application
     */
    public DotView(final Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

    /**
     * @param context
     * @param attrs
     */
    public DotView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public DotView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }

    /**
     * @param dots
     */
    //public void setDots(final Dots dots) { this.dots = dots; }

    public void setDots (final Dots  dots ) { this.dots  = dots ; }

    /**
     * This Method draws grid on the display and monsters
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override public void onDraw(final Canvas canvas) {
        System.out.println("enter onDraw");
        float addx = getWidth()/5;
        float addy = getHeight()/7;
        float ptsx[] = {addx,     0, addx,     getHeight(),
                        addx * 2, 0, addx * 2, getHeight(),
                        addx * 3, 0, addx * 3, getHeight(),
                        addx * 4, 0, addx * 4, getHeight()};

        float ptsy[] = {0, addy,     getWidth(), addy,
                        0, addy * 2, getWidth(), addy * 2,
                        0, addy * 3, getWidth(), addy * 3,
                        0, addy * 4, getWidth(), addy * 4,
                        0, addy * 5, getWidth(), addy * 5,
                        0, addy * 6, getWidth(), addy * 6,};

        final Paint paint = new Paint();
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(hasFocus() ? Color.WHITE : Color.GRAY);
        paint.setShadowLayer(10f, 3f, 3f, Color.BLACK);
        canvas.drawRect(0, 0, getWidth() - 1, getHeight() - 1, paint);
        canvas.drawLines(ptsx, paint);
        canvas.drawLines(ptsy, paint);


          if (null == dots) { return; }
       //  dots.addDot(1,3);
        paint.setStyle(Style.FILL);
        for (final Dot dot : dots.getDots()) {
            paint.setColor(Color.GREEN);
            canvas.drawRect(
                    dot.getY() * addx,
                    dot.getX() * addy,
                    dot.getY() * addx + addx-8,
                    dot.getX() * addy + addy-8,
                    paint);
        }
    }
}

