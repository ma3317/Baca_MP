//Magdalena Maksymiuk gr nr 4
import java.util.Scanner;

/*
Zadanie polega na napisaniu programu, ktorego zadaniem bedzie
scalenie n posortowanych ciagow liczb calkowitych, ktorych dlugosci nie
beda wieszke niz m,a caly program ma dzialac w O(m*n log2n)
i wykorzystywac tablicowa implementacje kopca

 */

public class Source {
    public static  Scanner scanner = new Scanner(System.in);
    //klasa reprezentujaca element w kopcu
    static class Element {
        int value; //wartosc elementu
        int arrayIndex; //indeks tablicy z ktorej pochodzi element
        int elementIndex; //indeks elementu w tablicy

        //konstruktor
        public Element(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
    }


    private static void mergeAndPrintArrays(int[][] arrays, int n) {
        //funkcja scalajaca i wypisujaca posortowane ciagi
        Element[] heap = new Element[n]; //kopiec tablicowy przechowujacy elementy
        int heapSize = 0; //aktualny rozmiar kopca

        // Inicjalizacja kopca elementami poczatkowymi z kazdego ciagu
        for (int i = 0; i < n; i++) {
            if (arrays[i].length > 0) {
                heap[heapSize] = new Element(arrays[i][0], i, 0);
                heapifyUp(heap, heapSize); //przesuniecie elementu w gore kopca
                heapSize++; //zwiekszenie rozmiaru kopca
            }
        }

        StringBuilder result = new StringBuilder();

        // Scala wszystkie ciagi uzywajac kopca
        while (heapSize > 0) {
            Element minElement = heap[0]; //najmniejszy element kopca
            result.append(minElement.value).append(" ");

            int nextIndex = minElement.elementIndex + 1;
            if (nextIndex < arrays[minElement.arrayIndex].length) {
                heap[0] = new Element(arrays[minElement.arrayIndex][nextIndex], minElement.arrayIndex, nextIndex); //aktualizacja kopca
            } else {
                heap[0] = heap[--heapSize]; //zmieniejszenie kopca
            }
            heapifyDown(heap, heapSize, 0); //przesuniecie korzenia kopca w dol
        }

        //wyswielenie wyniku scalania
        System.out.println(result.toString().trim());
    }

    //przesuwanie elementu w gore kopca
    private static void heapifyUp(Element[] heap, int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2; //wyznaczenie indeksu rodzica
            if (heap[parentIndex].value > heap[index].value) {
                //zamianan elementow i aktualizacja indeksow
                swap(heap, parentIndex, index);
                index = parentIndex;
            } else {
                //jesli element jest na wlasciwym m-cu
                break;
            }
        }
    }


    private static void heapifyDown(Element[] heap, int size, int index) {
        //funkcja przesuwajaca w dol kopca
        int smallest = index; //ustawiamy min element jako korzen
        int leftChild = 2 * index + 1; //indeks lewego dziecka
        int rightChild = 2 * index + 2; //indeks prawego dziecka

        if (leftChild < size && heap[leftChild].value < heap[smallest].value) {
            //jesli lewe dziecko jest mniejsze od obecnego min
            smallest = leftChild;
        }

        if (rightChild < size && heap[rightChild].value < heap[smallest].value) {
            //jesli prawe dziecko jest mniejsze od obecnego min
            smallest = rightChild;
        }

        if (smallest != index) {
            //jesli najmniejszy element nie jest korzeniem
            swap(heap, index, smallest);
            heapifyDown(heap, size, smallest);
        }
    }

    private static void swap(Element[] heap, int i, int j) {
        Element temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {


        int setAmount = scanner.nextInt(); // Liczba zestawow danych

        for (int setIndex = 0; setIndex < setAmount; setIndex++) {
            int n = scanner.nextInt(); // Liczba ciagow
            int[] lengths = new int[n];  //tablica przechowujaca dlugosci ciagow

            for (int i = 0; i < n; i++) {
                lengths[i] = scanner.nextInt(); //wczytywanie dlugosci kazdego z ciagow
            }

            int[][] arrays = new int[n][]; //tablica przechowujaca wszystkie ciagi

            //petla wczytujaca wszystkie elementy kazdego ciagu
            for (int i = 0; i < n; i++) {
                arrays[i] = new int[lengths[i]];
                for (int j = 0; j < lengths[i]; j++) {
                    arrays[i][j] = scanner.nextInt();
                }
            }
            //scalanie i wyswietlanie posortownych ciagow
            mergeAndPrintArrays(arrays, n);
        }

        scanner.close();
    }
}

/*

test.in
1
5
1 7 3 10 4
0
1 3 5 17 21 77 46
2 4 6
1 2 3 4 5 6 7 8 9 10
5 12 7 3

test.out
0 1 1 2 2 3 3 4 4 5 5 5 6 6 7 8 9 10 12 7 3 17 21 77 46


test2.in
2
3
1 2 3
7
0 0
0 0 0
5
3 12 4 5 1
2 12 99
2 11 13 14 15 16 33 49 52 67 88 92
44 72 89 91
21 33 45 61 52
42
test2.out
0 0 0 0 0 7
2 2 11 12 13 14 15 16 21 33 33 42 44 45 49 52 61 52 67 72 88 89 91 92 99

**/