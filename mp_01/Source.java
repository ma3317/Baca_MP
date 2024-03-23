// Magdalena Maksymiuk gr 4

//Idea programu
/*
Program bedzie zajmowal sie wyznaczaniem najwiekszej nieujmnej tablicy o jak najmniejszej liczbie elementow,
ktorych indeksy beda tworzyc ciag leksykograficznie najmniejszy. Problem zostal rozwiazany za pomoca algorytmu Kadana, ktory zostal 
dostosowany do tablic zarowno jednowymiarowych jak i dwuwymiarowych.

Aby program dzialal dla tablic dwuwymiarowych korzystalismy z dodatkowej, pomocniczej tablicy jednowymiarowej o rozmiarze takim samym 
jak liczba wierszy. W tej pomocniczej tablicy wyznaczamy algorytmem kadana maksymalna podtablice z zastrzezeniem ze ta tablica ma miec
jak najmniejszy rozmiar.

Kiedy juz wyznaczymy podtablice jednowymiarowa o najmniejszym rozmiarze, to musimy zapamietac jej indeksy. Wtedy korzystajac juz z uprzednio
wyznaczonej tablica dodatkowej wyznaczamy kolejna podtablice, w nastepujacy sposob : jesli suma podtablicy juz wyznaczonej jest wieksza od tablicy
maksymalnej to nadpisujemy jej wartosci z zastrzezeniem, ze wymiary naszej podtablicy musza byc jak najmniejsze (min pole prostokata). 
Nastepnie uzupelniamy wartosciami zerowymi tablice pomocnicza, aby moc jeszcze raz uzupelnic ja wartosciami z kolejnej "kolumny", powtarzajac uprzednie 
kroki, do momentu "przejscia" w ten sposob calej tablicy dwuwymiarowej.



*/
import java.util.Scanner;

public class Source {
    //zmienne przechowujace numery indeksow maksymalnej podtablicy oraz jej sume
    int rowBeg; 
    int rowEnd;
    int colBeg;
    int colEnd;
    int mSum;
    
    @Override
        public String toString() {
            //funkcja wypisujaca dane na wyjscie, przysloniecie metody toString
            return "ms_tab = a[" + rowBeg + ".." + rowEnd + "][" + colBeg + ".." + colEnd + "], msum=" + mSum;
        }
    
    
    public static int [] findMaxTab1D(int[] tab){
        //funkcja wyznaczajaca maksymalna podtablice algorytmem kadana
        int[] result = new int[3]; //tablica przechowujaca rezultat
        int currBegin = 0; // aktualny poczatek podtablicy
        int currSum = tab[0]; //aktualna maksymalna suma - pierwszy element tablicy
        int maxSum = tab[0]; // maksymalna suma - pierwszy element tablicy
        int begin = 0; // poczatek podtablicy
        int end = 0; // koniec podtablicy
        
        for(int i = 1; i < tab.length; i++){
            
            if(tab[i] < (tab[i] + currSum)){
                //sprawdzamy czy "oplaca" nam sie dodawac element tablicy do sumy
                //wazne w przypadku ujemnych
                currSum += tab[i]; //dodanie elementu tablicy do sumy
                if(currSum > maxSum){
                    //sprawdzenie czy dotychczasowa suma jest wieksza od aktualnej max sumy
                    maxSum = currSum; //przypisanie nowej wartosci aktualnej max sumy
                    end = i; //aktualizacja konca podtablicy
                    
                }
            } else {
                
                currSum = tab[i]; //ustawienie wartosci tablicy jako maksymalna sume
                currBegin = i; //aktualizacja dotychczasowego poczatku tablicy
                if(maxSum < currSum){
                    //sprawdzenie czy maksymalna wartosc nie jest mniejsza od dotychczasowej
                    maxSum = currSum; //aktualizacja maksymalnej sumy
                    end = i; //akualizacja konca podtablicy
                    begin = i; ////akualizacja poczatku podtablicy
                }
            }
            
            if(maxSum == currSum){
                //w przypadku kiedy zarowno suma aktualna jak i dotychczasowa sa
                //sobie rowne musimy sprawdzic czy aktualna maksymalna suma ma najmniejsza
                //liczbe elementow i czy jej indeksy sa w kolejnosci leksykograficznej
                if((i - currBegin + 1) < (end - begin + 1)){
                    end = i; //aktualizacja konca podtablicy
                    begin = currBegin; //aktualizacja poczatku podtablicy
                }
            }
        }
        
        //przypisanie wartosci do tablicy zwracajacej wynik
        result[0] = maxSum;
        result[1] = begin;
        result[2] = end;
        
        return result;
    }

