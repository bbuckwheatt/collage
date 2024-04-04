package model.filters;

import model.IPixel;

/**
 * This class is a subclass of BrightenFilter. It brightens the image based on the
 * value of the pixel.
 */
public class ValueBrighten extends BrightenFilter {

  /**
   * Sets the name to valueBrighten.
   */

  public ValueBrighten() {
    this.name = "ValueBrighten";
  }

  /**
   * Applies ValueBrighten filter.
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
    p.setR(upperBoundsCheck(maxVal, p.getR()));
    p.setB(upperBoundsCheck(maxVal, p.getG()));
    p.setB(upperBoundsCheck(maxVal, p.getB()));
    p.convertRGBtoHSL();
  }
}

