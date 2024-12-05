package Main;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HuffmanCoding {
    
    public static void compress(String inputFile, String outputFile) {
        try {
            // Print original file size
            long originalSize = Files.size(Paths.get(inputFile));
            System.out.println("Original file size: " + originalSize + " bytes");

            // Read input file and count frequencies
            String content = Files.readString(Paths.get(inputFile));
            Map<Character, Integer> frequencies = new HashMap<>();
            
            for (char c : content.toCharArray()) {
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            }

            // Create character and frequency arrays
            int uniqueChars = frequencies.size();
            char[] chars = new char[uniqueChars];
            int[] freqs = new int[uniqueChars];
            int index = 0;
            
            for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
                chars[index] = entry.getKey();
                freqs[index] = entry.getValue();
                index++;
            }

            System.out.println("Compressing file...");
            long startTime = System.currentTimeMillis();

            // Build Huffman tree
            MinHeap minHeap = new MinHeap(chars, freqs, uniqueChars);
            Node root = buildHuffmanTree(minHeap);

            // Generate Huffman codes and compress
            HuffmanCompressor compressor = new HuffmanCompressor();
            compressor.generateCodes(root, "");
            compressor.compressFile(inputFile, outputFile);

            // Save the tree for later decompression
            saveTree(root, outputFile + ".tree");
            
            // Print compression statistics
            long compressedSize = Files.size(Paths.get(outputFile));
            long compressionTime = System.currentTimeMillis() - startTime;
            
            System.out.println("Compression completed successfully!");
            System.out.println("Compressed file size: " + compressedSize + " bytes");
            System.out.printf("Compression ratio: %.2f%%\n", 
                (100.0 * compressedSize / originalSize));
            System.out.println("Compression time: " + compressionTime + "ms");

        } catch (IOException e) {
            System.err.println("Compression error: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public static void decompress(String inputFile, String outputFile) {
//        try {
//            System.out.println("\nDecompressing file...");
//            long startTime = System.currentTimeMillis();
//
//            // Load the Huffman tree
//            Node root = loadTree(inputFile + ".tree");
//            
//            // Create decompressor and process file
//            HuffmanDecompressor decompressor = new HuffmanDecompressor(root);
//            decompressor.decompressFile(inputFile, outputFile, 
//                                      (int)Files.size(Paths.get(inputFile)));
//            
//            long decompressionTime = System.currentTimeMillis() - startTime;
//            System.out.println("Decompression completed successfully!");
//            System.out.println("Decompression time: " + decompressionTime + "ms");
//
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println("Decompression error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
    
    public static void decompress(String inputFile, String outputFile) {
        try {
            System.out.println("\nDecompressing file...");
            long startTime = System.currentTimeMillis();

            // Load the Huffman tree
            Node root = loadTree(inputFile + ".tree");
            
            // Create decompressor and process file
            HuffmanDecompressor decompressor = new HuffmanDecompressor(root);
            decompressor.decompressFile(inputFile, outputFile);
            
            long decompressionTime = System.currentTimeMillis() - startTime;
            System.out.println("Decompression completed successfully!");
            System.out.println("Decompression time: " + decompressionTime + "ms");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Decompression error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Node buildHuffmanTree(MinHeap minHeap) {
        while (minHeap.getSize() > 1) {
            Node left = minHeap.extractMin();
            Node right = minHeap.extractMin();
            Node parent = new Node('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            minHeap.insert(parent);
        }
        return minHeap.extractMin();
    }

    private static void saveTree(Node root, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            out.writeObject(root);
        }
    }

    private static Node loadTree(String filename) 
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filename))) {
            return (Node) in.readObject();
        }
    }

    public static boolean compareFiles(String file1, String file2) throws IOException {
        try (BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(file1));
             BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(file2))) {

            // First compare file sizes
            long size1 = Files.size(Paths.get(file1));
            long size2 = Files.size(Paths.get(file2));
            
            if (size1 != size2) {
                System.out.println("Files have different sizes: " + 
                    size1 + " vs " + size2 + " bytes");
                return false;
            }

            // Compare contents
            int byte1, byte2;
            long totalBytes = 0;
            boolean filesMatch = true;

            while ((byte1 = bis1.read()) != -1) {
                byte2 = bis2.read();
                totalBytes++;
                
                if (byte1 != byte2) {
                    System.out.println("Files differ at byte " + totalBytes);
                    filesMatch = false;
                    break;
                }
            }

            return filesMatch && (bis2.read() == -1);
        }
    }
}