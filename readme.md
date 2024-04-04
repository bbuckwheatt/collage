Image Collager Image Editor
=====================

An overall description:
-----------------------
Image Collager is an image editor designed to work with layered images in a user-friendly way. It allows users to create new projects, add layers, import images, apply filters, and save their creations. The program has both a graphical user interface (GUI) and a command-line interface (CLI) for users with different preferences.

Features:
- Create new projects with customizable dimensions
- Add layers to projects
- Import images to layers
- Apply filters to layers
- Save projects and images in various formats
- User-friendly GUI and CLI

Requirements/dependencies list:
-------------------------------
- Java 11 or higher JRE
- JUnit 4 for running the tests

For instructions on how to use the program, please refer to the USEME file included in the project.

Design of the code:
--------------------
The code is organized using the Model-View-Controller (MVC) architecture, with separate packages for the model, view, and controller components. The design consists of multiple classes and interfaces, which are described below.




VIEW PACKAGE
The view package contains classes for rendering the application's interface.

ViewInterface:
This interface dictates the functionality for both the GUI view and the text view.

Methods:
  - void renderMessage(String message): Takes a String input (message) and displays it to the user. The way the message is displayed depends on the implementation (GUI or TextView).
  - void renderProject(): Updates the visual representation of the project.

GUI:
This class represents the graphical user interface for the image editor, providing a more interactive view for the user. It Implements ViewInterface.

Methods:
  - GUI(ProjectModel model, GUIControllerInterface controller): Constructor for the GUI class. Initializes the GUI and sets up the components.
  - void renderMessage(String message): Displays the provided message as a pop-up window in the GUI.
  - void renderProject(): Refreshes the GUI by rendering the current state of the project. Creates a buffered image from the model's combined image and updates the image canvas.

ImagePanel:
A simple panel to display a single image. Displays the image using a given BufferedImage during its refresh.

Methods:
  - ImagePanel(): Creates a new ImagePanel object and creates a JLabel.
  - protected void paintComponent(Graphics g2d): A built-in method that is overwritten for refreshing the image displayed.
  - void updateImage(BufferedImage image): Sets the image to be displayed to the given one.

CollageView:
This class represents the text user interface for the image editor, used in the command. It Implements ViewInterface.

Methods:
  - CollageView(ProjectModel model, Appendable destination): Constructor for the CollageView class. Initializes the GUI and sets up the components.
  - void renderMessage(String message): Displays the provided message to the command line.
  - void renderProject(): Outputs the attributes of the model in text form into the command line. 





CONTROLLER PACKAGE
The controller package contains classes responsible for handling user inputs and managing the flow of the application. 

IController:
Controller interface. Dictates what methods are necessary for a controller implementation.

Methods:
  - String readProject(String filename): Reads a project file and returns the contents as a string.
  - BufferedImage readImage(String filepath): Method to read images, changed from the previous simple read method to work with multiple image types by reading images in as BufferedImages as a first step.
  - void writeProject(String filename, String output): Writes a string to a file. Used in saving a project file.
  - void writeImage(String filename, BufferedImage image): New method to write the project being worked on as an image file of the user's choosing.
  - String endingChecker(String filepath): Checks the ending, or filetype, of a given filepath.

GUIControllerInterface:
Controller Interface for the GUI. This interface is responsible for handling all user input and communicating with the model.

Methods:
  - void controllerMakeNewProject(int x, int y): Controller method to call the model's newProject method.
  - void controllerLoadProject(String filePath): Controller method to call the model's load method.
  - void controllerSaveProject(String filePath): Controller method to call the model's saveProject method.
  - void controllerSaveImage(String filePath): Controller method to call the model's saveImage method. Changes per file type the user chooses.
  - void controllerAddLayer(String layerName): Controller method to call the model's addLayer method.
  - void controllerAddImage(String filePath, String layerName, int x, int y): Controller method to call the model's addImage method. Changes based on the chosen filetype.
  - void controllerSetFilter(String layerName, IFilter filter): Controller method to call the model's setFilter method.

GUIController: 
This class is responsible for handling user inputs and managing the application from a GUI user interface. Implements the GUIControllerInterface.

