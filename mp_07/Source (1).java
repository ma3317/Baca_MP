//Magdalena Maksymiuk gr nr 4
import java.util.Scanner;

/*
* Zadanie polega na implementacji iteracyjnej wersji
* QuickSort, ktra dziala w oczekiwanym czasie O(nlogn)
oraz nie zawiera wywolania rekurencyjnego i jest bez
* implementacji stosu.
Algorytm wybiera jako pivot mediane trzech elementow do
* momentu gdy podzadania sa wieksze lub rowne 20 , pozostale
* podzadania nalezy wykonywac insertionSort.


 */

public class Source {
    public static Scanner scanner = new Scanner(System.in);
    public static int findPivot (long[] A,int l, int r, int m){
        //metoda znajdujaca mediane z 3 elementow tablicy
        long left = A[l];
        long right = A[r];
        long middle = A[m];
        int result =-1;
        if((left<=middle && right>=middle) || (right<=middle && left>=middle)){
            result = m;
        }
        else if((middle<=left && right>=left )|| (right<=left && middle>=left) ){
            result = l;
        }
        else{
            result = r;
        }

        return result;

    }


    public static void quickSort(long[] A, int n) {
        //metoda iteracyjna quickSort, ktora
        //aby nie uzywac stosu, organizuje go wewnetrznie
        //aby zaznaczyc indeksy podtablic
        //pierwsze rekurencyjne wywolanie zastepujemy petla while
        //natomiast drugie - zamiast tworzyc stos to korzystamy z takiego pomyslu
        // pare [L',R'] ktora zamiast umieszczac na stosie
        // zapamietujemy wartosc biezacego R i dzieki temu 
        // jestesmy w stanie policzyc L' kolejnej podtablicy
        //markowanie prawego konca robimy przy uzyciu 2 wartownikow
    

        A[n + 1] = Long.MAX_VALUE - 1; //wartownik 1
        A[n + 2] = Long.MAX_VALUE; //wartownik 2
        long p =0; //pivot
        int l = 0, r = n + 1, m = 19; //left, right, m - ilosc elemenow od ktorych zaczynamy sortowac insertionSortem
        int i =0;
        while (true) {
            do {
                i = l;
                int j = r;
                int k = findPivot(A,l, r, (l+r)/2); //pivot indeks
                p = A[k]; //wartosc pivota
                i =k;

                //partition w implementacji Hore'a
                do {
                    while (A[++i] < p); // po zakonczeniu petli  A[i]>= pivot
                    while (A[--j] > p); // po zakonczeniu petli A[j] <= pivot
                    if (i < j) { //jesli kolejnosc indeksow sie zmieni
                        swap(A, i, j);  //zamieniamy elementy A[i], A[j]
                    }
                } while (i < j); //jesli kolejnosc indeksow sie zmieni


                A[k] = A[j];
                A[j] = p; //pivot na wlasciwe m-ce
                swap(A, i, r); //zamiast dodawania operacji na stos
                //zamieniamy A[i] z wartownikiem
                r = j; // r -> index pivot

                if(r-l <= m){
                    insertSort(A, 0, r); //pozadanie ma mniej niz 20 elementow
                }


            } while (r - l > m);


            l = r; //ustawiamy l na r

            do {
                l++; //przesuwamy l w prawo
            } while (A[l] == p);

            if (l <= n) {



               p = A[l];
                r =l;

                do {
                    r++; //przesuwamy r w prawo
                } while (A[r] <= p);

                r--;
               A[l] = A[r];
                A[r] = p;

            } else {

                break;
            }
        }
    }

    public static int findPivotIndex(long[] A,long p) {
        //metoda znajdujaca indeks pivot
        for(int i=0;i<A.length;i++){
            if(A[i]==p){
                return i;
            }
        }
        return 0;
    }
    public static void insertSort(long[] arr, int left, int right) {
        //insertion sort sortujacy w przedzialach [left,right]
        int n = right;
        boolean sorted = false;
        for (int i = left + 1; i <= n; ++i) {
            long key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;


            }

            arr[j + 1] = key;
        }
    }

    public static void insertSort(long[] A, int n) {
        for (int i = 1; i < n; i++) {
            long key = A[i];
            int j = i - 1;
            while (j >= 1 && A[j] > key) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = key;
        }
    }

    public static void insertionSort2(long[] array, int low, int high){
        //insertion sort sortujacy w przedzialach [high,low]
        for(int i = low + 1; i <= high; i++){
            long key = array[i];
            int j = i - 1;

            while(j >= low && array[j] > key){
                array[j+1] = array[j];
                j--;
            }

            array[j+1] = key;
        }
    }

    //metoda zamieniajaca 2 elementy w tablicy
    private static void swap(long[] A, int i, int j) {
        long temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public static void main(String[] args) {


        int setAmount =scanner.nextInt(); //liczba zestawow danych
        for(int i=0; i<setAmount; i++){

            int tabSize = scanner.nextInt(); //ilosc elementow w tablicy
            long[] tab = new long[tabSize+2]; //tablica z elementami do posortowania
            for(int j=0; j<tabSize; j++){
                tab[j] = scanner.nextLong(); //wczytanie elementow do tablicy
            }

            //sortowanie
            if(tabSize <=20){
                //jezeli jest mniej niz 20 elementow w tablicy
                insertionSort2(tab,0,tabSize-1);

            }else{
                //wiecej niz 20 elementow w tablicy
                quickSort(tab, tab.length - 3);
            }


            for(int j=0; j<tabSize; j++){
                System.out.print(tab[j] + " "); //wyswietlanie tablicy
            }
            System.out.println();


        }


    }
}

/*
*
TESTY.IN
3
10
9 15 5 6 8 1 13 11 23 4
15
-34 -15 -5 -6 -300 -1 -13 -11 -187
-4 0 3 -10 7 1000
18
-17 -16 -15 -14 -13 -12 -11 -10 -9 -8

1
31
1 -1 12 6 5555 69 -7 82 99 10 -113 -12 -135 147 15 167 -179 18 -99 -20 -30 29 289 -2701 26 125 -24000 -23 22 21 16

TESTY.OUT
1 4 5 6 8 9 11 13 15 23
-300 -187 -34 -15 -13 -11 -10 -6 -5 -4 -1 0 3 7 1000
-17 -16 -15 -14 -13 -12 -11 -10 -9 -8 -7 -6 -5 -4 -3 -2 -1 0

-24000 -2701 -179 -135 -111 -99 -30 -23 -20 -12 -7 -1 1 4 10 12 15 18 22 26 29 69 82 99 125 147 167 211 289 5555



*  */