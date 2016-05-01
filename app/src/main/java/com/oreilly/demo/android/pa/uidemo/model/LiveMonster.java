package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * This abstract class represents a monster with autonomous behavior.
 * Concrete subclasses should implement the <code>run()</code> method.
 */
public class LiveMonster  implements Monster {

  boolean DEBUG = false;

//////////////////////////////////////////////////////////////


  /**
   * The cell this monster is currently occupying.
   */
  private Dot currentDot;
  private int position;
  private int color;


  /**
   * The live thread of this monster.  This thread runs the <code>run()</code>
   * method implemented by concrete subclasses of this class.
   */

  /**
   * Monsters
   */
  private ExecutorService workThread;

  /**
   * The destination of the most recent attempt to move.
   */
  private Future task = null;

  public LiveMonster(Dot dot, int position, int color){
    currentDot = dot;
    this.position = position;
    this.color = color;
  }

  /**
   * This method indicates whether this monster is still alive.
   */

  public synchronized void setDot(Dot dot) { currentDot = dot; }
  public synchronized void setPosition(int position) { this.position = position; }
  public synchronized void setColor(int color) { this.color = color; }

  public synchronized Dot getDot() { return currentDot; }
  public synchronized int getPosition() { return position; }
  public synchronized int getColor() { return color; }

///////////////////////////////////////////////////////////////
  private static Random random = new Random();

  public void start(){}

  public void kill(){}

  public void enterDot(MonsterEvent event) {
    if (event.getMonster() != this) {
      //System.out.println(this + ": hello " + event.getMonster());
    }
  }

  public void leaveDot(MonsterEvent event) {
    if (event.getMonster() != this) {
     // System.out.println(this + ": goodbye " + event.getMonster());
    }
  }
}

