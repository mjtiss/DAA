import java.util.*;


class Arco {
    int no_final;
    
    Arco(int fim){
	no_final = fim;
    }

    int extremo_final() {
	return no_final;
    }
}


class No {
    LinkedList<Arco> adjs;
    
    No() {
	adjs = new LinkedList<Arco>();
    }
}


class Grafo {
    No verts[];
    int nvs, narcos;
    
    public Grafo(int n) {
	nvs = n;
	narcos = 0;
	verts  = new No[n];
	for (int i = 0 ; i < n ; i++)
	    verts[i] = new No();
	// para vertices numerados de 0 a n-1
    }
    
    public int num_vertices(){
	return nvs;
    }
    
    public int num_arcos(){
	return narcos;
    }

    public LinkedList<Arco> adjs_no(int i) {
	return verts[i].adjs;
    }
    
    public void insert_new_arc(int i, int j){
	verts[i].adjs.addFirst(new Arco(j));
	narcos++;
    }

    public Arco find_arc(int i, int j){
	for (Arco adj: adjs_no(i))
	    if (adj.extremo_final() == j) return adj;
	return null;
    }
}


public class SobPressao{	

    public static int[][] lerPrefs(Scanner stdin,int n) {
	int [][] prefs = new int[n][n];
	
	for(int i=0; i<n; i++)
	    for(int j=0;j<n;j++)
		prefs[i][j] = stdin.nextInt()-1; // corrige identificador
	
	return prefs;
    }


    public static Grafo constroiGrafo(int [] atrib,int [][] prefs, int n) {
	Grafo g = new Grafo(n);

	for(int p=0; p<n; p++) {
	    int r;
	    for(r=0;r<n && prefs[p][r] != atrib[p]; r++);  // rank 
	    for(int t=0; t <r; t++)  // pressao todas acima
		g.insert_new_arc(atrib[p],prefs[p][t]);
	}

	return g;
    }

    public static int [] grauEntrada(Grafo g,int n) {
	int [] grau = new int[n];
	
	for(int v=0;v<n;v++) 
	    grau[v] = 0;

	for(int v=0;v<n; v++) {
	    LinkedList<Arco> adjsv = g.adjs_no(v);
	    for (Arco a: adjsv) 
		grau[a.extremo_final()]++;
	}
	
	return grau;
    }

    
    public static int caminhoMaximoDAG(Grafo g,int n) {

	// determina grau de entrada dos vertices

	int [] grauE = grauEntrada(g,n);
    
	// inicializa fila e distancia

	int [] dist = new int[n];
	LinkedList<Integer> q = new LinkedList<Integer>();

	for (int v=0; v <n; v++) {
	    dist[v] = 0;
	    if (grauE[v]==0) 
		q.add(v);
	}

	// determina caminho maximo
	int contaVisitados = 0;
	int maxdist = -1;

	while(!q.isEmpty()) {
	    int v = q.remove();
	    contaVisitados++;
	    if (dist[v] > maxdist) 
		maxdist = dist[v];
	    LinkedList<Arco> adjsv = g.adjs_no(v);
	    for (Arco a: adjsv) {
		int w = a.extremo_final();
		if (dist[v]+1 > dist[w])
		    dist[w] = dist[v]+1;
		grauE[w]--;
		if (grauE[w] == 0) 
		    q.add(w);
	    }
	}

	if (contaVisitados != n)
	    return -1;
	return maxdist;
    }

	    
    public static void main(String args[]){
	Scanner stdin = new Scanner(System.in);

	int n = stdin.nextInt();

	int [][] prefs = lerPrefs(stdin,n);

	int nprop = stdin.nextInt();
	
	int [] atrib = new int[n];

	while(nprop-- > 0) {

            // ler proposta

	    for(int p=0;p<n;p++)
		atrib[p] = stdin.nextInt()-1; // corrige identificador

	    // constroi grafo

	    Grafo g = constroiGrafo(atrib,prefs,n);

	    // determinar caminho maximo se for DAG (ou retorna -1)

	    int pressao = caminhoMaximoDAG(g,n);

	    // escreve o resultado

	    if(pressao == -1)
		System.out.println("Indeterminado (nao Pareto-optima)");
	    else System.out.println(pressao);
	}
    }
}