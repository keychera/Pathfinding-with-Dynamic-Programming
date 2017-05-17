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

  public static void main(String[] args) {
    Path inputFilePath = Resource.getWorld("worldInput.txt");
    try {
      World world = new World(Files.readAllLines(inputFilePath));
      new WorldConsoleView(world);
      DpPathFinder.findPath(world, new Point(7,8), new Point(0, 0));
      new WorldConsoleView(world);
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {         
          JFrame frame = new MainFrame(world);
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
