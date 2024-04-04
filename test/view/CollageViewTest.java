package view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.filters.Normal;
import model.filters.RedFilter;
import model.ProjectModel;
import model.ProjectModelClass;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the view of a Collage. Tests the output functionality.
 */

public class CollageViewTest {
  ProjectModel project = new ProjectModelClass();
  Appendable out = new StringBuilder();
  ViewInterface appendableView = new CollageView(project, out);

  //sets up variables for testing.

  @Before
  public void setUp() {
    project.newProject(10, 10);
    project.addLayer("layer1");
    project.addLayer("layer2");
    project.addImage("2x2.ppm", "layer1", 0, 0);
    project.setFilter("layer1", new RedFilter());
    project.setFilter("layer2", new Normal());
  }

  //Tests the renderProject method in the view.

  @Test
  public void renderProject() throws IOException {
    String o = "Project Height: 10 Project Width: 10 Project Max Value 255\n" +
            "Layer Name layer1 Layer Filter RedFilter\n" +
            "Number of Images in Layer: 1\n" +
            "Image Size: 2 by 2\n" +
            " Starting at (0,0)\nLayer Name layer2 Layer Filter NormalFilter\n" +
            "Number of Images in Layer: 0";
    appendableView.renderMessage(o);

    Appendable out2 = new StringBuilder();
    out2.append(o);
    assertTrue(out.toString().equals(out2.toString()));

  }

  //tests the renderMessage functionality.
  @Test
  public void renderMessage() throws IOException {
    appendableView.renderMessage("Hello");

    Appendable out2 = new StringBuilder();
    out2.append("Hello");
    assertTrue(out.toString().equals(out2.toString()));

  }


}