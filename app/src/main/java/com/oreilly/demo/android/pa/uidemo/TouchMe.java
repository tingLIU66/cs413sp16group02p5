package com.oreilly.demo.android.pa.uidemo;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.AsyncTask;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.DotWorld;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.model.LiveMonster;
import com.oreilly.demo.android.pa.uidemo.view.DotView;

/** Android UI demo program */
public class TouchMe extends Activity {

    /** Listen for taps. */
    private static final class TrackingTouchListener implements View.OnTouchListener {
        private final Dots mDots;
       // private final Dot mDot;

         TrackingTouchListener(final Dots dots) { mDots = dots; }

            @Override public boolean onTouch(final View v, final MotionEvent evt) {
            final int action = evt.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                            evt.getX();
                            evt.getY();
                    if (monsterlist.size()!=0){
                     /*   Iterator<LiveMonster> i = monsterlist.iterator();
                        while (i.hasNext()) {
                            if (i.next().getDot().getX()==1) {
                                i.remove(); //

                                //list.remove(77);
                            }
                        }*/monsterlist.removeLast();
                    }
                            break;

                }

                default:
                    return false;
            }
            return true;
        }
    }

    private final Random rand = new Random();
   static volatile LinkedList<LiveMonster> monsterlist= new LinkedList<LiveMonster>();

    /** The application model */
    private final Dots dotModel = new Dots();
    private final Dots dotModel1 = new Dots();

    /** The application view */
    private DotView dotView;

    /** The dot generator */
    private Timer dotGenerator;
    CountDownTimer cdt;
    /** Called when the activity is first created. */
    @Override public void onCreate(final Bundle state) {
        super.onCreate(state);

        // install the view
        setContentView(R.layout.main);

        // find the dots view
        dotView = (DotView) findViewById(R.id.dots);
        dotView.setDots(dotModel);
        dotView.setDots(dotModel1);

        dotView.setOnCreateContextMenuListener(this);
        dotView.setOnTouchListener(new TrackingTouchListener(dotModel));
        dotView.setOnTouchListener(new TrackingTouchListener(dotModel1));


        // wire up the controller
        findViewById(R.id.button1).setOnClickListener((final View v) -> {
            final TextView tb3 = (TextView) findViewById(R.id.text3);
            final TextView buttonName = (TextView) findViewById(R.id.button1);
            isstart(true);
            findViewById(R.id.button1).setEnabled(false);
            cdt = new CountDownTimer(61000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tb3.setText(Integer.toString((int) ((millisUntilFinished / 1000)-1)));
                }

                @Override
                public void onFinish() {
                    isstart(false);
                    findViewById(R.id.button1).setEnabled(true);
                    buttonName.setText("Restart");
                }
            }.start();
        });

     }

    @Override
    public void onResume() {
        super.onResume();
        Dot dotArr[][] = new Dot[7][5];
        int x, y;
        for(int i=0; i< dotArr.length;i++) {
            x = i;
            for (int j = 0; j < dotArr[0].length; j++) {
                y = j;
                dotArr[i][j] = new Dot(x, y);
                dotModel.addDot(x, y);
            }
        }
//set neighbours of one monter
        DotWorld world= new DotWorld(dotArr);
        MoveTask mtask = new MoveTask();
        mtask.execute(dotArr);
        }

    @Override public void onPause() {
        super.onPause();
        if (dotGenerator != null) {
            dotGenerator.cancel();
            dotGenerator = null;
        }
    }

    boolean start = false;
    public void isstart(boolean s){
        if (s)
            start = true;
        else
            start = false;
    }

    class  MoveTask extends AsyncTask<Dot[][], Dots, Dot> {
        /**
         * @param dots the monsters we're drawing
         *
         */
        @Override
        protected void onProgressUpdate(Dots... dots) {

            dotView.invalidate();

        }
       @Override
        protected void onPreExecute() {

        }

        /**
         * This method makes the monsters to  move around among adjacent squares at random
         * @param dotarr
         * @return Dot
         */
        @Override
         protected Dot doInBackground(Dot[][]... dotarr) {

            LinkedList<LiveMonster> newmonsterlist= new LinkedList<LiveMonster>();
            //initial k=i*j monsters
            int p =0;
            for(int i=0; i< 5;i++) {
                for (int j = 0; j < 4; j++) {
                    LiveMonster m = new LiveMonster(dotarr[0][i][j],p++,Color.GREEN);
                    monsterlist.add(m);
                }}
            for(LiveMonster nm:monsterlist){
                System.out.println("monsters:" + nm.getDot().getX()+"-" + nm.getDot().getY()+"-" +nm.getPosition()+"-" +nm.getColor());
            }



            while (!this.isCancelled()) {
                if (start) {

               for(LiveMonster nm:monsterlist){
                   newmonsterlist.add(nm);
               }

               int n= newmonsterlist.size();
               if(n == 0) break;

                for(LiveMonster lm:newmonsterlist){
                    try {
                        lm.getDot().randomNeighbor().enter(lm);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                dotModel1.addDot(lm.getDot().getX(), lm.getDot().getY());

                try {
                    if(n>0 && n<10)
                        Thread.sleep(200);
                    else
                    Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                }

                newmonsterlist.clear();
                publishProgress(dotModel1);
            }}
                return null;

            }

        }
}