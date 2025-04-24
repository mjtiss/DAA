//-----------------------------------------------------------------------______
// Example of implementation of Dijkstra algorithm
//
// (assumes a weighted digraph, without negative weights)
// Based on https://www.dcc.fc.up.pt/~pribeiro/aulas/daa2223/codigo/dijkstra.java
//---------------------------------------------------------------------------------

import java.util.*;

// Class that represents an edge
class Edge {
    int enode;     // endnode
    int weight; // weight on edge
    
    Edge(int t, int w) {
        enode = t;
        weight = w;
    }
}

// Class that represents a node of the graph
class Node {
    public LinkedList<Edge> adj; // The list of outgoing edges (to adjacent nodes)
    public boolean visited;      // Has the node been visited in a graph traversal?
    public int distance;         //  Distance from a source node (or to keep some value)?
    
    Node() {
        adj = new LinkedList<>();
    } 
};

// Class that represents a node for the priority queue
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

// Class that represents a weighted digraph 
class Graph {
    int n;          // Number of nodes
    Node[] nodes;   // Array of nodes 
    
    Graph(int n) {  // constructs a graph with n nodes and 0 edges
        this.n = n;
        nodes = new Node[n+1];   // +1 if the labels start at 1 instead of 0
        for (int i=1; i<=n; i++)
            nodes[i] = new Node();
    }
    
    void addLink(int a, int b, int c) {
        nodes[a].adj.add(new Edge(b, c));
    }

    // Algorithm by Dijkstra
    void dijkstra(int s) {
	
        // Initialize all nodes as not visited and with "infinite" distance
        for (int i=1; i<=n; i++) {
            nodes[i].distance = Integer.MAX_VALUE;
            nodes[i].visited  = false;
        }
	
        // Inicialize the priority queue with source node s
        nodes[s].distance = 0;
        TreeSet<NodeQ> q = new TreeSet<>();
        q.add(new NodeQ(0, s)); // Creates a pair (dist=0, no=s)

        while (!q.isEmpty()) {
      
            // Remove node with minimum distance (the first in the set which is a BST)
            NodeQ nq = q.pollFirst();
            int  u = nq.node;
            nodes[u].visited = true;
            System.out.println(u + " [dist=" + nodes[u].distance + "]");
	        
            // Update distances of adjacent nodes ("Relax edges")
            for (Edge e : nodes[u].adj) {
                int v = e.enode;
                int cost = e.weight;
                if (!nodes[v].visited && nodes[u].distance + cost < nodes[v].distance) {
                    q.remove(new NodeQ(nodes[v].distance, v)); // Remove from the set
                    nodes[v].distance = nodes[u].distance + cost;
                    q.add(new NodeQ(nodes[v].distance, v));    // Insert a new node with a shorter distance
                }
            }
        }
    }
};


public class dijkstra {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        Graph g = new Graph(in.nextInt());
        int   e = in.nextInt();
        for (int i=0; i<e; i++) 
            g.addLink(in.nextInt(), in.nextInt(), in.nextInt());

        // Example starting from node 1
        g.dijkstra(1);
    }
}