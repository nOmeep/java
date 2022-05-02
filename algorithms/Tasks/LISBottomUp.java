import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/*
    Условие задачи:
        Дано целое число 1≤ n ≤ 10^3 и массив A[1…n] натуральных чисел, не превосходящих 2⋅10^9.
        Выведите максимальное 1 ≤ k ≤ n, для которого найдётся подпоследовательность 1 ≤ i1 < i2 < … < ik ≤ n длины k,
        в которой каждый элемент делится на предыдущий (формально: для  всех 1 ≤ j < k, A[ij] ∣ A[ij+1]).
*/

public class LISBottomUp {
    private final static Scanner in = new Scanner(System.in);

    public int run(int[] arr) {
        int[] arr_D = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            arr_D[i] = 1;
            for (int j = 0; j < i; j++) {
                if ((arr[j] <= arr[i]) && (arr_D[j] + 1 > arr_D[i]) && (arr[i] % arr[j] == 0)) {
                    arr_D[i] = arr_D[j] + 1;
                }
            }
        }

        System.out.println(Arrays.toString(arr_D));

        int[] ans = {0};
        IntStream.range(0, arr.length).forEach(i -> ans[0] = ans[0] > arr_D[i] ? ans[0] : arr_D[i]);

        return ans[0];
    }

    public static void main(String[] args) {
        int n = in.nextInt();
        int[] arr = new int[n];
        IntStream.range(0, arr.length).forEach(i -> arr[i] = in.nextInt());
        System.out.println(new LISBottomUp().run(arr));
    }
}