import java.util.ArrayList;
import java.util.Scanner;
/*
    Условие задачи:
        Даны целые числа 1 <= n <= 10^18 и 2 <= m <= 10^5 , необходимо найти остаток от деления n-го числа Фибоначчи на m.
*/
public class PizanoFib {
    private static Scanner in = new Scanner(System.in);

    public static long pizano(long n, long m) {
        ArrayList<Long> arr = new ArrayList<>(2);
        arr.add((long)0);
        arr.add((long)1);
        long periodLength = 0;
        for (int i = 2; i < m * m + 1; i++) {
            arr.add((arr.get(i - 1) + arr.get(i - 2)) % m); // как обычно считаем фиббоначи, но остаток от деления на м
            periodLength++; // увеличиваем длину периода
            if (arr.get(i) == 1 && arr.get(i - 1) == 0) { // елси вдруг нашелся наш период
                break;
            }
        }
        return arr.get((int)(n % periodLength));
    }

    public static void main(String[] args) {
        long n = in.nextLong();
        long m = in.nextLong();
        System.out.println(pizano(n, m));
    }
}
