package basic_hash;

import java.io.IOException;

public class Hash {
    private FileWriter hashedFile;

    public Hash () {
      hashedFile = new FileWriter("hash.txt");
    }

    /**
     * Hash a string and save to the hashedFile.
     * 
     * @return True = completed || False = Something went wrong.
     */
    public boolean hashFile (String textToHash) throws IOException {
        // TO-DO: Implement a hashing algorithm.

        return true;
    }
}
