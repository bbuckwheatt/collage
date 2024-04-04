package model.filters;

import model.IPixel;

/**
 * This class is a filter that darkens the image.
 * It decreases the red, green, and blue values by the average of the three.
 */
public class IntensityDarken extends DarkenFilter {

  /**
   * Sets the filter name to IntensityDarken.
   */
  public IntensityDarken() {
    this.name = "IntensityDarken";
  }

  /**
   * Applies Intensity Darken filter.
   *
   * @param p  pixel 1.
   * @param p2 pixel 2.
   */
  @Override
  public void apply(IPixel p, IPixel p2) {
    int avgVal = (p.getR() + p.getG() + p.getB()) / 3;
    p.setR(lowerBoundsCheck(avgVal, p.getR()));
    p.setG(lowerBoundsCheck(avgVal, p.getG()));
    p.setB(lowerBoundsCheck(avgVal, p.getB()));
    p.convertRGBtoHSL();
  }
}
