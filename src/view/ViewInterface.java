package view;

/**
 * Interface for the clients perspective of the project model. Dictates functionality for both
 * gui view and text view.
 */
public interface ViewInterface {
  /**
   * Renders the message to the destination.
   *
   * @param message the message to be rendered
   */
  void renderMessage(String message);

  /**
   * Renders the project to the destination.
   */
  public void renderProject();

}
