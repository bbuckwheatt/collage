package model;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import model.filters.IFilter;

/**
 * This interface represents a project.
 */
public interface ProjectModel<L> extends Project<L> {

  /**
   * Adds an image to a layer.
   *
   * @param contents represents the filepath to the image being added.
   * @param layer    is the layer a user wishes to add an image to.
   * @param x        the x coordinate where the user wants to place the top left pixel of the image.
   * @param y        the y coordinate where the user wants to place the top left pixel of the image.
   */

  void addImage(String contents, String layer, int x, int y);

  /**
   * Saves the current state of a project to a text file, for the purpose of picking up where a user
   * left off to continue working on a collage at a later date.
   */

  String saveProject();

  /**
   * Creates a new, blank collage project.
   *
   * @param width  a user must enter both the desired width and height of the project.
   * @param height a user must enter both the desired width and height of the project.
   */

  void newProject(int width, int height);

  /**
   * Opens a save file containing the data stored by the save() method to resume working on a
   * collage.
   */

  void load(String contents);

  /**
   * Adds a new layer to the project being worked on.
   *
   * @param name the user must provide the name of the layer they wish to add.
   */

  void addLayer(String name);

  /**
   * Exports the current project as a final image in PPM format.
   */
  String saveImage();

  /**
   * Sets the filter of a provided layer to a new filter.
   *
   * @param layer  Name of the layer being modified.
   * @param filter new filter to be applied.
   */

  void setFilter(String layer, IFilter filter);

  /**
   * Turns a string representation of an image into a BufferedImage.
   *
   * @param imageString given string representation.
   * @return buffered image object.
   */

  public BufferedImage bufferedImage(String imageString);

  /**
   * Turns a string representation of an image into a BufferedImage. This one has ARGB values.
   *
   * @param imageString given string representation.
   * @return buffered image object.
   */

  public BufferedImage bufferedImageARGB(String imageString);

  /**
   * Turns a buffered image into a string representation of an image.
   *
   * @param image given buffered image.
   * @return returns the string version.
   */

  public String toPPMString(BufferedImage image);

  /**
   * gets the layer name of layer i.
   *
   * @param i layer number.
   * @return layer name.
   */

  public String getLayerName(int i);

  /**
   * gets the filter of layer i.
   *
   * @param i layer number.
   * @return filter name.
   */

  public String getFilterName(int i);

  /**
   * Returns the list of images in layer i.
   *
   * @param i layer number.
   * @return list of images in layer i.
   */

  public List<IImage> getLayersImages(int i);

  /**
   * Gets the Height position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's Height value.
   */

  public int getImagesHeight(int i, int j);

  /**
   * Gets the Width position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's Width value.
   */

  public int getImagesWidth(int i, int j);

  /**
   * Gets the X position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's X value.
   */

  public int getImagesX(int i, int j);

  /**
   * Gets the Y position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's Y value.
   */

  public int getImagesY(int i, int j);

  /**
   * Returns a map of all filters and their names.
   *
   * @return the specified map.
   */

  public Map<String, IFilter> getAllFilters();

  /**
   * Turns a buffered image into a string representation of an image. This one has ARGB values.
   *
   * @param image given buffered image.
   * @return returns the string version.
   */

  public String toPPMStringRGBA(BufferedImage image);

  /**
   * Saves an image as a string representation but with 4 values per pixel.
   *
   * @return returns the string representation.
   */

  public String saveImageRGBA();

}
