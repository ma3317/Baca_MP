//Magdalena Maksymiuk gr nr 4

import java.util.Scanner;
/*
* Algorytm rozwiazujacy problem znalezienia mozliwej ilosci
* trojkatow w danej posortowanej tablicy( ktora sortujemy Intersection sort)
*  mozemy rozwiazac
* w nastepujacy sposob:
* za pomocą funkcji SeachBinFirst wyszukuje binarnie
*  indeks trzeciego boku trojkata, ktory ma byc wiekszy badz rowny
* sumie pozostalych dwoch bokow - znajdujemy taki indeks
* przy ktorym mamy pewnosc ze bok1 + bok2 > bok3 gdzie szukamy
* indeksu bok3
*
* zlozonosc calego rozwiazania O(n^2logn)
*
* */


public class Source {
    public static int SearchBinFirst(int[]tab, int begin, int end, int SumTriangles){

        //wyznaczanie trzeciego boku trojkata
        //bok 3 ma byc jednoczesnie mniejszy od sumy bok1 i bok2
        //oraz wiekszy od zarowno od bok1 i od bok2
        while(begin <= end){

            int middle = (begin + end)/2;
            if(tab[middle] >= SumTriangles){
                end = middle -1;
            }
            else {
                begin = middle +1;
            }
        }

        //zwracam skrajny lewy
        return begin;

    }
    static int findNumberOfTriangles(int[] tab){
        int numOfTriangles =0; //liczba trojkatow
        int n = tab.length; //rozmiar tablicy

        for(int i =0; i<n-2; i++){
            int p = i+2; // bok trzeci najbardziej na prawo
            for(int j= i+1; j<n-1 && tab[i]!=0; j++){

                int tempSum = tab[i]+tab[j];
                //korzystam tutaj z nierownosci trojkata
                // a +b >=c i na tej podstawie wybieram zakres szukania
                //ostatnie elementu do trójki(i,j,p), ktora
                //ma spelniac warunki nierownosci
                //znjadyjemy najwieksza wartosc taka p taka
                p =SearchBinFirst(tab,p,n-1,tempSum);
                numOfTriangles+=p-j-1;

            }
        }
        return numOfTriangles;
    }
    static void displayTriangles(int numTriangles, int[] tab){
        int n = tab.length; //rozmiar tablicy
        int count = 0; //liczba trojek

        for (int i = 0; i < n - 2; i++) {
                    //tutaj jest o(n^2) bo nie j, k nie zaczynaja sie od nowej wartosci
            for (int j = i + 1; j < n-1; j++) {
                int k= j + 1;
                while (k < n && tab[i] + tab[j] > tab[k]) {

                    if(j!=k){
                        System.out.print("(" +i + "," + j + "," + k+") ");
                        count++;

                    }
                    k++;

                    //zagwarantowanie ze wyswietli sie maksymalnie 10 trojek
                    if (count == 10) {
                        return;
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);

        //pobranie ilosci zestawow
        int setNum = scanner.nextInt();
        for(int i=0; i<setNum; i++){
            int amountOfData = scanner.nextInt();
            //pobranie ilosci danych w tablicy
            int[] tab = new int[amountOfData];
            //wyswietlenie numeru zestawu i ilosci elementow w tablicy
            System.out.println(i+1+": n= "+amountOfData);
            //wczytywanie tablicy
            for(int j =0; j<amountOfData; j++){
                int num = scanner.nextInt();
                tab[j] =  num;

            }

            //sortowanie tablicy intersection sort
            for(int j = amountOfData -2; j>=0; j--){
                int temp1 = tab[j];
                int w = j+1;
                while((w<amountOfData) &&(temp1 >tab[w])){
                    tab[w-1]=tab[w];
                    w++;
                }
                tab[w-1]=temp1;
            }
            //wyswietlenie posortowanej tablicy
             int counter25max= 0; //zliczanie elementow w wierszach
            //tak aby bylo maksymalnie 25 elementow w wierszu
                for(int p =0; p<amountOfData; p++){

                        //wyswietlenie elementu
                        System.out.print(tab[p] + " ");
                        counter25max++;
                        if(counter25max%25 ==0 && counter25max!=0){
                            System.out.println();
                            //warunek gwarantujacy wypisanie maksymalnie 25 elemento w wierszu
                        }
                }
                if(counter25max<25){
                    System.out.println(); //przejscie do nowej linii
                }


                //zliczanie ilosci trojkatow
            int numOfTriangles = findNumberOfTriangles(tab);
            //wyswietlanie trojek
            if(numOfTriangles >0){
                displayTriangles(numOfTriangles,tab);

            }

                if(numOfTriangles >0) {
                    System.out.println();
                    System.out.println("Total number of triangles is: " + numOfTriangles);
                }
                else{
                    System.out.println("Total number of triangles is: "+numOfTriangles);
                }
        }


        scanner.close();

        /*
        TEST.IN
        12
50
2 9 4 3 7 8 5 2 5 1 1 8 5 2 2 5 1 9 4 7 3 6 1 2 4 4 7 6 3 8 1 4 9 8 2 1 5 6 3 7 4 8 4 9 4 6 9 6 9 4
50
6 6 7 5 2 3 2 9 8 7 7 6 1 2 3 7 8 3 9 1 3 6 3 3 9 6 2 1 5 1 7 5 6 7 3 9 2 4 6 6 8 5 3 5 8 4 6 4 2 3
4
9 5 7 7
4
4 1 2 3
4
9 8 4 7
4
2 9 3 3
4
1 1 6 4
4
2 5 5 5
4
4 7 1 8
4
2 2 8 6
4
2 1 5 9
4
9 2 1 8

TEST.OUT
1: n= 50
1 1 1 1 1 1 2 2 2 2 2 2 3 3 3 3 4 4 4 4 4 4 4 4 4 
5 5 5 5 5 6 6 6 6 6 7 7 7 7 8 8 8 8 8 9 9 9 9 9 9 
(0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,2,3) (0,2,4) (0,2,5) (0,3,4) (0,3,5) (0,4,5) 
Total number of triangles is: 9132
2: n= 50
1 1 1 1 2 2 2 2 2 2 3 3 3 3 3 3 3 3 3 4 4 4 5 5 5 
5 5 6 6 6 6 6 6 6 6 6 7 7 7 7 7 7 8 8 8 8 9 9 9 9 
(0,1,2) (0,1,3) (0,2,3) (0,4,5) (0,4,6) (0,4,7) (0,4,8) (0,4,9) (0,5,6) (0,5,7) 
Total number of triangles is: 10331
3: n= 4
5 7 7 9 
(0,1,2) (0,1,3) (0,2,3) (1,2,3) 
Total number of triangles is: 4
4: n= 4
1 2 3 4 
(1,2,3) 
Total number of triangles is: 1
5: n= 4
4 7 8 9 
(0,1,2) (0,1,3) (0,2,3) (1,2,3) 
Total number of triangles is: 4
6: n= 4
2 3 3 9 
(0,1,2) 
Total number of triangles is: 1
7: n= 4
1 1 4 6 
Total number of triangles is: 0
8: n= 4
2 5 5 5 
(0,1,2) (0,1,3) (0,2,3) (1,2,3) 
Total number of triangles is: 4
9: n= 4
1 4 7 8 
(1,2,3) 
Total number of triangles is: 1
10: n= 4
2 2 6 8 
Total number of triangles is: 0
11: n= 4
1 2 5 9 
Total number of triangles is: 0
12: n= 4
1 2 8 9 
(1,2,3) 
Total number of triangles is: 1
*/

    }
}