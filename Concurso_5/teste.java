import java.util.*;

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
}

class Node {
    LinkedList<Integer> adj;

    Node() {
        adj = new LinkedList<>();
    }
}

class Graph {
    Map<Integer, Node> nodes = new HashMap<>();

    void addEdge(int a, int b) {
        nodes.putIfAbsent(a, new Node());
        nodes.putIfAbsent(b, new Node());
        nodes.get(a).adj.add(b);
        nodes.get(b).adj.add(a);
    }

    boolean bfs(int start, int goal) {
        if (!nodes.containsKey(start) || !nodes.containsKey(goal)) return false;

        Queue<Integer> q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        q.add(start);
        visited.add(start);

        while (!q.isEmpty()) {
            int u = q.poll();
            if (u == goal) return true;

            for (int v : nodes.get(u).adj) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    q.add(v);
                }
            }
        }
        return false;
    }
}

public class D {
    static int largMin, largMax, compMin, compMax, altMin;
    static int origem, destino;
    static List<Edge> edges = new ArrayList<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Ler restrições
        largMin = in.nextInt();
        largMax = in.nextInt();
        compMin = in.nextInt();
        compMax = in.nextInt();
        altMin = in.nextInt();

        origem = in.nextInt();
        destino = in.nextInt();

        // Ler arestas
        while (true) {
            int a = in.nextInt();
            if (a == -1) break;
            int b = in.nextInt();
            int l = in.nextInt();
            int c = in.nextInt();
            int h = in.nextInt();

            edges.add(new Edge(a, b, l, c, h));
        }

        System.out.println(maxValidLength());
    }

    static int maxValidLength() {
        int low = compMin;
        int high = compMax;
        int best = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (isValidPath(mid)) {
                best = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return best;
    }

    static boolean isValidPath(int testComp) {
        Graph g = new Graph();

        // Garantir que origem e destino existem no grafo mesmo que fiquem isolados
        g.nodes.putIfAbsent(origem, new Node());
        g.nodes.putIfAbsent(destino, new Node());

        for (Edge e : edges) {
            if (e.largura >= largMin && e.largura <= largMax &&
                e.comprimento >= testComp && e.comprimento <= compMax &&
                e.altura >= altMin) {

                g.addEdge(e.from, e.to);
            }
        }

        return g.bfs(origem, destino);
    }
}
