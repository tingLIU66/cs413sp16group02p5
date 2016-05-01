package com.oreilly.demo.android.pa.uidemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a world consisting of a rectangular grid
 * of cells.
 */
public class DotWorld {

  private Dot[][] grid;

  /**
   * This constructor builds a <code>DotWorld</code> from a rectangular
   * grid of cells.  It automatically makes horizontally, vertically,
   * and diagonally adjacent cells neighbors of each other.
   */
  //public DotWorld(final Cell[][] grid) {
    public DotWorld(final Dot[][] grid) {

      System.out.println("enter onDraw");

      this.grid = grid;

      int rows = grid.length;
      int cols = grid[0].length;

      for (int i = 0; i < rows; i ++) {
        for (int j = 0; j < cols; j ++) {
          if (i > 0) {
            if (j > 0) grid[i][j].addNeighbor(grid[i-1][j-1]);
            grid[i][j].addNeighbor(grid[i-1][j]);
            if (j < cols - 1) grid[i][j].addNeighbor(grid[i-1][j+1]);
          }
          if (j > 0) grid[i][j].addNeighbor(grid[i][j-1]);
          if (j < cols - 1) grid[i][j].addNeighbor(grid[i][j+1]);
          if (i < rows - 1) {
            if (j > 0) grid[i][j].addNeighbor(grid[i+1][j-1]);
            grid[i][j].addNeighbor(grid[i+1][j]);
            if (j < cols - 1) grid[i][j].addNeighbor(grid[i+1][j+1]);
          }
        }
      }
    }

  /**
   * This method adds an monster to the cell at the given position.
   * It is the responsibility of the caller of this method to
   * make sure that there is space for the monster at the given
   * position.
   * @throws InternalError If the thread invoking this method
   * is interrupted
   */
  public void addMonster(Monster monster, int xpos, int ypos) {
    try {
      grid[ypos][xpos].enter(monster);
    } catch (InterruptedException e) {
      throw new InternalError();
    }
  }
}