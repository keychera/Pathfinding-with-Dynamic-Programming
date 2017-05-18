package model;

import java.util.List;

public class World {
  private char[][] tiles;
  private int width;
  private int height;

  public World(List<String> strings) {
    height = strings.size();
    tiles = new char[height][];
    int i = 0;
    for (String string : strings) {
      tiles[i] = string.toCharArray();
      i++;
    }
    width = strings.get(0).length();
  }
  
  public World(World world) {
    this.height = world.height;
    this.width = world.width;
    tiles = new char[height][width];
    for (int row = 0; row < height; row++) {
      for (int collumn = 0; collumn < width; collumn++) {
        tiles[row][collumn] = world.tiles[row][collumn];
      }
    }
  }

  public char getTile(Point point) {
    return getTile(point.row,point.collumn);
  }

  public char getTile(int row,int collumn) {
    if (row < height && row >= 0 && collumn < width && collumn >= 0) {
      return tiles[row][collumn];
    } else {
      return '*';
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setTile(int row, int collumn, char info) {
    tiles[row][collumn] = info;
  }
}
