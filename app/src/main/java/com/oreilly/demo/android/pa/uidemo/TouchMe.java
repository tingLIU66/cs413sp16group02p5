package com.oreilly.demo.android.pa.uidemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.os.AsyncTask;

import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.DotWorld;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.model.LiveMonster;
import com.oreilly.demo.android.pa.uidemo.view.DotView;


/** Android UI demo program */
public class TouchMe extends Activity {
    /** Dot diameter */
    public static final int DOT_DIAMETER = 80;

    /** Listen for taps. */
    private static final class TrackingTouchListener implements View.OnTouchListener {
        private final Dots mDots;
       // private final Dot mDot;
        private List<Integer> tracks = new ArrayList<>();

        TrackingTouchListener(final Dots dots) { mDots = dots; }
       // TrackingTouchListener(final Dots dots) { mDot = dots; }

        @Override public boolean onTouch(final View v, final MotionEvent evt) {
            final int action = evt.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    final int idx1 = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.add(evt.getPointerId(idx1));
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    final int idx2 = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.remove(evt.getPointerId(idx2));
                    break;

                case MotionEvent.ACTION_MOVE:
                    final int n = evt.getHistorySize();
                    for (Integer i: tracks) {
                        final int idx = evt.findPointerIndex(i);
                        for (int j = 0; j < n; j++) {
                           // addDot(
                               // mDots,
                               // evt.getHistoricalX(idx, j),
                               // evt.getHistoricalY(idx, j),
                               // evt.getHistoricalPressure(idx, j),
                               // evt.getHistoricalSize(idx, j));
                        }
                    }
                    break;


                default:
                    return false;
            }

            for (final Integer i: tracks) {
                final int idx = evt.findPointerIndex(i);
              //  addDot(
                   // mDots,
                   // evt.getX(idx),
                   // evt.getY(idx),
                   // evt.getPressure(idx),
                   // evt.getSize(idx));
            }

            return true;
        }

