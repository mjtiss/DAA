import java.util.*;

// Classe principal com a main
public class B {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ler número de nós, número de ligações e custo de manutenção por ligação
        int n = scanner.nextInt();      // número de nós
        int r = scanner.nextInt();      // número de arestas
        int custo = scanner.nextInt();  // custo de manutenção (fixo) por ligação

        Graph graph = new Graph(n); // cria o grafo com n nós

        // Ler todas as arestas com rendimento bruto
        for (int i = 0; i < r; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int rendimento = scanner.nextInt();
            graph.addEdge(u, v, rendimento);
        }

        // Calcular rendimento líquido da MST com rendimento bruto máximo
        int resultado = graph.maximizarRendimentoLiquido(custo);

        if (resultado == -1) {
            System.out.println("impossivel");
        } else {
            System.out.println("rendimento optimo: " + resultado);
        }
    }
}

// Classe que representa uma aresta
class Edge implements Comparable<Edge> {
    int u, v, rendimento;

    Edge(int u, int v, int rendimento) {
        this.u = u;
        this.v = v;
        this.rendimento = rendimento;
    }

    // Ordenar por rendimento bruto decrescente
    public int compareTo(Edge other) {
        return Integer.compare(other.rendimento, this.rendimento);
    }
}

// Classe de nó — opcional para este problema mas mantida para estilo comum
class Node {
    LinkedList<Edge> adj;

    Node() {
        adj = new LinkedList<>();
    }
}

// Classe que representa o grafo
class Graph {
    int n;  // número de nós
    List<Edge> allEdges;  // lista de todas as arestas do grafo

    Graph(int n) {
        this.n = n;
        allEdges = new ArrayList<>();
    }

    // Adiciona uma aresta ao grafo
    void addEdge(int u, int v, int rendimento) {
        allEdges.add(new Edge(u, v, rendimento));
    }

    // Função principal: usar Kruskal para obter MST com rendimento bruto máximo
    int maximizarRendimentoLiquido(int custoPorLigacao) {
        Collections.sort(allEdges); // ordenar por rendimento decrescente

        UnionFind uf = new UnionFind(n);  // estrutura de conjuntos disjuntos
        int totalRendimento = 0;  // soma dos rendimentos escolhidos
        int ligacoesUsadas = 0;   // número de ligações usadas na MST

        for (Edge e : allEdges) {
            if (uf.union(e.u, e.v)) {  // se os nós não estavam ligados ainda
                totalRendimento += e.rendimento;
                ligacoesUsadas++;
                if (ligacoesUsadas == n - 1) break; // MST completa
            }
        }

        // Se não foi possível formar MST completa
        if (ligacoesUsadas < n - 1) return -1;

        // Cálculo do rendimento líquido
        return totalRendimento - custoPorLigacao * ligacoesUsadas;
    }
}

// Estrutura Union-Find para Kruskal
class UnionFind {
    int[] parent;

    UnionFind(int n) {
        parent = new int[n + 1]; // assume nós numerados de 1 a n
        for (int i = 1; i <= n; i++) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) return false; // já ligados
        parent[rootB] = rootA; // unir os conjuntos
        return true;
    }
}
