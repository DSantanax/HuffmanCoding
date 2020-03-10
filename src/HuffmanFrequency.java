import java.util.concurrent.RecursiveTask;

public class HuffmanFrequency extends RecursiveTask<int[]> {

    private String data;

    public HuffmanFrequency(String data) {
        this.data = data;
    }

    public int[] createFrequency() {
        int[] frequency = new int[256];
        //can use character as index
        for (Character letter : data.toCharArray()) {
            frequency[letter]++;
        }
        return frequency;
    }

    @Override
    protected int[] compute() {
        if (data.length() < 10000) {
            return createFrequency();
        } else {
            int midpoint = data.length() / 2;

            HuffmanFrequency left = new HuffmanFrequency(data.substring(0, midpoint));
            HuffmanFrequency right = new HuffmanFrequency(data.substring(midpoint));

            left.fork();

            int[] rightFreq = right.compute();
            int[] leftFreq = left.join();

            for (int i = 0; i < rightFreq.length; i++) {
                leftFreq[i] += rightFreq[i];
            }
            return leftFreq;
        }
    }
}
