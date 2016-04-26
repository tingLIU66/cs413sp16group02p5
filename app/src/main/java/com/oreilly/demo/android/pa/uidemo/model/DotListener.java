package com.oreilly.demo.android.pa.uidemo.model;

import java.util.EventListener;

/**
 * A listener to cell events.
 * @see DotEvent
 * @see Monster
 */
  public interface DotListener extends EventListener {

  /**
   * This method indicates that an actor has entered a cell. 
   * May be called from any thread, usually not the Swing thread.
   */
  void enterDot(DotEvent event);

  /**
   * This method indicates that an actor has left a cell.
   * May be called from any thread, usually not the Swing thread.
   */

  void leaveDot(DotEvent event);
}
