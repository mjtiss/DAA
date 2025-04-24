import java.util.*;

public class atrasos{

    // analise retorna (0,0) se for impossivel usar o veículo

    public static int[] Analise(int[] x, int k, int s, int t, int a, int b){
        int p = 0;
        int j = 1;

        while(k > 1 && x[j]!=s && x[j]!=t){
            j += 3;
            p += x[j-2];
            k --;

        }

        if (x[j]!= s){
            return new int[]{0,0};
        }

        int c = b;
        k --;

        while (x[j] !=t && c >= a && k > 0){
            j += 3;
            p +=  x[j-2];
            if (x[j-1] < c){
                c = x[j-1];
            }
            k --;
        }
        if (x[j]!=t || c < a){
            return new int[]{0,0};
        }

        return new int[]{c,p};
    }


    public static void main(String[] Args){
        Scanner stdin = new Scanner(System.in);

        int rotas = stdin.nextInt();
        int nElementos = stdin.nextInt();
        int origem = stdin.nextInt();
        int destino = stdin.nextInt();

        int melhorRota = -1;
        int menosProblemas = Integer.MAX_VALUE;
        int maxLugaresDisponiveis = -1;

        for(int i = 1; i <= rotas; i++){
            int nLocais = stdin.nextInt();

            int[] dadosRota = new int[3*nLocais - 1];

            for(int j = 1; j < dadosRota.length; j++){
                dadosRota[j] = stdin.nextInt();
            }


            int[] resposta = Analise(dadosRota, nLocais, origem, destino, nElementos, 200);
            int disponiveis = resposta[0];
            int problemasRota = resposta[1];

            if (disponiveis >= nElementos){
                if ((problemasRota < menosProblemas) || (problemasRota == menosProblemas && disponiveis > maxLugaresDisponiveis) || (problemasRota == menosProblemas && disponiveis == maxLugaresDisponiveis && melhorRota == -1)){
                    melhorRota = i;
                    menosProblemas = problemasRota;
                    maxLugaresDisponiveis = disponiveis;
                }
            }

        }

        if (melhorRota != -1){
            System.out.println("Rota = " + melhorRota + " Probs = " + menosProblemas + " Lugares = " + maxLugaresDisponiveis);
        }else{
            System.out.println("Impossivel");
        }

        stdin.close();

        // rotas unidirecionais que so passam uma vez pelo mesmo lugar


    }
}