package com.oreilly.demo.android.pa.uidemo.model;

import java.util.EventObject;

/**
 * This class represents events occuring in cells.  The cell in question
 * is usually the source of the event.
 * @see Cell
 * @see Monster
 * @see DotListener
 */
public class DotEvent extends EventObject {

  private static final long serialVersionUID = 6621950138682436187L;

  /**
   * The actor involved in this event.
   */
  private Monster monster;

  /**
   * This constructor builds a cell event involving the specified actor.
   */
  public DotEvent(Object source, Monster monster) {
    super(source);
    this.monster = monster;
  }

  /**
   * This method returns the actor involved in this event.
   */
  public Monster getMonster() { return monster; }

  public String toString() {
    return getClass().getName() + "[" + source + "," + monster + "]";
  }
}