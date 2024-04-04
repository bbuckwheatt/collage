package model;

import java.util.ArrayList;
import java.util.List;

import model.filters.IFilter;
import model.filters.Normal;

/**
 * Class to represent a layer of a project. Contains:
 * a name,
 * a filter,
 * and a list of images in the layer.
 */
public class Layer implements ILayer {
  private String name;
  private IFilter filter;
  private List<IImage> images;

  /**
   * Constructor for a layer, checks if values are null before initializing.
   *
   * @param name   the name of the layer.
   * @param filter the filter of the layer.
   * @param images the arraylist of images in the layer.
   */

  public Layer(String name, IFilter filter, List<IImage> images) {
    if (name == null || filter == null || images == null) {
      throw new IllegalArgumentException("Null values received.");
    } else {
      this.name = name;
      this.filter = filter;
      this.images = images;
    }
  }

  /**
   * Another constructor for a layer used by the add layer method in Project class.
   *
   * @param name checks if name is null before initializing, remaining values initialized to default
   *             filter and an empty arraylist until changed later.
   */

  public Layer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    } else {
      this.name = name;
      this.filter = new Normal();
      this.images = new ArrayList<IImage>();
    }
  }

  /**
   * Sets this filter to a provided filter.
   *
   * @param f provided filter to change to.
   */
  //should only be applied to images in a layer when the project is saved to a file
  public void setFilter(IFilter f) {
    this.filter = f;
  }

  /**
   * Applies the filter to every pixel in every image in every layer. Uses a loop
   *
   * @param background background is the background of the project.
   */

  public void applyFilterToLayer(Pixel[][] background) {
    for (int i = 0; i < images.size(); i++) {
      for (int r = 0; (r < images.get(i).getPixels().length); r++) {
        for (int c = 0; (c < images.get(i).getPixels()[r].length); c++) {
          int projectY = r + this.images.get(i).getY();
          int projectX = c + this.images.get(i).getX();
          if (projectY >= 0 && projectX >= 0 &&
                  projectY < background.length && projectX < background[0].length) {
            this.filter.apply(images.get(i).getPixels()[r][c], background[projectY][projectX]);
          }
        }
      }
    }
  }

  /**
   * Returns the arraylist of images.
   *
   * @return arraylist of images.
   */
  public List<IImage> getImages() {
    return this.images;
  }

  /**
   * Returns the name string.
   *
   * @return string representing name of layer.
   */

  public String getName() {
    return this.name;
  }

  /**
   * Returns the filter.
   *
   * @return Filter object.
   */

  public IFilter getFilter() {
    return this.filter;
  }
}
