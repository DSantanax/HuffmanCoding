import java.util.BitSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.RecursiveTask;

public class Encoder extends RecursiveTask<StringBuilder> {

    private final int ALPHABET_SIZE = 256;
    private String data;
    private int[] frequency;
    private Node root;
    private HashMap<Character, String> hashMap;
    private BitSet bits;
    private StringBuilder stringBuilder;

    public Encoder(String data) {
        this.data = data;
    }

    public Encoder(String data, HashMap<Character, String> map) {
        this.data = data;
        this.hashMap = map;
    }

    public void createFrequency() {
        frequency = new int[ALPHABET_SIZE];
        //can use character as index
        for (Character letter : data.toCharArray()) {
            frequency[letter]++;
        }
    }

    //TODO: parallelize creation of tree
    public void createHuffmanTree() {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (char i = 0; i < ALPHABET_SIZE; i++) {
            //add to PQ only those that had characters
            if (frequency[i] > 0) {
                pq.add(new Node(i, frequency[i], null, null));
            }
        }
        //root
        if (pq.size() == 1) {
            pq.add(new Node('\0', 1, null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = null;
            if (right != null) {
                parent = new Node('\0', left.getFreq() + right.getFreq(), left, right);
            }
            pq.add(parent);
        }
        //reference to all Nodes
        root = pq.poll();
    }

    public void createLookUpTable() {
        hashMap = new HashMap<>();
        recursiveLookup(root, "");
    }

    private void recursiveLookup(Node node, String stringCode) {
        if (node.isLeaf()) {
            recursiveLookup(node.getLeftChild(), stringCode + '0');
            recursiveLookup(node.getRightChild(), stringCode + '1');
        } else {
            hashMap.put(node.getCharacter(), stringCode);
        }
    }

    //TODO: parallelize this

    public void createEncodedString() {
        stringBuilder = new StringBuilder();
        bits = new BitSet();
        int indexBit = 0;
        for (Character character : data.toCharArray()) {
            String word = (hashMap.get(character));
            stringBuilder.append(word);
            for (int i = 0; i < word.length(); i++) {
                //1
                if (word.charAt(i) == '1') {
                    bits.set(indexBit++);
                }//0
                else {
                    bits.clear(indexBit++);
                }
            }
        }
    }

    public void setFrequency(int[] freq) {
        this.frequency = freq;
    }

    public HashMap<Character, String> getHashMap() {
        return hashMap;
    }

    public BitSet getEncodedData() {
        return bits;
    }

    public Node getRoot() {
        return root;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    //TODO return string builder
    //parallelize encoded string
    @Override
    protected StringBuilder compute() {
        if (data.length() < 25000) {
            createEncodedString();
            return stringBuilder;
        } else {
            int midpointData = data.length() / 2;

            Encoder left = new Encoder(data.substring(0, midpointData), hashMap);
            Encoder right = new Encoder(data.substring(midpointData), hashMap);

            left.fork();
            StringBuilder compute = right.compute();
            StringBuilder join = left.join();

            return join.append(compute);
        }
    }

    public void setEncodedData(StringBuilder myString) {
        this.stringBuilder = myString;
    }
}