        private void addDot(
                final Dots dots,
                final int x,
                final int y,
                final int p,
                final int s) {
            dots.addDot(x, y);
        }
    }

    private final Random rand = new Random();

    /** The application model */
    private final Dots dotModel = new Dots();

    /** The application view */
    private DotView dotView;

    /** The dot generator */
    private Timer dotGenerator;

    int dotcount =0 ;



    /** Called when the activity is first created. */
    @Override public void onCreate(final Bundle state) {
        super.onCreate(state);

        // install the view
        setContentView(R.layout.main);

        // find the dots view
        dotView = (DotView) findViewById(R.id.dots);
        dotView.setDots(dotModel);


        dotView.setOnCreateContextMenuListener(this);
        dotView.setOnTouchListener(new TrackingTouchListener(dotModel));

        dotView.setOnKeyListener((final View v, final int keyCode, final KeyEvent event) -> {
            if (KeyEvent.ACTION_DOWN != event.getAction()) {
                return false;
            }

            int color;
            switch (keyCode) {
                case KeyEvent.KEYCODE_SPACE:
                    color = Color.MAGENTA;
                    break;
                case KeyEvent.KEYCODE_ENTER:
                    color = Color.BLUE;
                    break;
                default:
                    return false;
            }

           // makeDot(dotModel, dotView, color);

            return true;
        });

        // wire up the controller
       // findViewById(R.id.button1).setOnClickListener((final View v) ->
           // makeDot(dotModel, dotView, Color.RED)
      //  );
       // findViewById(R.id.button2).setOnClickListener((final View v) ->
           // makeDot(dotModel, dotView, Color.GREEN)
       // );
/*
        final EditText tb1 = (EditText) findViewById(R.id.text1);
        final EditText tb2 = (EditText) findViewById(R.id.text2);
        dotModel.setDotsChangeListener((final Dots dots) -> {
            final Dot d = dots.getLastDot();
            tb1.setText((null == d) ? "" : String.valueOf(d.getX()));
            tb2.setText((null == d) ? "" : String.valueOf(d.getY()));
 */           dotView.invalidate();
       // });
    }

    @Override public void onResume() {
        super.onResume();

        Dot dotArr[][] = new Dot[7][5];
        int x, y;
        for(int i=0; i< dotArr.length;i++) {
            x = i;
            for (int j = 0; j < dotArr[0].length; j++) {
                y = j;
                dotArr[i][j] = new Dot(x, y);
            }
        }

            for(int i=0; i< dotArr.length;i++) {
                 for (int j = 0; j < dotArr[0].length; j++) {
                     System.out.println("dotArr[" + i + "][" + j+"]" + dotArr[i][j].getX() +dotArr[i][j].getY() );
                }
            }

          DotWorld world= new DotWorld(dotArr);

             List<Dot> neihbour = new ArrayList<Dot>();
                neihbour = dotArr[3][4].getNeighbors();
        System.out.println("the 1st neihbour is:" + neihbour.get(0).getX()+neihbour.get(0).getY());
        System.out.println("the 2 neihbour is:" + neihbour.get(1).getX()+neihbour.get(1).getY());
        System.out.println("the 3 neihbour is:" + neihbour.get(2).getX()+neihbour.get(2).getY());

       // for(int i=0; i< 1;i++) {
            //for (int j = 0; j < 2; j++) {

                new MoveTask().execute(dotArr[2][3]);
            //}
                new MoveTask().execute(dotArr[5][0]);
        //}


/*
        // create cells according to map
        Cell[][] cells = new Cell[7][5];
        for (int i = 0; i < 7; i ++) {
            cells[i] = new Cell[5];
            for (int j = 0; j < 5; j ++) {
                cells[i][j] = new Open() {
                };
            }
        }


        ArrayList<LiveMonster> monsters = new ArrayList<LiveMonster>();
        DotWorld world = new DotWorld(cells);
        for (int i=0; i<2; i++) {
            monsters.add(new LiveMonster());
            world.addMonster(monsters.get(i), i, i);
            //displayMonster(monsters.get(i), i, i);
        }

        for (LiveMonster m : monsters) {
            m.start();
        }

        /*
        if (dotGenerator == null) {
            dotGenerator = new Timer();
            // generate new dots, one every two seconds
            dotGenerator.schedule(new TimerTask() {
                @Override
                public void run() {
                    // must invoke makeDot on the UI thread to avoid
                    // ConcurrentModificationException on list of dots
                    runOnUiThread(() -> makeDot(dotModel, dotView, Color.GREEN));
                    //dotcount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(() ->dotModel.clearDots());
                     // runOnUiThread(() -> makeDot(dotModel, dotView, Color.GREEN));
                   // if(dotcount==1)
                       // dotGenerator.cancel();
                }
            }, /*initial delay*/ //0, /*periodic delay*/ 2000);
       // }


    }

    @Override public void onPause() {
        super.onPause();
        if (dotGenerator != null) {
            dotGenerator.cancel();
            dotGenerator = null;
        }
    }

    /** Install an options menu. */
    @Override public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    /** Respond to an options menu selection. */
    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
               // dotModel.clearDots();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Install a context menu. */
    @Override public void onCreateContextMenu(
            final ContextMenu menu,
            final View v,
            final ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 1, Menu.NONE, "Clear").setAlphabeticShortcut('x');
    }

    /** Respond to a context menu selection. */
    @Override public boolean onContextItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 1:
              //  dotModel.clearDots();
                return true;
            default:
                return false;
        }
    }

    /**
     * @param dots the dots we're drawing
     * @param view the view in which we're drawing dots
     */
    void makeDot(final Dots dots, final DotView view) {
        System.out.println("enter makedot");
       // dotView.invalidate();
        if(dots.getLastDot()!=null)
            dots.clearDots();
         //dots.addDot();
       }

    //}

    void makeDot2(final Dots dots, final DotView view) {
        int x, y;
      //  for(int i=0; i< 7;i++) {
          //  x = i;
            // for(int j=0; j<6;j++) {
            // y = j;
        if(dots.getLastDot()!=null)
        dots.clearDots();
            dots.addDot(rand.nextInt(5), 3);
       // }


   }



    class  MoveTask extends AsyncTask<Dot, Dot, Dot>{


        @Override
        protected void onProgressUpdate(Dot...dot) {
           //makeDot(dots[0], dotView);
            if(dotModel.getLastDot()!=null)
                dotModel.clearDots();
            System.out.println("last dot:" + dot[0].getX() + "," + dot[0].getY());
            dotModel.addDot(dot[0].getX(),dot[0].getY());
            dotView.invalidate();
        };
        protected void onPostExecute(String result) {

        }
        @Override
        protected Dot doInBackground(Dot... dot) {
            LiveMonster lm= new LiveMonster(dot[0]);
          while (!this.isCancelled()) {
                try {
                    lm.getDot().randomNeighbor().enter(lm);
                    // lm.getDot().enter(lm);
                } catch (InterruptedException e) {
                    e.printStackTrace();
               }

            System.out.println("now we get a new dot:"  + lm.getDot().randomNeighbor().getX() + "," + lm.getDot().randomNeighbor().getY());
            System.out.println("dot:" + lm.getDot().getX() + "," + lm.getDot().getY());
            Dot ndot = lm.getDot();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            publishProgress(ndot);

               }
                return null;
            }

        }







}