package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import controller.DpPathFinder;
import model.Point;
import model.World;
import view.WorldView;

public class Main {

  public static void main(String[] args) {
    Path inputFilePath = Resource.getWorld("worldInput.txt");
    try {
      World world = new World(Files.readAllLines(inputFilePath));
      new WorldView(world);
      DpPathFinder.findPath(world,new Point(2,2),new Point(0,0));
      System.out.println();
      new WorldView(world);
    } catch (IOException e) {
      System.out.println("fail to open res");
    }
  }

}
