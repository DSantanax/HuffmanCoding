public class Node implements Comparable<Node> {

    private Node leftChild;
    private Node rightChild;
    private char _character;
    private int freq;

    public Node(char _character, int freq, Node left, Node right) {
        this._character = _character;
        this.freq = freq;
        this.leftChild = left;
        this.rightChild = right;
    }

    //check if its a leaf (no predecessor states)
    public boolean isLeaf() {
        return ((leftChild != null) || (rightChild != null));
    }

    @Override
    public int compareTo(Node o) {

        //compare frequency else check character
        int comparedValue = Integer.compare(this.freq, o.freq);
        if (comparedValue != 0) {
            return comparedValue;
        }
        //compare character instead
        return Integer.compare(this._character, o._character);
    }

    public int getFreq() {
        return freq;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public char getCharacter() {
        return _character;
    }
}
