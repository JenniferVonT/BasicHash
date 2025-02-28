package basic_hash;

public class Hasher {
  public Hasher () {}

  /**
   * Hash a string.
   * 
   * @return The fixed hashed string.
   */
  public String hash (String textToHash) {
    int hashValue = 0;

    // Loop through each character.
    for (char c : textToHash.toCharArray()) {
        hashValue += (int) c; // Add the ASCII value of the character.
    }

    // Ensure the result is within the range 0-255 (8 bits) and return it.
    return String.valueOf(hashValue % 256);
  }
}
