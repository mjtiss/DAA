import java.io.*;
import java.util.*;

class Qnode {
    int vert;
    int vertkey;
    Qnode(int v, int key) {
        vert = v;
        vertkey = key;
    }
}

class HeapMax {
    private static int posinvalida = 0;
    int sizeMax, size;
    Qnode[] a;
    int[] pos_a;

    HeapMax(int vec[], int n) {
        a = new Qnode[n + 1];
        pos_a = new int[n + 1];
        sizeMax = n;
        size = n;
        for (int i = 1; i <= n; i++) {
            a[i] = new Qnode(i, vec[i]);
            pos_a[i] = i;
        }
        for (int i = n / 2; i >= 1; i--)
            heapify(i);
    }

    boolean isEmpty() {
        return size == 0;
    }

    int extractMax() {
        int vertv = a[1].vert;
        swap(1, size);
        pos_a[vertv] = posinvalida;
        size--;
        heapify(1);
        return vertv;
    }

    void increaseKey(int vertv, int newkey) {
        int i = pos_a[vertv];
        a[i].vertkey = newkey;
        while (i > 1 && compare(i, parent(i)) < 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    void insert(int vertv, int key) {
        if (sizeMax == size)
            throw new RuntimeException("Heap is full\n");
        size++;
        a[size] = new Qnode(vertv, Integer.MIN_VALUE);
        pos_a[vertv] = size;
        increaseKey(vertv, key);
    }

    void write_heap() {
        System.out.printf("Max size: %d\n", sizeMax);
        System.out.printf("Current size: %d\n", size);
        System.out.printf("(Vert,Key)\n---------\n");
        for (int i = 1; i <= size; i++)
            System.out.printf("(%d,%d)\n", a[i].vert, a[i].vertkey);
        System.out.printf("-------\n(Vert,PosVert)\n---------\n");
        for (int i = 1; i <= sizeMax; i++)
            if (pos_valida(pos_a[i]))
                System.out.printf("(%d,%d)\n", i, pos_a[i]);
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    private int compare(int i, int j) {
        if (a[i].vertkey > a[j].vertkey) return -1;
        if (a[i].vertkey == a[j].vertkey) return 0;
        return 1;
    }

    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int largest = i;
        if (l <= size && compare(l, largest) < 0)
            largest = l;
        if (r <= size && compare(r, largest) < 0)
            largest = r;
        if (i != largest) {
            swap(i, largest);
            heapify(largest);
        }
    }

    private void swap(int i, int j) {
        Qnode aux;
        pos_a[a[i].vert] = j;
        pos_a[a[j].vert] = i;
        aux = a[i];
        a[i] = a[j];
        a[j] = aux;
    }

    private boolean pos_valida(int i) {
        return (i >= 1 && i <= size);
    }

    // public static void main(String[] args) {
    //     int[] vec = {0, 4, 8, 9, 3, 4, 5, 1}; // index 0 is dummy
    //     HeapMax h = new HeapMax(vec, 7);
    //     h.write_heap();
    // }
}


class Edge {
    int from, to;
    int largura, comprimento, altura;

    Edge(int from, int to, int largura, int comprimento, int altura) {
        this.from = from;
        this.to = to;
        this.largura = largura;
        this.comprimento = comprimento;
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "(" + from + "->" + to + " | " + largura + ", " + comprimento + ", " + altura + ")";
    }
}


public class D {
    public static void main(String[] args) {
        ArrayList<Edge> edges = new ArrayList<>();
        HashMap<Integer, Integer> nodes = new HashMap<Integer,Integer>();
        Scanner scanner = new Scanner(System.in);


        int larguraMin = scanner.nextInt();
        int larguraMax = scanner.nextInt();
        int comprimentoMin = scanner.nextInt();
        int comprimentoMax = scanner.nextInt();
        int alturaMin = scanner.nextInt();

        int origem = scanner.nextInt();
        int destino = scanner.nextInt();

        int no1 = scanner.nextInt();

        while(no1 != -1){
            int no2 = scanner.nextInt();
            int larguraJaula = scanner.nextInt();
            int comprimentoJaula = scanner.nextInt();
            int alturaJaula = scanner.nextInt();
            if(larguraJaula >= larguraMin && larguraJaula <= larguraMax && 
                comprimentoJaula >= comprimentoMin && comprimentoJaula <= comprimentoMax && 
                alturaJaula >= alturaMin){
                edges.add(new Edge(no1, no2, larguraJaula, comprimentoJaula, alturaJaula));
                edges.add(new Edge(no2, no1, larguraJaula, comprimentoJaula, alturaJaula)); //bidirecional
                if(!nodes.keySet().constains(no1)){
                    nodes.put(nodes.size(),no1);
                }    
                if(!nodes.keySet().constains(no2))
                    nodes.put(nodes.size(),no2);
            }
            no1 = scanner.nextInt();

        }
        

        
        // dijkstra
        ArrayList<Integer> pai  = new ArrayList<>();
        ArrayList<Integer> dist = new ArrayList<>();
        for(i=0;i<nodes.size();i++){
            pai[i]=null;
            dist[i]=0;
        }
        HeapMax heap = new Heapmax(nodes.toArray(),edges.size());

        while(!heap.isEmpty()){
            Edge v = heap.extractMax();
            for(int w : nodes.toArray()){
                if(dist[v]+)
            }
        }

    }
}