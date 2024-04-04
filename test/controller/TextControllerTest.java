package controller;

import org.junit.Test;

import java.io.StringReader;

import model.ProjectModel;
import model.ProjectModelClass;
import view.CollageView;
import view.ViewInterface;

import static org.junit.Assert.assertEquals;

/**
 * Tests the controller method for a Collage. Focuses on user input and functionality of the Model
 * is tested in the model test class.
 */

public class TextControllerTest {

  ProjectModel defaultModel = new ProjectModelClass();
  Appendable out = new StringBuilder();
  Readable in;
  ViewInterface defaultView = new CollageView(defaultModel, out);

  //tests immediately quitting.
  @Test
  public void immediateQuit1() {
    in = new StringReader("quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project" +
            "\nQuitting program.", out.toString());
  }

  //tests immediately quitting after entering an invalid input.
  @Test
  public void immediateQuitAfterInvalid() {
    in = new StringReader("weaewf quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid command. Try again.\n"
            + "Please enter a command: load-project or new-project\n"
            + "Quitting program.", out.toString());
  }

  //tests failing to load a project.
  @Test
  public void loadProjectFail() {
    in = new StringReader("load-project project.collage quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid filepath. Try again.\n"
            + "Please enter a command: load-project or new-project\n"
            + "Quitting program.", out.toString());
  }

  //tests successfully loading a project.
  @Test
  public void loadProject() {
    in = new StringReader("load-project joe.collage quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Loading project joe.collage.\n"
            + "Project Height: 50 Project Width: 50 Project Max Value: 255\n" +
            "Layer Name: one Layer Filter: LumaBrighten\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 50 by 50\n" +
            " Starting at (0,0)\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 50 by 50\n" +
            " Starting at (0,0)\n"
            + "Quitting program.", out.toString());
  }

  //tests timing out.
  @Test(expected = IllegalStateException.class)
  public void timeOut() {
    in = new StringReader("");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
  }

  //tests timing out after starting.
  @Test(expected = IllegalStateException.class)
  public void startTimeOut() {
    in = new StringReader("new-project 10 10");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
  }

  //tests invalid input, then a valid new project, then quitting.
  @Test
  public void immediateQuitAfterInvalidThenNew() {
    in = new StringReader("weaewf new-project 100 100 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid command. Try again.\n"
            + "Please enter a command: load-project or new-project\n"
            + "Creating project 100 by 100\n"
            + "Project Height: 100 Project Width: 100 Project Max Value: 0\n"
            + "Quitting program.", out.toString());
  }

  //tests an invalid new project, then a valid one, then quitting.
  @Test
  public void invalidNewProject() {
    in = new StringReader("new-project -100 100 100 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid input. Try again.\n"
            + "Creating project 100 by 100\n"
            + "Project Height: 100 Project Width: 100 Project Max Value: 0\n"
            + "Quitting program.", out.toString());
  }

  //tests an invalid new project then a valid new project.
  @Test
  public void invalidNewProject2() {
    in = new StringReader("new-project -100 -100 100 100 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid input. Try again.\n"
            + "Invalid input. Try again.\n"
            + "Creating project 100 by 100\n"
            + "Project Height: 100 Project Width: 100 Project Max Value: 0\n"
            + "Quitting program.", out.toString());
  }

  //tests an invalid new project then a valid new project.
  @Test
  public void invalidNewProjectZero() {
    in = new StringReader("new-project 0 100 100 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid input. Try again.\n"
            + "Creating project 100 by 100\n"
            + "Project Height: 100 Project Width: 100 Project Max Value: 0\n"
            + "Quitting program.", out.toString());
  }

  //tests an invalid new project then a new invalid new project.
  @Test
  public void immediateQuitAfterInvalidThenNewInvalid() {
    in = new StringReader("weaewf new-project 100 qu wea 100 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid command. Try again.\n"
            + "Please enter a command: load-project or new-project\n"
            + "Invalid input. Try again.\n"
            + "Invalid input. Try again.\n" +
            "Creating project 100 by 100\n" +
            "Project Height: 100 Project Width: 100 Project Max Value: 0\n"
            + "Quitting program.", out.toString());
  }

  //tests an invalid new project then a valid new project.
  @Test
  public void immediateQuitAfterInvalidThenNewInvalid2() {
    in = new StringReader("weaewf new-project 100 dwa daw 100 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid command. Try again.\n"
            + "Please enter a command: load-project or new-project\n"
            + "Invalid input. Try again.\n"
            + "Invalid input. Try again.\n"
            + "Creating project 100 by 100\n"
            + "Project Height: 100 Project Width: 100 Project Max Value: 0\n"
            + "Quitting program.", out.toString());
  }

