import java.util.*;

public class ColorLines {
    // Direções: direita, esquerda, baixo, cima, diagonal principal, diagonal inversa
    static int[] dx = {1, -1, 0, 0, 1, -1};
    static int[] dy = {0, 0, 1, -1, 1, 1};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] tabuleiro = new int[9][9];

        // Ler o estado do tabuleiro
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tabuleiro[i][j] = scanner.nextInt();
            }
        }

        // Ler as posições de origem e destino
        int origemX = scanner.nextInt() - 1;
        int origemY = scanner.nextInt() - 1;
        int destinoX = scanner.nextInt() - 1;
        int destinoY = scanner.nextInt() - 1;

        // Se a célula de destino não estiver vazia, o movimento é inválido
        if (tabuleiro[destinoX][destinoY] != 0) {
            System.out.println(0);
            return;
        }

        // Simula o movimento da bola
        int bola = tabuleiro[origemX][origemY];
        tabuleiro[origemX][origemY] = 0;  // A célula de origem fica vazia
        tabuleiro[destinoX][destinoY] = bola;  // A célula de destino recebe a bola

        // Verificar alinhamentos e remover bolas
        Set<String> bolasRemovidas = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tabuleiro[i][j] != 0) {
                    // Verificar se há alinhamentos a partir da posição (i, j)
                    for (int dir = 0; dir < 6; dir++) {
                        int x = i;
                        int y = j;
                        List<String> alinhamento = new ArrayList<>();
                        // Procura bolas na direção especificada
                        for (int k = 0; k < 5; k++) {
                            if (x >= 0 && x < 9 && y >= 0 && y < 9 && tabuleiro[x][y] == tabuleiro[i][j]) {
                                alinhamento.add(x + "," + y);
                                x += dx[dir];
                                y += dy[dir];
                            } else {
                                break;
                            }
                        }
                        if (alinhamento.size() >= 5) {
                            // Adicionar bolas ao conjunto de removidas
                            bolasRemovidas.addAll(alinhamento);
                        }
                    }
                }
            }
        }

        // Remover as bolas do tabuleiro
        for (String pos : bolasRemovidas) {
            String[] parts = pos.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            tabuleiro[x][y] = 0;
        }

        // O número de bolas removidas
        System.out.println(bolasRemovidas.size());
    }
}
