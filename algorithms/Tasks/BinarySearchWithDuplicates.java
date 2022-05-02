import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

/*
    Условие задачи:
        В первой строке задано два целых числа 1 ≤ n ≤ 50000 и 1 ≤ m ≤ 50000 — количество отрезков и точек на прямой, соответственно. 
        Следующие n строк содержат по два целых числа a_i и b_i (a_i ≤ b_i) — координаты концов отрезков. 
        Последняя строка содержит m целых чисел — координаты точек. Все координаты не превышают 10^8 по модулю. 
        Точка считается принадлежащей отрезку, если она находится внутри него или на границе. 
        Для каждой точки в порядке появления во вводе выведите, скольким отрезкам она принадлежит.
*/

public class BinarySearchWithDuplicates {
    private static final Scanner in = new Scanner(System.in);

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            int pivot = partition(arr, low, high);

            if (pivot - low < high - pivot) { // во избежание переполнения стека вызовов вызываем рекурсию для наименьшей подпоследовательности
                quickSort(arr, low, pivot - 1); // left partition
                low = pivot + 1;
            }
            else {
                quickSort(arr, pivot + 1, high); // right partition
                high = pivot - 1;
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        swap(arr, low, getRandomPivot(low, high));

        int border = low + 1;

        for (int i = border; i <= high; i++) {
            if (arr[i] < arr[low]) {
                swap(arr, i, border++);
            }
        }

        swap(arr, low, border - 1);

        return border - 1;
    }

    // Два вспомогательных метода ниже

    private static int getRandomPivot(int low, int high) { // случайный опорный элемент
        Random rand = new Random();
        return rand.nextInt((high - low) + 1) + low;
    }

    private static void swap(int[] arr, int i, int j) { // обычный метод своп
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    // Бинарный поиск(в массиве с повторениями) для решения задачи

    // Поиск индекса первого элемента = х
    private static int binarySearchWithDuplicatesOnRightBound(int[] arr, int x) {
        int leftIndex = -1;
        int rightIndex = arr.length;

        while (leftIndex + 1 < rightIndex) {
            int medium = (leftIndex + rightIndex) >> 1;
            if (arr[medium] < x) {
                leftIndex = medium;
            }
            else {
                rightIndex = medium;
            }
        }
        if (rightIndex < arr.length && arr[rightIndex] == x) { // индекс первого найденного элемента
            return rightIndex;
        }
        // Если такого нет, тогда и элементов ему соответсвуют (если бы не задача, возвращал бы -1)
        // ПОэтому возвращаем число элементов, меньших его
        return leftIndex + 1;
    }

    // Поиск индекса последнего элемента = х
    private static int binarySearchWithDuplicatesOnLeftBound(int[] arr, int x) {
        int leftIndex = -1;
        int rightIndex = arr.length;

        while (leftIndex + 1 < rightIndex) {
            int medium = (leftIndex + rightIndex) >> 1;
            if (arr[medium] <= x) {
                leftIndex = medium;
            }
            else {
                rightIndex = medium;
            }
        }
        // Выдаем последний элемент, равный иксу
        // Даже если нет такого, выдаем индекс, куда бы он мог встать в случае чего
        return leftIndex + 1;
    }

    public static void main(String[] args) {
        int segmentCount = in.nextInt();
        int dotsCount = in.nextInt();

        int[] leftBoundArr = new int[segmentCount];
        int[] rightBoundArr = new int[segmentCount];
        int[] dotsArr = new int[dotsCount];

        IntStream.range(0, segmentCount).forEach( x -> {
            leftBoundArr[x] = in.nextInt();
            rightBoundArr[x] = in.nextInt();
        });

        IntStream.range(0, dotsCount).forEach(x -> dotsArr[x] = in.nextInt());

        quickSort(leftBoundArr);
        quickSort(rightBoundArr);

        Arrays.stream(dotsArr).forEach(x -> {
            System.out.print(binarySearchWithDuplicatesOnLeftBound(leftBoundArr, x) - binarySearchWithDuplicatesOnRightBound(rightBoundArr, x) + " ");
        });
    }
}