  //tests an invalid load project.
  @Test
  public void loadProjectInvalid() {
    in = new StringReader("load-project project.collage quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid filepath. Try again.\n"
            + "Please enter a command: load-project or new-project\n"
            + "Quitting program.", out.toString());
  }

  //tests an invalid load project then a valid new project.
  @Test
  public void loadProjectInvalidThenNew() {
    in = new StringReader("load-project project.collage new-project 100 100 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n"
            + "Invalid filepath. Try again.\n"
            + "Please enter a command: load-project or new-project\n"
            + "Creating project 100 by 100\n"
            + "Project Height: 100 Project Width: 100 Project Max Value: 0\n"
            + "Quitting program.", out.toString());
  }

  //tests adding a layer.

  @Test
  public void addLayer() {
    in = new StringReader("new-project 50 50 add-layer one quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 50 by 50\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Adding layer: one\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Quitting program.", out.toString());
  }

  //tests adding 2 layers.
  @Test
  public void addLayer2() {
    in = new StringReader("new-project 50 50 add-layer one add-layer two quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 50 by 50\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Adding layer: one\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: two\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Quitting program.", out.toString());
  }

  //tests starting a new project while in an existing project.
  @Test
  public void newProjectExistingProject() {
    in = new StringReader("new-project 50 50 add-layer one add-layer two new-project 2 2 quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 50 by 50\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Adding layer: one\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: two\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Creating project 2 by 2\n" +
            "Project Height: 2 Project Width: 2 Project Max Value: 0\n" +
            "Quitting program.", out.toString());
  }

  //tests loading a project in the middle of a different one.
  @Test
  public void loadProjectExistingProject() {
    in = new StringReader("new-project 50 50 add-layer one add-layer two " +
            "load-project joe.collage quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 50 by 50\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Adding layer: one\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: two\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Loading project joe.collage.\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 255\n" +
            "Layer Name: one Layer Filter: LumaBrighten\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 50 by 50\n" +
            " Starting at (0,0)\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 50 by 50\n" +
            " Starting at (0,0)\n" +
            "Quitting program.", out.toString());
  }


  @Test
  public void loadProjectExistingProjec2t() {
    in = new StringReader("new-project 50 50 add-layer one add-layer two " +
            "load-project joe.collage save-image iufghaoisf quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 50 by 50\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Adding layer: one\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: two\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Loading project joe.collage.\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 255\n" +
            "Layer Name: one Layer Filter: LumaBrighten\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 50 by 50\n" +
            " Starting at (0,0)\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 50 by 50\n" +
            " Starting at (0,0)\n" +
            "Saving project as an image.\n" +
            "Quitting program.", out.toString());
  }

  //tests adding an image to a layer, setting filters, and saving the project as a project.
  @Test
  public void addImageSetFilterSaveProject() {
    in = new StringReader("new-project 50 50 add-layer one add-layer two set-filter one" +
            " lumabrighten add-image-to-layer one mohg.ppm 0 0 save-project joe.collage quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 50 by 50\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Adding layer: one\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: two\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: LumaBrighten\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding image mohg.ppm to one at (0,0)\n" +
            "Saving project.\n" +
            "Quitting program.", out.toString());
  }

  //tests adding an image to a layer, setting filters, and saving the project as an image.
  @Test
  public void addImageSetFilterSaveImage() {
    in = new StringReader("new-project 50 50 add-layer one add-layer two set-filter one" +
            " lumabrighten add-image-to-layer one mohg.ppm 0 0 set-filter one valuebrighten " +
            "save-image joe.ppm quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 50 by 50\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Adding layer: one\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: two\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 0\n" +
            "Layer Name: one Layer Filter: LumaBrighten\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding image mohg.ppm to one at (0,0)\n" +
            "Project Height: 50 Project Width: 50 Project Max Value: 255\n" +
            "Layer Name: one Layer Filter: ValueBrighten\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (0,0)\n" +
            "Layer Name: two Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Saving project as an image.\n" +
            "Quitting program.", out.toString());
  }

