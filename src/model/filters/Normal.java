package model.filters;

import model.IPixel;

/**
 * This class is a subclass of Filter. It represents no filter being applied to the
 * original image.
 */
public class Normal extends Filter {

  /**
   * sets name to normal.
   */

  public Normal() {
    this.name = "NormalFilter";
  }

  /**
   * Applies normal filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  public void apply(IPixel p, IPixel p2) {
    //does nothing, represents no filter being applied to the original image.
  }
}