Methods:
  - GUIController(ProjectModel model): Contructor for the GUIController class. Initializes the GUI controller. 
  - void controllerMakeNewProject(int x, int y): Controller method to call the model's newProject method.
  - void controllerLoadProject(String filePath): Controller method to call the model's load method.
  - void controllerSaveProject(String filePath): Controller method to call the model's saveProject method.
  - void controllerSaveImage(String filePath): Controller method to call the model's saveImage method. Changes per file type the user chooses.
  - void controllerAddLayer(String layerName): Controller method to call the model's addLayer method.
  - void controllerAddImage(String filePath, String layerName, int x, int y): Controller method to call the model's addImage method. Changes based on the chosen filetype.
  - void controllerSetFilter(String layerName, IFilter filter): Controller method to call the model's setFilter method.

TextControllerInterface:
Controller Interface for the GUI. This interface is responsible for handling all user input and communicating with the model.

Methods:
  - void start(): Method to start the text based controller.

TextController 
This class is responsible for handling user inputs and managing the application from the command line interface. Implements the ITextControllerInterface.

Methods:
    - start(): Starts the controller and allows users to interact with the project through text-based commands. First prompts the user to select a new or loaded project, and then prompts for various commands to modify the project. Implements a timeout if no more inputs are received.
    - newProjectHelper(Scanner scanner): A helper method for the new-project command, which prompts the user for the width and height of the new project. Returns an array of 2 integers representing the width and height, respectively.
    - newProjectHelper2(int[] widthHeight): A helper method for the new-project command, which attempts to create a new project with the given width and height. Throws an IllegalArgumentException if the input is invalid.
    - loadProject(String path): A helper method for the load-project command, which attempts to load a project from the given file path. Throws an IllegalArgumentException if the file path is invalid.



