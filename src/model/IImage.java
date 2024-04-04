package model;

/**
 * Interface for an Image. Defines the necessary methods to make an Image class.
 */

public interface IImage {

  /**
   * populates an Image object's fields with components from an actual PPM image file.
   *
   * @param contents path to the file to convert to an Image object.
   */
  public void createImage(String contents);

  /**
   * Gets the height value of an image.
   *
   * @return an image's height field.
   */
  public int getHeight();

  /**
   * Gets the width value of an image.
   *
   * @return an image's width field.
   */

  public int getWidth();

  /**
   * Gets the X value of an image.
   *
   * @return an image's X field.
   */

  public int getX();

  /**
   * Gets the Y value of an image.
   *
   * @return an image's Y field.
   */

  public int getY();

  /**
   * Gets the MaxValue value of an image.
   *
   * @return an image's MaxValue field.
   */
  public int getMaxValue();

  /**
   * Gets the pixel array of an image.
   *
   * @return an image's pixels field.
   */

  public IPixel[][] getPixels();

}