    public static int[] findMaxTab2D(int tab[][]){
        int rows = tab.length; //liczba wierszy w tablicy
        int columns = tab[0].length; //liczba kolumn w tablicy
        int columnBeg = 0;//  wartosc indeksu poczatku kolumny(lewej czesci tablicy)
        int columnEnd = 0; //wartosc indeksu konca kolumny(prawej czesci tablicy)
        int rowBegin = 0; // wartoscindeksu poczatku wiersza (gory tablicy)
        int rowEnd = 0;// wartosc indeksu konca wiersza (dolu tablicy)
        int maxSum = 0;
        int[] result = new int[5]; 
        
        for (int left = 0; left < columns; left++){
            int[] temp = new int[rows];
            
            //przypisanie wszystkim elementom tabeli pomocniczej wartosci 0
            for(int p = 0; p < rows; p++){

                temp[p] = 0;
            }
            
            for(int right = left; right < columns; right++){
                
                for(int i = 0; i < rows; i++){
                    temp[i] += tab[i][right]; //dodawanie wartosci z kolejnych kolumn do tablicy pomocniczej
                }
                
                int[] kadane = findMaxTab1D(temp); //wyznaczenie podtablicy z tablicy pomocniczej
                int currSum = kadane[0];  //aktualna maksymalna suma
                int currBegin = kadane[1];  //aktualny poczatek podtablicy
                int currEnd = kadane[2]; //aktualny koniec podtablicy
                
                if((left == 0 && right == 0) || (currSum > maxSum)){
                    //aktualizacja maksymalnej sumy i indeksow w przypadku
                    //gdy mamy poczatek tablicy badz aktualna suma jest wieksza niz maksymalna suma
                    maxSum = currSum;
                    columnBeg = left;
                    columnEnd = right;
                    rowBegin = currBegin;
                    rowEnd = currEnd;
                } else if(currSum == maxSum){
                    //przypadek w ktorym suma aktualna jest rowna sumie maksymalnej
                    //w tym wypadku musimy wybrac podtablice o jak najmniejszej liczbie elementow tzn rozmiarze
                    int currColsSize = right - left + 1; //wyznaczenie rozmiaru dotychczasowo aktualnych kolumn
                    int currRowSize = currEnd - currBegin + 1; //wyznaczenie rozmiaru dotychczasowo aktualnych  wierszy
                    int colsSize = columnEnd - columnBeg + 1; //wyznaczenie rozmiaru aktualnych kolumn
                    int rowSize = rowEnd - rowBegin + 1; //wyznaczenie rozmiaru aktualnych wierszy
                    
                    if(currColsSize * currRowSize == colsSize * rowSize){
                        //przypadek w ktorym sprawdzamy czy rozmiary podtablic aktualnej i maksymalnej sa takie same
                        if(rowBegin > currBegin){
                            //aktualizacja wartosci
                            columnBeg = left;
                            columnEnd = right;
                            rowBegin = currBegin;
                            rowEnd = currEnd;
                        } else if(rowBegin == currBegin && left < columnBeg){
                            //tutaj sprawdzamy czy indeksy beda w kolejnosci leksykograficzne
                            //i wtedy aktualizujemy wartosci
                            columnBeg = left;
                            columnEnd = right;
                            rowBegin = currBegin;
                            rowEnd = currEnd;
                        }
                    } else if(currColsSize * currRowSize < rowSize * colsSize){
                        //przypadek w ktorym rozmiar aktualnej podtablicy jest mniejszy niz maksymalnej podtablicy
                        //zatem musimy zaktualizowac indeksy tak aby rozmiar podtablicy byl jak najmniejszy
                        columnBeg = left;
                        columnEnd = right;
                        rowBegin = currBegin;
                        rowEnd = currEnd;
                    }
                }
            }
        }
        
        //przypisanie wynikow do tablicy 
        result[0] = maxSum;
        result[1] = rowBegin;
        result[2] = rowEnd;
        result[3] = columnEnd;
        result[4] = columnBeg;

        return result;
    }
    public static void main(String[] args) {
        
        Source res = new Source();
        Scanner scanner = new Scanner(System.in);
        int n =scanner.nextInt(); //pobranie liczby zestawow 
        int counter =0; // zmienna do zliczania ujemnych
        int compareCounter; //zmienna do zliczania wszystkich
        int counterZero; //zmienna do zliczania elementow zerowych

        for (int i = 0; i < n; i++) {
            int numSet = scanner.nextInt(); //pobranie numeru zestawu
            scanner.next(); //pominiecie znaku :
            int rows = scanner.nextInt(); //pobranie ilosci wierszy
            int kolumny = scanner.nextInt(); //pobranie ilosci kolumn
            int[][] tab = new int[rows][kolumny]; 
            
            compareCounter = rows*kolumny; //aktualizacja zmiennej o ilosc elementow w tablicy
            counter =0; 
            counterZero=0;

            for (int j = 0; j < rows; j++) {


                for (int k = 0; k < kolumny; k++) {
                    tab[j][k] = scanner.nextInt(); //wczytanie elementu do tablicy
                    if(tab[j][k]<0){
                        tab[j][k]= (tab[j][k])*2; //mnozenie elementu tablicy przez odpowiednie wagi
                        counter++; //zliczanie ujemnych wartosci
                    }
                    else{
                        tab[j][k]= (tab[j][k])*3;//mnozenie elementu tablicy przez odpowiednie wagi
                    }

                    if(tab[j][k]==0){
                        counterZero++; //zliczanie wartosci zerowych
                    }

                }

            }

            //wyswietlanie danych
            if(counter==compareCounter){
                //przypadek tablicy z samymi ujemnymi elementami
                System.out.println(numSet + ": ms_tab is empty");
            }
            else if(compareCounter==counterZero){
                //przypadek tablicy z samymi zerami
                System.out.println(numSet +": ms_tab = a[0..0][0..0], msum=0");

            }
            else{
                int[] result = findMaxTab2D(tab); //tablica zwracajaca wynik 
                
                //przypisanie wartosci tablicy result do zmiennych pomocniczych
                 res.mSum =result[0];
                res.rowBeg =result[1];
                 res.rowEnd =result[2];
                 res.colBeg =result[4];
                 res.colEnd =result[3];
                
                System.out.println(numSet + ": " + res);
                //System.out.println(numSet +": ms_tab = a["+rowBeg+".."+rowEnd+"]["+colBeg+".."+colEnd+"], msum="+mSum);
            }

        }
        scanner.close();


    }
}


