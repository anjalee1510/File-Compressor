package Main;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCompressor {
    private Map<Character, String> huffmanCodes;
    private int originalFileLength;  // Add this field

    public HuffmanCompressor() {
        this.huffmanCodes = new HashMap<>();
    }

    public void compressFile(String inputFile, String outputFile) throws IOException {
        String content = Files.readString(Paths.get(inputFile));
        this.originalFileLength = content.length();  // Store original length

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFile))) {
            // Write the original file length at the start of compressed file
            dos.writeInt(originalFileLength);

            BitSet bitSet = new BitSet();
            int bitIndex = 0;

            // Convert content to bits using Huffman codes
            for (char c : content.toCharArray()) {
                String code = huffmanCodes.get(c);
                if (code != null) {
                    for (char bit : code.toCharArray()) {
                        if (bit == '1') {
                            bitSet.set(bitIndex);
                        }
                        bitIndex++;
                    }
                }
            }

            // Write the bits
            byte[] bytes = bitSet.toByteArray();
            dos.writeInt(bitIndex);  // Write the total number of bits
            dos.write(bytes);
        }
    }

    public void generateCodes(Node root, String code) {
        if (root == null) return;

        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.character, code);
            return;
        }

        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
    }
}