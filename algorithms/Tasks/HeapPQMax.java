import java.util.*;

/*
    Условие задачи:
        Первая строка входа содержит число операций 1 <= n <= 10^5. 
        Каждая из последующих n строк задают операцию одного из следующих двух типов:
            Insert x, где 0 <= x <= 10^9 — целое число;
            ExtractMax;
        Первая операция добавляет число xx в очередь с приоритетами, вторая — извлекает максимальное число и выводит его.
*/

class Node {
    private int key;

    public Node(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

class PriorityQueueOnHeap { // Куча
    private Node[] heapArray;
    private int maxSize;
    private int currentSize;

    public PriorityQueueOnHeap(int maxSize) {
        this.maxSize = maxSize;
        currentSize = 0;
        heapArray = new Node[maxSize]; // задаем размер массива, хотя можно было бы и ArrayList сделать, но можно и расширение с перекопированием сделать
    }

    public Node extractMax() { // удаление вершины
        Node root = heapArray[0]; // как и говорилось в лекции. Перемещаем в конец и удаляем его)
        heapArray[0] = heapArray[--currentSize]; // меняем местами
        trickleDown(0); // просеивание
        return root;
    }

    public void trickleDown(int index) { // метод просеивания вниз при удалении вершины, тк нужно идти от корня вниз
        int largerChild;
        Node top = heapArray[index];

        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1; // левый потомок
            int rightChild = 2 * index + 2; // правый потомок

            if (rightChild < currentSize && heapArray[leftChild].getKey() < heapArray[rightChild].getKey()) { // существует ли правый потомок
                largerChild = rightChild; // определяем наибольшего потомка
            } else {
                largerChild = leftChild;
            }

            if (top.getKey() >= heapArray[largerChild].getKey()) {
                break;
            }

            heapArray[index] = heapArray[largerChild];
            index = largerChild;
        }

        heapArray[index] = top;
    }


    public boolean insert(int key) {
        if(currentSize == maxSize) {
            return false;
        }

        Node newNode = new Node(key);
        heapArray[currentSize] = newNode;

        trickleUp(currentSize++);

        return true;
    }

    public void trickleUp(int index) { // просеивание вверх после добавления элемента в кучу
        int parent = (index - 1) / 2;
        Node bottom = heapArray[index];

        while(index > 0 && heapArray[parent].getKey() < bottom.getKey() ) {
            heapArray[index] = heapArray[parent]; // Смещаем вниз до тех пор, пока родитель либо не корень, либо меньше вставляемого

            index = parent;
            parent = (parent - 1) / 2;
        }

        heapArray[index] = bottom;
    }

}
public class HeapPQMax {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int nel = in.nextInt();
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap(nel);

        for (int i = 0; i < nel; i++) {
            String inputCommand = in.next();

            if (inputCommand.equals("Insert")) {
                int value = in.nextInt();

                pq.insert(value);
            } else {
                System.out.println(pq.extractMax().getKey());
            }
        }
    }
}