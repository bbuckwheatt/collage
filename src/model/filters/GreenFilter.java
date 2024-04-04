package model.filters;

import model.IPixel;

/**
 * This class is a filter that makes the image green.
 * It sets the red and blue values to 0.
 */
public class GreenFilter extends Filter {

  /**
   * sets the filter name to GreenFilter.
   */

  public GreenFilter() {
    this.name = "GreenFilter";
  }

  /**
   * Applies green filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */
  @Override
  public void apply(IPixel p, IPixel p2) {
    p.setR(0);
    p.setB(0);
    p.convertRGBtoHSL();
  }
}
