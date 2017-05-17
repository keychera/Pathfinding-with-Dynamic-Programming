package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.World;

public class WorldPanel extends JPanel {
  private final static int TILESIZE = 30;
  private final int width;
  private final int height;
  private World world;

  public WorldPanel(World world) {
    this.world = world;
    width = world.getWidth() * TILESIZE;
    height = world.getHeight() * TILESIZE;
    setPreferredSize(new Dimension(width, height));
    setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int row = 0; row < world.getHeight(); row++) {
      for (int collumn = 0; collumn < world.getWidth(); collumn++) {
        drawTile(g, row, collumn, world);
      }
    }
  }

  private void drawTile(Graphics g, int row, int collumn, World world) {
    if (world.getTile(row, collumn) == '^') {
      g.setColor(Color.RED);
      g.fillOval(collumn * TILESIZE + TILESIZE/3, row * TILESIZE+ TILESIZE/3, + TILESIZE/3, + TILESIZE/3);
    } else {
      if (world.getTile(row, collumn) == '*') {
        g.setColor(Color.DARK_GRAY);
      } else {
        g.setColor(Color.WHITE);
      }
      g.fillRect(collumn * TILESIZE, row * TILESIZE, TILESIZE, TILESIZE);
    }
  }
}