MODEL PACKAGE
The controller package contains classes pertaining to the model itself which is what the user will be manipulating through the view and controller.

  FILTERs PACKAGE
  Contains all files pertaining to filters, which users can apply to Images.

  IFilter Interface:
  The IFilter interface represents a filter that can be applied to a set of pixels in an image. All filters should be created based on this interface. It includes the base functionality of a filter.

  Methods:
    - getName(): This method returns the filter name. The output is a String representing the name of the filter.
    - apply(IPixel p, IPixel p2): This method applies the filter to a set of pixels. It takes in two IPixel objects, p and p2, which represent the first and second pixels, respectively. The method does not return any output. Instead, it directly modifies the input pixels.
  
  Filter:
  The Filter abstract class is the parent class for all filter classes that do/will exist. It implements the IFilter interface and provides a basic implementation for the getName() method.

  Methods:
     - getName(): This method returns the name of the given filter. It overrides the method from the IFilter interface. The output is a String representing the name of the filter.
  
  FiltersEnum:
  The FiltersEnum enum stores all the filters that are available to the user. It is used to create a list of filters that can be used in the program.

  Methods:
    - FiltersEnum(IFilter filter): This constructor sets the filter for the enum value to the provided filter.
    - getFilter(): This method returns the filter associated with the enum value. The output is an IFilter representing the filter.

  DarkenFilter:
  The DarkenFilter abstract class is used for filters that darken an image. It is a subclass of the Filter class.

  Methods:
    - lowerBoundsCheck(int value1, int value2): This method checks if the result of value2 - value1 is less than 0. If it is, value2 is set to 0; otherwise, value2 is set to value2 - value1. The method takes in two integer values, value1 and value2, and returns the value of value2 after the subtraction.

  BrightenFilter: 
  The BrightenFilter abstract class is used for filters that brighten an image. It is a subclass of the Filter class.

  Methods:
    - upperBoundsCheck(int value1, int value2): This method checks if the result of value1 + value2 is greater than 255. If it is, value2 is set to 255; otherwise, value2 is set to value2 + value1. The method takes in two integer values, value1 and value2, and returns the value of value2 after the addition.
  
  BlueFilter:
  The BlueFilter class is a filter that makes the image blue by setting the red and green values to 0. It is a subclass of the Filter class.

  Methods:
    - BlueFilter(): This constructor sets the filter name to "BlueFilter".
    - apply(IPixel p, IPixel p2): This method applies the blue filter to the image. It takes in two IPixel instances, p and p2, and sets the red and green values of p to 0. After that, it converts the RGB values of p to HSL values.

  RedFilter:
  The RedFilter class is a filter that makes the image red by setting the blue and green values to 0. It is a subclass of the Filter class.

  Methods:
    - RedFilter(): This constructor sets the filter name to "RedFilter".
    - apply(IPixel p, IPixel p2): This method applies the blue filter to the image. It takes in two IPixel instances, p and p2, and sets the blue and green values of p to 0. After that, it converts the RGB values of p to HSL values.

  GreenFilter:
  The GreenFilter class is a filter that makes the image green by setting the red and blue values to 0. It is a subclass of the Filter class.

  Methods:
    - GreenFilter(): This constructor sets the filter name to "GreenFilter".
    - apply(IPixel p, IPixel p2): This method applies the blue filter to the image. It takes in two IPixel instances, p and p2, and sets the red and blue values of p to 0. After that, it converts the RGB values of p to HSL values.
  
  DifferenceFilter:
  The DifferenceFilter class is a filter that applies the difference of two RGB values to an image. It is a subclass of the Filter class.

  Methods:
    - DifferenceFilter(): This constructor sets the filter name to "DifferenceFilter".
    - apply(IPixel p, IPixel p2): This method applies the difference filter to the image. It takes in two IPixel instances, p and p2, and calculates the absolute difference between their red, green, and blue values. The resulting values are set as the new RGB values for p. After that, it converts the RGB values of p to HSL values.
  
  MultiplyFilter
  The MultiplyFilter class is a filter that multiplies the L (lightness) value of two pixels. It is a subclass of the Filter class.

  Methods:
    - MultiplyFilter(): This constructor sets the filter name to "MultiplyFilter".
    - apply(IPixel p, IPixel p2): This method applies the multiply filter to the image. It takes in two IPixel instances, p and p2, and multiplies their L values. The resulting L value is set as the new L value for p. After that, it converts the HSL values of p to RGB values.
  
  ScreenFilter:
  The ScreenFilter class is a filter that applies a screen effect to two pixels' L (lightness) values. It is a subclass of the Filter class.

  Methods:
    - ScreenFilter(): This constructor sets the filter name to "ScreenFilter".
    - apply(IPixel p, IPixel p2): This method applies the screen filter to the image. It takes in two IPixel instances, p and p2, and applies the screen effect to their L values(p.setL(1 - (1 - p.getL()) * (1 - p2.getL()))). The resulting L value is set as the new L value for p. After that, it converts the HSL values of p to RGB values.
  
  IntensityBrigthen:
  IntensityBrighten
  The IntensityBrighten class is a filter that brightens an image by increasing the red, green, and blue values of a pixel by their average value. It is a subclass of the BrightenFilter class.

  Methods:
    - IntensityBrighten(): This constructor sets the filter name to "IntensityBrighten".
    - apply(IPixel p, IPixel p2): This method applies the intensity brighten filter to the given pixel. It calculates the average value of the red, green, and blue values of the pixel, and then sets the new values for each color channel by applying an upper bounds check to the sum of the average value and the original channel value. Finally, it converts the RGB values of the pixel to HSL values.
  
  IntensityDarken:
  The IntensityDarken class is a filter that darkens an image by decreasing the red, green, and blue values of a pixel by their average value. It is a subclass of the DarkenFilter class.

  Methods:
    - IntensityDarken(): This constructor sets the filter name to "IntensityDarken".
    - apply(IPixel p, IPixel p2): This method applies the IntensityDarken filter to the given pixel. It calculates the average value of the red, green, and blue values of the pixel and then sets the new values for each color channel by applying a lower bounds check to the difference between the average value and the original channel value. Finally, it converts the RGB values of the pixel to HSL values.
  
  LumaBrighten:
  The LumaBrighten class is a filter that brightens an image based on the luma value of the pixel. It is a subclass of the BrightenFilter class.

  Methods:
    - LumaBrighten(): This constructor sets the filter name to "LumaBrighten".
    - apply(IPixel p, IPixel p2): This method applies the LumaBrighten filter to the given pixel. It calculates the luma value of the pixel using the formula (0.2126 * red) + (0.7152 * green) + (0.0722 * blue), and then sets the new values for each color channel by applying an upper bounds check to the luma value and the original channel value. Finally, it converts the RGB values of the pixel to HSL values.
  
  LumaDarken:
  The LumaDarken class is a filter that darkens an image based on the luma value of the pixel. It is a subclass of the DarkenFilter class.

  Methods:
  - LumaDarken(): This constructor sets the filter name to "LumaDarken".
  - apply(IPixel p, IPixel p2): This method applies the LumaDarken filter to the given pixel. It calculates the luma value of the pixel using the formula (0.2126 * red) + (0.7152 * green) + (0.0722 * blue), and then sets the new values for each color channel by applying a lower bounds check to the luma value and the original channel value. Finally, it converts the RGB values of the pixel to HSL values.

  ValueBrighten:
  The ValueBrighten class is a filter that brightens an image based on the value of the pixel. It is a subclass of the BrightenFilter class.

  Methods:
    - ValueBrighten(): This constructor sets the filter name to "ValueBrighten".
    - apply(IPixel p, IPixel p2): This method applies the ValueBrighten filter to the given pixel. It calculates the maximum value among the red, green, and blue values of the pixel, and then sets the new values for each color channel by applying an upper bounds check to the maximum value and the original channel value. Finally, it converts the RGB values of the pixel to HSL values. 
  
  ValueDarken:
  The ValueDarken class is a filter that darkens an image based on the value of the pixel. It is a subclass of the DarkenFilter class.

  Methods:
  - ValueDarken(): This constructor sets the filter name to "ValueDarken".
  - apply(IPixel p, IPixel p2): This method applies the ValueDarken filter to the given pixel. It calculates the maximum value among the red, green, and blue values of the pixel, and then sets the new values for each color channel by applying a lower bounds check to the maximum value and the original channel value. Finally, it converts the RGB values of the pixel to HSL values.


