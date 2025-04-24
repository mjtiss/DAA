import java.util.*;

// Class that represents a node
class Node {
    public LinkedList<Integer> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;         // Has the node been visited in a graph traversal?

    Node() {
        adj = new LinkedList<Integer>();
    }
}

// Class that represents a graph
class Graph {
    int n; // Number of nodes of the graph
    Node nodes[]; // Array that will contain the nodes
    ArrayList<Integer>[] adjacencyList; // Lista de adjacências para armazenar o grafo

    @SuppressWarnings("unchecked")
    Graph(int n) {
        this.n = n;
        adjacencyList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            adjacencyList[i] = new ArrayList<>();
    }

    // Adiciona uma ligação entre os nós a e b
    public void addEdge(int a, int b) {
        if (adjacencyList[a] == null) adjacencyList[a] = new ArrayList<>();
        if (adjacencyList[b] == null) adjacencyList[b] = new ArrayList<>();

        adjacencyList[a].add(b);
        adjacencyList[b].add(a);
    }

    // Realiza a busca em profundidade (DFS) para encontrar o componente do nó v
    public void dfs(int v, boolean[] visitados, int[] componente, int componenteId) {
        visitados[v] = true;
        componente[v] = componenteId;

        for (int vizinho : adjacencyList[v])
            if (!visitados[vizinho]) {
                dfs(vizinho, visitados, componente, componenteId);
            }
    }
}

public class rascunho {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // Nós
        int r = scanner.nextInt(); // Ligações

        Graph g = new Graph(n);

        // Lê as ligações entre os nós
        for (int i = 0; i < r; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            g.addEdge(u, v);
        }

        boolean[] visitados = new boolean[n + 1]; // Marca se o nó foi visitado
        int[] componente = new int[n + 1]; // Armazena o componente de cada nó
        int componenteId = 0;
        int[] componenteMax = new int[n + 1]; // Armazena o nó com maior identificador de cada componente

        // DFS a partir dos nós por ordem decrescente
        for (int i = n; i >= 1; i--) {
            if (!visitados[i]) {
                componenteId++; // Novo componente encontrado
                g.dfs(i, visitados, componente, componenteId);

                // Encontrar o nó de maior identificador no componente
                int maxNode = i;
                for (int j = 1; j <= n; j++) {
                    if (componente[j] == componenteId && j > maxNode) {
                        maxNode = j;
                    }
                }

                // Atribui o maxNode a todos os nós do componente
                for (int j = 1; j <= n; j++) {
                    if (componente[j] == componenteId) {
                        componenteMax[j] = maxNode; // Só atribui ao nó do mesmo componente
                    }
                }
            }
        }

        // Número de questões
        int q = scanner.nextInt();
        // Responde a cada questão
        for (int i = 0; i < q; i++) {
            int v = scanner.nextInt();
            // Imprime o nó de maior identificador do componente a que o nó v pertence
            System.out.println("No " + v + ": " + componenteMax[v]);
        }
    }
}
