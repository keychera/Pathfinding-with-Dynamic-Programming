package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import model.Point;
import model.World;

public class DpPathFinder {
  private static World world;
  private static Memo[][] memos;
  private static Queue<WeightedPoint> checkingQueue;
  private static boolean isSolutionFound;
  private static Point target;
  private static Point start;

  private static class Memo {
    private List<WeightedPoint> directions;
    private int value;

    public Memo() {
      directions = new ArrayList<WeightedPoint>();
      value = Integer.MAX_VALUE;
    }

    public int getValue() {
      return value;
    }

    public boolean addNewNote(WeightedPoint dir) {
      if (this.value >= dir.weight) {
        if (this.value > dir.weight) {
          directions.clear();
          this.value = dir.weight;
        }
        directions.add(dir);
        return true;
      } else {
        return false;
      }
    }
  }

  private static class WeightedPoint extends Point {
    public int weight;

    public WeightedPoint(Point target) {
      this(target.row, target.collumn);
    }

    public WeightedPoint(int row, int collumn) {
      this(row, collumn, 0);
    }

    public WeightedPoint(int row, int collumn, int weight) {
      super(row, collumn);
      this.weight = weight;
    }
  }

  private static class WeightedPointComparator implements Comparator<WeightedPoint> {

    @Override
    public int compare(WeightedPoint o1, WeightedPoint o2) {
      return o1.weight - o2.weight;
    }
  }

  private static void initialize(World world, Point target, Point start) {
    isSolutionFound = false;
    DpPathFinder.target = target;
    DpPathFinder.start = start;
    DpPathFinder.world = world;
    memos = new Memo[world.getHeight()][world.getWidth()];
    for (int row = 0; row < world.getHeight(); row++) {
      for (int collumn = 0; collumn < world.getWidth(); collumn++) {
        memos[row][collumn] = new Memo();
      }
    }
    checkingQueue = new PriorityQueue<WeightedPoint>(10, new WeightedPointComparator());
  }

  public static void findPath(World world, Point target, Point start) {
    initialize(world, target, start);

    WeightedPoint weightedTarget = new WeightedPoint(target);
    checkingQueue.add(weightedTarget);
    memos[target.row][target.collumn].addNewNote(weightedTarget);

    isSolutionFound = isPointEqual(target, start);
    if (!isSolutionFound) {
      while (!checkingQueue.isEmpty()) {
        WeightedPoint checking = checkingQueue.remove();
        isSolutionFound = isPointEqual(checking, start);
        if (!isSolutionFound) {
          evaluate(checking);
        }
      }
    }
    if (!memos[start.row][start.collumn].directions.isEmpty()) {
      isSolutionFound = true;
    }
  }

  private static void printMemo() {
    for (int row = 0; row < world.getHeight(); row++) {
      for (int collumn = 0; collumn < world.getWidth(); collumn++) {
        System.out.print(row);
        System.out.print(",");
        System.out.print(collumn);
        System.out.println();
        System.out.print("\t");
        print(memos[row][collumn]);
        System.out.println();
      }
    }
  }

  private static void print(Memo memo) {
    for (int i = 0; i < memo.directions.size(); i++) {
      print(memo.directions.get(i));
      System.out.print(" , ");
    }
    System.out.println();
    System.out.print("value : ");
    System.out.println(memo.value);
  }

  private static void print(WeightedPoint checking) {
    System.out.print("{");
    System.out.print("(");
    System.out.print(checking.row);
    System.out.print(",");
    System.out.print(checking.collumn);
    System.out.print(")");
    System.out.print(" = ");
    System.out.print(checking.weight);
    System.out.print("} ");
  }

  private static void evaluate(WeightedPoint checking) {
    WeightedPoint left = new WeightedPoint(checking.row, checking.collumn - 1, checking.weight + 1);
    evaluateDirection(checking, left);
    WeightedPoint right =
        new WeightedPoint(checking.row, checking.collumn + 1, checking.weight + 1);
    evaluateDirection(checking, right);
    WeightedPoint up = new WeightedPoint(checking.row - 1, checking.collumn, checking.weight + 1);
    evaluateDirection(checking, up);
    WeightedPoint down = new WeightedPoint(checking.row + 1, checking.collumn, checking.weight + 1);
    evaluateDirection(checking, down);
  }

  private static void evaluateDirection(Point checking, WeightedPoint direction) {
    if (world.getTile(direction) != '*') {
      if (isMemoUpdatedAfterAddition(checking, direction)) {
        checkingQueue.add(direction);
      }
    }
  }

  private static boolean isMemoUpdatedAfterAddition(Point checking, WeightedPoint direction) {
    int weightDiff = Math.abs(heightOfTile(checking) - heightOfTile(direction));
    if (weightDiff != 0) {
      weightDiff -= 1;
    }
    WeightedPoint newNote = new WeightedPoint(checking.row, checking.collumn,
        memos[checking.row][checking.collumn].getValue() + 1 + weightDiff);
    return memos[direction.row][direction.collumn].addNewNote(newNote);
  }

  public static World constructSolution() {
    if (isSolutionFound) {
      World solution = new World(world);
      Point traverse = start;
      solution.setTile(traverse.row, traverse.collumn, '^');
      while (!isPointEqual(traverse, target)) {
        traverse = getMinimum(memos[traverse.row][traverse.collumn].directions);
        solution.setTile(traverse.row, traverse.collumn, '^');
      }
      return solution;
    } else {
      return null;
    }
  }

  public static int getSolutionCost() {
    if (isSolutionFound) {
      return memos[start.row][start.collumn].getValue();
    }
    return -1;
  }

  private static WeightedPoint getMinimum(List<WeightedPoint> directions) {
    WeightedPoint min = directions.get(0);
    for (WeightedPoint p : directions) {
      if (p.weight < min.weight) {
        min = p;
      }
    }
    return min;
  }

  private static boolean isPointEqual(Point p1, Point p2) {
    return p1.row == p2.row && p1.collumn == p2.collumn;
  }

  private static Integer heightOfTile(Point point) {
    return Integer.valueOf(Character.toString(world.getTile(point)));
  }
}
