import java.io.*;
import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.ForkJoinPool;


/**
 * a)	Create Three more implementations speeding up your original program.
 * a.	Note speedup in creating tree for each implementation if any
 * b.	Note speedup in encoding the file using the tree if any
 * Total speedup of parallelize program
 */

public class HuffmanCoding {

    public static void main(String[] args) {
        //create based on processors

        File constitution_file = new File("const.txt");
        StringBuilder total = new StringBuilder();
        try {

            //TODO: give each thead certain lines
            BufferedReader bf = new BufferedReader(new FileReader(constitution_file));
            String next;
            while ((next = bf.readLine()) != null) {
                total.append(next).append("\n");
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(total.toString().length());
        Encoder encoder = new Encoder(total.toString());
        //TODO: frequency;

        //multi-thread frequency (comment out)

        //  ForkJoinPool forkPool = new ForkJoinPool();
//        HuffmanFrequency frequency = new HuffmanFrequency(total.toString());
//        long startFreq = System.nanoTime();
//        int[] freq = forkPool.invoke(frequency);
//        System.out.println("Frequency time: " + ((System.nanoTime()-startFreq)/1_000_000) + "ms");
//        encoder.setFrequency(freq);

        //Single thread frequency (comment out)
        long startFreq = System.nanoTime();
        encoder.createFrequency();
        System.out.println("Frequency time: " + ((System.nanoTime() - startFreq) / 1_000_000) + "ms");


        //TODO: huffman tree
        long startHuffTree = System.nanoTime();
        //create tree here instead
        encoder.createHuffmanTree();
        System.out.println("Total time to create tree: " + (System.nanoTime() - startHuffTree) / 1_000_000 + "ms");

        encoder.createLookUpTable();
        //multi thread parallel encoder (comment out)
//        long startEncoder = System.nanoTime();
//        StringBuilder myString = forkPool.invoke(encoder);
//        System.out.println("Total time to encode data multi thread: " +  (System.nanoTime() - startEncoder)/1_000_000 + "ms");
//        forkPool.shutdown();
//        encoder.setEncodedData(myString);

        //TODO: fix issue where I need to set the forkjoin set BitSet when doing encoding
        //single thread (comment out)
        long startEncodingString = System.nanoTime();
        encoder.createEncodedString();
        System.out.println("Total time encoding single thread " + (System.nanoTime() - startEncodingString) / 1_000_000 + "ms");
        StringBuilder myString = encoder.getStringBuilder();

        BitSet dataEncoded = encoder.getEncodedData();
        //encoded data
        // System.out.println(dataEncoded.toString());
        Node root = encoder.getRoot();
        StringBuilder decoded = Decoder.decode(root, myString);
        //System.out.println(decoded);

        File encodedDataBinary = new File("encodedData.txt");

        File dataDecoded = new File("decodedData.txt");

        try {
            //write decoded data (from the string of 101s)
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataDecoded));
            bw.write(decoded.toString());

            //write bits out
            FileOutputStream fileOut = new FileOutputStream(encodedDataBinary);
            fileOut.write(dataEncoded.toByteArray());

            fileOut.close();
            bw.close();
            long encodedBytes = encodedDataBinary.length();

            //print information for original, encoded, and decoded files
            long originalFileSize = constitution_file.length();
            System.out.println("Original file bytes: " + originalFileSize);
            System.out.println("Encoded file bytes: " + encodedBytes);
            System.out.println("Compression percentage: " + (encodedBytes / (float) originalFileSize) * 100 + "%");
            long dataDecodedBytes = dataDecoded.length();
            System.out.println("Decoded file bytes: " + dataDecodedBytes);
            System.out.println("Decoded data difference: " + (dataDecodedBytes / (float) originalFileSize) * 100 + "%");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
