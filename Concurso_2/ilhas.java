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
    ArrayList<Integer>[] adjacencyList;    //Lista de adjacências para armazenar o grafo

    // constructs a graph with n nodes and 0 edges
    @SuppressWarnings("unchecked")
    Graph(int n) {
        this.n = n;
        //nodes  = new Node[n+1]; // +1 if the labels start at 1 instead of 0
        adjacencyList = new ArrayList[n+1];
        for (int i=1; i<=n; i++)
            adjacencyList[i] = new ArrayList<>();
    }
    
    // adds edge from a to b and another from b to a
    public void addEdge(int a, int b) {
        if (adjacencyList[a] == null) adjacencyList[a] = new ArrayList<>();
        if (adjacencyList[b] == null) adjacencyList[b] = new ArrayList<>();

        adjacencyList[a].add(b);
        adjacencyList[b].add(a);
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
    public void dfs(int v, boolean[] visitados, int[] componente, int componenteId) {
        visitados[v] = true;
        componente[v] =componenteId;
	    
	    for (int vizinho : adjacencyList[v])
            if (!visitados[vizinho]){
                dfs(vizinho, visitados, componente, componenteId);
            }
		
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

public class ilhas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();  // nos 
        int r = scanner.nextInt();  // ligacoes 

        Graph g = new Graph(n);

        for(int i = 0; i < r; i++){
            int u = scanner.nextInt();  
            int v = scanner.nextInt();
            g.addEdge(u,v);
        }

        boolean[] visitados = new boolean[n+1]; // marca se no foi visitado 
        int[] componente = new int[n+1];    // armazena o componente de cada no 
        int componenteId = 0;
        int[] componenteMax = new int[n+1]; // armazena o no com maior identificador de cada componente

        // DFS a partir dos nos por ordem decrescente 
        for(int i = n; i >= 1; i--){
            if (!visitados[i]){
                componenteId ++;
                g.dfs(i, visitados, componente, componenteId);

                int maxNode = i;

                for(int j = 1; j <= n; j++){
                    if (componente[j] == componenteId && j > maxNode){
                        maxNode = j;
                    }
                }

                // atribui o maxNode a todos os nos do componente
                for(int j = 1; j <= n; j++){
                    if (componente[j] == componenteId){
                        componenteMax[j] = maxNode;
                    }
                }
            }
        }

        int q = scanner.nextInt();
        for(int i = 0; i < q; i++){
            int v = scanner.nextInt();
            System.out.println("No " + v + ": " + componenteMax[v]);
        }
        
    }
}


