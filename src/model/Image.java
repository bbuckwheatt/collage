package model;

import java.util.Scanner;

/**
 * A class representing an individual image contained in a layer of a project. Contains:
 * an x value of where it will be placed.
 * a y value of where it will be placed.
 * a height dimension of how large the image is.
 * a width dimension of how large the image is.
 * a maximum alpha value of the pixels in the image.
 * and a 2d array of all pixels in the image.
 * fields are not all private so that other model classes can access them, which is necessary.
 */

public class Image implements IImage {
  protected int x;
  protected int y;
  private int height;
  private int width;
  protected int maxValue;
  protected Pixel[][] pixels;

  /**
   * Default constructor for the image class.
   */
  public Image() {
    this.x = 0;
    this.y = 0;
    this.height = 0;
    this.width = 0;
    this.maxValue = 0;
    this.pixels = new Pixel[height][width];
  }

  /**
   * Constructor for the image class.
   *
   * @param pixels   an array of pixels representing every pixel in the image.
   * @param height   the height of the image.
   * @param width    the width of the image.
   * @param maxValue the maximum alpha value in any pixel in the image.
   */

  public Image(Pixel[][] pixels, int height, int width, int maxValue) {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null");
    } else {
      this.x = 0;
      this.y = 0;
      this.pixels = pixels;
      this.height = height;
      this.width = width;
      this.maxValue = maxValue;
    }
  }

  /**
   * populates an Image object's fields with components from an actual PPM image file.
   *
   * @param contents path to the file to convert to an Image object.
   */

  public void createImage(String contents) {
    Scanner sc;
    sc = new Scanner(contents);
    String token;
    token = sc.next();
    if (token.equals("P3")) {

      this.width = sc.nextInt();
      this.height = sc.nextInt();
      this.maxValue = sc.nextInt();
      this.pixels = new Pixel[this.height][this.width];

      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          this.pixels[i][j] = new Pixel(r, g, b, this.maxValue);
        }
      }
    } else if (token.equals("P3A")) {
      this.width = sc.nextInt();
      this.height = sc.nextInt();
      this.maxValue = sc.nextInt();
      this.pixels = new Pixel[this.height][this.width];

      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          int a = sc.nextInt();
          this.pixels[i][j] = new Pixel(r, g, b, a);
        }
      }
    } else {
      throw new IllegalArgumentException("Not a valid image type!");
    }
  }

  /**
   * Gets the Height value of an image.
   *
   * @return an image's height field.
   */

  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the width value of an image.
   *
   * @return an image's width field.
   */

  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the X value of an image.
   *
   * @return an image's X field.
   */

  public int getX() {
    return this.x;
  }

  /**
   * Gets the Y value of an image.
   *
   * @return an image's Y field.
   */

  public int getY() {
    return this.y;
  }

  /**
   * Gets the MaxValue value of an image.
   *
   * @return an image's MaxValue field.
   */

  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Gets the pixel array of an image.
   *
   * @return an image's pixels field.
   */

  public IPixel[][] getPixels() {
    return this.pixels;
  }

}