IImage Interface: The image interface represents an individual image on a layer in a project of the Collaging tool. The interface provides methods that would be necessary to include in any implementation of an Image in the Collaging tool. These methods include:

public void createImage(String contents); This method creates an object of type Image given a string representation of the image in PPM form.

public int getHeight(); This method returns the height field of the object of Image type. This represents the height in pixels of the image.

public int getWidth(); This method returns the width field of the object of Image type. This represents the width in pixels of the image.

  public int getX(); This method returns the x field of the object of Image type. This represents the x value of where the image is placed on the overall project.

public int getY(); This method returns the y field of the object of Image type. This represents the Y value of where the image is placed on the overall project.

  public int getMaxValue(); This method returns the Max Value field of the object of Image type. This represents the maximum alpha value of any pixel in the image.

  public IPixel[][] getPixels(); This method returns the pixels field of the object of Image type. This represents the 2d array of pixels that make up the image.

Image class: This class is the implementation of the image object as defined in our program. The Image class has 6 fields, as defined below:
​​ protected int x;  This represents the x value of where the image is placed on the overall project.
  protected int y; This represents the Y value of where the image is placed on the overall project.
  private int height; This represents the height in pixels of the image.
  private int width; This represents the width in pixels of the image.
  protected int maxValue; This represents the maximum alpha value of any pixel in the image.
  protected Pixel[][] pixels; This represents the 2d array of pixels that make up the image.
In addition to methods in the interface for Image, the Image class has 2 constructors.
The first constructor takes in no parameters, and initializes all the ints to 0 and the array to empty.
The second constructor takes in 4 fields of the class as parameters and sets the provided parameters to the class fields after checking that the array is not null. The 2 excluded fields are x and y values for the image.

