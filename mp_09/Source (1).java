//Magdalena Maksymiuk gr nr 4
import java.util.Scanner;
/*
* Zadanie polega na napisaniu aplikacji dzialajacej na
* kolejce priorytetowej MIN/MAX, ktora bedzie zawierala
* elementy typu Person i bedzie zrealizowana jako drzewo BST.
* Aplikacja zawiera trzy moduly : edycji, kolejkowania i raportowania.
*
* */
// Definicja klasy Person
class Person {
    public int priority; //priorytej
    public String name; //imie
    public String surname; //nazwisko

    //konstruktor
    public Person(int priority, String name, String surname) {
        this.priority = priority;
        this.name = name;
        this.surname = surname;
    }

    //override metody toString zeby latwiej wyswietlac
    @Override
    public String toString() {
        return priority + " - " + name + " " + surname;
    }
}

// Definicja klasy Node
class Node {
    public Person info; //dane osoby
    public Node left; //lewy wezel
    public Node right; //prawy wezel

    //konstruktor
    public Node(Person info) {
        this.info = info;
        this.left = null;
        this.right = null;
    }
}

// Implementacja stosu wiazanego
class StackNode<T> {
    T data; //dane
    StackNode<T> next; //wskaznik na nastepny element

    //konstruktor
    public StackNode(T data) {
        this.data = data;
        this.next = null;
    }
}

class Stack<T> {
    private StackNode<T> top; //wierzcholek

    public Stack() {
        this.top = null; //inicjalizacja wierzcholka na null
    }

    public void push(T data) {
        //metoda do wstawiania elementu na stos
        StackNode<T> newNode = new StackNode<>(data); //tworzenie nowego wezla
        newNode.next = top;
        top = newNode;
    }

    public T pop() {
        //metoda do usuwania elementu ze stosu
        if (isEmpty()) {
            return null; //w przypadku gdy stos jest pusty
        }
        T data = top.data; //pobranie danych
        top = top.next; //aktualizacja wierzcholka
        return data; //zwrocenie danych
    }

    public T peek() {
        //metoda do odczytywania elementu ze stosu
        //bez zdejmowania ich
        if (isEmpty()) {
            return null; // w przypadku pustego stosu
        }
        return top.data; //zwrocenie danych ze stosu
    }

    public boolean isEmpty() {
        //metoda do sprawdzania czy stos jest pusty
        return top == null; //true jesli jest pusty
    }
}

// Definicja klasy BST
class BST {
    private Node root; //korzen

    //konstruktor
    public BST() {
        this.root = null; //inicjalizacja korzenia na null
    }

    //MODUL EDYCJI ----------------------------------------------------------------
    // Funkcja tworzaca drzewo BST
    public void create(String order, Person[] persons) {
        //rekurencyjnie tworzy drzewo BST an podstawie listy n elementow
        //typu Person w porzadku PREORDER lub POSTORDER
        root = null;
        if (order.equals("PREORDER")) {
            root = createPreOrder(root, persons, 0); //tworzenie drzewa w kolejnosci PREORDER
        } else if (order.equals("POSTORDER")) {
            root = createPostOrder(root, persons, persons.length - 1); //tworzenie drzewa w kolejnosci POSTORDER
        }
    }

    private Node createPreOrder(Node node, Person[] persons, int index) {
       //pomocnicza rekurencyjna funkcja tworzaca w kolejnosc PREORDER
        if (index >= persons.length) {
            return node;
        }
        node = insertRec(node, persons[index]); //wstawienie elementu w sposob rekurencyjny
        node = createPreOrder(node, persons, index + 1);//dla nastepnego elementu
        return node;
    }

    private Node createPostOrder(Node node, Person[] persons, int index) {
        //pomocnicza rekurencyjna funkcja tworzaca w kolejnosci POSTORDER
        if (index < 0) {
            return node;
        }
        node = insertRec(node, persons[index]); //wstawianie elementu w sposob rekurencyjny
        node = createPostOrder(node, persons, index - 1);
        return node;
    }

