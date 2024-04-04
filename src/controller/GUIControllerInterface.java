package controller;

import model.filters.IFilter;

/**
 * Controller Interface for the GUI. This interface is responsible for handling all user input and
 * communicating with the model.
 */

public interface GUIControllerInterface extends IController {

  /**
   * Controller method to call the model's newProject method.
   */
  public void controllerMakeNewProject(int x, int y);

  /**
   * Controller method to call the model's load method.
   */

  public void controllerLoadProject(String filePath);

  /**
   * Controller method to call the model's saveProject method.
   */

  public void controllerSaveProject(String filePath);

  /**
   * Controller method to call the model's saveImage method. Changes per file type the user chooses.
   */

  public void controllerSaveImage(String filePath);

  /**
   * Controller method to call the model's addLayer method.
   */
  public void controllerAddLayer(String layerName);

  /**
   * Controller method to call the model's addImage method. Changes based on the chosen filetype.
   */

  public void controllerAddImage(String filePath, String layerName, int x, int y);

  /**
   * Controller method to call the model's setFilter method.
   */

  public void controllerSetFilter(String layerName, IFilter filter);
}
