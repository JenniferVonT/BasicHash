package basic_hash;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class hashTest {
  private Hasher SUT;
  private static double tolerancePercentage = 0.5;
  private StringBuilder reportBuilder;
  private FileWriter uniformityReport;
  private FileWriter avalancheReport;

  /**
   * Setup the system under test before each test.
   */
  @BeforeEach
  void setUp() {
    SUT = new Hasher();
    reportBuilder = new StringBuilder();
    uniformityReport = new FileWriter("uniformity_report.txt", "src/test/testTextFiles");
    avalancheReport = new FileWriter("avalanche_report.txt", "src/test/testTextFiles"); 
  }

  @Test
  void testHash () {
    String testString = "testing testing";

    int hashedValue = SUT.hash(testString);

    // Test that the testring is not the same string coming out.
    assertNotNull(hashedValue);
  }

  @Test
  void testHashMatchTrue () {
    String testString = "testing testing";

    int hashedValue = SUT.hash(testString);
    int hashedValue2 = SUT.hash(testString);

    // Test that the hash is the same when the same string is processed.
    assertEquals(hashedValue, hashedValue2);
  }

  @Test
  void testHashMatchFalse () {
    String testString = "testing testing";
    String testString2 = "Testing Testing 2";

    int hashedValue = SUT.hash(testString);
    int hashedValue2 = SUT.hash(testString2);

    // Test that the hash is NOT the same when different strings are processed.
    assertNotEquals(hashedValue, hashedValue2);
  }

  @Test
  void runUniformityReport () {
    try {
      // Reading the "uniformity_test.txt" file
      String content = Files.readString(Path.of("src/test/testTextFiles/uniformity_test.txt"));
      String[] lines = content.split("\n");
  
      // Hash each line and store the results in a map to check for collisions
      Map<Integer, Integer> hashDistribution = new HashMap<>();
  
      // Write the header of the report
      reportBuilder.append("Test Report: Hash Uniformity Test\n");
      reportBuilder.append("Date: " + LocalDate.now() + "\n");
      reportBuilder.append("====================================\n");
  
      StringBuilder reportData = new StringBuilder();
  
      // Write the information about the hash distribution
      reportData.append("\nHash Distribution (buckets and their counts):\n\n");
  
      // Iterate over each line, compute its hash value,
      // and update the count of occurrences of each hash in the hashDistribution map.
      for (String line : lines) {
        int hashValue = SUT.hash(line);
        hashDistribution.put(hashValue, hashDistribution.getOrDefault(hashValue, 0) + 1);
      }
  
      // Write the distribution and unique hash counts to the report
      for (Map.Entry<Integer, Integer> entry : hashDistribution.entrySet()) {
        reportData.append("Bucket " + entry.getKey() + ": " + entry.getValue() + " times\n");
      }
  
      // Check the number of unique hash values (buckets used)
      int uniqueHashCount = hashDistribution.size();
      reportBuilder.append("\nUnique Hash Values: " + uniqueHashCount + "\n");
      reportBuilder.append("Total Hash Values: " + lines.length + "\n");
  
      // Check if the hash distribution is relatively uniform
      int averageHashesPerBucket = lines.length / 256;  // There are 256 buckets (0-255)
      reportBuilder.append("Average Hashes per Bucket: " + averageHashesPerBucket + "\n");
  
      // Equate the accepted tolerance range.
      double tolerance = averageHashesPerBucket * tolerancePercentage;
      reportBuilder.append("Tolerance: " + tolerance + "\n");
      reportBuilder.append("Tolerance %: " + tolerancePercentage * 100 + "%" + "\n");
  
      // Write the report about buckets within tolerance range
      reportData.append("\nBuckets outside tolerance range:\n\n");
  
      // Debugging output
      double lowerBound = averageHashesPerBucket - tolerance;
      double upperBound = averageHashesPerBucket + tolerance;
  
      reportBuilder.append("Upper limit: " + upperBound + "\n" + "Lower limit: " + lowerBound + "\n");
  
      boolean allBucketsWithinTolerance = true;
      int outOfRangeCount = 0;  // Count of buckets outside the tolerance range
  
      for (Map.Entry<Integer, Integer> entry : hashDistribution.entrySet()) {
        int bucketValue = entry.getKey();
        int count = entry.getValue();
  
        // If the count for the bucket is outside the tolerance, include the bucket value and the count
        if (count < lowerBound || count > upperBound) {
          reportData.append("Bucket " + bucketValue + " with count " + count + " is out of range\n");
          allBucketsWithinTolerance = false;
          outOfRangeCount++;  // Increment the out-of-range counter
        }
      }
  
      if (allBucketsWithinTolerance) {
        reportData.append("\nAll buckets are within the tolerance range.\n");
      }
  
      // Statistical analysis: calculate percentage of buckets out of range
      int totalBuckets = hashDistribution.size();
      double outOfRangePercentage = (double) outOfRangeCount / totalBuckets * 100;
  
      // Append the percentage of buckets out of range to the report
      reportBuilder.append("Buckets Out of Range: " + outOfRangeCount + "\n");
      reportBuilder.append("Percentage of Buckets Out of Range: " + String.format("%.2f", outOfRangePercentage) + "%\n");
  
      reportBuilder.append(reportData.toString());

      // Write the report to a .txt file.
      uniformityReport.write(reportBuilder.toString());
  
    } catch (IOException e) {
      reportBuilder.append("IOException occurred while reading the file: ").append(e.getMessage()).append("\n");
    }
  }

  @Test
  void runAvalancheReport() {
    try {
      // Read the file into a List of Strings
      Path avalancheFilePath = Path.of("src/test/testTextFiles/avalanche_test.txt");
      List<String> lines = Files.readAllLines(avalancheFilePath);

      StringBuilder reportData = new StringBuilder();

      // Write the header of the report
      reportBuilder.append("Test Report: Hash Avalanche Test\n");
      reportBuilder.append("Date: " + LocalDate.now() + "\n");
      reportBuilder.append("====================================\n");

      // For tracking number of failures and total comparisons
      int totalComparisons = lines.size() - 1;
      int failedComparisons = 0;

      // For an 8-bit hash, expect about 50% of 8 bits to change
      int expectedChanges = 4; // Expected 50% bit changes (for an 8-bit hash)
      double tolerance = expectedChanges * tolerancePercentage; // Allow some tolerance. 

      // Loop through the lines and compare each line with the next one
      for (int i = 0; i < totalComparisons; i++) {
        String originalString = lines.get(i);
        String nextString = lines.get(i + 1);

        // Hash the original string
        int originalHash = SUT.hash(originalString);

        // Hash the next string
        int nextHash = SUT.hash(nextString);

        // Count the number of bit changes between the two hashes
        int bitChanges = countBitChanges(originalHash, nextHash);

        // Check if the bit changes are within the acceptable range
        if (Math.abs(bitChanges - expectedChanges) > tolerance) {
          // Record the failure in the report with the actual strings
          reportData.append("Avalanche effect test failed for:\n");
          reportData.append("Original String: " + originalString + "\n");
          reportData.append("Original Hash: " + originalHash + "\n");
          reportData.append("Next String: " + nextString + "\n");
          reportData.append("Next Hash: " + nextHash + "\n");
          reportData.append("Expected around " + expectedChanges + " bit changes, but got " + bitChanges + ".\n\n");
          failedComparisons++;
        }
      }

      // Write summary of the results
      reportBuilder.append("\nTotal Comparisons: ").append(totalComparisons).append("\n");
      reportBuilder.append("Failed Comparisons: ").append(failedComparisons).append("\n");
      reportBuilder.append("Tolerance: " + tolerance + "\n");
      reportBuilder.append("Tolerance %: " + tolerancePercentage * 100 + "%" + "\n");
      reportBuilder.append("Pass Rate: ").append(((totalComparisons - failedComparisons) / (double) totalComparisons) * 100).append("%\n");
      reportBuilder.append("\n\n\n" + "Data sets: " + "\n\n");

      reportBuilder.append(reportData.toString());

      // Save the report to a file
      avalancheReport.write(reportBuilder.toString());

    } catch (IOException e) {
        e.printStackTrace();  // Handle file reading exceptions
    }
  }

/**
 * Count how many bits differ between two integer hashes.
 */
private int countBitChanges(int hash1, int hash2) {
  // XOR the two hashes to get the differing bits
  int differingBits = hash1 ^ hash2;

  // Count the number of set bits (1s) in the result
  int count = 0;

  while (differingBits != 0) {
    count += differingBits & 1; // Increment count if the least significant bit is 1
    differingBits >>>= 1; // Right shift by 1 (unsigned shift to avoid sign extension)
  }

  return count;
}

}
