import java.util.*;

class Edge {
    int enode;
    int weight;

    Edge(int t, int w) {
        enode = t;
        weight = w;
    }
}

class Node {
    public LinkedList<Edge> adj;
    public boolean visited;
    public int distance;

    Node() {
        adj = new LinkedList<>();
    }
}

class NodeQ implements Comparable<NodeQ> {
    public int cost;
    public int node;

    NodeQ(int c, int n) {
        cost = c;
        node = n;
    }

    @Override
    public int compareTo(NodeQ nq) {
        if (cost < nq.cost) return -1;
        if (cost > nq.cost) return +1;
        if (node < nq.node) return -1;
        if (node > nq.node) return +1;
        return 0;
    }
}

class Graph {
    int n;
    Node[] nodes;

    Graph(int n) {
        this.n = n;
        nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++)
            nodes[i] = new Node();
    }

    void addLink(int a, int b, int c) {
        nodes[a].adj.add(new Edge(b, c));
        nodes[b].adj.add(new Edge(a, c)); // Grafo não direcionado
    }

    List<Integer> dijkstra(int s) {
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

        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) result.add(i);

        // Ordenar por distância (menor primeiro) e em caso de empate pelo identificador
        result.sort((a, b) -> {
            if (nodes[a].distance != nodes[b].distance)
                return Integer.compare(nodes[a].distance, nodes[b].distance);
            return Integer.compare(a, b);
        });

        return result;
    }
}

public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int destino = in.nextInt();
        Graph g = new Graph(n);

        while (true) {
            int u = in.nextInt();
            if (u == -1) break;
            int v = in.nextInt();
            int c = in.nextInt();
            g.addLink(u, v, c);
        }

        List<Integer> ordem = g.dijkstra(destino);
        for (int i = 0; i < ordem.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(ordem.get(i));
        }
        System.out.println();
    }
}
