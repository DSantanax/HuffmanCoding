Requirements:
    Java 8 or above
    JDK 1.8 or above

Compile:
    javac HuffmanCoding.java Encoder.java Decoder.java Node.java HuffmanFrequency.java
Run:
    java HuffmanCoding.java
Input:
    None
Arguments:
    None
Output:
    [Information regarding the decoding]

Disclaimer:
    I am still working on adjusting the output to show both parallel stats.
    I commented out the parallelization to show single thread only.
    In order to run the parallel threads comment out the single thread as
    noted in the comments and remove the comments from the parallel section.
    I will update it soon I apologize for the delay. Also adjusting
    the output for BitSet in parallel for encoding.