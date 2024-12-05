package Main;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

public class HuffmanDecompressor {
    private Node root;

    public HuffmanDecompressor(Node huffmanTree) {
        this.root = huffmanTree;
    }

    public void decompressFile(String inputFile, String outputFile) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            // Read the original file length
            int originalLength = dis.readInt();
            int totalBits = dis.readInt();
            
            // Read the compressed data
            byte[] bytes = dis.readAllBytes();
            BitSet bitSet = BitSet.valueOf(bytes);

            // Traverse the Huffman tree and write decompressed text
            Node current = root;
            int charCount = 0;
            
            for (int i = 0; i < totalBits && charCount < originalLength; i++) {
                current = bitSet.get(i) ? current.right : current.left;

                if (current.left == null && current.right == null) {
                    writer.write(current.character);
                    charCount++;
                    current = root;
                }
            }
        }
    }
}