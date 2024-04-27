//Magdalena Maksymiuk gr 4
import java.util.Scanner;

class StackArray{
    int maxSize; //rozmiar stosu
    char[] elems; //tablica zawierajaca stos
    int top; //indeks wierzcholka

    public StackArray(int size){
        //konstruktor
        maxSize = size;
        elems = new char[maxSize];
        top = -1;
    }

    public void push(char elem){
        //wstawia element na szczyt stosu
        if(!isFull()){
            elems[++top]=elem;
        }
    }

    public char pop(){
        //usuwa element ze szczytu stosu
        if(isEmpty()){
            return '$';
        }
        else return elems[top--];
    }

    public char peek(){
        //zwraca wartosc na szczycie stosu
        if(!isEmpty()){
            return elems[top];
        }
        else return '$'; //musi cos zwracac
    }

    public boolean isEmpty(){

        return(top == -1);
    }

    public boolean isFull(){

        return (top == maxSize - 1);
    }
}


class StackArrayString{
    int maxSize; //rozmiar stosu
    String[] elems; //tablica zawierajaca stos
    int top; //indeks wierzcholka

    public StackArrayString(int size){
        //konstruktor
        maxSize = size;
        elems = new String[maxSize];
        top = -1;
    }

    public void push(String elem){
        //wstawia element na szczyt stosu
        if(!isFull()){
            elems[++top]=elem;
        }
    }

    public String pop(){
        //usuwa element ze szczytu stosu
        if(isEmpty()){
            return "$";
        }
        else return elems[top--];
    }

    public String peek(){
        //zwraca wartosc na szczycie stosu
        if(!isEmpty()){
            return elems[top];
        }
        else return "$"; //musi cos zwracac bo inaczej IDE sie buntuje
    }

    public boolean isEmpty(){

        return(top == -1);
    }

    public boolean isFull(){

        return (top == maxSize - 1);
    }
}

class StackArray2{
    //stos do trzymania priorytetow
    int maxSize; //rozmiar stosu
    int[] elems; //tablica zawierajaca stos
    int top; //indeks wierzcholka

    public StackArray2(int size){
        //konstruktor
        maxSize = size;
        elems = new int[maxSize];
        top = -1;
    }

    public void push(int elem){
        //wstawia element na szczyt stosu
        if(!isFull()){
            elems[++top]=elem;
        }
    }

    public int pop(){
        //usuwa element ze szczytu stosu
        if(isEmpty()){
            return 22; //bo inaczej IDE sie buntuje
        }
        else return elems[top--];
    }

    public int peek(){
        //zwraca wartosc na szczycie stosu
        if(!isEmpty()){
            return elems[top];
        }
        else{
            return 22;
        }
        //musi cos zwracac bo inaczej IDE sie buntuje
    }

    public boolean isEmpty(){
        return(top == -1);
    }

    public boolean isFull(){
        return (top == maxSize - 1);
    }
}

public class Source {
    static Scanner scanner = new Scanner(System.in);


    public static String removeSpaces(String str) {
        //funkcja usuwajaca spacje z wyrazenia
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static int priority(char operator){
        int temp =9;
        if(operator=='(' || operator==')'){
            return 8;
        }else if(operator =='!' || operator =='~'){
            return 7;
        }else if(operator =='^'){
            return 6;
        }else if(operator =='/' || operator =='%' || operator =='*'){
            return 5;
        }else if(operator =='+' || operator =='-'){
            return 4;
        }else if(operator =='<' || operator =='>'){
            return 3;
        }else if(operator =='&'){
            return 2;
        }else if(operator =='|'){
            return 1;
        }else if(operator =='='){
            return 0;
        }
        return temp;
    }
    public static boolean isOperand(char c){
        if(c=='(' || c==')' || c=='!' || c=='~' || c=='^'
                || c=='*'|| c=='/' || c=='%'|| c=='+'
                || c=='-'|| c=='>'|| c=='<'|| c=='&'|| c=='|'
                || c=='='){
            return true;
        }
        else return false;
    }


    static int getPriority(char ch)
    {
        //poprawic tutaj
        if(ch == '='){
            return 2;
        }
        /*else if( ch == '|'){
            return 2;
        }*/
        else if( ch == '|'){
            return 3;
        }
        else if( ch == '&'){
            return 4;
        }
        else if( ch == '>' || ch == '<'){
            return 5;
        }
        else if (ch == '+' || ch == '-')
            return 6;
        else if (ch == '*' || ch == '/' || ch =='%')
            return 7;
        else if (ch == '^')
            return 8;
        else
            return -1;
    }

    // Operator has Left --> Right associativity
    static boolean hasLeftAssociativity(char ch) {
        if (ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch =='='
                || ch == '|' || ch=='&' || ch=='<' || ch =='>' || ch =='%' ) {
            return true;
        } else {
            return false;
        }
    }

    static String infToOnp(String expression)
    {
        //
        StackArray stack = new StackArray(256);

        String output = new String("");

        int n = expression.length();
        for (int i = 0; i < n; ++i) {

            char c = expression.charAt(i);

            if (isLetter(c)){
                output += c;
                output+=' ';
            }

            else if (c == '(')
                stack.push(c);

            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '('){
                    output += stack.pop();
                    output+=' ';
                }

                stack.pop();
            }
            else {
                while (!stack.isEmpty() && getPriority(c) <= getPriority(stack.peek())
                        && hasLeftAssociativity(c)) {

                    output += stack.pop();
                    output+=' ';
                }
                stack.push(c);
            }
        }


