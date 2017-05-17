package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.Point;
import model.World;

public class DpPathFinder {
  private static World world;
  private static Memo[][] memos;
  private static Queue<Point> checkingQueue;

  private static class Memo {
    private List<Point> directions;
    private int value;

    public Memo() {
      directions = new ArrayList<Point>();
      value = Integer.MAX_VALUE;
    }

    public int getValue() {
      return value;
    }

    public boolean addNewNote(Point dir, int value) {
      if (this.value >= value) {
        if (this.value > value) {
          directions.clear();
          this.value = value;
        }
        directions.add(dir);
        return true;
      } else {
        return false;
      }
    }
  }

  public static void findPath(World world, Point target, Point start) {
    initialize(world);

    checkingQueue.add(target);
    memos[target.row][target.collumn].addNewNote(target, 0);

    boolean isStartFound = isPointEqual(target, start);
    while (!checkingQueue.isEmpty() && !isStartFound) {
      Point checking = checkingQueue.remove();
      isStartFound = isPointEqual(checking, start);
      if (!isStartFound) {
        evaluate(checking);
      }
    }
    if (isStartFound) {
      System.out.println("found solution");
      constructSolution(target, start);
    } else {
      System.out.println("no path found");
    }
  }

  private static void constructSolution(Point target, Point start) {
    Point traverse = start;
    world.setTile(traverse.row, traverse.collumn, '^');
    while (!isPointEqual(traverse, target)) {
      traverse = memos[traverse.row][traverse.collumn].directions.get(0);
      world.setTile(traverse.row, traverse.collumn, '^');
    }
  }

  private static boolean isPointEqual(Point p1, Point p2) {
    return p1.row == p2.row && p1.collumn == p2.collumn;
  }

  private static void evaluate(Point checking) {
    Point up = new Point(checking.row, checking.collumn + 1);
    evaluateDirection(checking, up);
    Point down = new Point(checking.row, checking.collumn - 1);
    evaluateDirection(checking, down);
    Point left = new Point(checking.row - 1, checking.collumn);
    evaluateDirection(checking, left);
    Point right = new Point(checking.row + 1, checking.collumn);
    evaluateDirection(checking, right);
  }

  private static void evaluateDirection(Point checking, Point direction) {
    if (world.getTile(direction.row, direction.collumn) != '*') {
      if (isMemoUpdatedAfterAddition(checking, direction)) {
        checkingQueue.add(direction);
      }
    }
  }

  private static boolean isMemoUpdatedAfterAddition(Point checking, Point direction) {
    return memos[direction.row][direction.collumn].addNewNote(checking,
        memos[checking.row][checking.collumn].getValue() + 1);
  }

  private static void initialize(World world) {
    DpPathFinder.world = world;
    memos = new Memo[world.getHeight()][world.getWidth()];
    for (int row = 0; row < world.getHeight(); row++) {
      for (int collumn = 0; collumn < world.getWidth(); collumn++) {
        memos[row][collumn] = new Memo();
      }
    }
    checkingQueue = new LinkedList<Point>();
  }

}
