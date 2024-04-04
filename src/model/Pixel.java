package model;

/**
 * Class representing a pixel of an image.
 * Has an r (red), g (green), b (blue), and a (alpha) value.
 * **Added for A5: Added H, S, and L values to a pixel to represent the HSL format of pixel color.
 * A pixel's color is constantly stored in both HSL and RGB format.
 * Also added constructor checks to ensure that RGB and HSL values are positive at time of creation.
 */

public class Pixel implements IPixel {
  private int r;
  private int g;
  private int b;
  private int a;
  private double h;
  private double s;
  private double l;
  RepresentationConverter delegate;

  /**
   * Constructor to make a blank pixel.
   */

  public Pixel() {
    this.r = 0;
    this.g = 0;
    this.b = 0;
    this.a = 0;
    this.h = 0.0;
    this.s = 0.0;
    this.l = 0.0;
    this.delegate = new RepresentationConverter();
  }

  ;

  /**
   * Constructor for a pixel with alpha channel.
   *
   * @param r sets the red value.
   * @param g sets the green value.
   * @param b sets the blue value.
   * @param a sets the alpha value.
   */
  public Pixel(int r, int g, int b, int a) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0 || a < 0) {
      throw new IllegalArgumentException("R,G, B, and alpha values must all be positive.");
    } else {
      this.r = r;
      this.g = g;
      this.b = b;
      this.a = a;
      this.h = 0.0;
      this.s = 0.0;
      this.l = 0.0;
      this.delegate = new RepresentationConverter();
      convertRGBtoHSL();
    }
  }

  /**
   * Constructor for a pixel without alpha channel.
   *
   * @param r sets the red value.
   * @param g sets the green value.
   * @param b sets the blue value.
   */
  public Pixel(int r, int g, int b) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("R,G, and B values must all be positive.");
    } else {
      this.r = r;
      this.g = g;
      this.b = b;
      this.a = 255;
      this.delegate = new RepresentationConverter();
      this.h = 0;
      this.s = 0;
      this.l = 0;
      convertRGBtoHSL();
    }
  }

  /**
   * Creates a pixel with hsl values and generates the rgb values from that.
   *
   * @param h h value.
   * @param s s value.
   * @param l l value.
   * @throws IllegalArgumentException throws an error if the values are invalid for a pixel.
   */

  public Pixel(double h, double s, double l) throws IllegalArgumentException {
    if (h <= 0 || h >= 360 || s < 0 || s > 1 || s < 0 || s > 1) {
      throw new IllegalArgumentException("Invalid HSL values.");
    }
    this.a = 255;
    this.h = h;
    this.s = s;
    this.l = l;
    this.delegate = new RepresentationConverter();
    convertHSLtoRGB();
  }

  /**
   * Turns the pixel into a string representation.
   *
   * @return a 4 part string with spaces between them.
   */
  @Override
  public String toString() {
    String string = r + " " + g + " " + b + " " + a;
    return string;
  }

  /**
   * Turns the pixel into a string representation.
   *
   * @return a 3 part string with spaces between them.
   */

  public String toStringRGB() {
    String string = r + " " + g + " " + b;
    return string;
  }

  /**
   * Returns the hsl values of a pixel.
   *
   * @return returns the string of the pixel.
   */

  public String toStringHSL() {
    String string = h + " " + s + " " + l;
    return string;
  }

  /**
   * Returns the red value of a pixel.
   *
   * @return red value.
   */

  public int getR() {
    return this.r;
  }

  /**
   * Returns the green value of a pixel.
   *
   * @return green value.
   */

  public int getG() {
    return this.g;
  }

  /**
   * Returns the blue value of a pixel.
   *
   * @return blue value.
   */

  public int getB() {
    return this.b;
  }

  /**
   * Returns the alpha value of a pixel.
   *
   * @return alpha value.
   */

  public int getA() {
    return this.a;
  }

  /**
   * Returns the hue value of a pixel.
   *
   * @return hue value.
   */

  public double getH() {
    return this.h;
  }

  /**
   * Returns the saturation value of a pixel.
   *
   * @return saturation value.
   */

  public double getS() {
    return this.s;
  }

  /**
   * Returns the lightness value of a pixel.
   *
   * @return lightness value.
   */

  public double getL() {
    return this.l;
  }

  /**
   * sets the red value of a pixel to the provided value.
   */

  public void setR(int val) {
    this.r = val;
  }

  /**
   * sets the green value of a pixel to the provided value.
   */

  public void setG(int val) {
    this.g = val;
  }

  /**
   * sets the blue value of a pixel to the provided value.
   */

  public void setB(int val) {
    this.b = val;
  }

  /**
   * sets the alpha value of a pixel to the provided value.
   */

  public void setA(int val) {
    this.a = val;
  }

  /**
   * sets the hue value of a pixel to the provided value.
   */

  public void setH(double val) {
    this.h = val;
  }

  /**
   * sets the saturation value of a pixel to the provided value.
   */

  public void setS(double val) {
    this.s = val;
  }

  /**
   * sets the lightness value of a pixel to the provided value.
   */

  public void setL(double val) {
    this.l = val;
  }

  /**
   * Uses a class delegate from RepresentationConverter to perform the necessary calculations to
   * convert a pixel's RGB values into the HSL format.
   */

  public void convertRGBtoHSL() {
    double[] hsl = delegate.convertRGBtoHSL(
            (double) this.r / 255, (double) this.g / 255, (double) this.b / 255);
    this.h = hsl[0];
    this.s = hsl[1];
    this.l = hsl[2];
  }

  /**
   * Uses a class delegate from RepresentationConverter to perform the necessary calculations to
   * convert a pixel's HSL values into the RGB format.
   */
  public void convertHSLtoRGB() {
    double[] temp = delegate.convertHSLtoRGB(this.h, this.s, this.l);
    this.r = (int) temp[0];
    this.g = (int) temp[1];
    this.b = (int) temp[2];
  }
}
