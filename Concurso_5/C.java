import java.util.*;

public class C {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int origem = scanner.nextInt(); // nó origem
        int A = scanner.nextInt();      // dimensão mínima do cubo
        int B = scanner.nextInt();      // dimensão máxima do cubo

        int N = scanner.nextInt();      // número de nós
        int R = scanner.nextInt();      // número de arestas

        Graph graph = new Graph(N);

        // Leitura das ligações normais (sem restrições conhecidas)
        for (int i = 0; i < R; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v, Integer.MAX_VALUE); // sem restrição → capacidade infinita
        }

        int restritas = scanner.nextInt(); // número de arestas com restrições

        // Leitura das ligações restritas
        for (int i = 0; i < restritas; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int h = scanner.nextInt();
            int l = scanner.nextInt();

            int capacidade = Integer.MAX_VALUE;
            if (h != -1 && l != -1) capacidade = Math.min(h, l);
            else if (h != -1) capacidade = h;
            else if (l != -1) capacidade = l;

            graph.updateEdge(u, v, capacidade); // atualiza capacidade se houver restrição
        }

        int[] maxTamanho = graph.widestPath(origem); // calcula maior cubo possível para cada nó

        boolean todosOk = true;
        for (int i = 1; i <= N; i++) {
            if (i == origem) continue;
            int capacidade = maxTamanho[i];

            if (capacidade >= B) continue;

            todosOk = false;
            if (capacidade >= A) {
                System.out.println("No " + i + ": " + capacidade);
            } else {
                System.out.println("No " + i + ": 0");
            }
        }

        if (todosOk) {
            System.out.println("Ok todos destinos!");
        }
    }
}

// Classe Edge representa uma aresta com limite de capacidade (tamanho de cubo)
class Edge {
    int dest, capacidade;

    Edge(int dest, int capacidade) {
        this.dest = dest;
        this.capacidade = capacidade;
    }
}

// Classe Node com lista de adjacências
class Node {
    LinkedList<Edge> adj;

    Node() {
        adj = new LinkedList<>();
    }
}

// Classe Graph com algoritmo de Widest Path
class Graph {
    int n;
    Node[] nodes;

    Graph(int n) {
        this.n = n;
        nodes = new Node[n + 1]; // índice começa em 1
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node();
        }
    }

    // Adiciona aresta bidirecional
    void addEdge(int u, int v, int capacidade) {
        nodes[u].adj.add(new Edge(v, capacidade));
        nodes[v].adj.add(new Edge(u, capacidade));
    }

    // Atualiza capacidade de uma aresta existente (ou cria uma nova se necessário)
    void updateEdge(int u, int v, int novaCapacidade) {
        boolean updated = false;
        for (Edge e : nodes[u].adj) {
            if (e.dest == v) {
                e.capacidade = Math.min(e.capacidade, novaCapacidade);
                updated = true;
                break;
            }
        }
        for (Edge e : nodes[v].adj) {
            if (e.dest == u) {
                e.capacidade = Math.min(e.capacidade, novaCapacidade);
                break;
            }
        }

        // Se não encontrou a aresta, adiciona
        if (!updated) addEdge(u, v, novaCapacidade);
    }

    // Widest Path: para cada nó, guarda o maior tamanho de cubo possível
    int[] widestPath(int origem) {
        int[] capacidade = new int[n + 1];
        Arrays.fill(capacidade, -1);
        capacidade[origem] = Integer.MAX_VALUE;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[1], a[1]));
        pq.add(new int[]{origem, Integer.MAX_VALUE});

        while (!pq.isEmpty()) {
            int[] atual = pq.poll();
            int u = atual[0];
            int capAtual = atual[1];

            for (Edge e : nodes[u].adj) {
                int v = e.dest;
                int novaCapacidade = Math.min(capAtual, e.capacidade);

                if (novaCapacidade > capacidade[v]) {
                    capacidade[v] = novaCapacidade;
                    pq.add(new int[]{v, novaCapacidade});
                }
            }
        }

        return capacidade;
    }
}
