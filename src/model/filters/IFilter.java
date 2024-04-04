package model.filters;

import model.IPixel;

/**
 * Represents the interface for a Filter. All filters should be created based on this.
 * Includes the base functionality of a filter.
 */
public interface IFilter {

  /**
   * Filter name.
   *
   * @return Returns the filter name.
   */
  public String getName();

  /**
   * Applies a filter to a set of pixels.
   *
   * @param p  first pixel.
   * @param p2 second pixel.
   */
  public void apply(IPixel p, IPixel p2);
}
