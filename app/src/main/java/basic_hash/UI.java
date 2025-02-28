package basic_hash;

import java.util.Scanner;

public class UI {
  private Scanner input;

  protected UI(Scanner input) {
    this.input = input;
  }

  /**
   * Get user input to select what file to process and set it in settings.
   */
  public String selectFile () {
    try {
      System.out.println("Input the name of the file you want to process (should be located in the textFiles folder): ");
      String file = input.nextLine();

      return file;
    } catch (IllegalArgumentException error) {
      clearConsole();
      return error.getMessage();
    }
  }

  /**
   * Ask user if they want to exit the application or repeat the process.
   *
   * @return - Yes (true) || No (false).
   */
  public boolean endSession () {
    System.out.println("\n\nWould you want to exit (X) or run again (R)");
    String alt = input.nextLine().toUpperCase();

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
