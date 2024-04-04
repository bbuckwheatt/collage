package controller;

import java.util.Map;
import java.util.Scanner;

import model.ProjectModel;
import model.filters.IFilter;
import view.ViewInterface;

/**
 * Represents a text based controller for the project. is used for scripts as well.
 */
public class TextController implements TextControllerInterface {
  private final ProjectModel model;
  private final ViewInterface view;
  private final Readable input;
  private boolean projectSelected;

  /**
   * Constructs a controller for the project.
   *
   * @param model the desired project model to control
   * @param view  the desired view to control
   * @param input the desired input source for this controller
   * @throws IllegalArgumentException if the model, view, or input is null
   */
  public TextController(ProjectModel model, ViewInterface view, Readable input) {
    if (model == null || input == null) {
      throw new IllegalArgumentException("Either the model, view, or input are null!");
    }
    this.model = model;
    this.view = view;
    this.input = input;
    this.projectSelected = false;
  }

  /**
   * Starts the controller. Allows for running the Collaging tool with a text-based controller and
   * view.
   * Users enter commands and receive text prompts to represent what they are changing.
   */
  @Override
  public void start() {
    Scanner scanner = new Scanner(input);
    //First loop represents before a project is selected to work on,
    // whether that is a new or loaded project. Only accepts load, new, and quit.
    while (!projectSelected) {
      //switch case to check whether input is load command or new project command, or quit.
      //if load command, load project's data
      //if new project command, create new project
      //if quit, quits the program
      //if none of above, invalid command message
      //if no entry at all, time out.
      if (!scanner.hasNext()) {
        timeOut();
        throw new IllegalStateException("No more inputs.");
      }
      view.renderMessage("Please enter a command: load-project or new-project\n");
      String command = scanner.next();
      switch (command) {
        case "load-project":

          String filepath = scanner.next();
          //try to run load on filepath
          //if load fails, print error message
          loadProject(filepath);
          break;
        case "new-project":
          int[] widthHeight = newProjectHelper(scanner);
          //try to start with numbers
          newProjectHelper2(widthHeight);
          break;
        case "quit":
          view.renderMessage("Quitting program.");
          return;
        default:
          view.renderMessage("Invalid command. Try again.\n");
          break;
      }
    }
    while (scanner.hasNext()) {
      String command = scanner.next();
      switch (command) {
        case "add-layer":
          String layerName = scanner.next();
          model.addLayer(layerName);
          view.renderMessage("Adding layer: " + layerName + "\n");
          view.renderProject();
          break;
        case "add-image-to-layer":
          String layertoadd = scanner.next();
          String filepath = scanner.next();
          int x = scanner.nextInt();
          int y = scanner.nextInt();
          if (endingChecker(filepath).equals("png")) {
            model.addImage(model.toPPMStringRGBA(readImage(filepath)), layertoadd, x, y);
          } else if (endingChecker(filepath).equals("ppm")) {
            model.addImage((readProject(filepath)), layertoadd, x, y);
          } else {
            model.addImage(model.toPPMString(readImage(filepath)), layertoadd, x, y);
          }
          view.renderMessage("Adding image " + filepath + " to " + layertoadd
                  + " at (" + x + "," + y + ")\n");
          break;
        case "set-filter":
          String layername = scanner.next();
          String filtername = scanner.next();
          try {
            Map<String, IFilter> temp = model.getAllFilters();
            for (Map.Entry<String, IFilter> filter : temp.entrySet()) {
              if (filter.getKey().equalsIgnoreCase(filtername)) {
                model.setFilter(layername, filter.getValue());
                view.renderProject();
              }
            }
          } catch (IllegalArgumentException e) {
            view.renderMessage("Invalid filter. Try again.\n");
          }
          break;
        case "save-project":
          String savepath = scanner.next();
          writeProject(savepath, model.saveProject());
          view.renderMessage("Saving project.\n");
          break;
        case "save-image":
          String saveimagepath = scanner.next();
          if (endingChecker(saveimagepath).equals("png")) {
            writeImage(saveimagepath, model.bufferedImageARGB(model.saveImageRGBA()));
          } else if (endingChecker(saveimagepath).equals("ppm")) {
            writeProject(saveimagepath, model.saveImage());
          } else {
            writeImage(saveimagepath, model.bufferedImage(model.saveImage()));
          }
          view.renderMessage("Saving project as an image.\n");
          break;
        case "quit":
          view.renderMessage("Quitting program.");
          return;
        case "load-project":
          String filepath2 = scanner.next();
          loadProject(filepath2);
          break;
        case "new-project":
          int[] widthHeight = newProjectHelper(scanner);
          //try to start with numbers
          newProjectHelper2(widthHeight);
          break;
        default:
          view.renderMessage("Invalid command. Try again.\n");
          break;
      }
    }
    if (!scanner.hasNext()) {
      timeOut();
      throw new IllegalStateException("No more inputs.");
    }
  }

