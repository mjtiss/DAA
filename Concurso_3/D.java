import java.io.*;
import java.util.*;

enum Color{
    WHITE, GRAY, BLACK
}

class Node{
    public LinkedList<Integer> adj;
    public boolean visited;
    public int label;
    public Color color;

    Node(){
        adj = new LinkedList<Integer>();
    }
}

class Graph{
    int n;
    Node nodes[];

    Graph(int n){
        this.n = n;
        nodes = new Node[n+1];
        for(int i = 1; i <= n; i++){
            nodes[i] = new Node();
        }
    }

    public void addEdge(int a, int b){
        nodes[a].adj.add(b);
    }

    public boolean isEdge(int a, int b){
        return nodes[a].adj.contains(b);
    }

    public void clearVisited(){
        for(int i = 1; i <= n; i++){
            nodes[i].visited = false;
        }
    }

    public int numVertices(){
        return n;
    }

    public void bfs(int v){
        LinkedList<Integer> q = new LinkedList<Integer>();
        for(int i = 1; i <= n; i++){
            nodes[i].visited = false;
        }

        q.add(v);
        nodes[v].visited = true;
        System.out.println(v);

        while(q.size() > 0){
            int u = q.removeFirst();
            for(int w : nodes[u].adj){
                if (!nodes[w].visited){
                    q.add(w);
                    nodes[w].visited = true;
                    System.out.println(w);
                }
            }
        }
    }

    public void dfs(int v){
        nodes[v].visited = true;
        System.out.print(v);

        for(int w : nodes[v].adj)
            if (!nodes[w].visited)
                dfs(w);
    }
    
    public static Graph readGraph(Scanner scanner){
        int n = scanner.nextInt();
        Graph g = new Graph(n);
        int nedges = scanner.nextInt();
        for(int i = 0; i < nedges; i++){
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            g.addEdge(u,v);
        }
        return g;
    }

    boolean dfs_hasCycle(int v){
        nodes[v].color = Color.GRAY;

        for(int w : nodes[v].adj){
            if (nodes[w].color == Color.GRAY){
                return true;
            }
            if (nodes[w].color == Color.WHITE && dfs_hasCycle(w)){
                return true;
            }
        }
        nodes[v].color = Color.BLACK;
        return false;
    }

    public boolean hasCycle(){
        for(int v = 1; v <= n; v++){
            nodes[v].color = Color.WHITE;
        }
        for(int v = 1; v <= n; v++){
            if (nodes[v].color == Color.WHITE && dfs_hasCycle(v)){
                return true;
            }
        }
        return false;
    }

    public boolean isTopologicalOrder(int[] order){
        for(int i = 0; i < n; i++){
            nodes[order[i]].label = i;
        }

        for(int u = 1; u <= n; u++){
            for(int v : nodes[u].adj){
                if (nodes[u].label > nodes[v].label){
                    return false;
                }
            }
        }
        return true;
    }

    public void inDegrees(int[] inDegs){
        for(int i = 1; i <= n; i++){
            inDegs[i] = 0;
        }
        for(int i = 1; i <= n; i++){
            for(int j : nodes[i].adj){
                inDegs[j] ++;
            }
        }
    }

    public boolean topologicalSort(int[] order){
        int[] inDegs = new int[n+1];
        inDegrees(inDegs);

        PriorityQueue<Integer> q = new PriorityQueue<>();
        for(int i = 1; i <= n; i++){
            if(inDegs[i] == 0){
                q.add(i);
            }
        }
        int index = 0;
        boolean single = true;

        while(!q.isEmpty()){
            if (q.size() > 1){
                single = false;
            }
            int u = q.poll();
            order[index++] = u;

            for(int v : nodes[u].adj){
                inDegs[v]--;
                if(inDegs[v] == 0){
                    q.add(v);
                }
            }
        }
        return single;
    }
}


public class D{

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        int nos = scanner.nextInt();
        int tarefas = scanner.nextInt();

        for(int i = 1; i <= nos; i++){
            
        }

    }
}