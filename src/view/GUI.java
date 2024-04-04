package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.Map;

import controller.GUIControllerInterface;
import model.ProjectModel;
import model.filters.IFilter;

/**
 * This class represents the GUI for the image editor.
 * It is a much more interactive view for the user to use.
 */
public class GUI extends JFrame implements ViewInterface {

  private JPanel layersPanel;
  private int layerCount = 0;
  private ButtonGroup layerGroup;
  private final ProjectModel model;

  private JScrollPane imagePanel;

  private GUIControllerInterface controller;
  private ImagePanel imageCanvas;

  /**
   * Constructor for the GUI class.
   * It initializes the GUI and sets up the components.
   */
  public GUI(ProjectModel model, GUIControllerInterface controller) {
    super("Collager");

    this.model = model;

    this.controller = controller;


    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");


    JMenuItem loadProjectMenuItem = new JMenuItem("Load Project");
    JMenuItem saveProjectMenuItem = new JMenuItem("Save Project");
    JMenuItem saveImageMenuItem = new JMenuItem("Save Image");
    JMenuItem newProjectMenuItem = new JMenuItem("New Project");

    fileMenu.add(newProjectMenuItem);
    fileMenu.add(loadProjectMenuItem);
    fileMenu.add(saveProjectMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(saveImageMenuItem);

    menuBar.add(fileMenu);

    layersPanel = new JPanel();
    layersPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));

    layerGroup = new ButtonGroup();

    JButton addLayerButton = new JButton("Add Layer");
    JButton addImageButton = new JButton("Add Image");
    JButton addFilterButton = new JButton("Set Layer Filter");


    layersPanel.add(addLayerButton);
    layersPanel.add(addImageButton);
    layersPanel.add(addFilterButton);


    setLayout(new BorderLayout());


    add(menuBar, BorderLayout.NORTH);
    add(layersPanel, BorderLayout.WEST);

    imageCanvas = new ImagePanel();
    imagePanel = new JScrollPane(imageCanvas);
    add(imagePanel, BorderLayout.CENTER);


    //adding listeners to buttons

    newProjectMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String xStr = JOptionPane.showInputDialog(GUI.this,
                "Enter x value:");
        if (xStr == null) {
          return;
        }
        String yStr = JOptionPane.showInputDialog(GUI.this,
                "Enter y value:");

        try {
          int x = Integer.parseInt(xStr);
          int y = Integer.parseInt(yStr);


          controller.controllerMakeNewProject(x, y);
          renderProject();


          //need to catch if user enters negative values too
        } catch (NumberFormatException ex) {
          renderMessage("Invalid input. Please enter integer values for x and y.");
        }
      }
    });

    //if there's no project
    addImageButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (model.getWidth() == 0) {
          renderMessage("Please create a new project first.");
          return;
        }
        if (model.getLayer().size() == 0) {
          renderMessage("Please add a layer first.");
          return;
        }
        if (layerGroup.getSelection() == null) {
          renderMessage("Please select a layer first.");
          return;
        }
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(GUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          String filePath = selectedFile.getAbsolutePath();
          // Do something with the filePath
          System.out.println("Selected file: " + filePath);
          String layerName = GUI.this.layerGroup.getSelection().getActionCommand();

          //looks long, but its just getting x and y
          int x = -1;
          int y = -1;

          String xStr = JOptionPane.showInputDialog(GUI.this,
                  "Enter x value:");
          if (xStr == null) {
            return;
          }
          String yStr = JOptionPane.showInputDialog(GUI.this,
                  "Enter y value:");

          try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);
          } catch (NumberFormatException ex) {
            renderMessage("Invalid input. Please enter integer values for x and y.");
          }

          //want the button to do nothing if inputs aren't valid
          if (x == -1 || y == -1) {
            return;
          }

          controller.controllerAddImage(filePath, layerName, x, y);
          renderProject();
        }
      }
    });

    saveImageMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (model.getWidth() == 0) {
          renderMessage("Please create a new project first.");
          return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(GUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String filePath = file.getAbsolutePath();
          controller.controllerSaveImage(filePath);
        }
      }
    });

    addLayerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (model.getWidth() == 0) {
          renderMessage("Please create a new project first.");
          return;
        }
        GUI.this.layerCount++;
        JRadioButton layerBox = new JRadioButton("Layer-" + GUI.this.layerCount);
        layerBox.setActionCommand("Layer-" + GUI.this.layerCount);
        GUI.this.layersPanel.add(layerBox, GUI.this.layersPanel.getComponentCount() - 2);
        GUI.this.layerGroup.add(layerBox);
        GUI.this.layersPanel.revalidate();
        controller.controllerAddLayer("Layer-" + GUI.this.layerCount);
      }
    });

    addFilterButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (model.getWidth() == 0) {
          renderMessage("Please create a new project first.");
          return;
        }
        String layerName = GUI.this.layerGroup.getSelection().getActionCommand();
        final IFilter[] filterAdd = new IFilter[1];

        JPanel panel = new JPanel(new GridLayout(0, 1));
        ButtonGroup filterGroup = new ButtonGroup();
        Map<String, IFilter> temp = model.getAllFilters();
        for (Map.Entry<String, IFilter> filter : temp.entrySet()) {
          JRadioButton filterButton = new JRadioButton(filter.getKey());
          filterButton.setActionCommand(filter.getKey());
          filterGroup.add(filterButton);
          panel.add(filterButton);
        }

        int result = JOptionPane.showConfirmDialog(GUI.this, panel,
                "Select a filter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
          String selectedFilterName = filterGroup.getSelection().getActionCommand();
          filterAdd[0] = temp.get(selectedFilterName);
        }
        controller.controllerSetFilter(layerName, filterAdd[0]);
        renderProject();
      }
    });

    loadProjectMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(GUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          String filePath = selectedFile.getAbsolutePath();
          // Do something with the filePath
          System.out.println("Selected file: " + filePath);
          controller.controllerLoadProject(filePath);
          renderProject();
        }
      }
    });

    saveProjectMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (model.getWidth() == 0) {
          renderMessage("Please create a new project first.");
          return;
        }
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(GUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          controller.controllerSaveProject(file.getAbsolutePath());
        }
      }
    });


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setVisible(true);

  }

  /**
   * displays the provided message as a pop-up window in the GUI.
   *
   * @param message the message to be rendered.
   */
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(GUI.this, message);
  }

  /**
   * Refreshes the GUI by rendering the current state of the project.
   * Creates a buffered image from the model's combined image and updates the image canvas.
   */
  public void renderProject() {
    BufferedImage image = model.bufferedImage(model.saveImage());
    imageCanvas.updateImage(image);
    imagePanel.repaint();
    imagePanel.revalidate();

  }
}

