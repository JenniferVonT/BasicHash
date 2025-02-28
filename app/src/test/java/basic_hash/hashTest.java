package basic_hash;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Scanner;

public class hashTest {
  private ByteArrayOutputStream outputStream;
  private PrintStream originalOut;
  private InputStream originalIn;
  private UI SUT;

  /**
   * Setup the system under test before each test.
   */
  @BeforeEach
  void setUp() {
    // Redirect System.out to capture console output
    outputStream = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Backup System.in to restore later
    originalIn = System.in;

    SUT = new UI(new Scanner(System.in, "UTF-8"), new Settings());
  }

  /**
   * Reset the console between the tests.
   */
  @AfterEach
  void tearDown() {
    // Restore original System.out and System.in
    System.setOut(originalOut);
    System.setIn(originalIn);
  }

  @Test void testHash() {
    
  }
    
}
