package model.filters;

import model.IPixel;

/**
 * Applies a filter that takes the difference of two r,g,b values and applies the result of that.
 */
public class DifferenceFilter extends Filter {

  /**
   * Sets the filter name to DifferenceFilter.
   */

  public DifferenceFilter() {
    this.name = "DifferenceFilter";
  }

  /**
   * Applies difference filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    p.setR(Math.abs(p.getR() - p2.getR()));
    p.setG(Math.abs(p.getG() - p2.getG()));
    p.setB(Math.abs(p.getB() - p2.getB()));
    p.convertRGBtoHSL();
  }

}