ILayer interface: The Layer interface represents an individual layer in a project of the Collaging tool. The interface provides methods that would be necessary to include in any implementation of a Layer in the Collaging tool. These methods include:

  void setFilter(IFilter f); This method sets the layer’s filter to a Filter object provided to the method.

  public void applyFilterToLayer(Pixel[][] background); This method applies the filter of the layer to each pixel contained on the layer, with some filters taking the pixels on the layer below this layer into account with how the filter is applied.

  public List<IImage> getImages(); This method returns the list of Image objects contained on the current layer.

  public String getName(); This method returns the name of the current layer.

  public IFilter getFilter(); This method returns the filter of the current layer.

Layer class: This class is the implementation of a Layer as used in the Collaging project. A layer has 3 fields, specified below:
 private String name; Represents the name of a layer.
  private IFilter filter; Represents the filter of the layer. By default, this is Normal. 
  private List<IImage> images; Represents the list of images contained within a layer.
In addition to the methods specified in the ILayer interface, a layer also has 2 constructors:
The first takes in a name as a parameter, and sets that to the class field if it is not null, then sets the filter to normal and the list of images to empty.
The second constructor takes in all three fields as parameters, checks if each is null, then sets each to the corresponding fields of the class.

IPixel interface: The pixel interface represents an individual pixel contained in an image, which is contained in a layer, contained in a project of the Collaging tool. The interface provides methods that would be necessary to include in any implementation of a pixel in the Collaging tool. These methods include:

public int getR(); Returns the R value associated with a pixel. Represents the red element of a pixel.

  public int getG(); Returns the G value associated with a pixel. Represents the green element of a pixel.

  public int getB(); Returns the B value associated with a pixel. Represents the blue element of a pixel.

  public int getA(); Returns the A value associated with a pixel. Represents the alpha element of a pixel.

  public double getH(); Returns the H value associated with a pixel. Represents the Hue element of a pixel.

  public double getS(); Returns the S value associated with a pixel. Represents the saturation element of a pixel.

  public double getL(); Returns the L value associated with a pixel. Represents the lightness element of a pixel.

  public void setR(int val); Sets the R value of a pixel to the provided R value.

  public void setG(int val); Sets the G value of a pixel to the provided G value.
 
 public void setB(int val); Sets the B value of a pixel to the provided B value.

  public void setA(int val); Sets the A value of a pixel to the provided A value.

  public void setH(double val); Sets the H value of a pixel to the provided H value.

  public void setS(double val); Sets the S value of a pixel to the provided S value.

  public void setL(double val); Sets the L value of a pixel to the provided L value.

  public void convertHSLtoRGB(); Converts a pixel’s HSL fields to corresponding RGB values.

  public void convertRGBtoHSL(); Converts a pixel’s RGB fields to corresponding HSL values.

 public String toString(); Prints a String in the format “R G B A” representing 4 values of a pixel.

  public String toStringRGB(); Prints a String in the format “R G B” representing 3 values of a pixel.

  public String toStringHSL(); Prints a String in the format “H S L” representing 3 values of a pixel.

Pixel class: The pixel class contains 8 fields necessary to make a pixel work with our model implementation. These fields are as follows:
 private int r; This represents the redness of a pixel.
  private int g; This represents the greenness of a pixel.
  private int b; This represents the blueness of a pixel.
  private int a; This represents the alpha of a pixel. Or, how opaque it is or isn’t.
  private double h; This represents the hue of a pixel.
  private double s; This represents the saturation of a pixel.
  private double l; This represents the lightness of a pixel.
  RepresentationConverter delegate; This is an object of the type RepresentationConverter, a utility class, which is used to simplify conversions back and forth between HSL and RGB, and vice versa.
Besides these fields, a pixel has 4 constructors, as follows:
The first constructor takes no parameters and sets all fields to 0, and creates a new RepresentationConverter for the delegate.
The next constructor takes in R,G,B, and A values, checks that they are positive, and sets those to the fields of the class. Then it sets HSL values to 0, creates a new representation converter for delegate, then calls ConvertRGBtoHSL and sets the appropriate HSL values for the provided RGBA values.
The next constructor does the exact same thing but for only RGB values, not RGBA. Here, A is set to 255.
The final constructor takes in HSL values, sets RGB to zero, creates a new RepresentationConverter, then calls ConvertHSLtoRGB to set the appropriate values for RGB. Alpha is also set to 255 here.

