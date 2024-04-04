import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import controller.TextController;
import controller.TextControllerInterface;
import controller.GUIController;
import model.ProjectModelClass;
import view.CollageView;
import view.GUI;
import view.ViewInterface;

/**
 * The main class for the project.
 */
public class Main {
  /**
   * The main method for the project.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    ProjectModelClass model;
    if (args.length > 0) {
      if (args[0].equals("-file")) {
        String filePath = args[1];
        Readable reader = null;
        try {
          reader = new FileReader(filePath);
          Scanner scan = new Scanner(reader);
          StringBuilder build = new StringBuilder();
          while (scan.hasNextLine()) {
            build.append(scan.nextLine() + " ");
          }
          model = new ProjectModelClass();
          Appendable out = System.out;
          ViewInterface view = new CollageView(model, out);
          Readable in = new StringReader(build.toString());
          TextControllerInterface controller = new TextController(model, view, in);
          controller.start();
        } catch (FileNotFoundException ex) {
          System.out.println("File not found, quitting program...");
        }
      } else if (args[0].equals("-text")) {
        model = new ProjectModelClass();
        Appendable out = System.out;
        ViewInterface view = new CollageView(model, out);
        InputStreamReader in = new InputStreamReader(System.in);
        TextControllerInterface controller = new TextController(model, view, in);
        controller.start();
      } else {
        System.out.println("Invalid command, quitting program...");
      }
    } else {
      model = new ProjectModelClass();
      GUIController controller = new GUIController(model);
      GUI view = new GUI(model, controller);
    }
  }
}