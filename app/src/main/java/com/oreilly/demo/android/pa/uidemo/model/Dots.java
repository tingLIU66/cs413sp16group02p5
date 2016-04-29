package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/** A list of dots. */
public class Dots {
    /** DotChangeListener. */
    public interface DotsChangeListener {
        /** @param dots the dots that changed. */
        void onDotsChange(Dots dots);
    }

    private final LinkedList<Dot> dots = new LinkedList<>();
    private final List<Dot> safeDots = Collections.unmodifiableList(dots);

    private DotsChangeListener dotsChangeListener;

    /** @param l set the change listener. */
    public void setDotsChangeListener(final DotsChangeListener l) {
        dotsChangeListener = l;
    }

    /** @return the most recently added dot. */
    public Dot getLastDot() {
        return (dots.size() <= 0) ? null : dots.getLast();
    }

    /** @return immutable list of dots. */
    public List<Dot> getDots() { return safeDots; }

    /**
     * @param x dot horizontal coordinate.
     * @param y dot vertical coordinate.
      */
    public void addDot(final int x, final int y) {
        dots.add(new Dot(x, y));
        notifyListener();
    }

    /** Remove all dots. */
    public void clearLastDots() {
      //  dots.clear();
        dots.removeLast();
        notifyListener();
    }

    public void clearDots() {
          dots.clear();
//        dots.removeLast();
        notifyListener();
    }

    private void notifyListener() {
        if (null != dotsChangeListener) {
            dotsChangeListener.onDotsChange(this);
        }
    }
}