RepresentationConverter class: This class is a utility class used to convert a Pixel’s HSL values into RGB values and vice versa. This class has 2 methods:
convertRGBtoHSL which takes in an R, G, and B value all as ints and performs calculations to conver those int H, S, and L values which it returns as doubles.
convertHSLtoRGB which takes in an H, S, and L value all as doubles and performs calculations to conver those int R, G, and B values which it returns as ints.

Project interface: This represents the highest Interface level for a Project in our Collaging tool. This includes methods that do not mutate the project in any way. The interface has a generic type L, representing whichever layer implementation the project is using at the time.
public List<L> getLayer(); This method returns the list of layers contained in the project.

  public int getWidth(); This method returns the width of the project.

  public int getHeight(); This method returns the height of the project.

  public int getMaxValue(); This method returns the max value of the project. Gathered from all pixels in all images in all layers of the project.


ProjectModel interface: This interface extends the Project interface, and represents a more specific interface of what a Project implementation would need to contain to work appropriately with our Collaging tool. The methods are as follows:
 void addImage(String contents, String layer, int x, int y); Adds an image, provided to the method in ppm string form, to the specified layer, and places the top left corner pixel at coordinates x and y.

  String saveProject(); Saves the project into a string representing the whole image, to then be written into a file by the controller.

  void newProject(int width, int height); creates a new project by setting this.x and this.y of the model implementation to the provided width and height.

  void load(String contents); loads a project, taking in a string ppm representing the project as a whole, and sets the elements of the project to this current project.

  void addLayer(String name); adds a new layer to the project.

  String saveImage(); Saves the current project as an image, represented as a string PPM version of the image. Is then converted to any type the user specifies.

  void setFilter(String layer, IFilter filter); Sets the filter of a layer layer to the filter provided as filter.

  public BufferedImage bufferedImage(String imageString); Turns a PPM string representing an image into a BufferedImage version of the same image.

  public BufferedImage bufferedImageARGB(String imageString); Turns a PPM string representing an image into a BufferedImage version of the same image, but with ARGB format, so the alpha value is preserved.

  public String toPPMString(BufferedImage image); Turns a buffered Image back into a ppm string representation of that image.

  public String getLayerName(int i); returns the string of the layer name, at position i in the model’s list of layers.

  public String getFilterName(int i); returns the string of the layer’s filter name, at the layer at position i in the model’s list of layers.

  public List<IImage> getLayersImages(int i); returns the layer’s list of images at the layer at position i in the model’s list of layers.

  public int getImagesHeight(int i, int j); returns an image’s height from the image at position j in the list of images at the layer at position i in the model’s list of layers.

  public int getImagesWidth(int i, int j);  returns an image’s width from the image at position j in the list of images at the layer at position i in the model’s list of layers.

  public int getImagesX(int i, int j);  returns an image’s x from the image at position j in the list of images at the layer at position i in the model’s list of layers.

  public int getImagesY(int i, int j);  returns an image’s y from the image at position j in the list of images at the layer at position i in the model’s list of layers.

  public Map<String, IFilter> getAllFilters(); Returns a map of String, IFilter representing all available filters for use in the project.

  public String toPPMStringRGBA(BufferedImage image); Turns a supplied buffered image into a string representation which contains r,g,b, and alpha values. Not a true ppm representation, but is necessary for png files to work properly.

  public String saveImageRGBA(); Saves the current project as a string representing an image with r,g,b, and a values. Necessary for png files to work properly.


ProjectModelClass class: Represents our implementation of a project, and our model, for the Collaging tool. The ProjectModelClass contains 4 fields as follows:
 private int width; This represents the width of a project.
  private int height; This represents the height of a project.
  private int maxValue; This represents the max alpha value in any pixel contained in a project.
  private List<ILayer> layers; This represents the list of layers contained in a project.

The only method not included in the interface ProjectModel is the private helper method below:
private void addImageSimple(String layername, Image image) This method adds an image of type Image defined above to the specified layer with name layername.



