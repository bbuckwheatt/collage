package model.filters;

/**
 * This class is used for filters that darken an image.
 */
public abstract class DarkenFilter extends Filter {
  /**
   * This method checks if value2 - value1 is less than 0, if it is, value2 is set to 0, otherwise
   * value2 is set to value2 - value1.
   *
   * @param value1 the first value to be subtracted
   * @param value2 the second value to be subtracted
   * @return the value of value2 after the subtraction
   */
  public int lowerBoundsCheck(int value1, int value2) {
    if ((value2 - value1) < 0) {
      value2 = 0;
    } else {
      value2 = value2 - value1;

    }
    return value2;
  }
}
