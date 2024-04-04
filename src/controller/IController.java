package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Controller interface. dictates what methods are necessary for a controller implementation.
 */

public interface IController {

  /**
   * Reads a project file and returns the contents as a string.
   *
   * @param filename the name of the file to read
   * @return the contents of the file as a string
   */
  public default String readProject(String filename) {
    Scanner sc;
    try {
      //file reader better abstraction, filereader is a readable
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    return builder.toString();
  }

  /**
   * New method to read specifically images, changed from the previous simple read method to work
   * with multiple image types by reading images in as BufferedImages as a first step.
   *
   * @param filepath the filepath to the image to be read. Immediately turned into a File object.
   * @return returns the result of the ImageIO's read method,
   *          which is the image turned into a BufferedImage.
   */
  public default BufferedImage readImage(String filepath) {
    try {
      //file reader better abstraction, filereader is a readable
      BufferedImage image = ImageIO.read(new File(filepath));
      return image;
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + filepath + " not found!");
    }
  }

  /**
   * Writes a string to a file. Used in saving a project file.
   *
   * @param filename the name of the file to write to.
   * @param output   the string to write to the file.
   */
  public default void writeProject(String filename, String output) {
    try {
      FileWriter writer = new FileWriter(filename);
      writer.write(output);
      writer.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not write");

    }
  }

  /**
   * New method to write the project being worked on as an image file of the user's chosing.
   *
   * @param filename this is the path of the image being written, with the file name and file type
   *                 "suffix" as the last elements of the string.
   * @param image    the image being written.
   */

  public default void writeImage(String filename, BufferedImage image) {
    String ending = "";
    if (filename.contains(".")) {
      for (int i = filename.length(); i > 0; i--) {
        String current = String.valueOf(filename.charAt(i - 1));
        if (current.equals(".")) {
          break;
        }
        ending = filename.charAt(i - 1) + ending;
      }
    } else {
      throw new IllegalArgumentException("Invalid entry, please enter a filetype.");
    }
    try {
      File file = new File(filename);
      ImageIO.write(image, ending, file);

    } catch (IOException e) {
      throw new IllegalStateException("Could not write");
    }
  }

  /**
   * Checks the ending, or filetype, of a given filepath.
   *
   * @param filepath given file path to check.
   * @return return the file type ending.
   */
  default String endingChecker(String filepath) {
    String ending = "";
    if (filepath.contains(".")) {
      for (int i = filepath.length(); i > 0; i--) {
        String current = String.valueOf(filepath.charAt(i - 1));
        if (current.equals(".")) {
          break;
        }
        ending = filepath.charAt(i - 1) + ending;
      }
    } else {
      throw new IllegalArgumentException("Invalid entry, please enter a filetype.");
    }
    return ending;
  }
}
