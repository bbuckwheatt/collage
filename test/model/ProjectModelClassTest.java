package model;

import org.junit.Test;

import model.filters.BlueFilter;
import model.filters.GreenFilter;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the model of the Collage. Tests base functionality of methods.
 */

public class ProjectModelClassTest {

  ProjectModelClass projectModel = new ProjectModelClass();

  //adds an image to a layer and checks to see that the project has the correct fields after.
  @Test
  public void addImage() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", 11, 11);
    assertEquals(projectModel.getLayer().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getHeight(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getWidth(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getX(), 11);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getY(), 11);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getMaxValue(), 255);
  }

  //adds an image to a negative location and checks that the fields are correct after.
  @Test
  public void addImageNegative() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", -1, 1);
    assertEquals(projectModel.getLayer().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getHeight(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getWidth(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getX(), -1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getY(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getMaxValue(), 255);

  }

  //adds an image to a negative location and checks that the fields are correct after.
  @Test
  public void addImageNegative2() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", -1, -1);
    assertEquals(projectModel.getLayer().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getHeight(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getWidth(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getX(), -1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getY(), -1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getMaxValue(), 255);

  }

  //adds an image to a negative location and checks that the fields are correct after.
  @Test
  public void addImageNegative3() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", 1, -1);
    assertEquals(projectModel.getLayer().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().size(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getHeight(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getWidth(), 2);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getX(), 1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getY(), -1);
    assertEquals(projectModel.getLayer().get(0).getImages().get(0).getMaxValue(), 255);

  }

  //attempts to add an image that does not exist.
  @Test(expected = IllegalArgumentException.class)
  public void addInvalidImage() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("fakeimage.ppm", "Layer-One", 1, -1);
  }

  //saves a blank project.
  @Test
  public void newProject() {
    projectModel.newProject(4, 4);
    projectModel.saveProject();
    assertEquals(projectModel.getLayer().size(), 0);
  }

  //tests that an invalid new project throws an error.

  @Test(expected = IllegalArgumentException.class)
  public void newProjectInvalid2() {
    projectModel.newProject(-4, 4);
    //projectModel.saveProject();
  }

  //tests that an invalid new project throws an error.
  @Test(expected = IllegalArgumentException.class)
  public void newProjectinvalid3() {
    projectModel.newProject(4, -4);
    //projectModel.saveProject("empty.collage");
  }

  //tests that an invalid new project throws an error.
  @Test(expected = IllegalArgumentException.class)
  public void newProjectInvalid4() {
    projectModel.newProject(-1, -1);
    //projectModel.saveProject("empty.collage");
  }

  //tests that an invalid new project throws an error.
  @Test(expected = IllegalArgumentException.class)
  public void newProjectInvalid5() {
    projectModel.newProject(0, 0);
    //projectModel.saveProject("empty.collage");
  }

  //tests that an invalid new project throws an error.
  @Test(expected = IllegalArgumentException.class)
  public void newProjectInvalid6() {
    projectModel.newProject(1, 0);
    //projectModel.saveProject("empty.collage");
  }

  //tests that an invalid new project throws an error.
  @Test(expected = IllegalArgumentException.class)
  public void newProjectInvalid7() {
    projectModel.newProject(0, 1);
    //projectModel.saveProject("empty.collage");
  }

  //attempts to load a project string that is not a project at all.
  @Test(expected = IllegalArgumentException.class)
  public void load() {
    projectModel.load("fakefile.collage");
  }

  //tests multiple layers.
  @Test
  public void addLayer() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("1");
    projectModel.addLayer("2");
    assertEquals(projectModel.getLayer().size(), 2);
  }

  //tests that no layers can be made with duplicate names.
  @Test
  public void addLayer2() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("2");
    projectModel.addLayer("2");
    assertEquals(projectModel.getLayer().size(), 2);
    assertEquals(projectModel.getLayer().get(0).getName(), "2");
    assertEquals(projectModel.getLayer().get(1).getName(), "2(1)");
  }

  //tests saving an image.
  @Test
  public void saveImage() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", 1, 1);
    assertEquals(projectModel.saveImage(), "P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 255 0 0 0 255 0 0 0 0 \n" +
            "0 0 0 0 0 255 0 0 255 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n");
    assertEquals(projectModel.saveProject(), "C1\n" +
            "4 4\n" +
            "255\n" +
            "Layer-One NormalFilter\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "255 0 0 255\n" +
            "0 255 0 255\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "0 0 255 255\n" +
            "0 0 255 255\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n" +
            "0 0 0 0\n");

    ProjectModelClass image = new ProjectModelClass();
    image.newProject(4, 4);
    image.addLayer("1");
    image.addImage("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 0 255 0 0 0 255 0 0 0 0 \n" +
            "0 0 0 0 0 255 0 0 255 0 0 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n", "1", 0, 0);
    image.saveProject();

    assertEquals(image.getWidth(), projectModel.getWidth());
    assertEquals(image.getHeight(), projectModel.getHeight());
    assertEquals(image.getMaxValue(), projectModel.getMaxValue());
  }

  //tests saving an image.
  @Test
  public void saveImage2() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", 1, 1);
    projectModel.addLayer("Layer-Two");
    projectModel.setFilter("Layer-Two", new BlueFilter());
    projectModel.addImage("P3\n" +
            "2 3\n" +
            "255\n" +
            "255 255 255 0 0 0\n" +
            "255 255 255 0 0 0\n" +
            "255 255 255 0 0 0", "Layer-Two", 0, 0);
    projectModel.addLayer("Layer-Three");
    projectModel.setFilter("Layer-Three", new GreenFilter());
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-Three", 2, 2);
    projectModel.saveImage();
    projectModel.saveProject();

    ProjectModelClass image = new ProjectModelClass();
    image.newProject(4, 4);
    image.addLayer("1");
    image.addImage("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 255 0 0 0 0 255 0 0 0 0 \n" +
            "0 0 255 0 0 0 0 0 0 0 255 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n", "1", 0, 0);

    assertEquals(image.getWidth(), projectModel.getWidth());
    assertEquals(image.getHeight(), projectModel.getHeight());
    assertEquals(image.getMaxValue(), projectModel.getMaxValue());
  }

  //tests loading and saving functionality, seeing that the fields remain the same across load/save.
  @Test
  public void saveProjectLoadProjectSaveProject() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", 1, 1);
    ProjectModelClass loaded = new ProjectModelClass();
    loaded.load(projectModel.saveProject());
    assertEquals(projectModel.saveProject(), loaded.saveProject());

    assertEquals(loaded.getLayer().size(), projectModel.getLayer().size());
    assertEquals(loaded.getLayer().get(0).getName(), projectModel.getLayer().get(0).getName());
    assertEquals(loaded.getLayer().get(0).getFilter().getName(),
            projectModel.getLayer().get(0).getFilter().getName());
    assertEquals(loaded.getLayer().get(0).getImages().size(),
            projectModel.getLayer().get(0).getImages().size());
  }
  //  @Test
  //  public void test(){
  //    projectModel.newProject(500, 500);
  //    projectModel.addLayer("layer1");
  //    projectModel.addImage();
  //    String test = projectModel.saveImage();
  //    System.out.println(test);
  //
  //  }

  @Test
  public void testytesty() {
    projectModel.newProject(4, 4);
    projectModel.addLayer("Layer-One");
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-One", 1, 1);
    projectModel.addLayer("Layer-Two");
    projectModel.setFilter("Layer-Two", new BlueFilter());
    projectModel.addImage("P3\n" +
            "2 3\n" +
            "255\n" +
            "255 255 255 0 0 0\n" +
            "255 255 255 0 0 0\n" +
            "255 255 255 0 0 0", "Layer-Two", 0, 0);
    projectModel.addLayer("Layer-Three");
    projectModel.setFilter("Layer-Three", new GreenFilter());
    projectModel.addImage("P3\n" +
            "2 2\n" +
            "255\n" +
            "255 0 0 0 255 0\n" +
            "0 0 255 0 0 255", "Layer-Three", 2, 2);
    projectModel.saveImage();
    projectModel.saveProject();

    ProjectModelClass image = new ProjectModelClass();
    image.newProject(4, 4);
    image.addLayer("1");
    image.addImage("P3\n" +
            "4 4\n" +
            "255\n" +
            "0 0 255 0 0 0 0 0 0 0 0 0 \n" +
            "0 0 255 0 0 0 0 255 0 0 0 0 \n" +
            "0 0 255 0 0 0 0 0 0 0 255 0 \n" +
            "0 0 0 0 0 0 0 0 0 0 0 0 \n", "1", 0, 0);

    assertEquals(projectModel.saveImage(),
            projectModel.toPPMString(projectModel.bufferedImage(projectModel.saveImage())));
  }

}