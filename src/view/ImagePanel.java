package view;

//taken from Professor Lucia Nunez, lab8 4/04/23


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 * Simple panel to display a single image.
 * Displays the image using a given BufferedImage
 * during its refresh.
 */
public class ImagePanel extends JPanel {
  private final JLabel imageLabel;
  private BufferedImage image;

  /**
   * creates a new imagepanel object and creates a JLabel.
   */

  public ImagePanel() {
    this.imageLabel = new JLabel();
    this.add(imageLabel);
  }

  /**
   * A built-in method that is overwritten for refreshing the image displayed.
   *
   * @param g2d the <code>Graphics</code> object to protect
   */

  @Override
  protected void paintComponent(Graphics g2d) {
    if (this.image != null) {
      this.imageLabel.setIcon(new ImageIcon(this.image));
    }
  }

  /**
   * Sets the image to be displayed to the given one.
   *
   * @param image image to be displayed on refresh
   */
  public void updateImage(BufferedImage image) {
    this.image = image;
  }
}
