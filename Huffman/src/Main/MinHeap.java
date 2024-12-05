package Main;

public class MinHeap {
    private int size;
    private Node[] array;

    public MinHeap(char[] chars, int[] freq, int uniqueSize) {
        this.size = uniqueSize;
        this.array = new Node[size];

        for (int i = 0; i < uniqueSize; i++) {
            array[i] = new Node(chars[i], freq[i]);
        }

        buildMinHeap();
    }

    private void buildMinHeap() {
        int n = size - 1;
        for (int i = (n - 1) / 2; i >= 0; i--) {
            heapify(i);
        }
    }

    private void heapify(int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && array[left].frequency < array[smallest].frequency) {
            smallest = left;
        }

        if (right < size && array[right].frequency < array[smallest].frequency) {
            smallest = right;
        }

        if (smallest != i) {
            Node temp = array[i];
            array[i] = array[smallest];
            array[smallest] = temp;
            heapify(smallest);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node extractMin() {
        if (isEmpty()) {
            return null;
        }

        Node root = array[0];
        array[0] = array[size - 1];
        size--;
        heapify(0);

        return root;
    }

    public void insert(Node node) {
        if (size >= array.length) {
            Node[] newArray = new Node[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }

        int i = size;
        array[size] = node;
        size++;

        while (i != 0 && array[parent(i)].frequency > array[i].frequency) {
            Node temp = array[i];
            array[i] = array[parent(i)];
            array[parent(i)] = temp;
            i = parent(i);
        }
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    public int getSize() {
        return size;
    }
}