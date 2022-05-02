import java.util.*;

/*
    Условие задачи:
        По данной непустой строке s длины не более 10^4, состоящей из строчных букв латинского алфавита, постройте оптимальный беспрефиксный код. 
        В первой строке выведите количество различных букв k, встречающихся в строке, и размер получившейся закодированной строки. 
        В следующих k строках запишите коды букв в формате "letter: code". В последней строке выведите закодированную строку

    Sample Input:
        a
    Sample Output:
        1 1
        a: 0
        0
*/

public class HuffmanCodeString {
    private static final Scanner in = new Scanner(System.in);

    class Node implements Comparable<Node>{
        private String uniqueBinary; // кодирование символа в данной строке
        private int frequency; // частота конкретной вершины

        public Node(int frequency) {
            this.frequency = frequency;
            uniqueBinary = "";
        }

        public void setUniqueBinary(String uniqueBinary) {
            this.uniqueBinary = uniqueBinary;
        }

        public int getFrequency() {
            return frequency;
        }

        public String getUniqueBinary() {
            return uniqueBinary;
        }

        @Override
        public int compareTo(Node o) {
            return this.frequency - o.frequency;
        }
    }

    class InnerNode extends Node {
        private Node left;
        private Node right;

        public InnerNode(Node left, Node right) {
            super(left.getFrequency() + right.getFrequency());

            this.left = left;
            this.right = right;
        }

        public InnerNode(Node left) {
            super(left.getFrequency());
            this.left = left;
            this.right = new Node(0);
        }

        @Override
        public void setUniqueBinary(String uniqueBinary) {
            super.setUniqueBinary(uniqueBinary);
            left.setUniqueBinary(uniqueBinary + "0"); // левому узлу к коду добавлям 0
            right.setUniqueBinary(uniqueBinary + "1"); // правому узлу добавляем к коду 1
        }
    }

    class LeafNode extends Node {
        char symbol;

        public LeafNode(char symbol, int frequency) {
            super(frequency);

            this.symbol = symbol;
        }

        @Override
        public void setUniqueBinary(String uniqueBinary) { // здесь метод перегружен для удобного вывода
            super.setUniqueBinary(uniqueBinary);
            System.out.println(symbol + ": " + uniqueBinary);
        }
    }

    private void huffman(String inputString) {
        Map<Character, Integer> symbols = new HashMap<>(); // key-value для запоминания символов и их частоты

        for (int i = 0; i < inputString.length(); i++) { // проходим по всей строке
            char tmp = inputString.charAt(i); // значение текущего символа
            if (!symbols.containsKey(tmp)) { // символ уже встречался ранее?
                symbols.put(tmp, 1); // если не встречался -> засовываем, а частоту ставим 1
            } else {
                symbols.put(tmp, symbols.get(tmp) + 1); // если уже есть, увеличиваем его частоту
            }
        }

        PriorityQueue<Node> allNodes = new PriorityQueue<>(); // очередь с приоритето для вершин

        Map<Character, Node> mapWithBinaryCodes = new HashMap<>(); // мэп для хранения бинарных кодов, чтобы потом выводить их

        for (Map.Entry<Character, Integer> currentNode : symbols.entrySet()) { // супер крутой способ проитерировать по мэпу
            LeafNode tmpLeafNode = new LeafNode(currentNode.getKey(), currentNode.getValue());

            mapWithBinaryCodes.put(currentNode.getKey(), tmpLeafNode); // дабавляем в мэп с ключами созданную ноду, чтоб потом выводить коды

            allNodes.add(tmpLeafNode); // абузерские методы, вычлинающие ключ + значение конкретной ноды
        }

        int resultSum = 0;

        while (allNodes.size() > 1) {
            Node first = allNodes.poll(); // берем первый элемент с наименьшей частотой

            Node second = allNodes.poll(); // берем второй элемент с наименьшей частотой

            InnerNode tmpNode = new  InnerNode(first, second); // соединяем и заносим внутрь очереди, где сложатся и и частоты из-за реализации InnerNode

            resultSum += tmpNode.getFrequency();

            allNodes.add(tmpNode);
        }

        Node root = allNodes.poll();

        if (symbols.size() == 1) { // если вдруг у нас был всего один символ на всю строку
            resultSum = inputString.length();
            System.out.println(symbols.size() + " " + resultSum); //выводим количесво различных букв в строке и итоговую сумму частот
            root.setUniqueBinary("0");
        } else { // если 2 и более различных символов в строке
            System.out.println(symbols.size() + " " + resultSum); //выводим количесво различных букв в строке и итоговую сумму частот
            root.setUniqueBinary("");
        }

        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) { // каждый символ в строке заменяем на соответсвтующий бинарный код
            resultString.append(mapWithBinaryCodes.get(inputString.charAt(i)).getUniqueBinary());
        }

        System.out.println(resultString); // вывод закодированного сообщения;
    }

    public static void main(String... args) {
        String inputString = in.next();
        //System.out.println(inputString);
        new HuffmanCodeString().huffman(inputString);
    }
}