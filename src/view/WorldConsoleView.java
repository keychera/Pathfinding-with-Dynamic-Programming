package view;

import model.World;

public class WorldConsoleView {

  public WorldConsoleView(World world) {
    for (int row = 0; row < world.getHeight(); row++) {
      for (int collumn = 0; collumn < world.getWidth(); collumn++) {
        System.out.print(world.getTile(row,collumn));
      }
      System.out.println();
    }
  }

}
