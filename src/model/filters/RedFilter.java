package model.filters;

import model.IPixel;

/**
 * This is a filter that makes the image red.
 * It sets the green and blue values of each pixel to 0.
 */
public class RedFilter extends Filter {

  /**
   * Sets the filter name to red.
   */

  public RedFilter() {
    this.name = "RedFilter";
  }

  /**
   * Applies Red filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    p.setG(0);
    p.setB(0);
    p.convertRGBtoHSL();
  }

}
