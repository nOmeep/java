import java.util.Scanner;
import java.util.stream.IntStream;

public class CountSort {
    private Scanner in = new Scanner(System.in);

    private void countSort(int[] arr, int maxValue) {
        int length = arr.length;
        int[] out = new int[length];
        int[] allCounts = new int[maxValue]; // maxValue - максимальное возможное число в arr

        IntStream.range(0, length).forEach(x -> allCounts[arr[x] - 1] += 1);
        IntStream.range(1, maxValue).forEach(x -> allCounts[x] += allCounts[x - 1]);

        for (int i = length - 1; i >= 0; i--) {
            out[allCounts[arr[i] - 1] - 1] = arr[i];
            allCounts[arr[i] - 1]--;
        }

        IntStream.range(0, length).forEach(x -> System.out.print(out[x] + " "));
        System.out.println();
    }
    
    public void run() {
        int n = in.nextInt();
        int[] inputNums = new int[n];
        IntStream.range(0, n).forEach(x -> inputNums[x] = in.nextInt());
        countSort(inputNums, 10); // на сколько массив делать передается
    }

    public static void main(String[] args) {
        new CountSort().run();
    }
}