import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class BinarySearch {
    private static Scanner in = new Scanner(System.in);

    public static int binarySearch(int x, int[] arr) {
        int l = 0;
        int r = arr.length - 1;
        int currElem;

        while (l <= r) {
            currElem = (r + l) / 2;
            if (arr[currElem] == x) {
                return currElem + 1; // по постановке задачи надо инкрементить индекс
            }
            else if (arr[currElem] > x) {
                r = currElem - 1;
            }
            else {
                l = currElem + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int n = in.nextInt();

        int[] arrN = new int[n];
        IntStream.range(0, arrN.length).forEach(x -> arrN[x] = in.nextInt());
        Arrays.sort(arrN);

        int k = in.nextInt();
        int[] arrK = new int[k];
        IntStream.range(0, arrK.length).forEach(x -> arrK[x] = in.nextInt());

        for (int i = 0; i < arrK.length; i++) {
            System.out.print(binarySearch(arrK[i], arrN) + " ");
        }

        System.out.println();
    }
}