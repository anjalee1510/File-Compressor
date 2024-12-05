package Main;

import java.io.Serializable;
import java.util.Arrays;

public class Code implements Serializable {
    private static final long serialVersionUID = 2L;  // Add serialVersionUID
    
    char character;
    int length;
    int[] codeArray;
    Code next;

    public Code(char character, int length, int[] codeArray) {
        this.character = character;
        this.length = length;
        this.codeArray = Arrays.copyOf(codeArray, length);
        this.next = null;
    }
}