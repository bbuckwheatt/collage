package model.filters;

import model.IPixel;

/**
 * Applies a filter that multiplies the l value of 2 pixels.
 */
public class MultiplyFilter extends Filter {

  /**
   * Sets the filter name to MultiplyFilter.
   */

  public MultiplyFilter() {
    this.name = "MultiplyFilter";
  }

  /**
   * applies the multiply filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    p.setL(p.getL() * p2.getL());
    p.convertHSLtoRGB();
  }
}
