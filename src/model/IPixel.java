package model;

/**
 * Represents the interface for a Pixel. Pixels have RGB and HSL values.
 */
public interface IPixel {

  /**
   * Returns the red value of a pixel.
   *
   * @return red value.
   */

  public int getR();

  /**
   * Returns the green value of a pixel.
   *
   * @return green value.
   */

  public int getG();

  /**
   * Returns the blue value of a pixel.
   *
   * @return blue value.
   */

  public int getB();

  /**
   * Returns the alpha value of a pixel.
   *
   * @return alpha value.
   */

  public int getA();

  /**
   * Returns the hue value of a pixel.
   *
   * @return hue value.
   */

  public double getH();

  /**
   * Returns the saturation value of a pixel.
   *
   * @return saturation value.
   */

  public double getS();

  /**
   * Returns the lightness value of a pixel.
   *
   * @return lightness value.
   */

  public double getL();

  /**
   * sets the red value of a pixel to the provided value.
   */

  public void setR(int val);

  /**
   * sets the green value of a pixel to the provided value.
   */

  public void setG(int val);

  /**
   * sets the blue value of a pixel to the provided value.
   */

  public void setB(int val);

  /**
   * sets the alpha value of a pixel to the provided value.
   */

  public void setA(int val);

  /**
   * sets the hue value of a pixel to the provided value.
   */

  public void setH(double val);

  /**
   * sets the saturation value of a pixel to the provided value.
   */

  public void setS(double val);

  /**
   * sets the lightness value of a pixel to the provided value.
   */

  public void setL(double val);

  /**
   * Uses a class delegate from RepresentationConverter to perform the necessary calculations to
   * convert a pixel's HSL values into the RGB format.
   */

  public void convertHSLtoRGB();

  /**
   * Uses a class delegate from RepresentationConverter to perform the necessary calculations to
   * convert a pixel's RGB values into the HSL format.
   */

  public void convertRGBtoHSL();

  /**
   * Turns the pixel into a string representation.
   *
   * @return a 4 part string with spaces between them.
   */
  public String toString();

  /**
   * Turns the pixel into a string representation.
   *
   * @return a 3 part string with spaces between them.
   */

  public String toStringRGB();

  /**
   * Returns the hsl values of a pixel.
   *
   * @return returns the string of the pixel.
   */

  public String toStringHSL();
}
