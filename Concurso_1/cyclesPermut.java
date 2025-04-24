import java.util.*;

public class CyclesPermut {
    public static void cyclesPermut(int[] x) { // assumes x[1], x[2], ..., x[n] is the permutation
        int n = x.length - 1;
        boolean[] visited = new boolean[n + 1];
        
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                int k = x[i];
                visited[i] = true;
                System.out.print("(" + i);
                while (k != i) {
                    System.out.print("," + k);
                    visited[k] = true;
                    k = x[k];
                }
                System.out.print(")");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            x[i] = scanner.nextInt();
        }
        
        cyclesPermut(x);
        scanner.close();
    }
}

/* 

O código recebe uma permutação de númerps inteiros de 1 a n e identifica os ciclos dessa permutação. Um ciclo 
ocorre quando seguimos os índices da permutação até retornar ao ponto inicial. 

1) Entrada de dados:
    - O programa lê um inteiro n, que representa o tamanho da permutação.
    - Em seguida, lê n valores que representam a permutação nnum vetor x, onde x[i] indica para onde o
    índice i é mapeado 

2) Identificação dos ciclos:
    - Um vetor visited mantém controlo dos índices já processados. 
    - Percorre-se a permutação indicando índices não visitados e imprime o ciclo correspondente. 
    - Ao encontrar um índice i, ele é marcado como visitado e segue-se a sequência 
    x[i] -> x[x[i]] ->... até retornar a i.

3) Saída:
    - Os ciclos são impressos no formato (a, b, c, ...), onde cada conjunto representa um ciclo na permutação.

*/