  /**
   * Helper method 1 for the new project functionality as used in the controller.
   *
   * @param scanner scanner is the scanner which is used in the start method,
   *                and is scanning for input.
   * @return returns an array of ints with 2 values in it, representing height and width.
   */

  private int[] newProjectHelper(Scanner scanner) {
    int height = 0;
    int width = 0;
    while (scanner.hasNext() && width <= 0) {
      String input = scanner.next();
      try {
        width = Integer.parseInt(input);
        if (width <= 0) {
          try {
            view.renderMessage("Invalid input. Try again.\n");
          } catch (IllegalStateException e) {
            throw new IllegalStateException("Could not append");
          }
        }
      } catch (NumberFormatException nfe) {
        try {
          view.renderMessage("Invalid input. Try again.\n");
        } catch (IllegalStateException e) {
          throw new IllegalStateException("Could not append");
        }
      }

    }
    //trying to get 2nd number
    while (scanner.hasNext() && height <= 0) {
      String input = scanner.next();
      try {
        height = Integer.parseInt(input);
        if (height <= 0) {
          try {
            view.renderMessage("Invalid input. Try again.\n");
          } catch (IllegalStateException e) {
            throw new IllegalStateException("Could not append");
          }
        }
      } catch (NumberFormatException nfe) {
        try {
          view.renderMessage("Invalid input. Try again.\n");
        } catch (IllegalStateException e) {
          throw new IllegalStateException("Could not append");
        }
      }
    }
    int[] widthHeight = new int[2];
    widthHeight[0] = width;
    widthHeight[1] = height;
    return widthHeight;
  }

  /**
   * Helper method 2 used in the new project functionality as used in the controller.
   *
   * @param widthHeight an array of 2 values representing the width and height of the project.
   */

  private void newProjectHelper2(int[] widthHeight) {
    //try to start with numbers
    try {
      if (widthHeight[0] > 0 && widthHeight[1] > 0) {
        view.renderMessage("Creating project " + widthHeight[0] + " by " + widthHeight[1] + "\n");
        model.newProject(widthHeight[0], widthHeight[1]);
        view.renderProject();
        this.projectSelected = true;
      }
    } catch (IllegalArgumentException e) {
      //if numbers are invalid, restart entering width/height
      try {
        view.renderMessage("Invalid height/width. Try again.\n");
      } catch (IllegalStateException ex) {
        throw new IllegalStateException("Could not append");
      }
    }
  }

  /**
   * If there are no more arguments in the input, the program displays a method and times out.
   * (shuts down).
   */
  private void timeOut() {
    try {
      view.renderMessage("No more inputs. Shutting down.");
    } catch (IllegalStateException exec) {
      throw new IllegalStateException("Could not append.");
    }
  }

  /**
   * Helper for the loadProject functionality, as applied in the controller.
   *
   * @param path path is the filepath to be supplied to the read method.
   */

  private void loadProject(String path) {
    try {
      model.load(readProject(path));
      view.renderMessage("Loading project " + path + ".\n");
      view.renderProject();
      this.projectSelected = true;
    } catch (IllegalArgumentException e) {
      view.renderMessage("Invalid filepath. Try again.\n");
    }
  }
}

