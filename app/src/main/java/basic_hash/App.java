/*
 * Starting point of the application.
 */
package basic_hash;

import java.util.Scanner;
import java.io.IOException;

public class App {
    private UI ui;
    private FileWriter file;
    private Hasher hasher;
  
    /**
     * Instantiate the App class.
     *
     * @param scannerInput - A scanner for user input.
     */
    App (Scanner scannerInput) {
      this.hasher = new Hasher();
      this.ui = new UI(scannerInput);
    }
  
    /**
     * Starts the application "menu" where the user can input data.
     */
    private void selectFileMenu () {
      try {
        file = new FileWriter(ui.selectFile());
      } catch (IllegalArgumentException error) {
        ui.showError(error.getMessage());
        selectFileMenu();
      }
    }
  
    /**
     * Process the hashing.
     */
    private void processFile () {
      try {
        String plainText = file.read();
        String hashedString = hasher.hash(plainText);

        FileWriter hashFile = new FileWriter("hash.txt");
        
        hashFile.write(hashedString);
    
        ui.clearConsole();
        ui.showMessage("The file was hashed and stored in hash.txt in the textFiles folder");
      } catch (IOException error) {
        ui.clearConsole();
        ui.showError(error.getMessage());
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
        app.selectFileMenu();
        app.processFile();
      } while (!app.endMessage());
  
      // Close the scanner when exiting the application.
      consoleInput.close();
    }
  }
  
