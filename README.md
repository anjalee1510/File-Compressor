# File-Compressor
# Huffman Coding Compression & Decompression

This project implements **Huffman Coding**, a lossless data compression algorithm, for compressing and decompressing text files. It uses a **min-heap** to build the Huffman tree and applies **bit manipulation** for efficient compression and decompression. The program provides functionality to:

- Compress text files into a binary format using Huffman codes.
- Decompress the binary files back into their original text form.
- Verify the integrity of the decompressed file by comparing it to the original.

## Project Overview

### Features:
- **Compression**: Converts a given text file into a compressed binary format using Huffman codes.
- **Decompression**: Restores the original text from the compressed binary format.
- **Min-Heap Implementation**: Efficiently constructs the Huffman tree by using a **min-heap** for the lowest frequency node selection.
- **Bit-Level Data Handling**: The compressed file stores the bits representing the Huffman codes for each character in the file.
- **Serialization**: The Huffman tree is serialized and saved to facilitate accurate decompression.


