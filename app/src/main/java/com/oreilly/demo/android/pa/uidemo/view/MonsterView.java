package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;


/**
 * I see spots!
 *
 * @author <a href="mailto:android@callmeike.net">Blake Meike</a>
 */
public class MonsterView extends View {

   //private volatile Dots dots;
    private static Random random = new Random(System.currentTimeMillis());
   // private volatile Dot dot;
    private volatile Monsters monsters;
  //  private Dots dots = new Dots();
    /**
     * @param context the rest of the application
     */
    public MonsterView(final Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

    /**
     * @param context
     * @param attrs
     */
    public MonsterView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MonsterView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }

    /**
     * @param
     */
    //public void setDots(final Dots dots) { this.dots = dots; }

    //public void setDots (final Dots  dots ) { this.dots  = dots ; }
    public void setMonsters (final Monsters  monsters ) { this.monsters  = monsters ; }

    public class RandomCollection<E> {
        private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
        private final Random random;
        private double total = 0;

        public RandomCollection() {
            this(new Random());
        }

        public RandomCollection(Random random) {
            this.random = random;
        }

        public void add(double weight, E result) {
            if (weight <= 0) return;
            total += weight;
            map.put(total, result);
        }

        public int size(){
            return map.size();
        }

        public E next() {
            double value = random.nextDouble() * total;
            return map.ceilingEntry(value).getValue();
        }
    }

/*
    public int getRandomColor(){
        RandomCollection<Integer> colors = new RandomCollection<Integer>();

        colors.add(30, Color.YELLOW);
        colors.add(70, Color.GREEN);

        int color = colors.next();
        return color;
    }

*/

    public void setCenterDots(){


    }
    /**
     * This Method draws grid on the display and monsters
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override public void onDraw(final Canvas canvas) {
        System.out.println("enter onDraw");
      //  Rect rec = new Rect();

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




////////////////////////////////////////////////////////////////////////////////////////////////////////////


        System.out.println("monsters is null?" + monsters.getMonsters().size());

        if (null == monsters) { return; }
        // if(dots.getDots().size()==2){
        paint.setStyle(Style.FILL);
        for (final Monster monster : monsters.getMonsters()) {
            paint.setColor(monster.getColor());
            Dot dot = monster.getDot();
         /*   canvas.drawRect(
                    monster.getDot().getY() * addx+50,
                    monster.getDot().getX() * addy+50,
                    monster.getDot().getY() * addx + addx-50,
                    monster.getDot().getX() * addy + addy-50,
                    paint);*/

            canvas.drawCircle(monster.getDot().getX() * addx + addx/2,monster.getDot().getY()*addy+addy/2,50,paint);
        }

        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
// 绘图
        // canvas.drawBitmap(bitmap, dot.getY() * addx, dot.getX() * addy, paint);}

        monsters.clearMonsters();//}
    }
}

