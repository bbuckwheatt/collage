package model;

import java.util.List;

import model.filters.IFilter;

/**
 * Interface for an Layer. Defines the necessary methods to make a Layer class.
 */
public interface ILayer {

  /**
   * Sets the layer's filter to a given Filter.
   *
   * @param f given filter.
   */

  void setFilter(IFilter f);

  /**
   * Applies the filter to every pixel in every image in every layer. Uses a loop
   *
   * @param background background is the background of the project.
   */

  public void applyFilterToLayer(Pixel[][] background);

  /**
   * Returns the arraylist of images.
   *
   * @return arraylist of images.
   */
  public List<IImage> getImages();

  /**
   * Returns the name string.
   *
   * @return string representing name of layer.
   */

  public String getName();

  /**
   * Returns the filter.
   *
   * @return Filter object.
   */

  public IFilter getFilter();
}
