//Magdalena Maksymiuk gr nr 4
import java.util.Scanner;

/* Zadanie polega na napisaniu dwoch metod:
recPackage i StackSimPackage, ktore rozwiazuja problem pakowania plecaka
opisany na wykladzie.
Metody znajduja ciag elementow ktorych waga razem jest dokladnie
rowna pojemnosci plecaka.

* */

class stackArray {
    //implementacja stosu za pomoca tablicy
    //z wykladu
    public int[] tab; //tablica zawierajca stos
    public int maxSize; //rozmiar tablicy zawierajacej stos
    public int currentSize; //aktualny rozmiar stosu

    stackArray(int maxSize) {
        //konstruktor stosu
        this.maxSize = maxSize; //ustawiamy rozmiar stosu
        this.tab = new int[maxSize]; //tworzymy tablice dla elementow stosu
        this.currentSize = 0; //aktualny rozmiar stosu ustawiamy na 0
    }

    public void push(int element) {
        //wstaiwa element na szczyt stosu
        tab[currentSize] = element; //wstawiamy element na szczyt stosu
        currentSize++; //zwiekszamy aktualny rozmiar stosu
    }

    public int pop() {
        //usuwamy element ze szczytu stosu
        if (isEmpty()) {
            return -1;
            //w przypadku gdy stos jest pusty zwracamy -1
        } else {
            currentSize--; //zmniejszamy aktualny rozmiar stosu
            return tab[currentSize]; //pobieramy element ze szczytu stosu
        }
    }

    public int peek() {
        //zwraca wartosc elementu ze szczytu stosu
        if (isEmpty()) {
            return -1;
            //w przypadku gdy stos jest pusty zwracamy -1
        } else {
            //zwracamy element ze szczytu stosu ale go nie usuwamy
            return tab[currentSize - 1];
        }
    }


