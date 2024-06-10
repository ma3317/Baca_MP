//Magdalena Maksymiuk gr nr 4
/*
* Zadanie polega na implementacji rekurencujnej metody
* ktora znajduje k-ty co do wielkosci element tablicy w
* porzadku niemalejacym (liczac od najmniejszego).
* Metoda dziala w przypadku pesymistycznym w O(n) oraz dziala w :miejscu
*
* Alorytm rozwiazujacy podany problem dziala analogicznie do
* algorytmu Hore'a przy zalozeniu ze M - dzieli zbiory S1 i S3 tak
* ze sa nie wieksze niz 3/4 zbioru ulegajacemu podzialowi

* Wyjasnienie zlozonosci: warto zauwazyc ze jedno podzadanie
* jest wielkosci n/5 (wyznaczenie mediany median) oraz
* jedno podzadanie S1 lub S3 wielkosci co najwyzej 3*n/4,
* oraz  posortowanie "piatek" jest liniowym kosztem
*  zatem zauwazmy ze 1/5 +3/4 <1
*/

import java.util.Scanner;

public class Source {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int setAmount = scanner.nextInt(); // liczba zestawww

        for (int i = 0; i < setAmount; i++) {
            int n = scanner.nextInt(); // dlugosc tablicy
            int[] tab = new int[n]; // tablica z liczbami
            for (int j = 0; j < n; j++) {
                tab[j] = scanner.nextInt(); // wczytanie danych
            }

            int m = scanner.nextInt(); // liczba zapytan o k-ty element
            for (int p = 0; p < m; p++) {
                int k = scanner.nextInt(); // wczytanie danych
                if (k >= 1 && k <= n) {
                    int kthElement = medianOfMedians(tab, 0, n - 1, k - 1);
                    System.out.println(k + " " + kthElement);
                } else {
                    System.out.println(k + " brak");
                }
            }
        }

    }

    // algorytm mediana median
    public static int medianOfMedians(int[] array, int left, int right, int k) {
       //metoda znajdujaca rekurencyjnie mediane median
        if (left == right) { //jesli jest tylko 1 element
            return array[left];
        }

        int pivotValue = selectPivotValue(array, left, right); //wybieramy pivot
        int pivotIndex = partition(array, left, right, pivotValue); //dzielimy tablice pivotem

        if (k == pivotIndex) { //jesli pivot jest k-tym elementem
            return array[k]; //zwracamy pivot
        } else if (k < pivotIndex) { //k-ty element jest po lewejs stronie pivota
            return medianOfMedians(array, left, pivotIndex - 1, k); //szukamy  lewej podtablicy
        } else {
            return medianOfMedians(array, pivotIndex + 1, right, k); //szukamy w prawej potablicy
        }
    }

    // wybieramy wartosc pivota
    public static int selectPivotValue(int[] array, int left, int right) {
        //wybiera wartosc pivota
        if (right - left + 1 <= 5) { //jesli podtablica ma mniej niz 5 elementow
            return array[partition5(array, left, right)];
        }

        //dzieli tablice na grupy po 5 elementow
        //moze byc 1 grupa ktora ma mniej niz 5 elementow

        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right); //prawy koniec
            int median5 = partition5(array, i, subRight); //mediana aktualnej grupy
            swap(array, median5, left + (i - left) / 5); //przeniesienie mediany na poczatek tablicy
        }

        int mid = (right - left) / 10 + left + 1; //szukanie indeksu srodkowej mediany
        //rekurencyjne wyszukanie mediany median
        return medianOfMedians(array, left, left + (right - left) / 5, mid);
    }

    // metoda sortujaca podtablice i zwracajaca indeks jej mediany
    public static int partition5(int[] array, int left, int right) {
        insertionSort(array, left, right); //sortowanie podtablicy
        return (left + right) / 2; //indeks mediant
    }

    // insertionSort dla niewielkich podtablic
    public static void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= left && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    public static int partition(int[] array, int left, int right, int pivotValue) {
        int pivotIndex = left;
        //szukanie indeksu pivota
        for (int i = left; i <= right; i++) {
            if (array[i] == pivotValue) {
                pivotIndex = i;
                break; //przerwanie petli po znalezieniu pivota
            }
        }
        swap(array, pivotIndex, right); //pivot na koniec tablicy
        int storeIndex = left; //indeks dla operacji mniejszych od pivota
        for (int i = left; i < right; i++) {
            if (array[i] < pivotValue) {
                swap(array, storeIndex, i);
                storeIndex++;
            }
        }
        swap(array, storeIndex, right); // Przeniesienie pivota na odpowiednie miejsce

        return storeIndex;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

//testy
/*
*


test.in
6
5
1 2 3 4 5
3
1 2 3
5
5 3 4 4 3
5
2 5 1 3 4
10
1 1 1 1 1 1 1 1 1 1
5
1 10 0 -1 11
5
1 2 6 4 5
3
0 7 5
5
5 3 7 4 12
5
2 5 1 3 4
10
0 1 2 3 4 5 6 7 8 9
5
1 10 0 -1 11

test.out
1 1
2 2
3 3
2 3
5 5
1 3
3 4
4 4
1 1
10 1
0 brak
-1 brak
11 brak
0 brak
7 brak
5 6
2 4
5 12
1 3
3 5
4 7
1 0
10 9
0 brak
-1 brak
11 brak

*
*
* */