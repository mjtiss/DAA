// -------------------------------------------------------------
// Design and Analysis of Algorithms 2024/2025 (FCUP)
// http://www.dcc.fc.up.pt/~apt/aulas/DAA/2425/
// File: Search.java
// -------------------------------------------------------------


import java.io.*;
import java.util.*;

// Edge class to represent a weighted connection between nodes
class Edge {
    int to, rev;     // destination and reverse edge index
    int capacity;    // capacity of the edge
    int flow;        // current flow

    Edge(int to, int rev, int capacity) {
        this.to = to;
        this.rev = rev;
        this.capacity = capacity;
        this.flow = 0;
    }
}


class Graph {
    int n;
    List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    Graph(int n) {
        this.n = n;
        adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++)
            adj[i] = new ArrayList<>();
    }

    // Adds a forward and backward (residual) edge
    void addEdge(int u, int v, int capacity) {
        adj[u].add(new Edge(v, adj[v].size(), capacity));
        adj[v].add(new Edge(u, adj[u].size() - 1, 0));  // Reverse edge
    }

    // Edmonds-Karp algorithm for maximum flow from source
    int[] edmondsKarp(int source) {
        int[] flowToNode = new int[n + 1];  // Total flow reaching each node

        for (int sink = 1; sink <= n; sink++) {
            if (sink == source) continue;
            int flow = bfsAndAugment(source, sink);
            flowToNode[sink] = flow;
        }

        return flowToNode;
    }

    // BFS to find shortest augmenting path and return total flow to sink
    int bfsAndAugment(int source, int sink) {
        int flow = 0;
        int[][] parent = new int[n + 1][2];

        while (true) {
            Arrays.fill(parent, new int[]{-1, -1});
            Queue<Integer> q = new LinkedList<>();
            q.add(source);
            while (!q.isEmpty() && parent[sink][0] == -1) {
                int u = q.poll();
                for (int i = 0; i < adj[u].size(); i++) {
                    Edge e = adj[u].get(i);
                    if (parent[e.to][0] == -1 && e.capacity > e.flow && e.to != source) {
                        parent[e.to][0] = u;
                        parent[e.to][1] = i;
                        q.add(e.to);
                    }
                }
            }

            if (parent[sink][0] == -1) break;

            // Find bottleneck capacity
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; ) {
                int u = parent[v][0];
                int idx = parent[v][1];
                pathFlow = Math.min(pathFlow, adj[u].get(idx).capacity - adj[u].get(idx).flow);
                v = u;
            }

            // Update residual capacities
            for (int v = sink; v != source; ) {
                int u = parent[v][0];
                int idx = parent[v][1];
                adj[u].get(idx).flow += pathFlow;
                int rev = adj[u].get(idx).rev;
                adj[v].get(rev).flow -= pathFlow;
                v = u;
            }

            flow += pathFlow;
        }

        return flow;
    }
}


public class A {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nMinElementos = scanner.nextInt();
        int origemGrupo = scanner.nextInt();
        int nMaxElementos = scanner.nextInt();

        int nos = scanner.nextInt();
        int ramos = scanner.nextInt();
        Graph g = new Graph(nos);

        for(int i = 1; i <= ramos; i++ ){
            int origem = scanner.nextInt();
            int destino = scanner.nextInt();
            int lugaresDisponiveis = scanner.nextInt();
            g.addEdge(origem, destino, lugaresDisponiveis);
            
        }

        int[] result = g.edmondsKarp(origemGrupo);

        for (int i = 1; i <= nos; i++) {
            if (i == origemGrupo) continue;
            System.out.println("Destination " + i + ": " + result[i]);
        }
        
    }
}