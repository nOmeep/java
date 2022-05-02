import java.util.*;

public class Levenstain {
    private static Scanner in = new Scanner(System.in);

    private static PriorityQueue<Integer> result = new PriorityQueue<>();

    public static int levenstain(String str1, String str2) {
        int str1_length = str1.length();
        int str2_length = str2.length();
        int[] Di_1 = new int[str2_length + 1];
        int[] Di = new int[str2_length + 1];

        for (int j = 0; j <= str2_length; j++) {
            Di[j] = j;
        }

        for (int i = 1; i <= str1_length; i++) {
            System.arraycopy(Di, 0, Di_1, 0, Di_1.length);

            Di[0] = i;
            for (int j = 1; j <= str2_length; j++) {
                int cost = (str1.charAt(i - 1) != str2.charAt(j - 1)) ? 1 : 0;
                Di[j] = min(
                        Di_1[j] + 1,
                        Di[j - 1] + 1,
                        Di_1[j - 1] + cost
                );
            }
        }

        return Di[Di.length - 1];
    }

    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }

    private static void solve(String a, String b) {
        int b_length = b.length();
        for (int i = 0; i < a.length() - b_length + 1; i++) {
            StringBuilder currPos = new StringBuilder();
            currPos.append(a, i, i + b_length);
            int addCount = levenstain(currPos.toString(), b);
            result.add(addCount);
        }
    }

    public static void main(String[] args) {
        String first = in.next();
        String second = in.next();
        solve(first, second);
        System.out.println(result.poll());
    }
}
