package basic_hash;

import static org.junit.jupiter.api.Assertions.*;

/* 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class hashTest {
  private Hasher SUT;

  /**
   * Setup the system under test before each test.
   */
  @BeforeEach
  void setUp() {
    SUT = new Hasher();
  }

  @Test void testHash () {
    String testString = "testing testing";

    String hashedString = SUT.hash(testString);

    // Test that the testring is not the same string coming out.
    assertFalse(hashedString.equals(testString));
  }

  @Test void testHashMatchTrue () {
    String testString = "testing testing";

    String hashedString = SUT.hash(testString);
    String hashedString2 = SUT.hash(testString);

    // Test that the hash is the same when the same string is processed.
    assertTrue(hashedString.equals(hashedString2));
  }

  @Test void testHashMatchFalse () {
    String testString = "testing testing";
    String testString2 = "Testing Testing 2";

    String hashedString = SUT.hash(testString);
    String hashedString2 = SUT.hash(testString2);

    // Test that the hash is NOT the same when different strings are processed.
    assertFalse(hashedString.equals(hashedString2));
  }

/* 
  @Test void testHashUniformity () {
    try {
        // Reading the "uniformity_test.txt" file
        String content = Files.readString(Path.of("src/test/testTextFiles/uniformity_test.txt"));
        String[] lines = content.split("\n");

        // Hash each line and store the results in a map to check for collisions
        Map<String, Integer> hashDistribution = new HashMap<>();
        for (String line : lines) {
            String hash = SUT.hash(line);
            hashDistribution.put(hash, hashDistribution.getOrDefault(hash, 0) + 1);
        }

        // Check if the hash distribution is relatively uniform
        int totalHashes = hashDistribution.size();
        int averageHashesPerBucket = lines.length / totalHashes;

        // Allow a small tolerance (say 10% of the average)
        double tolerance = averageHashesPerBucket * 0.1;
        for (int count : hashDistribution.values()) {
            assertTrue(Math.abs(count - averageHashesPerBucket) < tolerance,
                    "Hashes are not evenly distributed, too many collisions!");
        }
    } catch (IOException e) {
        fail("IOException occurred while reading the file: " + e.getMessage());
    }
  }
*/

/*
  @Test void testHashAvalanche () {
    // Read the file
    Path avalancheFilePath = Path.of("src/test/testTextFiles/avalanche_test.txt");
    List<String> lines = Files.readAllLines(avalancheFilePath);

    // Loop through the lines and compare each line with the next one
    for (int i = 0; i < lines.size() - 1; i++) {
        String originalString = lines.get(i);

        // Hash the original string
        String originalHash = SUT.hash(originalString);

        // Get the next line in the file for comparison
        String nextString = lines.get(i + 1);

        // Hash the next string
        String nextHash = SUT.hash(nextString);

        // Count the number of bit changes between the two hashes
        int bitChanges = countBitChanges(originalHash, nextHash);

        // For a good avalanche effect, we expect roughly 50% bit changes
        // Example: For a 128-bit hash, we would expect about 64 bit changes
        int expectedChanges = originalHash.length() * 4; // 128-bit hash, expect 128 bits
        int tolerance = expectedChanges / 10; // Allow some tolerance (e.g., 10% variation)

        assertTrue(Math.abs(bitChanges - expectedChanges) <= tolerance,
                "Avalanche effect test failed for line " + (i + 1) + ". Expected around " + expectedChanges + " bit changes, but got " + bitChanges);
    }
  }
*/

  /**
   * Count how many bits differ between two hexadecimal hashes.
   */
  /*
  private int countBitChanges (String hash1, String hash2) {
    if (hash1.length() != hash2.length()) {
        throw new IllegalArgumentException("Hashes must have the same length!");
    }

    int count = 0;
    // Iterate over each character in the hashes (which are in hexadecimal format)
    for (int i = 0; i < hash1.length(); i++) {
        char char1 = hash1.charAt(i);
        char char2 = hash2.charAt(i);

        // Convert the hex chars to their binary representation and compare
        String binary1 = String.format("%4s", Integer.toBinaryString(Character.digit(char1, 16)))
                            .replace(' ', '0');
        String binary2 = String.format("%4s", Integer.toBinaryString(Character.digit(char2, 16)))
                            .replace(' ', '0');

        // Count how many bits are different
        for (int j = 0; j < binary1.length(); j++) {
            if (binary1.charAt(j) != binary2.charAt(j)) {
                count++;
            }
        }
    }
    return count;
  }
 */
}
