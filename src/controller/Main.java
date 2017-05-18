package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Point;
import model.World;
import view.MainFrame;
import view.WorldConsoleView;

public class Main {
  private static MainFrame frame;

  public static void main(String[] args) {
    Path inputFilePath = Resource.getWorld("worldInput.txt");
    try {
      World world = new World(Files.readAllLines(inputFilePath));
//      new WorldConsoleView(world);
      Point target = new Point(2,1);
      Point start = new Point(0,0);
      DpPathFinder.findPath(world, target, start);
      World solution = DpPathFinder.constructSolution();
//      new WorldConsoleView(solution);
      int solutionCost = DpPathFinder.getSolutionCost();
      
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {         
          frame = new MainFrame(world,solution,solutionCost, start, target);
          frame.repaint();
        }
      });
      
    } catch (IOException e) {
      System.out.println("fail to open res");
    } catch (IndexOutOfBoundsException e) {
      System.out.println();
      System.out.println("target is not found on the map/the map is invalid");
    }
  }

}
