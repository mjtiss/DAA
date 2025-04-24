// -------------------------------------------------------------
// Design and Analysis of Algorithms 2024/2025 (FCUP)
// http://www.dcc.fc.up.pt/~apt/aulas/DAA/2425/
// File: Search.java
// -------------------------------------------------------------


import java.io.*;
import java.util.*;


// Class that represents an edge 
class Edge{
    int to;             // no de destino
    int cost;           // custo da aresta
    int temperature;    // temperatura da aresta

    Edge(int to, int cost, int temperature){
        this.to = to;
        this.cost = cost;
        this.temperature = temperature;
    }
}

public class C {

    public static void algoritmoDijkstra(Graph G, int s, int[] dist, int[] pai){

        for(int v = 1; v <= G.n; v++){
            dist[v] = Integer.MAX_VALUE;    // definr a distancia como infinita
            pai[v] = -1;    // definir pai como NULL(nao exite)
        }
        dist[s] = 0;    // a distancia do no origem e 0


        // min-heap 
        PriorityQueue<Integer> Q = new PriorityQueue<> (Comparator.comparingInt(v -> dist[v]));
        Q.add(s);

        while(!Q.isEmpty()){
            int v = Q.poll();

            for(Edge e : G.nodes[v].adj){
                int w = e.to;
                int weight = e.cost;

                if (dist[v] + weight < dist[w]){
                    dist[w] = dist[v] + weight;
                    pai[w] = v;

                    Q.add(w);
                }
            }
        }
    }

    // verificar se e possivel fazer o transporte sem ultrapassar o valor 
    public static void verificarTransporte(Graph G, int origem, int destino, int limite, int[] dist){

        //Rodar o Dijkstra 
        int[] pai = new int[G.n + 1];
        algoritmoDijkstra(G, origem, dist, pai);


        //Verificar se a distancia ao destino esta dentro do limite 
        if(dist[destino] <= limite){
            System.out.println("Sim");
        }else{
            System.out.println("Nao");
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int temp_min = scanner.nextInt();
        int temp_max = scanner.nextInt();
        int origem = scanner.nextInt();
        int destino = scanner.nextInt();

        int nos = scanner.nextInt();    // numero de nos
        int ramos = scanner.nextInt();  // numero de arestas

        Graph g = new Graph(nos);

        for(int i = 1; i <= ramos; i++){
            int u = scanner.nextInt();              // origem do troço
            int v = scanner.nextInt();              // destino do troço 
            int temperatura = scanner.nextInt();    // temperatura 
            int custo = scanner.nextInt();          // nao necessario neste problema

            // so adicionar se a temperatura estiver dentro do intervalo
            if (temperatura >= temp_min && temperatura <= temp_max){
                g.addEdge(u, v, custo, temperatura);
            } 

        }

        int cenarios = scanner.nextInt();
        int[] dist = new int[nos + 1];
        for(int i = 0; i < cenarios; i++){
            int limite = scanner.nextInt();
            verificarTransporte(g,origem,destino, limite, dist);
        }

        
    }
}


// Class that represents a node
class Node {
    public LinkedList<Edge> adj = new LinkedList<Edge>(); // Lista de arestas adjaccentes
    public boolean visited;         // Has the node been visited in a graph traversal?
    // public int distance;         // Distance from a source node (or to keep some value)?

}

// Class that represents a graph
class Graph {
    int n;           // Number of nodes of the graph
    Node nodes[];    // Array that will contain the nodes

    // constructs a graph with n nodes and 0 edges
    Graph(int n) {
        this.n = n;
        nodes  = new Node[n+1]; // +1 if the labels start at 1 instead of 0
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b,int cost, int temperature) {
        nodes[a].adj.add(new Edge(b,cost,temperature));
        nodes[b].adj.add(new Edge(a,cost,temperature)); // bidirecional
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
	return nodes[a].adj.contains(b);
    }

    // sets all nodes as not visited
    /*public void clearVisited() {
	for(int i=1; i<=n; i++)
	    return nodes[i].visited = false;
    }*/
	


    // --------------------------------------------------------------
    // Breadth-First Search (BFS) from node v: example implementation
    // --------------------------------------------------------------
    public int bfs(int origem, int destino) {
        int[] dist = new int[n + 1];    //dist[i] = distancia de origem ate i
        boolean[] visited = new boolean[n + 1];

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(origem);
        visited[origem] = true;
        dist[origem] = 0;

        while(!queue.isEmpty()){
            int u = queue.removeFirst();
            
            if (u == destino){
                return dist[u]; // chegamos ao destino, devolver a distancia
            }

            for (Edge e : nodes[u].adj){
            int v = e.to;
            if (!visited[v]){
                visited[v] = true;
                dist[v] = dist[u] + 1;
                queue.add(v);
                }
            }
        }
        /*for (int i=1; i<=n; i++) nodes[i].visited = false;

        q.add(v);
        nodes[v].visited = true;
	System.out.println(v);
	    
        while (q.size() > 0) {
            int u = q.removeFirst();
            for (int w : nodes[u].adj)
                if (!nodes[w].visited) {
                    q.add(w);
                    nodes[w].visited  = true;
		    System.out.println(w);
                }	    
        }*/

       return -1; // nao foi possivel chegar ao destino
    }

    // --------------------------------------------------------------
    // Depth-First Search (DFS) from node v: example implementation
    // --------------------------------------------------------------
    /*public void dfs(int v) {
        nodes[v].visited = true;
	System.out.println(v);
	    
	for (int w : nodes[v].adj)
	    if (!nodes[w].visited) 
		dfs(w);
    }*/


    // --------------------------------------------------------------
    // Read a undirected graph in the format:
    // nr_nodes
    // nr_edges
    // src_1 dest_1
    // src_2 dest_2
    // ...
    // src_n dest_n
    /*public static Graph readGraph(Scanner scanner) {
        int n = scanner.nextInt();
        Graph g = new Graph(n);
        int nedges = scanner.nextInt();
        for (int i = 0; i < nedges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            g.addEdge(u, v);
        }
        return g;
    }*/
  
}

/*
    - Verificar se e possivel transportar um animal de uma certa especie
    de um no origrem para um certo no destino
    - Se for possivel, inficar o comprimento minimo do percurso 
    - A especie tem restricoes quanto a temperatura minima e maxima que
    suporta, pelo que so poderao ser considerados percursos compativeis
    com essas restricoes
*/

