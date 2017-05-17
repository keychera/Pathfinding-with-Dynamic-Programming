package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.World;

public class MainFrame extends JFrame{
  WorldPanel worldpanel;
  
  public MainFrame(World world) {
    setTitle("World");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLayout(new BorderLayout());
    worldpanel = new WorldPanel(world);
    add(worldpanel,BorderLayout.CENTER);
    pack();
    setVisible(true);
  }
  
  public void repaint() {
    worldpanel.repaint();
  }
}