        while (!stack.isEmpty()) {
            // if (stack.peek() == '(')

            output += stack.pop();
            output+=' ';
        }
        return output;
    }

    static String fixExpressionINF(String expr){
        String result = expr.substring(4);
        StringBuilder sb = new StringBuilder();
        int n = expr.length();
        for(int i=0; i<n; i++){
            char c = expr.charAt(i);
            if(c=='(' || c==')'){
                sb.append(c);
            }
            else if(isLetter(c)){
                sb.append(c);
            }
            else if(isOperand(c)){
                sb.append(c);
            }
        }

        result = sb.toString();
        return result;
    }
    static String fixExpressionONP(String expr){
        String result = expr.substring(4);
        StringBuilder sb = new StringBuilder();
        int n = expr.length();
        for(int i=0; i<n; i++){
            char c = expr.charAt(i);
            if(isLetter(c)){
                sb.append(c);
            }
            else if(isOperand(c) && c!='(' && c!=')'){
                sb.append(c);
            }
        }
        result = sb.toString();
        return result;
    }


    public static boolean isOperand2(char c){
        if(c=='(' || c==')' || c=='^'
                || c=='*'|| c=='/' || c=='%'|| c=='+'
                || c=='-'|| c=='>'|| c=='<'|| c=='&'||
                c=='|' || c=='='){
            return true;
        }
        else return false;
    }

    static boolean isOneArgOperator(char c){
        if(c =='!' || c =='~'){
            return true;
        }
        else{
            return false;
        }
    }

    static boolean isLetter(char c){
        if(c>='a' && c<='z'){
            return true;
        }
        else{
            return false;
        }
    }


    static boolean AutomatMTINF(String expr){
        //automat sprawdzajacy poprawnosci wyrazenia w INF
        boolean res = true;
        int q =0;//stan poczatkowy
        int n = expr.length();
        for(int i=0; i<n; i++){
            char c = expr.charAt(i);

            //sprawdzamy jaki stan
            if(q ==0) {
                //System.out.println(c);
                if(c=='('){
                    q=0;
                }
                else if(isOneArgOperator(c)){
                    //c jednoargumentowy operator
                    q=2;
                }
                else if(isLetter(c)){
                    //c - litera
                    q=1;
                }
                else{
                    return false;
                }

            }else if(q==1){

                if(c==')'){
                    //c dwuargumentowy
                    q=1;
                }
                else if(isOperand2(c)){
                    q=0;

                }
                else{
                    return false;
                }
            }
            else if(q==2){
                if(c=='('){
                    q=0;
                }
                else if(isOneArgOperator(c)){
                    q=2;
                }
                else if(isLetter(c)){
                    q=1;
                }
                else{
                    return false;
                }


            }

        }

        if(q==1){
            res = true;
        }
        else{
            res = false;
        }

        return res;
    }

    static int getPriorityOfChar(char c)
    {
        if(c =='='){
            return 1;
        }
        else if(c == '|'){
            return 2;
        }
        else if(c == '&'){
            return 3;
        }
        if(c=='<' || c=='>')
            return 4;
        if(c=='+' || c=='-')
            return 5;
        else if(c == '*' || c =='/')
            return 6;
        else if(c=='^')
            return 7;
        else if(c =='~' || c =='!') //tutaj jeszcze nie wiem czy rozne okreslenia dla unarnych maja miec te same priorytety
            return 8;//update na razie dziala ale zobaczymy pozniej xd
        else if(c >= 'a' && c<= 'z')
            return 9;

        return 0; //dla zabezpieczenia przed bledami
    }

    static boolean isOperatorNumRight(String expr){
        boolean cond = true;
        int n = expr.length();
        int operandCounter =0;
        int operatorCounter=0;
        for(int i=0; i<n; i++){
            char c = expr.charAt(i);
            if(isLetter(c)){
                operandCounter++;
            }
            else{
                if(!isOneArgOperator(c)){
                    operatorCounter++;
                }

            }

        }

        if(operatorCounter <= operandCounter-1){
            cond = true;
        }else{
            cond = false;
        }
        return cond;
    }

    static int getPriorityONP(String znak)
    {
        if(znak.equals("="))
            return 1;
        else if(znak.equals("|"))
            return 2;
        else if(znak.equals("&"))
            return 3;
        else if(znak.equals("<") || znak.equals(">"))
            return 4;
        else if(znak.equals("+") || znak.equals("-"))
            return 5;
        else if(znak.equals("*") || znak.equals("/"))
            return 6;
        else if(znak.equals("^"))
            return 7;
        else if(znak.equals("~") || znak.equals("!")) //minus unarny (np. -4, -5 zmienia znak liczby)
            return 8;
        else if(znak.charAt(0) >= 'a' && znak.charAt(0) <= 'z') //dla uproszczenia przyjmujemy, że liczby będą reprezentować litery
            return 9;

        return 0; //przypadek cos po drodze sie zepsulo ... jak moje volviak wczoraj
    }
    static String onpToInf(String expr){
        String res="";
        int n = expr.length();
        StackArrayString stringStack = new StackArrayString(n);
        StackArray2 priorityStack = new StackArray2(n);

        //ewentualnie zamien potem na petle z charami po znaku
        // ???
        for(int i=0; i<n; i++){
            //przechodzimy po wszystkich znakach
            char c = expr.charAt(i);
            String temp ="";
            //StringBuilder sb = new StringBuilder();
            String temp2="";
            temp+=c;
            int charPriority = getPriorityONP(temp);
            if(isLetter(c)){
                //operand

                stringStack.push(temp);
                priorityStack.push(charPriority);

            }
            else{

                temp2="";
                if(!isOneArgOperator(c)){
                    //dwuargumentowe operatory
                    int stackOperator =priorityStack.peek();
                    if(stackOperator <= charPriority){
                        temp2 ="("+stringStack.pop()+")";
                    }
                    else{
                        temp2 = stringStack.pop();
                    }

                    priorityStack.pop();

                    if(stackOperator < charPriority){
                        temp2 ="("+stringStack.pop()+")" + c + temp2;
                    }
                    else{
                        temp2 = stringStack.pop() + c + temp2;
                    }

                    priorityStack.pop();

                }
                else{

                    int stackOperator =priorityStack.peek();
                    if(stackOperator <= charPriority){
                        temp2 = c+"("+stringStack.pop()+")";
                    }
                    else{
                        temp2 = c+ stringStack.pop();
                    }

                    priorityStack.pop();
                }

                stringStack.push(temp2);
                priorityStack.push(charPriority);
            }
        }

        res =stringStack.pop();


        return res;
    }

    static String addSpaces(String expr){
        int n = expr.length();
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<n; i++){
            char c = expr.charAt(i);
            sb.append(c);
            sb.append(' ');

        }

        return sb.toString();
    }
    public static void main (String[]args){

        int setAmount = Integer.parseInt(scanner.nextLine());; //liczba zestawow danych
        for (int i = 0; i < setAmount; i++) {

            String temp = scanner.nextLine();

            //usuwanie spacji z wyrazenia
            String temp2 = removeSpaces(temp);
            char c = temp2.charAt(0);
            String expression;
            StackArray stack = new StackArray(257);
            if (c == 'I') {
                //dopisac sprawdzanie niesparowanych nawiasow
                expression = fixExpressionINF(temp2);

                boolean cond = AutomatMTINF(expression);
                if(cond){
                    //zamienia nam wszystkie znaki poza wymienionymi na puste
                    //expression = expression.replaceAll("[^a-z()!~^*/%+\\-<>?&|=]", "");
                    String res = infToOnp(expression);
                    //System.out.println(res);
                    int k = res.length();
                    res =res.substring(0,k-1);
                    System.out.println("ONP: " + res);
                }else{
                    System.out.println("ONP: error");
                }

            } else if (c == 'O') {
                //ONp->INF
                //dopisac sprawdzanie czy wgl wyra
                expression = fixExpressionONP(temp2);
                //expression = expression.replaceAll("[^a-z!~^*/%+\\-<>?&|=]", "");
                boolean cond = isOperatorNumRight(expression);
                //System.out.println(cond);
                if(cond==true){
                    String res =onpToInf(expression);
                    res = addSpaces(res);
                    int k = res.length();
                    res =res.substring(0,k-1);
                    System.out.println("INF: " + res);
                }
                else{
                    System.out.println("INF: error");
                }

            }


        }


    }
}