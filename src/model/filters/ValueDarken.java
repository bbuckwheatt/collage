package model.filters;

import model.IPixel;

/**
 * This class is a subclass of DarkenFilter. It darkens the image based on the
 * value of the pixel.
 */
public class ValueDarken extends DarkenFilter {

  /**
   * Sets the name to ValueDarken.
   */

  public ValueDarken() {
    this.name = "ValueDarken";
  }

  /**
   * Applies the ValueDarken filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    int maxVal;
    if (p.getR() > p.getG()) {
      maxVal = p.getR();
    } else {
      maxVal = p.getG();
    }
    if (maxVal < p.getB()) {
      maxVal = p.getB();
    }
    p.setR(lowerBoundsCheck(maxVal, p.getR()));
    p.setG(lowerBoundsCheck(maxVal, p.getG()));
    p.setB(lowerBoundsCheck(maxVal, p.getB()));
    p.convertRGBtoHSL();
  }
}
