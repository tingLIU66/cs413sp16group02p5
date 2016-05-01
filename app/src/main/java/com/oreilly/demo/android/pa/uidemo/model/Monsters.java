package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by developer on 5/1/2016.
 */
public class Monsters {
    /** MonsterChangeListener. */
    public interface MonstersChangeListener {
        /** @param monsters the monsters that changed. */
        void onMonstersChange(Monsters monsters);
    }

    private final LinkedList<Monster> monsters = new LinkedList<>();
    private final List<Monster> safeMonsters = Collections.unmodifiableList(monsters);

    private MonstersChangeListener monstersChangeListener;

    /** @param l set the change listener. */
    public void setMonstersChangeListener(final MonstersChangeListener l) {
        monstersChangeListener = l;
    }

    /** @return the most recently added dot. */
    public Monster getLastMonster() {
        return (monsters.size() <= 0) ? null : monsters.getLast();
    }

    /** @return immutable list of monsters. */
    public List<Monster> getMonsters() { return safeMonsters; }

    /**
     * @param x dot horizontal coordinate.
     * @param y dot vertical coordinate.
     */
    public void addMonster(Monster monster) {
        monsters.add(monster);
        notifyListener();
    }

    /** Remove all monsters. */
    public void clearLastMonsters() {
        //  monsters.clear();
        monsters.removeLast();
        notifyListener();
    }

    public void clearMonsters() {
        monsters.clear();
//        monsters.removeLast();
        notifyListener();
    }

    private void notifyListener() {
        if (null != monstersChangeListener) {
            monstersChangeListener.onMonstersChange(this);
        }
    }
}