    private Node insertRec(Node node, Person person) {
        //pomocnicza rekurencyjna funkcja wstawiajaca elementy w drzewo BST
        if (node == null) { //sprawdzenie czy wezel nie jest null
            return new Node(person); //wtedy tworzymy nowy wezel z danymi
        }
        if (person.priority < node.info.priority) {
            //jesli priorytet osoby jest mniejszy niz w aktualnym wezle
            node.left = insertRec(node.left, person); //wstaw do lewego
        } else { //w innym przypadku
            node.right = insertRec(node.right, person); //wstaw do prawego
        }
        return node; //zwraca aktualny wezel
    }


    // Funkcja usuwajaca wezel o danym priorytecie
    //gdy w drzewie wezel ma 2 potomkow to zamienia go z jego nastepnikiem
    //jesli osoba o priorytecie x nie wystepuje w kolejce to wypisuje DELETE x : BRAK
    public String delete(int priority) {
        Node parent = null; //wezel rodzica
        Node current = root; //ustawiamy aktualny wezel na korzen
//szukamy wezel o danym priorytecie
        //petla wykonuje sie do momementu znalezenia odpowiedniego priorytetu
        while (current != null && current.info.priority != priority) {
            parent = current;// ustawienie aktualnego wezla jako rodzica
            if (priority < current.info.priority) { //szukanie w lewym poddrzewie
                current = current.left;
            } else {//szukanie w prawym poddrzewie
                current = current.right;
            }
        }

        //jezeli wezel nie zostal odnaleziony
        //zwracamy informacje o braku
        if (current == null) {
            return "DELETE " + priority + ": BRAK";
        }

        //usuwanie wezla majacego co najwyzej jedno dziecko
        if (current.left == null || current.right == null) {
            Node newCurr = (current.left == null) ? current.right : current.left;

            if (parent == null) {
                //sprawdzenie czy usuwany wezel jest korzeniem
                root = newCurr;
            } else if (current == parent.left) {
                //sprawdzenie czy usuwany wezel jest lewym dzieckiem
                parent.left = newCurr;
            } else {
                //sprawdzenie czy usuwany wezel jest prawym dzieckiem
                parent.right = newCurr;
            }
        } else {
            //usuwanie wezla majacego dwoje dzieci
            Node p = null; //zmienna rodzica
            Node temp = current.right;

            //szukanie najmniejszego wezla w prawym poddrzewie
            while (temp.left != null) {
                p = temp;
                temp = temp.left; //przejscie do lewego dziecka
            }

            if (p != null) { //sprawdzenie czy rodzic istnieje
                p.left = temp.right;
            } else {
                current.right = temp.right;
            }

            current.info = temp.info; //ustawienie prawego dziecka usuwanego wezla
        }

        return null;
    }

    //MODUL KOLEJKOWANIA ----------------------------------------------------------------

    private Node insertIter(Node node, Person person) {
        //iteracyjna funkcja pomocnicza analogiczna do iterRec
        //ale dzialajaca iteracyjnie
        //nie uzywam jej w create
        if (node == null) {
            return new Node(person); //nowy wezel z danymi osoby
        }
        Node parent = null;
        Node current = node; //ustawienie aktualnego wezla na korzen
        while (current != null) {
            parent = current; //ustawienie rodzica jako aktualnego
            if (person.priority < current.info.priority) {
                //znaleziony priorytet osoby jest mniejszy niz w current
                current = current.left; //przejscie do lewego
            } else {
                current = current.right; //przejscie do prawego
            }
        }
        //sprawdzenie w jakim miejscu wstawic nowy wezel
        if (person.priority < parent.info.priority) {
            parent.left = new Node(person); //wstawienie do lewego
        } else {
            parent.right = new Node(person); //wstawienie do prawego
        }
        return node;
    }
    // Funkcja dodajaca osobe do drzewa
    public void enqueue(Person person) {
        root = insertIter(root, person); //wstawienie iteracyjnie
    }

