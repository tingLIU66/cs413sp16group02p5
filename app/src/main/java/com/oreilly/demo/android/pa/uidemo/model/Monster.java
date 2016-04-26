package com.oreilly.demo.android.pa.uidemo.model;

/**
 * This interface represents an monster in a world of cells.
 *
 * @see Cell
 * @see World
 */
public interface Monster extends DotListener {

  /**
   * This method sets the cell that this monster currently occupies.
   * This method is meant to be called by the <code>leave</code> method
   * in the <code>Cell</code> class.
   */
  void setDot(Dot dot);

  /**
   * This method returns the cell that this monster currently occupies.
   */
  Dot getDot();

  /**
   * This method brings this monster to life.
   */
  void start();

  /**
   * This method kills this monster.
   */
  void kill();
}