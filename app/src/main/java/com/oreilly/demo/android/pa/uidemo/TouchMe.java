package com.oreilly.demo.android.pa.uidemo;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.os.AsyncTask;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.DotWorld;
import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;

/** Android UI demo program */
public class TouchMe extends Activity {

    /** Listen for taps. */
    private static final class TrackingTouchListener implements View.OnTouchListener {
       // private final Dots mDots;
        private final Monsters mMonsters;

        // TrackingTouchListener(final Dots dots) { mDots = dots; }
        TrackingTouchListener(final Monsters monsters) { mMonsters = monsters; }

            @Override public boolean onTouch(final View v, final MotionEvent evt) {
            final int action = evt.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                            evt.getX();
                            evt.getY();
                    if (monsterlist.size()!=0){
                     /*   Iterator<Monster> i = monsterlist.iterator();
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
   static volatile LinkedList<Monster> monsterlist= new LinkedList<Monster>();

    /** The application model */

    private final Monsters monsterModel = new Monsters();
    Dot dotArr[][] = new Dot[7][5];
    /** The application view */
    private MonsterView monsterView;

    /** The dot generator */
    private Timer dotGenerator;
    CountDownTimer cdt;
    /** Called when the activity is first created. */
    @Override public void onCreate(final Bundle state) {
        super.onCreate(state);

        // install the view
        setContentView(R.layout.main);

        // find the dots view
        monsterView = (MonsterView) findViewById(R.id.dots);
       // monsterView.setDots(dotModel);
        monsterView.setMonsters(monsterModel);

        monsterView.setOnCreateContextMenuListener(this);
       // monsterView.setOnTouchListener(new TrackingTouchListener(dotModel));
        monsterView.setOnTouchListener(new TrackingTouchListener(monsterModel));

        // wire up the controller
        findViewById(R.id.button1).setOnClickListener((final View v) -> {
            final TextView tb3 = (TextView) findViewById(R.id.text3);
            final TextView buttonName = (TextView) findViewById(R.id.button1);
            isstart(true);
            findViewById(R.id.button1).setEnabled(false);
            cdt = new CountDownTimer(61000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tb3.setText(Integer.toString((int) ((millisUntilFinished / 1000)-1))+"s");
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
        initial();
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
    private static Random random = new Random(System.currentTimeMillis());
    private boolean start = false;
    public void isstart(boolean s){
        if (s)
            start = true;
        else
            start = false;
    }

    public void initial(){
        int x, y;
        for(int i=0; i< dotArr.length;i++) {
            x = i;
            for (int j = 0; j < dotArr[0].length; j++) {
                y = j;
                dotArr[i][j] = new Dot(x, y);
               // dotModel.addDot(x, y);
            }
        }

    }

    class  MoveTask extends AsyncTask<Dot[][], Monsters, Dot> {
        /**
         * @param monsters the monsters we're drawing
         *
         */
        @Override
        protected void onProgressUpdate(Monsters... monsters) {

            monsterView.invalidate();

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

            LinkedList<Monster> newmonsterlist= new LinkedList<Monster>();
            //initial k=i*j monsters
            int p =0;
            for(int i=0; i< 5;i++) {
                for (int j = 0; j < 4; j++) {
                    int c= random.nextInt(2);
                    Monster m = new Monster(dotarr[0][i][j],p++,c==0?Color.GREEN:Color.YELLOW);
                    monsterlist.add(m);
                }}
            for(Monster nm:monsterlist){
                System.out.println("monsters:" + nm.getDot().getX()+"-" + nm.getDot().getY()+"-" +nm.getPosition()+"-" +nm.getColor());
            }



            while (!this.isCancelled()) {
                if (start) {

               for(Monster nm:monsterlist){
                   newmonsterlist.add(nm);
               }

               int n= newmonsterlist.size();
               if(n == 0) break;

                for(Monster lm:newmonsterlist){
                    int c= random.nextInt(2);
                    lm.setColor(c==0?Color.GREEN:Color.YELLOW);
                    System.out.println("color:"+c);
                    try {
                        lm.getDot().randomNeighbor().enter(lm);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                   monsterModel.addMonster(lm);
                //dotModel1.addDot(lm.getDot().getX(), lm.getDot().getY());

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
                publishProgress(monsterModel);
            }}
                return null;

            }

        }
}