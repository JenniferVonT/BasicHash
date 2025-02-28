/*
 * Starting point of the application.
 */
package basic_hash;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class App {
    private UI ui;
    private Settings settings;
  
    /**
     * Instantiate the App class.
     *
     * @param scannerInput - A scanner for user input.
     */
    App (Scanner scannerInput) {
      this.settings = new Settings();
      this.ui = new UI(scannerInput, this.settings);
    }
  
    /**
     * Starts the application "menu" where the user can input data.
     */
    private void startMenu () {
      ui.selectFile();
    }
  
    /**
     * Process the hashing.
     */
    private void processFile () {
        String plainText = convertFileToString();
        String successMessage = "";

        // TO-DO: call hash.
  
        ui.clearConsole();
        ui.showMessage(successMessage);
    }
  
    /**
     * Converts the file content to a string.
     *
     * @return - The file content as a string.
     */
    private String convertFileToString () {
      try {
        StringBuilder content = new StringBuilder();
        File file = settings.getFile();
        content.append(new String(Files.readAllBytes(file.toPath())));
  
        return content.toString();
      } catch (IOException error) {
        return null;
      }
    }
  
    /**
     * Ask if the user wants to exit the application.
     *
     * @return yes (true) or no (false).
     */
    private boolean endMessage () {
      return ui.endSession();
    }
  
  
    /**
     * The main method to start the application, keep this last!
     *
     * @param args
     */
    public static void main (String[] args) {
      Scanner consoleInput = new Scanner(System.in, "UTF-8");
      App app = new App(consoleInput);
  
      // Loop the application to avoid unwanted application exits until the user wants to exit.
      do {
        app.startMenu();
        app.processFile();
      } while (!app.endMessage());
  
      // Close the scanner when exiting the application.
      consoleInput.close();
    }
  }
  
