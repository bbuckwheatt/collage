package model.filters;

import model.IPixel;

/**
 * This class is a subclass of BrightenFilter. It brightens the image based on the
 * luma value of the pixel.
 */
public class LumaBrighten extends BrightenFilter {

  /**
   * sets the filter name to LumaBrighten.
   */

  public LumaBrighten() {
    this.name = "LumaBrighten";
  }

  /**
   * Applies LumaBrighten filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    int luma = (int) (.2126 * p.getR() + .7152 * p.getG() + .722 * p.getB());
    p.setR(upperBoundsCheck(luma, p.getR()));
    p.setB(upperBoundsCheck(luma, p.getG()));
    p.setB(upperBoundsCheck(luma, p.getB()));
    p.convertRGBtoHSL();
  }
}
