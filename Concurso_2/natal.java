import java.util.*;


import java.io.*;
import java.util.*;

// Class that represents a node
class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    // public int distance;         // Distance from a source node (or to keep some value)?


    Node() {
        adj = new LinkedList<Integer>();
    }
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
    public void addEdge(int a, int b) {
        nodes[a].adj.add(b);
        nodes[b].adj.add(a);
    }

    // checks whether {a,b} is an edge
    public boolean isEdge(int a,int b) {
	return nodes[a].adj.contains(b);
    }

    // sets all nodes as not visited
    public void clearVisited() {
	for(int i=1; i<=n; i++)
	    nodes[i].visited = false;
    }
	


    // --------------------------------------------------------------
    // Breadth-First Search (BFS) from node v: example implementation
    // --------------------------------------------------------------
    public void bfs(int inicio, int distanciaMax, int[] paisNatais, Set<Integer> acessivel) {
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        int[] distancias = new int[n+1]; // guarda a distancia a partir do no inicial
        Arrays.fill(distancias, -1); // -1 == não visitado
        for (int i=1; i<=n; i++) nodes[i].visited = false;

        q.add(inicio);
        distancias[inicio] = 0; 
        /*nodes[v].visited = true;
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

       while (!q.isEmpty()){
        int u = q.removeFirst();

        if (distancias[u] <= distanciaMax && paisNatais[u] > 0){
            acessivel.add(u);
        }

        for(int w : nodes[u].adj){
            if (distancias[w] == -1){
                distancias[w] = distancias[u] + 1;
                q.add(w);
            }
        }
       }
    }

    // --------------------------------------------------------------
    // Depth-First Search (DFS) from node v: example implementation
    // --------------------------------------------------------------
    public void dfs(int v) {
        nodes[v].visited = true;
	System.out.println(v);
	    
	for (int w : nodes[v].adj)
	    if (!nodes[w].visited) 
		dfs(w);
    }


    // --------------------------------------------------------------
    // Read a undirected graph in the format:
    // nr_nodes
    // nr_edges
    // src_1 dest_1
    // src_2 dest_2
    // ...
    // src_n dest_n
    public static Graph readGraph(Scanner scanner) {
        int n = scanner.nextInt();
        Graph g = new Graph(n);
        int nedges = scanner.nextInt();
        for (int i = 0; i < nedges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            g.addEdge(u, v);
        }
        return g;
    }
  
}

/*para encontrar lojas com Pais Natais num grafo de lojas interconectaas utilizamos um BFS para 
encontrar os caminhos e determinar quantas lojas cumprem as condições*/


public class natal{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] paisNatais = new int[n+1];
        for(int i = 1; i <= n; i++){
            paisNatais[i] = scanner.nextInt();
        }

        int r = scanner.nextInt();
        Graph g = new Graph(n);
        for(int i = 0; i < r; i++){
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            g.addEdge(x,y);
        }

        int P = scanner.nextInt();  // Loja atual
        int K = scanner.nextInt();  // distancia macima 

        // loja P ja tem pais natais 
        if(paisNatais[P] > 0){
            System.out.println("Que sorte");
            return;
        }

        // caso contrario aplicamos o bfs 
        Set<Integer> acessivel = new HashSet<>();
        g.bfs(P,K,paisNatais, acessivel);

        System.out.println(acessivel.size());
        
    }
    

}