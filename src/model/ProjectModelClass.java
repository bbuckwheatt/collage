package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.filters.FiltersEnum;
import model.filters.IFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents the Project users will work on to create a collage of images contained in
 * layers. A project has a maximum width and height which represent the dimensions of the final
 * image produced, a maxValue which is the maximum alpha value of any pixel in any image of any
 * layer. And finally the project has a list of layers.
 */

public class ProjectModelClass implements ProjectModel<ILayer> {
  private int width;
  private int height;
  private int maxValue;
  private List<ILayer> layers;

  /**
   * Creates a new ProjectModelClass object.
   */
  public ProjectModelClass() {
    int width = 0;
    int height = 0;
    int maxValue = 0;
    List<ILayer> layers = new ArrayList<ILayer>();
    Pixel[][] background = new Pixel[height][width];
  }

  /**
   * Adds an image to a layer.
   *
   * @param contents represents the filepath to the image being added.
   * @param layer    is the layer a user wishes to add an image to.
   * @param x        the x coordinate where the user wants to place the top left pixel of the image.
   * @param y        the y coordinate where the user wants to place the top left pixel of the image.
   */

  public void addImage(String contents, String layer, int x, int y)
          throws IllegalArgumentException {
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getName().equals(layer)) {
        Image image = new Image();
        image.x = x;
        image.y = y;
        image.createImage(contents);
        layers.get(i).getImages().add(image);
        //sets a new max
        findMax();
      }
    }
  }

  /**
   * Saves the current state of a project to a text file, for the purpose of picking up where a user
   * left off to continue working on a collage at a later date.
   */


  @Override
  public String saveProject() {
    //FileWriter writer = new FileWriter(filename);
    StringBuilder builder = new StringBuilder();
    builder.append("C1\n");
    builder.append(this.width + " " + this.height + "\n");
    builder.append(this.maxValue + "\n");
    //looping through layers
    for (int i = 0; i < layers.size(); i++) {
      Pixel[][] temp = new Pixel[this.height][this.width];
      builder.append(layers.get(i).getName() + " " + layers.get(i).getFilter().getName() + "\n");
      //temp created at the size of the total project with zeroes as place-holders.
      for (int k = 0; k < this.height; k++) {
        for (int l = 0; l < this.width; l++) {
          temp[k][l] = new Pixel(0, 0, 0, 0);
        }
      }
      //through the images in a layer
      for (int j = 0; j < layers.get(i).getImages().size(); j++) {
        //through the pixels in an image
        for (int m = 0; m < layers.get(i).getImages().get(j).getHeight(); m++) {
          for (int n = 0; n < layers.get(i).getImages().get(j).getWidth(); n++) {
            int projectY = m + layers.get(i).getImages().get(j).getY();
            int projectX = n + layers.get(i).getImages().get(j).getX();

            if (projectY >= 0 && projectX >= 0 &&
                    projectY < this.height && projectX < this.width) {
              Pixel np = transparencyCalculator(temp[projectY][projectX],
                      layers.get(i).getImages().get(j).getPixels()[m][n]);
              temp[projectY][projectX] = np;

            }
          }
        }
      }
      //turn temp into an arraylist of width*height rows, and one column.
      ArrayList<Pixel> flattemp = new ArrayList<Pixel>();
      for (int o = 0; o < this.height; o++) {
        for (int p = 0; p < this.width; p++) {
          flattemp.add(temp[o][p]);
        }
      }
      //after the layer is finished, write it to the file.
      for (int g = 0; g < flattemp.size(); g++) {
        builder.append(flattemp.get(g).toString() + "\n");
      }
    }

    return builder.toString();
  }


  /**
   * Creates a new, blank collage project.
   *
   * @param width  a user must enter both the desired width and height of the project.
   * @param height a user must enter both the desired width and height of the project.
   */

  public void newProject(int width, int height) throws IllegalArgumentException {
    if (width > 0 && height > 0) {
      this.width = width;
      this.height = height;
      this.layers = new ArrayList<ILayer>();
    } else {
      throw new IllegalArgumentException("Width or height cannot be negative.");
    }

  }

  /**
   * Opens a save file containing the data stored by the save() method to resume working on a
   * collage.
   */

  public void load(String contents) {
    Scanner sc;
    //now set up the scanner to read from the string we just built
    sc = new Scanner(contents);

    String token;

    token = sc.next();
    if (!token.equals("C1")) {
      throw new IllegalArgumentException("Invalid Collage file: plain " +
              "RAW file should begin with C1");
    }

    int width = sc.nextInt();

    int height = sc.nextInt();

    int maxValue = sc.nextInt();

    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.layers = new ArrayList<ILayer>();


    while (sc.hasNext()) {
      String layerName = sc.next();
      String filterName = sc.next();
      this.addLayer(layerName);
      //loop through ever element in enum
      for (FiltersEnum filter : FiltersEnum.values()) {
        if (filter.name().equalsIgnoreCase(filterName)) {
          this.setFilter(layerName, filter.getFilter());
        }
      }

      Pixel[][] pixels = new Pixel[height][width];
      for (int r = 0; r < width * height && sc.hasNextInt(); r++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        int alpha = sc.nextInt();
        Pixel pixel = new Pixel(red, green, blue, alpha);
        pixels[r / width][r % width] = pixel;
      }

      Image image = new Image(pixels, height, width, maxValue);
      this.addImageSimple(layerName, image);
    }
  }


  /**
   * Adds a new layer to the project being worked on.
   *
   * @param name the user must provide the name of the layer they wish to add.
   */

  public void addLayer(String name) {
    int instances = 0;
    for (ILayer layer : layers) {
      if (layer.getName() == name) {
        instances++;
      }
    }
    Layer layer;
    if (instances != 0) {
      layer = new Layer(name + "(" + instances + ")");
    } else {
      layer = new Layer(name);
    }
    this.layers.add(layer);
  }

  /**
   * Exports the current project as a final image in PPM format.
   */


  public String saveImage() {
    //FileWriter writer = new FileWriter(filename);
    StringBuilder builder = new StringBuilder();
    builder.append("P3\n");
    builder.append(this.width + " " + this.height + "\n");
    builder.append(this.maxValue + "\n");
    Pixel[][] overalltemp = new Pixel[this.height][this.width];
    for (int k = 0; k < this.height; k++) {
      for (int l = 0; l < this.width; l++) {
        overalltemp[k][l] = new Pixel(0, 0, 0);
      }
    }
    //looping through layers
    for (int i = 0; i < layers.size(); i++) {
      Pixel[][] temp = new Pixel[this.height][this.width];
      //temp created at the size of the total project with zeroes as place-holders.
      for (int k = 0; k < this.height; k++) {
        for (int l = 0; l < this.width; l++) {
          temp[k][l] = new Pixel(0, 0, 0, 0);
        }
      }
      this.layers.get(i).applyFilterToLayer(overalltemp);
      //through the images in a layer
      for (int j = 0; j < layers.get(i).getImages().size(); j++) {
        //through the pixels in an image
        for (int m = 0; m < layers.get(i).getImages().get(j).getHeight(); m++) {
          for (int n = 0; n < layers.get(i).getImages().get(j).getWidth(); n++) {
            int projectY = m + layers.get(i).getImages().get(j).getY();
            int projectX = n + layers.get(i).getImages().get(j).getX();
            //
            if (projectY >= 0 && projectX >= 0 &&
                    projectY < this.height && projectX < this.width) {
              Pixel np = transparencyCalculator(temp[projectY][projectX],
                      layers.get(i).getImages().get(j).getPixels()[m][n]);
              temp[projectY][projectX] = np;

            }
          }
        }
      }
      //overwrite overalltemp with temp
      for (int w = 0; w < this.height; w++) {
        for (int h = 0; h < this.width; h++) {
          Pixel np = transparencyCalculator(overalltemp[w][h], temp[w][h]);
          overalltemp[w][h] = np;
        }
      }

    }
    //convert 4 component elements in overalltemp to 3 component elements
    for (int w = 0; w < this.height; w++) {
      for (int h = 0; h < this.width; h++) {
        int red = overalltemp[w][h].getR();
        int green = overalltemp[w][h].getG();
        int blue = overalltemp[w][h].getB();
        try {
          red = overalltemp[w][h].getR() * overalltemp[w][h].getA() / 255;
          green = overalltemp[w][h].getG() * overalltemp[w][h].getA() / 255;
          blue = overalltemp[w][h].getB() * overalltemp[w][h].getA() / 255;
        } catch (Exception e) {
          //do nothing
        }
        overalltemp[w][h] = new Pixel(red, green, blue);
      }
    }
    //print overalltemp to file
    for (int w = 0; w < this.height; w++) {
      for (int h = 0; h < this.width; h++) {
        builder.append(overalltemp[w][h].toStringRGB() + " ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Saves an image as a string representation but with 4 values per pixel.
   *
   * @return returns the string representation.
   */

  public String saveImageRGBA() {
    //FileWriter writer = new FileWriter(filename);
    StringBuilder builder = new StringBuilder();
    builder.append("P3\n");
    builder.append(this.width + " " + this.height + "\n");
    builder.append(this.maxValue + "\n");
    Pixel[][] overalltemp = new Pixel[this.height][this.width];
    for (int k = 0; k < this.height; k++) {
      for (int l = 0; l < this.width; l++) {
        overalltemp[k][l] = new Pixel(0, 0, 0);
      }
    }
    //looping through layers
    for (int i = 0; i < layers.size(); i++) {
      Pixel[][] temp = new Pixel[this.height][this.width];
      //temp created at the size of the total project with zeroes as place-holders.
      for (int k = 0; k < this.height; k++) {
        for (int l = 0; l < this.width; l++) {
          temp[k][l] = new Pixel(0, 0, 0, 0);
        }
      }
      this.layers.get(i).applyFilterToLayer(overalltemp);
      //through the images in a layer
      for (int j = 0; j < layers.get(i).getImages().size(); j++) {
        //through the pixels in an image
        for (int m = 0; m < layers.get(i).getImages().get(j).getHeight(); m++) {
          for (int n = 0; n < layers.get(i).getImages().get(j).getWidth(); n++) {
            int projectY = m + layers.get(i).getImages().get(j).getY();
            int projectX = n + layers.get(i).getImages().get(j).getX();
            //
            if (projectY >= 0 && projectX >= 0 &&
                    projectY < this.height && projectX < this.width) {
              Pixel np = transparencyCalculator(temp[projectY][projectX],
                      layers.get(i).getImages().get(j).getPixels()[m][n]);
              temp[projectY][projectX] = np;

            }
          }
        }
      }
      //overwrite overalltemp with temp
      for (int w = 0; w < this.height; w++) {
        for (int h = 0; h < this.width; h++) {
          Pixel np = transparencyCalculator(overalltemp[w][h], temp[w][h]);
          overalltemp[w][h] = np;
        }
      }

    }
    //print overalltemp to file
    for (int w = 0; w < this.height; w++) {
      for (int h = 0; h < this.width; h++) {
        builder.append(overalltemp[w][h].toString() + " ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }


  /**
   * Sets the filter of a provided layer to a new filter.
   *
   * @param layer  Name of the layer being modified.
   * @param filter new filter to be applied.
   */

  public void setFilter(String layer, IFilter filter) {
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getName().equals(layer)) {
        layers.get(i).setFilter(filter);
      }
    }
  }

  /**
   * Finds the maximum alpha value across all images in all layers in a project.
   */
  private void findMax() {
    int max = layers.get(0).getImages().get(0).getMaxValue();
    for (int i = 0; i < layers.size(); i++) {
      for (int j = 1; j < layers.get(i).getImages().size(); j++) {
        if (layers.get(i).getImages().get(j).getMaxValue() < max) {
          max = layers.get(i).getImages().get(j).getMaxValue();
        }

      }
    }
    this.maxValue = max;
  }

  /**
   * Finds the correct alpha between 2 pixels.
   *
   * @param bp background pixel.
   * @param fp front pixel.
   * @return the correct combo pixel.
   */

  private Pixel transparencyCalculator(IPixel bp, IPixel fp) {
    if (fp.getA() == bp.getA() && fp.getR() == bp.getR() && fp.getG() == bp.getG() &&
            fp.getB() == bp.getB() && fp.getA() == 0 && fp.getR() == 0 && fp.getG() == 0 &&
            fp.getB() == 0) {
      return new Pixel(0, 0, 0, 0);
    } else if (fp.getA() == 0 && bp.getA() == 0) {
      return new Pixel(0, 0, 0, 0);
    } else {
      double af2 = (fp.getA() / 255 + bp.getA() / 255 * (1 - (fp.getA() / 255)));
      double af = af2 * 255;
      double rf = (fp.getA() / 255 * fp.getR() + bp.getR() * (bp.getA() / 255)
              * (1 - fp.getA() / 255)) * (1 / af2);
      double gf = (fp.getA() / 255 * fp.getG() + bp.getG() * (bp.getA() / 255)
              * (1 - fp.getA() / 255)) * (1 / af2);
      double bf = (fp.getA() / 255 * fp.getB() + bp.getB() * (bp.getA() / 255)
              * (1 - fp.getA() / 255)) * (1 / af2);

      try {
        af2 = (fp.getA() / this.maxValue + bp.getA() / this.maxValue *
                (1 - (fp.getA() / this.maxValue)));
        af = af2 * this.maxValue;
        rf = (fp.getA() / this.maxValue * fp.getR() + bp.getR() * (bp.getA() / this.maxValue)
                * (1 - fp.getA() / this.maxValue)) * (1 / af2);
        gf = (fp.getA() / this.maxValue * fp.getG() + bp.getG() * (bp.getA() / this.maxValue)
                * (1 - fp.getA() / this.maxValue)) * (1 / af2);
        bf = (fp.getA() / this.maxValue * fp.getB() + bp.getB() * (bp.getA() / this.maxValue)
                * (1 - fp.getA() / this.maxValue)) * (1 / af2);
      } catch (ArithmeticException e) {
        //do nothing
      }
      return new Pixel((int) rf, (int) gf, (int) bf, (int) af);
    }
  }

  /**
   * Adds an image to a layer.
   *
   * @param layername the name of the layer to add the image to.
   * @param image     the image to be added.
   */
  private void addImageSimple(String layername, Image image) {
    for (int i = 0; i < this.layers.size(); i++) {
      if (this.layers.get(i).getName().equals(layername)) {
        layers.get(i).getImages().add(image);
        //sets a new max
        findMax();
      }
    }
  }

  /**
   * Returns the list of layers.
   *
   * @return Arraylist of layers.
   */

  public List<ILayer> getLayer() {
    return this.layers;

  }

  /**
   * Returns the width of the project.
   *
   * @return int of the width.
   */
  public int getWidth() {
    return this.width;

  }

  /**
   * Returns the height of the project.
   *
   * @return int of the height.
   */
  public int getHeight() {
    return this.height;

  }

  /**
   * Returns the max value of the project.
   *
   * @return int of the max value.
   */
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Turns a String into a BufferedImage, for passing to the controller.
   *
   * @param imageString Represents a String representation of a project's composite image.
   * @return returns a BufferedImage version of an image.
   */

  public BufferedImage bufferedImage(String imageString) {
    Scanner sc = new Scanner(imageString);
    String p3 = sc.next();
    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int rgb = 255 << 24;
        rgb |= sc.nextInt() << 16;
        rgb |= sc.nextInt() << 8;
        rgb |= sc.nextInt();
        image.setRGB(c, r, rgb);
      }
    }
    return image;
  }

  /**
   * Turns a string representation of an image into a BufferedImage. This one has ARGB values.
   *
   * @param imageString given string representation.
   * @return buffered image object.
   */

  public BufferedImage bufferedImageARGB(String imageString) {
    Scanner sc = new Scanner(imageString);
    String p3 = sc.next();
    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        int alpha = sc.nextInt();
        int rgb = alpha << 24;
        rgb |= red << 16;
        rgb |= green << 8;
        rgb |= blue;
        image.setRGB(c, r, rgb);
      }
    }
    return image;
  }

  /**
   * Turns a buffered image into a string representation of an image.
   *
   * @param image given buffered image.
   * @return returns the string version.
   */

  public String toPPMString(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    StringBuilder sb = new StringBuilder();
    sb.append("P3\n");
    sb.append(width + " " + height + "\n");
    sb.append(255 + "\n");
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int rgb = image.getRGB(c, r);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        sb.append(red + " " + green + " " + blue + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Turns a buffered image into a string representation of an image. This one has ARGB values.
   *
   * @param image given buffered image.
   * @return returns the string version.
   */

  public String toPPMStringRGBA(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    StringBuilder sb = new StringBuilder();
    sb.append("P3A\n");
    sb.append(width + " " + height + "\n");
    sb.append(this.maxValue + "\n");
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int rgb = image.getRGB(c, r);
        int alpha = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        sb.append(red + " " + green + " " + blue + " " + alpha + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * gets the layer name of layer i.
   *
   * @param i layer number.
   * @return layer name.
   */

  public String getLayerName(int i) {
    return this.getLayer().get(i).getName();
  }

  /**
   * gets the filter of layer i.
   *
   * @param i layer number.
   * @return filter name.
   */

  public String getFilterName(int i) {
    return this.getLayer().get(i).getFilter().getName();
  }

  /**
   * Returns the list of images in layer i.
   *
   * @param i layer number.
   * @return list of images in layer i.
   */

  public List<IImage> getLayersImages(int i) {
    return this.getLayer().get(i).getImages();
  }

  /**
   * Gets the Height position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's Height value.
   */

  public int getImagesHeight(int i, int j) {
    return this.getLayersImages(i).get(j).getHeight();
  }

  /**
   * Gets the Width position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's Width value.
   */

  public int getImagesWidth(int i, int j) {
    return this.getLayersImages(i).get(j).getWidth();
  }

  /**
   * Gets the X position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's X value.
   */

  public int getImagesX(int i, int j) {
    return this.getLayersImages(i).get(j).getX();
  }

  /**
   * Gets the Y position of an image in layer i, and at position j in the images list.
   *
   * @param i layer number.
   * @param j image number.
   * @return image's Y value.
   */

  public int getImagesY(int i, int j) {
    return this.getLayersImages(i).get(j).getY();
  }

  /**
   * Returns a map of all filters and their names.
   *
   * @return the specified map.
   */

  public Map<String, IFilter> getAllFilters() {
    HashMap<String, IFilter> masterList = new HashMap<String, IFilter>();
    for (FiltersEnum filter : FiltersEnum.values()) {
      masterList.put(filter.name(), FiltersEnum.valueOf(filter.name()).getFilter());
    }
    return masterList;
  }
}