    // Funkcja usuwajaca osobe o najwyzszym priorytecie
    public Person dequeueMax() {
        if (root == null) return null; //sprawdzenie czy drzewo nie jest puste

        Node parent = null; //ustawienie rodzica na null
        Node maxNode = root; // ustawienie korzenia jako max
        while (maxNode.right != null) {
            parent = maxNode; //ustawiamy  maxNode jako rodzic
            maxNode = maxNode.right; // maxNode jako prawe dziecko
        }
        if (parent != null) {
            parent.right = maxNode.left; //przypisanie lewego dziecka maxNode
        } else {
            root = maxNode.left; //ustawienie nowego korzenia
        }
        return maxNode.info; //dane wezla o najwyzszym priorytecie
    }

    // Funkcja usuwajaca osobe o najnizszym priorytecie
    public Person dequeueMin() {
        if (root == null) return null;
        //sprawdzenie czy drzewo jest puste
        Node parent = null;
        Node minNode = root; //ustawienie minNode na korzen
        while (minNode.left != null) {
            //przechodzimy do najbardziej lewego wezla
            parent = minNode; //ustawienie rodzica na minNode
            minNode = minNode.left; //przejscie do lewego dziecka
        }
        if (parent != null) {
            parent.left = minNode.right; //przypisanie prawego dziecka
        } else {
            root = minNode.right; //ustawienie nowego korzenia
        }
        return minNode.info; //zwrocenie danych minimalnego wezla
    }

    // Funkcja znajdujaca osobe o najwyzszym priorytecie wiekszym od x
    // pod warunkiem istnienia osoby o priorytecie x

    public String next(int priority) {
        Node current = root; //ustawienie aktualnego wezla na korzen
        Node successor = null;
        boolean found = false; //zmienna wskazujaca czy znaleziono wezel

        while (current != null) {
            //przejscie przez drzewo
            if (current.info.priority > priority) {
                //gdy aktualny priorytet jest wiekszy niz szukany
                successor = current;
                current = current.left; //przejscie do lewego dziecka
            } else if (current.info.priority < priority) {
                //gdy aktualny priorytet jest mniejszy niz szukany
                current = current.right; //przejscie do prawego dziecka
            } else {
                //gdy aktualny priorytet jest rowny szukanemu
                found = true; //znaleziono
                current = current.right; //przejscie do prawego dziecka
            }
        }

        //sprawdzenie czy osoba o priorytecie x istnieje
        //i wypisanie informacji
        if (!found) {
            //nie znalezino
            return "NEXT " + priority + ": BRAK";
        }
        return successor != null ? "NEXT " + priority + ": " + successor.info.toString() : "NEXT " + priority + ": BRAK";
    }

    // Funkcja znajdujaca osobe o najnizszym priorytecie mniejszym od x
    public String prev(int priority) {
        Node current = root; //ustawienie aktualnego wezla na korzen
        Node predecessor = null;
        boolean found = false; //zmienna wskazujaca czy znaleziono wezel

        while (current != null) {

            if (current.info.priority < priority) {
                //jezeli aktualny priorytet jest wiekszy od szukanego
                predecessor = current;
                current = current.right; //przejscie do prawego dziecka
            } else if (current.info.priority > priority) {
                //jezeli aktualny priorytet jest mniejszy od szukanego
                current = current.left; //przejscie do lewego dziecka
            } else {
                //jezeli aktualny priorytet jest rowny szukanemu
                found = true; //znaleziono
                current = current.left; //przejscie do lewego dziecka
            }
        }

        //jezeli nie znaleziono priorytetu x
        if (!found) {
            return "PREV " + priority + ": BRAK";
        }
        //sprawdzenie czy wezel zostal znaleziony i wypisanie
        return predecessor != null ? "PREV " + priority + ": " + predecessor.info.toString() : "PREV " + priority + ": BRAK";
    }

