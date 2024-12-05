package Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HuffmanMain {

    public static void main(String[] args) {
        // Define file paths
        String inputFile = "C:\\Users\\aneea\\OneDrive\\Desktop\\Sample.txt";
        String compressedFile = "C:\\Users\\aneea\\OneDrive\\Desktop\\compressed.bin";
        String decompressedFile = "C:\\Users\\aneea\\OneDrive\\Desktop\\decompressed.txt";

//        // Create a test file (optional, if you want to create a sample file)
//        try {
//            Files.writeString(Paths.get(inputFile), 
//                "This is a test file for Huffman coding compression.\n" +
//                "It contains repeated characters to demonstrate compression efficiency.\n" +
//                "AAAAABBBBBCCCCCDDDDD");
//        } catch (IOException e) {
//            System.err.println("Error creating test file: " + e.getMessage());
//            return;
//        }

        // Compress and decompress
        HuffmanCoding.compress(inputFile, compressedFile);
        HuffmanCoding.decompress(compressedFile, decompressedFile);

        // Verify the decompressed file matches the original
        try {
            boolean filesMatch = HuffmanCoding.compareFiles(inputFile, decompressedFile);
            if (filesMatch) {
                System.out.println("Verification successful! Files are identical.");
            } else {
                System.err.println("Verification failed! Files are different.");
            }
        } catch (IOException e) {
            System.err.println("Error verifying files: " + e.getMessage());
        }
    }
}