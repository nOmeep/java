import java.util.*;

/*
    Условие задачи:
        Восстановите строку по её коду и беспрефиксному коду символов. 
        В первой строке входного файла заданы два целых числа k и l через пробел — количество различных букв, встречающихся в строке, 
        и размер получившейся закодированной строки, соответственно. 
        В следующих k строках записаны коды букв в формате "letter: code".
        Ни один код не является префиксом другого. Буквы могут быть перечислены в любом порядке. 
        В качестве букв могут встречаться лишь строчные буквы латинского алфавита; каждая из этих букв встречается в строке хотя бы один раз. 
        Наконец, в последней строке записана закодированная строка. Исходная строка и коды всех букв непусты. 
        Заданный код таков, что закодированная строка имеет минимальный возможный размер.
        В первой строке выходного файла выведите строку s. Она должна состоять из строчных букв латинского алфавита. 
        Гарантируется, что длина правильного ответа не превосходит 10^4 символов.

    Sample Input 1:
        1 1
        a: 0
        0
    Sample Output 1:
        a
*/


public class DecodeHuffman {
    public static Scanner in = new Scanner(System.in);

    public void decode() {
        int diffLetters = in.nextInt(); // количество различный символов строки
        int codedStringLength = in.nextInt(); // длина закодированной строки

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < diffLetters; i++) {
            String[] letter = in.next().split(":");

            map.put(in.next(), letter[0]);
        }

        String codedString = in.next(); // сама закодированная строка

        StringBuilder resultString = new StringBuilder(); // куда буду конкатенировать и реплейсать
        StringBuilder tmpString = new StringBuilder(); // где будет постепенно храниться код и откуда будет вноситься в декодированную строку
        for (int i = 0; i < codedStringLength; i++) {
            tmpString.append(codedString.charAt(i)); // посимвольно считываем строку

            if (map.get(tmpString.toString()) != null) { // если по такому ключу существует значение
                resultString.append(map.get(tmpString.toString())); // добавляем в итоговую строку раскодированный символ
                tmpString.setLength(0);
            }
        }

        System.out.println(resultString);
    }

    public static void main(String[] args) {
        new DecodeHuffman().decode();
    }
}