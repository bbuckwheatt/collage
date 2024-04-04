package model.filters;

import model.IPixel;

/**
 * This class is a filter that brightens the image.
 * It increases the red, green, and blue values by the average of the three.
 */
public class IntensityBrighten extends BrightenFilter {

  /**
   * Sets the filter name to IntensityBrighten.
   */
  public IntensityBrighten() {
    this.name = "IntensityBrighten";
  }

  /**
   * Applies the intensity brighten filter to a given pixel.
   *
   * @param p  first pixel.
   * @param p2 second pixel.
   */

  @Override
  public void apply(IPixel p, IPixel p2) {
    int avgVal = (p.getR() + p.getG() + p.getB()) / 3;
    p.setR(upperBoundsCheck(avgVal, p.getR()));
    p.setB(upperBoundsCheck(avgVal, p.getG()));
    p.setB(upperBoundsCheck(avgVal, p.getB()));
    p.convertRGBtoHSL();
  }
}
