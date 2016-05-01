package com.oreilly.demo.android.pa.uidemo.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/** A dot: the coordinates, color and size. */
public final class Dot {
    private final int x, y;


    /**
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     */
    public Dot(final int x, final int y) {
        this.x = x;
        this.y = y;
       // sema = new Semaphore(capacity);

    }

    /** @return the horizontal coordinate. */
    public int getX() { return x; }

    /** @return the vertical coordinate. */
    public int getY() { return y; }

///////////////////////////////////////////////////////////////////////////////////////////////////////
    boolean DEBUG = false;

    private static Random random = new Random(System.currentTimeMillis());

    /**
     * The neighboring cells of this cell.
     */
    protected ArrayList<Dot> neighbors = new ArrayList<Dot>();

    /**
     * The current occupants of this cell.
     */
    protected ArrayList<Monster> occupants = new ArrayList<Monster>();

    /**
     * A semaphore to control entry into this limited-capacity cell.
     */
    protected Semaphore sema = new Semaphore(10000);

    /**
     * Constructs a cell with the given capacity.
     */
   // public Dot(int capacity) {
        //sema
   // }

    /**
     * This method adds a neighbor to this cell in a thread-safe manner.
     */
    public synchronized void addNeighbor(Dot dot) { neighbors.add(dot); }

    /**
     * This method adds an occupant to this cell in a thread-safe manner.
     */
    protected synchronized void addOccupant(Monster monster) { occupants.add(monster); }

    /**
     * This method removes an occupant from this cell in a thread-safe manner.
     */
    protected synchronized void removeOccupant(Monster monster) { occupants.remove(monster); }

    /**
     * This method waits for space to open in this cell, if necessary, and then
     * places the monster inside the cell.
     */
    public void enter(Monster monster) throws InterruptedException {
        Dot previous = monster.getDot();
        if (this != previous) {
            if (DEBUG) System.out.println(monster + " waiting for space in " + this);
            // if necessary, wait for space in this cell
            sema.acquire();
            // if the monster was somewhere else before, take them out of there
            if (previous != null) {
                previous.leave(monster);
            }
            if (DEBUG) System.out.println(monster + " entering " + this);
            // put the monster into this cell
            monster.setDot(this);
            addOccupant(monster);
            // fire event to tell all occupants of this cell about the new arrival
            enterDot(new MonsterEvent(this, monster));
           // getView().update();
        } else {
            if (DEBUG) System.out.println(monster + " staying in " + this);
        }
    }

    public void leave(Monster monster) {
        if (DEBUG) System.out.println(monster + " leaving " + this);
        removeOccupant(monster);
        // fire event to tell all occupants of this cell about the departure
        leaveDot(new MonsterEvent(this, monster));
        // release the space that was occupied by the monster who just left
        sema.release();
       // getView().update();
    }

    /**
     * This method fires an <code>MonsterEvent.enterCell</code> event
     * to all occupants of this cell.
     */
    @SuppressWarnings("unchecked")
    public void enterDot(final MonsterEvent event) {
        List<Monster> currentOccupants;
        synchronized (this) {
            currentOccupants = (List<Monster>) this.occupants.clone();
        }
        for (Monster a : currentOccupants) {
            a.enterDot(event);
        }
    }

    /**
     * This method fires an <code>MonsterEvent.leaveCell</code> event
     * to all occupants of this cell.
     */
    @SuppressWarnings("unchecked")
    public void leaveDot(final MonsterEvent event) {
        List<Monster> currentOccupants;
        synchronized (this) {
            currentOccupants = (List<Monster>) this.occupants.clone();
        }
        for (Monster a : currentOccupants) {
            //a.enterCell(event);
            a.leaveDot(event);
        }
    }

    /**
     * This method returns a clone of this cell's neighbors.
     */
    @SuppressWarnings("unchecked")
    public synchronized List<Dot> getNeighbors() { return (List<Dot>) neighbors.clone(); }

    /**
     * This method returns a clone of this cell's occupants.
     */
    @SuppressWarnings("unchecked")
    public synchronized List<Monster> getOccupants() { return (List<Monster>) occupants.clone(); }

    public synchronized Dot randomNeighbor() {
        int size = neighbors.size();
       // System.out.println("Size=:"+size);
        return size == 0 ? null : (Dot) neighbors.get(random.nextInt(size));
    }
}