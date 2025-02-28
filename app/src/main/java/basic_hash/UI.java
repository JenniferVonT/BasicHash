package basic_hash;

import java.util.Scanner;

public class UI {
  private Scanner input;
  private Settings settings;

  protected UI(Scanner input, Settings settings) {
    this.input = input;
    this.settings = settings;
  }

  /**
   * Get user input to select what file to process and set it in settings.
   */
  public void selectFile () {
    try {
      System.out.println("Input the name of the file you want to process (should be located in the textFiles folder): ");
      String file = this.input.nextLine();

      settings.setFile(file);
    } catch (IllegalArgumentException error) {
      clearConsole();
      System.out.println(error.getMessage());
      selectFile();
    }
  }

  /**
   * Ask user if they want to exit the application or repeat the process.
   *
   * @return - Yes (true) || No (false).
   */
  public boolean endSession () {
    System.out.println("\n\n");
    System.out.println("Would you want to exit (X) or run again (R)");
    String alt = this.input.nextLine().toUpperCase();

    switch (alt) {
      case "X":
        clearConsole();
        return true;
      case "R":
       clearConsole();
        return false;
      default:
        clearConsole();
        System.out.println("Invalid input.");
        return endSession();
    }
  }

  /**
   * Show a generic message.
   *
   * @param message
   */
  public void showMessage (String message) {
    System.out.println("\n\n" + message);
  }

  /**
   * Show a generic error message.
   *
   * @param message
   */
  public void showError (String message) {
    clearConsole();
    System.out.println("\u001B[31m" + "\nERROR: " + message + "\u001B[0m");
  }

  /**
   * Clear the console visually by printing clear lines.
   */
  public void clearConsole () {
    for (int i = 0; i < 30; i++) {
      System.out.println();
    }
  } 
}
