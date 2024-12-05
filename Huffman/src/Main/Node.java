package Main;

import java.io.Serializable;

public class Node implements Serializable {
    private static final long serialVersionUID = 1L;  // Add serialVersionUID
    
    char character;
    int frequency;
    Node left, right;

    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = this.right = null;
    }
}