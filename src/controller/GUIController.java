package controller;

import model.ProjectModel;
import model.filters.IFilter;

/**
 * Controller class for the GUI. This class is responsible for handling all user input and
 * communicating with the model.
 */
public class GUIController implements GUIControllerInterface {


  private ProjectModel model;

  /**
   * Constructor for the GUIController. takes in a model of the interface type.
   */
  public GUIController(ProjectModel model) {
    this.model = model;
  }

  /**
   * Controller method to call the model's newProject method.
   */

  public void controllerMakeNewProject(int x, int y) {
    model.newProject(x, y);
  }

  /**
   * Controller method to call the model's load method.
   */
  public void controllerLoadProject(String filePath) {
    model.load(readProject(filePath));
  }

  /**
   * Controller method to call the model's saveProject method.
   */
  public void controllerSaveProject(String filePath) {
    writeProject(filePath, model.saveProject());
  }

  /**
   * Controller method to call the model's saveImage method. Changes per file type the user chooses.
   */
  public void controllerSaveImage(String filePath) {
    if (endingChecker(filePath).equals("png")) {
      writeImage(filePath, model.bufferedImageARGB(model.saveImageRGBA()));
    } else if (endingChecker(filePath).equals("ppm")) {
      writeProject(filePath, model.saveImage());
    } else {
      writeImage(filePath, model.bufferedImage(model.saveImage()));
    }
  }

  /**
   * Controller method to call the model's addLayer method.
   */

  public void controllerAddLayer(String layerName) {
    model.addLayer(layerName);
  }

  /**
   * Controller method to call the model's addImage method. Changes based on the chosen filetype.
   */
  public void controllerAddImage(String filePath, String layerName, int x, int y) {
    if (endingChecker(filePath).equals("png")) {
      model.addImage(model.toPPMStringRGBA(readImage(filePath)), layerName, x, y);
    } else if (endingChecker(filePath).equals("ppm")) {
      model.addImage((readProject(filePath)), layerName, x, y);
    } else {
      model.addImage(model.toPPMString(readImage(filePath)), layerName, x, y);
    }
  }

  /**
   * Controller method to call the model's setFilter method.
   */
  public void controllerSetFilter(String layerName, IFilter filter) {
    model.setFilter(layerName, filter);
  }
}


