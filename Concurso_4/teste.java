import java.util.*;

// Classe para representar uma aresta
class Edge {
    int enode;
    int weight;

    Edge(int t, int w) {
        enode = t;
        weight = w;
    }
}

// Classe que representa um nó do grafo
class Node {
    public LinkedList<Edge> adj = new LinkedList<>();
    public boolean visited;
    public int distance;

    Node() {
        distance = Integer.MAX_VALUE;
        visited = false;
    }
}

// Nó para fila de prioridade
class NodeQ implements Comparable<NodeQ> {
    public int cost;
    public int node;

    NodeQ(int c, int n) {
        cost = c;
        node = n;
    }

    @Override
    public int compareTo(NodeQ nq) {
        if (cost != nq.cost)
            return Integer.compare(cost, nq.cost);
        return Integer.compare(node, nq.node);
    }
}

// Grafo com Dijkstra
class Graph {
    int n;
    Node[] nodes;

    Graph(int n) {
        this.n = n;
        nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node();
    }

    void addEdge(int a, int b, int c) {
        // Como é não direcionado, adiciona em ambos os sentidos
        nodes[a].adj.add(new Edge(b, c));
        nodes[b].adj.add(new Edge(a, c));
    }

    void dijkstra(int s) {
        for (int i = 1; i <= n; i++) {
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited = false;
        }

        nodes[s].distance = 0;
        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0, s));

        while (!q.isEmpty()) {
            NodeQ nq = q.pollFirst();
            int u = nq.node;
            if (nodes[u].visited) continue;

            nodes[u].visited = true;

            for (Edge e : nodes[u].adj) {
                int v = e.enode;
                int cost = e.weight;
                if (!nodes[v].visited && nodes[u].distance + cost < nodes[v].distance) {
                    q.remove(new NodeQ(nodes[v].distance, v));
                    nodes[v].distance = nodes[u].distance + cost;
                    q.add(new NodeQ(nodes[v].distance, v));
                }
            }
        }
    }

    List<Integer> getSortedNodesByDistance() {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            result.add(i);
        result.sort((a, b) -> {
            if (nodes[a].distance != nodes[b].distance)
                return Integer.compare(nodes[a].distance, nodes[b].distance);
            return Integer.compare(a, b);
        });
        return result;
    }
}

public class teste {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // número de regiões
        int dest = sc.nextInt(); // região destino

        Graph g = new Graph(n);

        while (true) {
            int a = sc.nextInt();
            if (a == -1) break;
            int b = sc.nextInt();
            int d = sc.nextInt();
            g.addEdge(a, b, d);
        }

        g.dijkstra(dest);

        List<Integer> order = g.getSortedNodesByDistance();
        for (int id : order) {
            System.out.print(id + " ");
        }
        System.out.println();
    }
}
