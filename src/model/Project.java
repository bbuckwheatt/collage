package model;

import java.util.List;

/**
 * This interface represents a project.
 * Only contains getters.
 */
public interface Project<L> {

  /**
   * Returns the list of layers.
   *
   * @return Arraylist of layers.
   */

  public List<L> getLayer();

  /**
   * Returns the width of the project.
   *
   * @return int of the width.
   */

  public int getWidth();

  /**
   * Returns the height of the project.
   *
   * @return int of the height.
   */
  public int getHeight();

  /**
   * Returns the max value of the project.
   *
   * @return int of the max value.
   */
  public int getMaxValue();

}