    // MODUL RAPORTOWANIA ----------------------------------------------------------------
    public void preorder() {
        //funkcja wypisujaca elementy drzewa w kolejnosci PREOREDER
        System.out.print("PREORDER: ");
        if (root == null) {
            //jezeli drzewo jest puste
            System.out.println();
            return;
        }
        Stack<Node> stack = new Stack<>(); //utworzenie stosu
        stack.push(root); //wstawienie korzenia na stos
        while (!stack.isEmpty()) {
            Node node = stack.pop(); //zdjecie wezla ze stosu
            System.out.print(node.info); //wypisanie informacji o wezle
            if (!stack.isEmpty() || node.right != null || node.left != null) {
                System.out.print(", "); //wypisanie przecinka tak zeby ostatnia wypisywana osoba go nie miala
            }
            if (node.right != null) {
                //jezeli istnieje prawe dziecko dodajemy je na stos
                stack.push(node.right);
            }
            if (node.left != null) {
                //jezeli istnieje lewe dziecko dodajemy je na stos
                stack.push(node.left);
            }
        }
        System.out.println();
    }

    public void inorder() {
        //funkcja wypisujaca w kolejnosci INORDER
        System.out.print("INORDER: ");
        if (root == null) {
            //jesli drzewo jest puste
            System.out.println();
            return;
        }

        Stack<Node> stack = new Stack<>(); //utworzenie nowego stosu
        Node current = root; //ustawienie aktualnego wezla na korzen
        boolean first = true; //zmienna informujaca nas czy wypisalismy juz pierwsza czesc
        while (!stack.isEmpty() || current != null) {
            //petla bedzie sie wykonywac do momentu braku wezlow lub pustego stosu
            if (current != null) {
                //jezeli aktualny wezel nie jest null
                stack.push(current); //dodajemy wezel na stos
                current = current.left; //przechodzimy do lewego dziecka
            } else {
                current = stack.pop(); //zdejmujemy ze stosu
                if (!first) {
                    System.out.print(", "); //wypisanie przecinka
                }
                System.out.print(current.info); //wypisanie danych osoby
                first = false;
                current = current.right; //przejscie do prawego dziecka
            }
        }
        System.out.println();
    }

    public void postorder() {
        //funkcja wypisujaca elementy drzewa w
        // kolejnosci POSTORDER
        System.out.print("POSTORDER: ");
        if (root == null) {
            //jesli drzewo jest puste
            System.out.println();
            return;
        }
        Stack<Node> stack1 = new Stack<>(); //pierwszy stos
        Stack<Node> stack2 = new Stack<>(); //drugi stos
        stack1.push(root); //wstawienie korzenia na pierwszy stos
        while (!stack1.isEmpty()) {
            Node node = stack1.pop(); //zdjecie ze stosu1
            stack2.push(node); // wstawienie na stos2
            if (node.left != null) {
                //jezeli istnieje lewe dziecko to wstaw je na 1 stos
                stack1.push(node.left);
            }
            if (node.right != null) {
                //jezeli istnieje prawe dziecko to wstaw je na 1 stos
                stack1.push(node.right);
            }
        }
        boolean first = true;
        while (!stack2.isEmpty()) {
            if (!first) {
                //warunek zeby ostatni element byl bez przecinka na koncu
                System.out.print(", ");
            }
            System.out.print(stack2.pop().info); //zdejmij ze stosu2 i wypisz
            first = false;
        }
        System.out.println();
    }

    public int height() {
        //rekurencyjna funkcja zwracajaca wysokosc drzewa
        return height(root);
    }

    private int height(Node node) {
        //pomocnicza rekurencyjna funkcja zwracajaca wysokosc drzewa
        if (node == null) return -1;
        int leftHeight = height(node.left); //wysokosc lewego poddrzewa
        int rightHeight = height(node.right); // wysokosc prawego poddrzewa
        return Math.max(leftHeight, rightHeight) + 1;
    }
}

public class Source {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int setAmount = scanner.nextInt(); //ilosc zestawow
        scanner.nextLine();