  //tests the methods written in our script.
  @Test
  public void script() {
    in = new StringReader("new-project 500 500\n" +
            "add-layer layer1\n" +
            "add-layer layer2\n" +
            "add-layer layer3\n" +
            "add-image-to-layer layer1 mohg.ppm -100 -50\n" +
            "add-image-to-layer layer2 mohg.ppm 0 50\n" +
            "add-image-to-layer layer3 mohg.ppm 200 50\n" +
            "set-filter layer1 normalfilter\n" +
            "set-filter layer2 redfilter\n" +
            "set-filter layer3 valuebrighten\n" +
            "save-project finalimage.collage\n" +
            "save-image finalimage.ppm " +
            "quit");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 500 by 500\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Adding layer: layer1\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: layer2\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: layer2 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: layer3\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: layer2 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: layer3 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding image mohg.ppm to layer1 at (-100,-50)\n" +
            "Adding image mohg.ppm to layer2 at (0,50)\n" +
            "Adding image mohg.ppm to layer3 at (200,50)\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 255\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (-100,-50)\n" +
            "Layer Name: layer2 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (0,50)\n" +
            "Layer Name: layer3 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (200,50)\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 255\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (-100,-50)\n" +
            "Layer Name: layer2 Layer Filter: RedFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (0,50)\n" +
            "Layer Name: layer3 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (200,50)\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 255\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (-100,-50)\n" +
            "Layer Name: layer2 Layer Filter: RedFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (0,50)\n" +
            "Layer Name: layer3 Layer Filter: ValueBrighten\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (200,50)\n" +
            "Saving project.\n" +
            "Saving project as an image.\n" +
            "Quitting program.", out.toString());
  }
  //  @Test
  //  public void generateImage() {
  //    in = new StringReader("new-project 500 500 add-layer layer1 add-image-to-layer layer1 " +
  //            "mohg.ppm 0 0 save-image newtest.ppm quit");
  //    ControllerInterface defaultController = new CollageController(defaultModel, defaultView, in)
  //    defaultController.start();
  //  }
  //
  //  @Test
  //  public void readProperly() {
  //    in = new StringReader("");
  //    ControllerInterface defaultController = new CollageController(defaultModel, defaultView, in)
  //    System.out.println(defaultController.read("mohg.ppm"));
  //  }
  //
  //  @Test
  //  public void writeProperly() {
  //    in = new StringReader("");
  //    ControllerInterface defaultController = new CollageController(defaultModel, defaultView, in)
  //    defaultController.write("testwriting.ppm", defaultController.read("mohg.ppm"));
  //  }

  @Test
  public void scriptRed() {
    in = new StringReader("  new-project 1000 1000\n" +
            "  add-layer layer1\n" +
            "  add-image-to-layer layer1 mohg.ppm 0 0\n" +
            "  set-filter layer1 redfilter\n" +
            "  save-image redppm.ppm\n" +
            "new-project 1000 1000\n" +
            "  add-layer layer1\n" +
            "  add-image-to-layer layer1 mohg.jpg 0 0\n" +
            "  set-filter layer1 redfilter\n" +
            "  save-image redjpg.jpg\n" +
            "new-project 1000 1000\n" +
            "  add-layer layer1\n" +
            "  add-image-to-layer layer1 mohg.png 0 0\n" +
            "  set-filter layer1 redfilter\n" +
            "  save-image redpng.png");
    TextControllerInterface defaultController = new TextController(defaultModel, defaultView, in);
    defaultController.start();
    assertEquals("Please enter a command: load-project or new-project\n" +
            "Creating project 500 by 500\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Adding layer: layer1\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: layer2\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: layer2 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding layer: layer3\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 0\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: layer2 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Layer Name: layer3 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 0\n" +
            "Adding image mohg.ppm to layer1 at (-100,-50)\n" +
            "Adding image mohg.ppm to layer2 at (0,50)\n" +
            "Adding image mohg.ppm to layer3 at (200,50)\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 255\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (-100,-50)\n" +
            "Layer Name: layer2 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (0,50)\n" +
            "Layer Name: layer3 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (200,50)\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 255\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (-100,-50)\n" +
            "Layer Name: layer2 Layer Filter: RedFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (0,50)\n" +
            "Layer Name: layer3 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (200,50)\n" +
            "Project Height: 500 Project Width: 500 Project Max Value: 255\n" +
            "Layer Name: layer1 Layer Filter: NormalFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (-100,-50)\n" +
            "Layer Name: layer2 Layer Filter: RedFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (0,50)\n" +
            "Layer Name: layer3 Layer Filter: ValueBrighten\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 400 by 400\n" +
            " Starting at (200,50)\n" +
            "Saving project.\n" +
            "Saving project as an image.\n" +
            "Quitting program.", out.toString());
  }
}