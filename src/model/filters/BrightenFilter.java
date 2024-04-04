package model.filters;

/**
 * This class is used for filters that brighten an image.
 */
public abstract class BrightenFilter extends Filter {
  /**
   * Checks if the value1 + value2 is greater than 255, if so, set value2 to 255.
   *
   * @param value1 the first value to be added.
   * @param value2 the second value to be added.
   * @return the value2.
   */
  public int upperBoundsCheck(int value1, int value2) {
    if ((value2 + value1) > 255) {
      value2 = 255;
    } else {
      value2 = value2 + value1;
    }
    return value2;
  }
}
