package basic_hash;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
  private File file;

  public Settings() {}

  /**
   * Check file type and set the current default working file in the settings.
   *
   * @param fileName
   */
  public void setFile (String fileName) {
    // Check that it is a .txt file only.
    if (!fileName.endsWith(".txt")) {
      throw new IllegalArgumentException("Must be a .txt file");
    }

    // Define the path to the file dynamically...
    Path textFilesDir = Paths.get(System.getProperty("user.dir"), "src", "main", "textFiles");
    Path absolutePath = textFilesDir.resolve(fileName).toAbsolutePath().normalize();

    File file = absolutePath.toFile();

    // And check if the file even exists.
    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist in the textFiles folder, pleaser insert it and try again.");
    }

    this.file = file;
  }

  public File getFile () {
    return this.file;
  }
}
