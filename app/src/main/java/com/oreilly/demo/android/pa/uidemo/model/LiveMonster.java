package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * This abstract class represents a monster with autonomous behavior.
 * Concrete subclasses should implement the <code>run()</code> method.
 */
public class LiveMonster  implements Monster, Runnable  {

  boolean DEBUG = false;

//////////////////////////////////////////////////////////////


  /**
   * The cell this monster is currently occupying.
   */
  private Dot currentDot;

  /**
   * The live thread of this monster.  This thread runs the <code>run()</code>
   * method implemented by concrete subclasses of this class.
   */
  private ExecutorService liveThread;

  /**
   * The work thread of this monster.  This thread handles requests, usually
   * coming from the live thread, to do some work, such as moving to another cell.
   * It is necessary to use a separate thread for this because an attempt to
   * enter another cell might block.  For example, without using a separate
   * work thread, this can cause deadlock
   * if two monsters are trying to move into each other's cell of capacity one.
   * A separate work thread also allows an monster to change their mind
   * if another task of higher priority should take precedence over
   * a task on which the work thread is currently working.
   */
  private ExecutorService workThread;

  /**
   * The destination of the most recent attempt to move.
   */
  private Future task = null;

  public LiveMonster(Dot dot){
    currentDot = dot;

  }

  /**
   * This method indicates whether this monster is still alive.
   */
  protected synchronized boolean isAlive() { return liveThread != null; }

  /**
   * This method brings this monster to life by starting its internal threads.
   */
  public synchronized void start() {
    System.out.println("Start");
    if (! isAlive()) {
      liveThread = Executors.newFixedThreadPool(1);
      workThread = Executors.newFixedThreadPool(1);
    }
    liveThread.execute(this);
  }

  /**
   * This method kills this monster by stopping its internal threads.
   */
  public synchronized void kill() {
    if (isAlive()) {
      liveThread.shutdown();
      workThread.shutdown();
      liveThread = null;
      workThread = null;
    }
  }

  public synchronized void setDot(Dot dot) { currentDot = dot; }

  public synchronized Dot getDot() { return currentDot; }

  /**
   * This method is used to schedule the runnable for execution by this
   * monster.  If the monster is still waiting for a previously scheduled
   * runnable to execute, then this invocation preempts the previous one.
   */
  protected synchronized void execute(Runnable runnable) {
    if (DEBUG) System.out.println(this + " wants to execute " + runnable);
    if (task != null && ! task.isDone()) {
      task.cancel(true);
    }
    task = workThread.submit(runnable);
  }
  
  /**
   * This method removes this dead monster from the cell it occupied.
   */
  protected synchronized void die() {
    Dot dot = getDot();
    setDot(null);
    dot.leave(this);
  }

///////////////////////////////////////////////////////////////
  private static Random random = new Random();

  public void run() {
    while (! Thread.interrupted()) {
      try {
        Thread.sleep(random.nextInt(1000));
        // schedule a move for execution
        execute(move);
      } catch (InterruptedException exc) {
        if (DEBUG) System.out.println(this + " live thread interrupted");
        Thread.currentThread().interrupt();
      }
    }
    System.out.println(this + ": squeak!");
  }

  /**
   * A runnable representing a move.
   */
  private Runnable move = new Runnable() {
    public void run() {
      try {
        getDot().randomNeighbor().enter(LiveMonster.this);
      } catch (InterruptedException e) {
        // if interrupted before entering the cell, then set interrupted flag
        // so that the worker thread can detect this
        Thread.currentThread().interrupt();
      }
    }
  };

  public void enterDot(DotEvent event) {
    if (event.getMonster() != this) {
      //System.out.println(this + ": hello " + event.getMonster());
    }
  }

  public void leaveDot(DotEvent event) {
    if (event.getMonster() != this) {
     // System.out.println(this + ": goodbye " + event.getMonster());
    }
  }
}

