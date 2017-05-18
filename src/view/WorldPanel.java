package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.Point;
import model.World;

public class WorldPanel extends JPanel {
  public final static int TILESIZE = 30;
  private final int width;
  private final int height;
  private World world;
  private World solution;
  private Point start;
  private Point target;
  private boolean isShowingSolution;

  public WorldPanel(World world, World solution, Point start, Point target) {
    this.setWorld(world);
    this.setSolution(solution);
    this.setStart(start);
    this.setTarget(target);
    isShowingSolution = false;
    width = world.getWidth() * TILESIZE;
    height = world.getHeight() * TILESIZE;
    setPreferredSize(new Dimension(width, height));
    setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int row = 0; row < getWorld().getHeight(); row++) {
      for (int collumn = 0; collumn < getWorld().getWidth(); collumn++) {
        drawTile(g, row, collumn, getWorld());
      }
    }
  }

  private void drawTile(Graphics g, int row, int collumn, World world) {
    if (world.getTile(row, collumn) == '*') {
      g.setColor(Color.DARK_GRAY);
    } else {
      int cVal = 255 - (Integer.valueOf(Character.toString(world.getTile(row, collumn))) * 10);
      g.setColor(new Color(cVal, cVal, cVal));
    }
    g.fillRect(collumn * TILESIZE, row * TILESIZE, TILESIZE, TILESIZE);
    if (isShowingSolution) {
      if (getSolution() != null) {
        if (getSolution().getTile(row, collumn) == '^') {
          g.setColor(Color.BLUE);
          g.fillOval(collumn * TILESIZE + TILESIZE / 3, row * TILESIZE + TILESIZE / 3, +TILESIZE / 3,
              +TILESIZE / 3);
        }
      } else {
        
      }
    }
    if (getTarget().row != getStart().row || getTarget().collumn != getStart().collumn) {
      if (row == getStart().row && collumn == getStart().collumn) {
        g.setColor(Color.GREEN);
        g.fillRect(collumn * TILESIZE + TILESIZE / 9, row * TILESIZE + TILESIZE / 9, +TILESIZE / 6,
            +TILESIZE / 6);
      }
      if (row == getTarget().row && collumn == getTarget().collumn) {
        g.setColor(Color.RED);
        g.fillRect(collumn * TILESIZE + TILESIZE / 9, row * TILESIZE + TILESIZE / 9, +TILESIZE / 6,
            +TILESIZE / 6);
      }
    } else {
      if (row == getStart().row && collumn == getStart().collumn) {
        g.setColor(Color.GREEN);
        g.fillRect(collumn * TILESIZE + TILESIZE / 12, row * TILESIZE + TILESIZE / 9, +TILESIZE / 6,
            +TILESIZE / 6);
        g.setColor(Color.RED);
        g.fillRect(collumn * TILESIZE + TILESIZE * 3 / 12, row * TILESIZE + TILESIZE / 9,
            +TILESIZE / 6, +TILESIZE / 6);
      }
    }
  }

  public boolean isShowingSolution() {
    return isShowingSolution;
  }

  public void toggleSolution() {
    isShowingSolution = !isShowingSolution;
  }

  public Point getStart() {
    return start;
  }

  public void setStart(Point start) {
    this.start = start;
  }

  public Point getTarget() {
    return target;
  }

  public void setTarget(Point target) {
    this.target = target;
  }

  public World getSolution() {
    return solution;
  }

  public void setSolution(World solution) {
    this.solution = solution;
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World world) {
    this.world = world;
  }
}