        for (int x = 1; x <= setAmount; x++) {
            System.out.println("ZESTAW " + x); //wypisanie zestaw +nr zestawu
            int commandCount = scanner.nextInt(); //liczba polecen
            scanner.nextLine();

            BST bst = new BST();

            for (int i = 0; i < commandCount; i++) {
                String commandLine = scanner.nextLine();
                String[] commandParts = commandLine.split(" ");
                String command = commandParts[0];

                switch (command) {
                    case "CREATE":
                        String order = commandParts[1];
                        int n = Integer.parseInt(commandParts[2]);
                        Person[] persons = new Person[n];
                        int index = 0;
                        for (int j = 3; j < commandParts.length; j += 3) {
                            int priority = Integer.parseInt(commandParts[j]);
                            String name = commandParts[j + 1];
                            String surname = commandParts[j + 2];
                            persons[index++] = new Person(priority, name, surname);
                        }
                        bst.create(order, persons);
                        break;
                    case "DELETE":
                        int deletePriority = Integer.parseInt(commandParts[1]);
                        String result = bst.delete(deletePriority);
                        if (result != null) System.out.println(result);
                        break;
                    case "ENQUE":
                        int priority = Integer.parseInt(commandParts[1]);
                        String name = commandParts[2];
                        String surname = commandParts[3];
                        bst.enqueue(new Person(priority, name, surname));
                        break;
                    case "DEQUEMAX":
                        Person maxPerson = bst.dequeueMax();
                        if (maxPerson != null) System.out.println("DEQUEMAX: " + maxPerson);
                        break;
                    case "DEQUEMIN":
                        Person minPerson = bst.dequeueMin();
                        if (minPerson != null) System.out.println("DEQUEMIN: " + minPerson);
                        break;
                    case "NEXT":
                        int nextPriority = Integer.parseInt(commandParts[1]);
                        System.out.println(bst.next(nextPriority));
                        break;
                    case "PREV":
                        int prevPriority = Integer.parseInt(commandParts[1]);
                        System.out.println(bst.prev(prevPriority));
                        break;
                    case "PREORDER":
                        bst.preorder();
                        break;
                    case "INORDER":
                        bst.inorder();
                        break;
                    case "POSTORDER":
                        bst.postorder();
                        break;
                    case "HEIGHT":
                        System.out.println("HEIGHT: " + bst.height());
                        break;
                }
            }
        }
        scanner.close();
    }
}

