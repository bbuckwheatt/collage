package model.filters;

import model.IPixel;


/**
 * This class is a filter that makes the image blue.
 * It sets the red and green values to 0.
 */
public class BlueFilter extends Filter {

  /**
   * Sets the filter name to BlueFilter.
   */

  public BlueFilter() {
    this.name = "BlueFilter";
  }

  /**
   * Applies blue filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    p.setR(0);
    p.setG(0);
    p.convertRGBtoHSL();
  }
}