    public boolean isEmpty() {
        //funkcja sprawdzajaca czy stos jest pusty
        return currentSize == 0;
    }
    public boolean isFull() {
        //funkcja sprawdzajaca czy stos jest pelny
        return currentSize == maxSize;
    }

    }

 public class Source {
    public static int[] elems; //tablica do przechowywania elementow
    public static Scanner scanner = new Scanner(System.in);
    public static boolean isFind; //zmienna do oceniania czy znaleziono rozwiazanie
     public static boolean recPackage(int[] elems, int currVolume, int i, int backpackVolume, int[] solution){
         //funkcja rekurencyjna odpowiednia do StackSimPackage
       //funkcja znajduje rozwiazanie problemu zaznaczajac
         //w rozwiazaniowej tablicy 1 jezeli bierzemu element o konkretnym indeksie
         //oraz 0 jezli nie bierzemy go pod uwage
         //currVolume aktualna pojemnosc plecaka
         if (currVolume == backpackVolume) {
             return true; //zwraca prawde jezeli znaleziono juz rozwiazanie
             //zeby zapiewnic zgodna z zadaniem kolejnosc
         } else if (i != elems.length) {
             solution[i] = 1;// bierzemy element do rozwizania
             currVolume += elems[i]; //dodajemy element do aktualnej pojemnosci plecaka
             if (recPackage(elems, currVolume, i + 1, backpackVolume, solution)) {
                 return true;
             }
             currVolume -= elems[i]; //usuwamy element z aktualnej pojemnosci plecaka
             solution[i] = 0;// nie bierzemy elementu do rozwiaznia
             return recPackage(elems, currVolume, i + 1, backpackVolume, solution);
         }
         return false; //nie znaleziono rozwiazania
     }

     public static void StackSimPackage(int[] elems, int backpackVolume) {
         int n = elems.length; //ilosc mozliwych elementow w plecaku
         int i = 0;
         int currVolume = 0; //aktualna pojemnosc plecaka
         stackArray stack = new stackArray(n); //stos na ktorym bedziemy trzymac indeksy elementow
         //ktore idealnie maja taka sama wage jak plecak

         while (i < n) {
             stack.push(i); //dodajemy indeks elementu do stosu
             currVolume += elems[i]; //zwiekszamy aktualna pojemnosc plecaka

             if (currVolume == backpackVolume) {
                 //znaleziono rozwiazanie
                 isFind = true;
                 break;
             } else if (currVolume < backpackVolume) {
                 //jesli aktualna pojemnosc plecaka jest mniejsza od wlasciwej pojemnosci plecaka
                 //to mozemy dodac kolejny element 
                 if (i != elems.length - 1) {
                     //jesli to nie jest ostatni element tablicy
                     //to mozemy dodac kolejny element i przejsc dalej
                     i++;
                     continue;
                 } else {
                     //to jest ostatni element tablicy
                     //wiec nie mozemy dodac kolejnego elementu i to oznacza ze albo nie bedzie w ogole
                     //rozwiazania albo musimy zaczac szukanie od kolejnego elementu tablicy
                     currVolume -= elems[i];// usuniecie alktualnego elementu z aktualnej pojemnosci plecaka
                     stack.pop(); //usuniecie ostatniego elementu ze stosu
                     if (stack.isEmpty()) {
                         isFind = false; //jezeli stos jest pusty to znaczy ze nie ma rozwiazania i
                         //mozemy przerwac poszukiwania
                         break;
                     }
                     //jezeli natomiast stos nie jest pusty to zdejmujemy ostatni element ze stosu
                     //i bedziemy zaczynac od tego elementu szukac rozwiazania
                     currVolume -= elems[stack.peek()]; //usuniecie aktualnego elementu z aktualnej pojemnosci plecaka
                     i = stack.pop(); //ustawienie indeksu ostatniego elementu ze stosu jako szukany obecnie indeks
                 }

             } else if (currVolume > backpackVolume) {
                 //jesli aktualna pojemnosc plecaka jest wieksza od wlasciwej pojemnosci plecaka
                 //to trzeba sprawdzic czy to jest ostatni element tablicy
                 if (i != elems.length - 1) {
                     currVolume -= elems[i]; //usuniecie aktualnego elementu z aktualnej pojemnosci plecaka
                     stack.pop();//usuniecie ostatniego indeksu ze stosu
                 } else {
                     //jezeli to jest ostatni element tablicy to trzeba usunac analogicznie do poprzedniego przypadku
                     //indeksy zeby zaczac szukanie od kolejnego elementu tablicy
                     currVolume -= elems[i]; //usuniecie aktualnego elementu z aktualnej pojemnosci plecaka
                     stack.pop(); //usuniecie aktualnego indeksu ze stosu 
                     if (stack.isEmpty()) {
                         //stos jest pusty to znaczy ze nie ma rozwiazania 
                         //i mozemy przerwac poszukiwania
                         isFind = false;
                         break;
                     }
                     currVolume -= elems[stack.peek()]; //usuniecie aktualnego elementu z aktualnej pojemnosci plecaka
                     i = stack.pop(); //ustawienie aktualnego indeksu ze stosu jako szukany obecnie indeks
                 }
             }

             i++;

         }

         //wyswietlenie wyniku

         if (isFind) {
             System.out.print("ITER: "+ backpackVolume +" = ");
             for(int j =0 ; j<stack.currentSize; j++){
                 System.out.print(elems[stack.tab[j]]+" ");
                 //wyswietlenie rozwiazania
             }
             System.out.println();
         }



     }

    public static void main(String[] args) {
        int setAmount = scanner.nextInt(); //liczba zestawow
        for (int i = 0; i < setAmount; i++) {
            int backpackVolume = scanner.nextInt(); //szukana pojemnosc
            int elemsCount = scanner.nextInt(); //ilosc mozliwych elementow
            elems = new int[elemsCount]; //tablica do przechowywania elementow
            for (int j = 0; j < elemsCount; j++) {
                elems[j] = scanner.nextInt(); //wypelniamy tablice elementami
            }
            isFind = false; //na poczatku zakladamy ze nie ma takiej sumy
            int[] solution = new int[elems.length]; //tablica do przechowywania rozwiazan
            //jezeli recPackage zwroci prawda to znaczy ze znaleziono rozwiazanie
            //w przeciwnym wypadku znaczy ze nie ma takiej sumy
            if(recPackage(elems, 0, 0,backpackVolume,solution)){
                System.out.print("REC: "+backpackVolume+" = ");
                for (int k = 0; k < solution.length; k++) {
                    if (solution[k] == 1) {
                        System.out.print( elems[k] +" "); //wyswietlenie rozwiazania
                    }
                }
                System.out.println();
                StackSimPackage(elems, backpackVolume); //wywolanie funkcji iteracyjnej StackSimPackage
            }
            else{
                System.out.println("BRAK"); //wyswietlenie braku rozwiazania
            }



        }
    }



}

/**
 * testy in;
 * 2
20
5
11 8 7 6 5
21
3
5 6 7

testy out
REC: 20 = 8 7 5 
ITER: 20 = 8 7 5 
BRAK


testy in
4
21
7
11 8 7 6 5 12 8
50
3
5 6 7 12
15
5
11 8 7 6 5
20
3
5 6 7

testy out
REC: 21 = 8 7 6 
ITER: 21 = 8 7 6 
BRAK
REC: 15 = 8 7 
ITER: 15 = 8 7 
BRAK



 */