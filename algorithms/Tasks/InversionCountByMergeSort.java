import java.util.Scanner;
import java.util.stream.IntStream;

/*
    Условие задачи:
        Первая строка содержит число 1≤n≤10, вторая — массив A[1…n], содержащий натуральные числа, не превосходящие 10^9. 
        Необходимо посчитать число пар индексов 1 ≤ i < j ≤ n, для которых A[i]>A[j]. (Такая пара элементов называется инверсией массива. 
        Количество инверсий в массиве является в некотором смысле его мерой неупорядоченности: например, в упорядоченном по неубыванию массиве инверсий нет вообще, 
        а в массиве, упорядоченном по убыванию, инверсию образуют каждые два элемента.)
*/

public class InversionCountByMergeSort {
    private static final Scanner in = new Scanner(System.in);

    private static long inversionCount = 0; // число инверсий может превышать инт

    public static int[] recMergeSort(int[] arr, int lowerBound, int higherBound) {
        if (arr.length == 0) { // если подается путой массив, тогда возвращаем его же
            return arr;
        }

        if (lowerBound < higherBound) {
            int medium = (lowerBound + higherBound) / 2;

            return merge(recMergeSort(arr, lowerBound, medium), recMergeSort(arr, medium + 1, higherBound));
        }

        return new int[] {arr[lowerBound]};
    }

    private static int[] merge(int[] arr1, int[] arr2) {
        int firstPointer = 0; // индекс текущего элемента в первом массиве
        int secondPointer = 0; // индекс текущего элемента во втором массиве

        int arr1Length = arr1.length;
        int arr2Length = arr2.length;

        int[] mergedResult = new int[(arr1Length + arr2Length)]; // возвращаемы слитый результат

        for (int mergedIndex = 0; mergedIndex < arr1Length + arr2Length; mergedIndex++) {
            if (firstPointer < arr1Length && secondPointer < arr2Length) { // сливаем до тех пор, пока какой-либо из массивов не закончится
                if (arr1[firstPointer] <= arr2[secondPointer]) {
                    mergedResult[mergedIndex] = arr1[firstPointer++]; // сливаем и сдвигаем указатель
                }
                else {
                    mergedResult[mergedIndex] = arr2[secondPointer++]; // сливаем и сдвигаем указатель
                    inversionCount += arr1Length - firstPointer; // увеличиваем число инверсий
                }
            }
            else { // досливание остатков там, где не до конца дошли
                if (firstPointer < arr1Length) {
                    mergedResult[mergedIndex] = arr1[firstPointer++]; // досливаем первую
                }
                else {
                    mergedResult[mergedIndex] = arr2[secondPointer++]; // досливаем вторую
                }
            }
        }

        return mergedResult;
    }

    public static void main(String[] args) {
        int nel = in.nextInt();
        int[] arr = new int[nel];
        IntStream.range(0, nel).forEach(x -> arr[x] = in.nextInt());

        //Arrays.stream(arr).forEach(System.out::println);

        recMergeSort(arr, 0, nel - 1);

        System.out.println(inversionCount);
    }
}
