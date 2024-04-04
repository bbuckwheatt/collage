package model.filters;

import model.IPixel;

/**
 * Applies a filter that screens two pixels' L values.
 */
public class ScreenFilter extends Filter {

  /**
   * Sets the filter name to ScreenFilter.
   */

  public ScreenFilter() {
    this.name = "ScreenFilter";
  }

  /**
   * applies the screen filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    p.setL(1 - (1 - p.getL()) * (1 - p2.getL()));
    p.convertHSLtoRGB();
  }
}
