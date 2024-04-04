package model.filters;

import model.IPixel;

/**
 * This class is a subclass of DarkenFilter. It darkens the image based on the
 * luma value of the pixel.
 */
public class LumaDarken extends DarkenFilter {

  /**
   * Sets the filter name to Luma Darken.
   */
  public LumaDarken() {
    this.name = "LumaDarken";
  }

  /**
   * Applies LumaDarken filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    int luma = (int) (.2126 * p.getR() + .7152 * p.getG() + .722 * p.getB());
    p.setR(lowerBoundsCheck(luma, p.getR()));
    p.setG(lowerBoundsCheck(luma, p.getG()));
    p.setB(lowerBoundsCheck(luma, p.getB()));
    p.convertRGBtoHSL();
  }
}
