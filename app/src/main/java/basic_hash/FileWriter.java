package basic_hash;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileWriter {
  private File file;

  /**
   * Constructor that instantiates the class with just the filename.
   *
   * @param filename
   * @param dirPathFromAPP - Optional path to the files directory.
   */
  FileWriter (String filename) {
    if (!isFileTypeCorrect(filename)) {
      throw new IllegalArgumentException("Filetype not supported");
    }

    // Create a path to the textFiles directory.
    Path textFilesDir = Paths.get(System.getProperty("user.dir"), "src", "main", "textFiles");

    // Create the file and save in a global field.
    createFileObj(textFilesDir, filename);
  }

  /**
   * Optional constructor that instantiates the class with a filename
   * and a directory path for the file from the app dir.
   *
   * @param filename
   * @param dirPathFromAPP - The relative path from the app directory.
   */
  FileWriter (String filename, String dirPathFromAPP) {
    if (!isFileTypeCorrect(filename)) {
      throw new IllegalArgumentException("Filetype not supported");
    }

    // Create a path to the chosen directory.
    Path textFilesDir = Paths.get(System.getProperty("user.dir"), dirPathFromAPP);

    // Create the file and save in a global field.
    createFileObj(textFilesDir, filename);
  }

  /**
   * Test that the file name has a supported file type.
   *
   * @param filename
   */
  private boolean isFileTypeCorrect (String filename) {
    String fn = filename.toLowerCase();

    // Check that the filetype matches one of the supported file types.
    for (FileType ft : FileType.values()) {
      // Format the filetypes the same to make it case insensitive.
      String supportedFT = "." + ft.toString().toLowerCase();

      if (fn.endsWith(supportedFT)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Create a file object and store in the global field.
   *
   * @param directoryPath
   * @param filename
   */
  private void createFileObj (Path directoryPath, String filename) {
    // Ensure the directory exists
    if (!directoryPath.toFile().exists()) {
      directoryPath.toFile().mkdirs();
    }
    
    Path absolutePath = directoryPath.resolve(filename).toAbsolutePath().normalize();
      
    this.file = absolutePath.toFile();
  }

  /**
   * Overwrite content to the file connected to this writer.
   *
   * @param content
   * @throws IOException
   */
  public void write (String content) throws IOException {
    Files.write(file.toPath(), content.getBytes(), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.TRUNCATE_EXISTING);
  }

  /**
   * Get the file content as a string.
   */
  public String read () throws IOException {
    if (!file.exists()) {
      throw new IOException("File not found: " + file.getAbsolutePath());
    }

    return Files.readString(file.toPath());
  }

  /**
   * Get the file name.
   * 
   * @return The file name.
   */
  public String getFileName() {
    return file.getName();
  }

  public String getDirName () {
    File parentDir = file.getParentFile(); // Get the parent directory
    return (parentDir != null) ? parentDir.getName() : "No Parent Directory";
  }
}