/*
test.in
11
1 : 2 5
 1 1 -1 -1 0
 1 1 -1 -1 4
2 : 2 5
 0 -1 -1 1 1
 6 -2 -20 1 1
3 : 2 5
 0 -1 -1 4 0
 4 -2 -2 0 0
4 : 2 5
 -1 -2 -3 -1 -2
 -1 -1 -1 -1 -5
5 : 1 6
-2 7 -4 8 -5 4
6 : 2 2
-1 0
0 -1
7 : 3 3
0 0 0
0 0 1
0 0 1
8 : 1 5
10 6 -9 -10 16 
9 : 2 2
2 2
2 -5 
10 : 8 1
0
2
3
-2
-3
0
-4
5
11 : 1 2
0 1


testy.out
1: ms_tab = a[0..1][0..4], msum=16
2: ms_tab = a[1..1][0..0], msum=18
3: ms_tab = a[0..0][3..3], msum=12
4: ms_tab is empty
5: ms_tab = a[0..0][1..5], msum=39
6: ms_tab = a[0..0][1..1], msum=0
7: ms_tab = a[1..2][2..2], msum=6
8: ms_tab = a[0..0][0..4], msum=58
9: ms_tab = a[0..1][0..0], msum=12
10: ms_tab = a[7..7][0..0], msum=15
11: ms_tab = a[0..0][1..1], msum=3


*/