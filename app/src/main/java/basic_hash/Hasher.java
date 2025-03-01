package basic_hash;

public class Hasher {
  private static final int HASH_BUCKETS = 256;

  Hasher () {}

  /**
   * Hash a string into an 8-bit hashvalue between 0-256 buckets.
   * 
   * @return The fixed hashed string.
   */
  public int hash (String textToHash) {
    int hashValue = 0;

    // Loop through each character in the string.
    for (char c : textToHash.toCharArray()) {
        // Apply an equation to get a hashvalue.
        hashValue = (hashValue * 31 + c) ^ (hashValue >>> 16);
    }

    // Ensure the result is within the range 0-255 (8-bit hash)
    hashValue = Math.abs(hashValue) % HASH_BUCKETS;

    return hashValue;
  }
}
