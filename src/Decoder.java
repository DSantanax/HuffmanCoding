public class Decoder {

    public static StringBuilder decode(Node root, StringBuilder decoded) {
        StringBuilder decode = new StringBuilder();
        Node current = root;

        for (int i = 0; i < decoded.length(); ) {

            while (current.isLeaf()) {

                if (decoded.charAt(i) == '1') {
                    current = current.getRightChild();
                } else {
                    current = current.getLeftChild();
                }
                i++;
            }
            decode.append(current.getCharacter());
            current = root;
        }
        return decode;
    }

}
