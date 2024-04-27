//Magdalena Maksymiuk gr nr 4
import java.util.Scanner;


public class Source {
    static public Scanner scanner = new Scanner(System.in);

    public static int[] rekFirst(int[][] tab, int colSize, int rowSize, int findElem, int i, int j, int[] currResult  ){

        if(i >= colSize || j<0){
            return currResult;
        }

        if(tab[j][i] == findElem){
            if(j<currResult[1]){
                currResult[0]=i;
                currResult[1]=j;
            }
            else if(j==currResult[1] && i<currResult[0]){
                currResult[0]=i;
                currResult[1]=j;
            }
        }

        if(tab[j][i] >= findElem){
            j--;
        }
        else{
            i++;
        }

        return rekFirst(tab,colSize,rowSize,findElem,i,j,currResult);
    }
    public static int[] rekLast(int[][] tab, int colSize, int rowSize, int findElem, int i, int j, int[] currResult  ){

        if(i >= colSize || j<0){
            return currResult;
        }

        if(tab[j][i] == findElem){
            if(j > currResult[1]){
                currResult[0]=i;
                currResult[1]=j;
            }
            else if(j==currResult[1] && i > currResult[0]){
                currResult[0]=i;
                currResult[1]=j;
            }
        }

        if(tab[j][i] > findElem){
            j--;
        }
        else{
            i++;
        }

        return rekLast(tab,colSize,rowSize,findElem,i,j,currResult);
    }

    public static int[] iterFirst(int[][] tab, int rowSize, int colSize, int findElem, int[] currResult){
    currResult[0]=rowSize;
    currResult[1]=colSize;

    int i=0;
    int j = colSize -1;

    while(i<rowSize && j>=0){

        if(tab[j][i] == findElem){
            if(j< currResult[1]){
                currResult[0]=i;
                currResult[1]=j;
            }
            else if(j==currResult[1] && i< currResult[0]){
                currResult[0]=i;
                currResult[1]=j;
            }
        }

        if(tab[j][i] >=findElem){
            j--;
        }
        else{
            i++;
        }
    }
    return currResult;

    }
    public static int[] iterLast(int[][] tab, int colSize, int rowSize, int findElem, int[] currResult){
        currResult[0]=-1;
        currResult[1]=-1;

        int i=0;
        int j = rowSize -1;

        while(i<colSize && j>=0){
            if(tab[j][i] == findElem){
                if(j > currResult[1]){
                    currResult[0]=i;
                    currResult[1]=j;
                }
                else if(j==currResult[1] && i > currResult[0]){
                    currResult[0]=i; //x
                    currResult[1]=j; //y
                }
            }

            if(tab[j][i] > findElem){
                j--;
            }
            else{
                i++;
            }
        }
        return currResult;

    }
    public static void main(String[] args) {

        int setAmount = scanner.nextInt(); //liczba zestawow danych

        for(int i=0; i <setAmount; i++){

            int rowSize = scanner.nextInt(); //liczba wierszy w tablicy
            int colSize = scanner.nextInt(); //liczba kolumn w tablicy
            int elem; //szukany element tablicy

            int[][] tab = new int[rowSize][colSize];
            for(int j=0; j<rowSize; j++){
                for(int k=0; k<colSize; k++){
                    tab[j][k]= scanner.nextInt(); //wczytanie danych do tablicy
                }
            }

            elem = scanner.nextInt(); //element do znalezienia

            int[] tempRes = new int[2]; //pomocnicza tablica z indeksami
            tempRes[0]=colSize; //y
            tempRes[1]=rowSize; //x
            // rekFirst

            int[] result = rekFirst(tab,colSize,rowSize, elem,0, rowSize-1,tempRes);

            if(result[0] < 0 || result[0] > colSize || result[1]<0 || result[1]>=rowSize){
                System.out.println("recFirst: "+ elem + " missing in array" );
            }
            else{
                System.out.println( "recFirst: " + elem + " = a[" + result[1] + "]" +"["+ result[0] + "]" );
            }

            //rekLast
            tempRes[0]=-1;
            tempRes[1]=-1;
            int[] result2 =rekLast(tab,colSize,rowSize, elem,0, rowSize-1,tempRes);
            if(result2[0] < 0 || result2[0] > colSize || result2[1]<0 || result2[1]>=rowSize){
                System.out.println("recLast: "+ elem + " missing in array" );
            }
            else{
                System.out.println( "recLast: " + elem + " = a[" + result2[1] + "]" +"["+ result2[0] + "]" );
            }

            //iterFist
            int[] result3 =iterFirst(tab, colSize, rowSize, elem, tempRes);
            if(result3[0] < 0 || result3[0] > colSize || result3[1]<0 || result3[1]>=rowSize){
                System.out.println("iterFirst: "+ elem + " missing in array" );
            }
            else{
                System.out.println( "iterFirst: " + elem + " = a[" + result3[1] + "]" +"["+ result3[0] + "]" );
            }
            //iterLast
            int[] result4 =iterLast(tab, colSize, rowSize, elem, tempRes);
            if(result4[0] < 0 || result4[0] > colSize || result4[1]<0 || result4[1]>=rowSize){
                System.out.println("iterLast: "+ elem + " missing in array" );
            }
            else{
                System.out.println( "iterLast: " + elem + " = a[" + result4[1] + "]" +"["+ result4[0] + "]" );
            }


            //wypisanie tej ostatniej linijki

            System.out.println("---");

        }


    }
}