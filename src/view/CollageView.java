package view;

import java.io.IOException;

import model.ProjectModel;

/**
 * Represents a view of the project. Is used for the text view of the collager.
 */
public class CollageView implements ViewInterface {
  private final ProjectModel model;
  private final Appendable destination;

  /**
   * Constructs a view of the project.
   *
   * @param model       the desired project model to display
   * @param destination the desired output destination for this view
   * @throws IllegalArgumentException if the model or destination is null
   */
  public CollageView(ProjectModel model, Appendable destination) throws IllegalArgumentException {
    if (model == null || destination == null) {
      throw new IllegalArgumentException("Either the model or destination are null!");
    }

    this.model = model;
    this.destination = destination;
  }

  /**
   * Prints a text view to the appendable of what the project currently looks like.
   */

  public void renderProject() {
    renderMessage("Project Height: "
            + this.model.getHeight()
            + " Project Width: "
            + this.model.getWidth()
            + " Project Max Value: "
            + this.model.getMaxValue()
            + "\n");
    //for (Layer layer : this.model.getLayer()) {
    for (int i = 0; i < this.model.getLayer().size(); i++) {
      renderMessage("Layer Name: "
              + this.model.getLayerName(i)
              + " Layer Filter: "
              + this.model.getFilterName(i)
              + "\n"
              + "Number of Images in Layer: "
              + this.model.getLayersImages(i).size()
              + "\n");
      for (int j = 0; j < this.model.getLayersImages(i).size(); j++) {
        renderMessage("Image Size: "
                + this.model.getImagesHeight(i, j)
                + " by "
                + this.model.getImagesWidth(i, j)
                + "\n Starting at ("
                + this.model.getImagesX(i, j)
                + ","
                + this.model.getImagesY(i, j)
                + ")\n");
      }
    }

  }

  /**
   * renders the message to the destination.
   *
   * @param message the message to be rendered
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      destination.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to append to destination!");
    }
  }
}
