package com.oreilly.demo.android.pa.uidemo.model;

import java.util.EventListener;

/**
 * A listener to cell events.
 * @see MonsterEvent
 * @see Monster
 */
  public interface MonsterListener extends EventListener {

  /**
   * This method indicates that an actor has entered a cell. 
   * May be called from any thread, usually not the Swing thread.
   */
  void enterDot(MonsterEvent event);

  /**
   * This method indicates that an actor has left a cell.
   * May be called from any thread, usually not the Swing thread.
   */

  void leaveDot(MonsterEvent event);
}