//test1.in
/*
1
19
CREATE POSTORDER 4 15 Hiccup Haddock 30 Astrid Hofferson 45 Stoick theVast 40 Fishlegs Ingerman
POSTORDER
PREORDER
INORDER
PREV 15
NEXT 45
NEXT 30
PREV 30
PREV 50
NEXT 50
DEQUEMAX
DEQUEMIN
INORDER
DELETE 50
DELETE 30
POSTORDER
ENQUE 35 Snotlout Jorgenson
INORDER
HEIGHT

test1.out
ZESTAW 1
POSTORDER: 15 - Hiccup Haddock, 30 - Astrid Hofferson, 45 - Stoick theVast, 40 - Fishlegs Ingerman
PREORDER: 40 - Fishlegs Ingerman, 30 - Astrid Hofferson, 15 - Hiccup Haddock, 45 - Stoick theVast
INORDER: 15 - Hiccup Haddock, 30 - Astrid Hofferson, 40 - Fishlegs Ingerman, 45 - Stoick theVast
PREV 15: BRAK
NEXT 45: BRAK
NEXT 30: 40 - Fishlegs Ingerman
PREV 30: 15 - Hiccup Haddock
PREV 50: BRAK
NEXT 50: BRAK
DEQUEMAX: 45 - Stoick theVast
DEQUEMIN: 15 - Hiccup Haddock
INORDER: 30 - Astrid Hofferson, 40 - Fishlegs Ingerman
DELETE 50: BRAK
POSTORDER: 40 - Fishlegs Ingerman
INORDER: 35 - Snotlout Jorgenson, 40 - Fishlegs Ingerman
HEIGHT: 1


test2.in

2
17
CREATE POSTORDER 5 20 Valka Haddock 25 Gobber theBelch 35 Heather Ingerman 50 Ruffnut Thorston 60 Tuffnut Thorston
INORDER
PREORDER
POSTORDER
PREV 25
NEXT 50
PREV 70
NEXT 70
DEQUEMAX
DEQUEMIN
INORDER
DELETE 70
DELETE 35
POSTORDER
ENQUE 40 Dagur theDeranged
INORDER
HEIGHT
18
CREATE PREORDER 6 10 Drago Bludvist 22 Eret SonofEret 28 Krogan theMerciless 32 Viggo Grimborn 44 Grimmel theGrisly 55 Mulch theFarmer
PREORDER
INORDER
POSTORDER
PREV 22
NEXT 44
PREORDER
PREV 60
NEXT 60
DEQUEMAX
DEQUEMIN
INORDER
DELETE 60
DELETE 28
POSTORDER
ENQUE 30 Savage the Treacherous
INORDER
HEIGHT

test2.out
ZESTAW 1
INORDER: 20 - Valka Haddock, 25 - Gobber theBelch, 35 - Heather Ingerman, 50 - Ruffnut Thorston, 60 - Tuffnut Thorston
PREORDER: 60 - Tuffnut Thorston, 50 - Ruffnut Thorston, 35 - Heather Ingerman, 25 - Gobber theBelch, 20 - Valka Haddock
POSTORDER: 20 - Valka Haddock, 25 - Gobber theBelch, 35 - Heather Ingerman, 50 - Ruffnut Thorston, 60 - Tuffnut Thorston
PREV 25: 20 - Valka Haddock
NEXT 50: 60 - Tuffnut Thorston
PREV 70: BRAK
NEXT 70: BRAK
DEQUEMAX: 60 - Tuffnut Thorston
DEQUEMIN: 20 - Valka Haddock
INORDER: 25 - Gobber theBelch, 35 - Heather Ingerman, 50 - Ruffnut Thorston
DELETE 70: BRAK
POSTORDER: 25 - Gobber theBelch, 50 - Ruffnut Thorston
INORDER: 25 - Gobber theBelch, 40 - Dagur theDeranged, 50 - Ruffnut Thorston
HEIGHT: 2
ZESTAW 2
PREORDER: 10 - Drago Bludvist, 22 - Eret SonofEret, 28 - Krogan theMerciless, 32 - Viggo Grimborn, 44 - Grimmel theGrisly, 55 - Mulch theFarmer
INORDER: 10 - Drago Bludvist, 22 - Eret SonofEret, 28 - Krogan theMerciless, 32 - Viggo Grimborn, 44 - Grimmel theGrisly, 55 - Mulch theFarmer
POSTORDER: 55 - Mulch theFarmer, 44 - Grimmel theGrisly, 32 - Viggo Grimborn, 28 - Krogan theMerciless, 22 - Eret SonofEret, 10 - Drago Bludvist
PREV 22: 10 - Drago Bludvist
NEXT 44: 55 - Mulch theFarmer
PREORDER: 10 - Drago Bludvist, 22 - Eret SonofEret, 28 - Krogan theMerciless, 32 - Viggo Grimborn, 44 - Grimmel theGrisly, 55 - Mulch theFarmer
PREV 60: BRAK
NEXT 60: BRAK
DEQUEMAX: 55 - Mulch theFarmer
DEQUEMIN: 10 - Drago Bludvist
INORDER: 22 - Eret SonofEret, 28 - Krogan theMerciless, 32 - Viggo Grimborn, 44 - Grimmel theGrisly
DELETE 60: BRAK
POSTORDER: 44 - Grimmel theGrisly, 32 - Viggo Grimborn, 22 - Eret SonofEret
INORDER: 22 - Eret SonofEret, 30 - Savage the, 32 - Viggo Grimborn, 44 - Grimmel theGrisly

HEIGHT: 2
*/