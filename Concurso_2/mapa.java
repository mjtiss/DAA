
import java.io.*;
import java.util.*;

// Class that represents a node
class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?
    public int num_adj;
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
        nodes[a].num_adj ++;
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
    public void bfs(int v) {
        LinkedList<Integer> q = new LinkedList<Integer>();   // queue
        for (int i=1; i<=n; i++) nodes[i].visited = false;

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
        int trajetos = scanner.nextInt();
        Graph g = new Graph(n);
        int nedges = scanner.nextInt();
        for(int j = 0; j < trajetos; j++){
            for (int i = 0; i < nedges; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                g.addEdge(u, v);
            }
        }
        return g;
    }
  
}

public class mapa{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int n_nos = scanner.nextInt();            // nverts
        int n_trajetos = scanner.nextInt();       // nrots   
        Graph grafo = new Graph(n_nos);
        //System.out.println("N : " + n_nos + "trajetos " + n_trajetos);
        for(int i = 0; i < n_trajetos; i++){
            int n_edges = scanner.nextInt();
            int n_adjacentes1 = scanner.nextInt();
            //System.out.println("Edges de node " + i + ": " + n_edges);
            for(int j = 0; j < n_edges-1; j++){
            int n_adjacentes2 = scanner.nextInt();
            //System.out.println("adicionar aresta " + n_adjacentes1 + "->" + n_adjacentes2);
            if (!grafo.isEdge(n_adjacentes1, n_adjacentes2)){
                grafo.addEdge(n_adjacentes1, n_adjacentes2);
            }
            n_adjacentes1 = n_adjacentes2;
            }
        }

        for(int i = 1; i <= grafo.n; i++){
            System.out.println(grafo.nodes[i].num_adj);
            /*for(int j : grafo.nodes[i].adj)
                System.out.print(grafo.nodes[i].adj);
            System.out.println();*/
        }


    }
}