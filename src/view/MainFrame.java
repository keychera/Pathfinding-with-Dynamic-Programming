package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.DpPathFinder;
import model.Point;
import model.World;

public class MainFrame extends JFrame implements ActionListener, MouseListener {
  private WorldPanel worldpanel;
  private boolean isChangingStart;
  private boolean isChangingTarget;
  private JLabel costLabel;
  private JButton changeStartTargetButton;

  public MainFrame(World world, World solution, int solutionCost, Point start, Point target) {
    setTitle("World");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new BorderLayout());

    isChangingStart = false;
    isChangingTarget = true;

    worldpanel = new WorldPanel(world, solution, start, target);
    worldpanel.addMouseListener(this);
    add(worldpanel, BorderLayout.CENTER);

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(4, 1));

    JButton toggleButton = new JButton();
    toggleButton.setText("toggle solution");
    toggleButton.addActionListener(this);


    changeStartTargetButton = new JButton();
    changeStartTargetButton.setActionCommand("s");
    changeStartTargetButton.setText("changing target...");
    changeStartTargetButton.addActionListener(this);

    leftPanel.add(toggleButton);
    leftPanel.add(changeStartTargetButton);

    costLabel = new JLabel();
    costLabel.setText("   solution cost " + Integer.toString(solutionCost));
    leftPanel.add(costLabel);
    
    leftPanel.setPreferredSize(new Dimension(150,worldpanel.getHeight()));

    add(leftPanel, BorderLayout.WEST);

    pack();
    setVisible(true);
  }

  public void repaint() {
    worldpanel.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "s") {
      isChangingStart = !isChangingStart;
      isChangingTarget = !isChangingTarget;
      if (isChangingStart) {
        changeStartTargetButton.setText("changing start...");
      } else {
        changeStartTargetButton.setText("changing target...");
      }
    } else {
      recalculate();
      worldpanel.toggleSolution();
      repaint();
    }
  }

  private void recalculate() {
    DpPathFinder.findPath(worldpanel.getWorld(), worldpanel.getTarget(), worldpanel.getStart());
    worldpanel.setSolution(DpPathFinder.constructSolution());
    costLabel.setText("   solution cost " + Integer.toString(DpPathFinder.getSolutionCost()));
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (isChangingStart || isChangingTarget) {
      int collumn = e.getX() / WorldPanel.TILESIZE;
      int row = e.getY() / WorldPanel.TILESIZE;
      if (worldpanel.getWorld().getTile(row, collumn) != '*') {
        if (isChangingStart) {
          worldpanel.setStart(new Point(row, collumn));
        }
        if (isChangingTarget) {

          worldpanel.setTarget(new Point(row, collumn));
        }
      }
    }
    recalculate();
    repaint();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub

  }
}

