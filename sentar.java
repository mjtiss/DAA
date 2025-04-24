import java.util.Scanner;

public class sentar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int tiposCadeiras = scanner.nextInt();  
        int totalCadeiras = 0;
        int[] tipos = new int[tiposCadeiras];
        
        
        for (int i = 0; i < tiposCadeiras; i++) {
            int t = scanner.nextInt(); 
            int v = scanner.nextInt(); 
            totalCadeiras += v;
            tipos[t - 1] = v;
        }

        int nHabitantes = scanner.nextInt();
        int sentados = 0;
        int[] prefs = new int[100];

    
        for (int j = 0; j < nHabitantes; j++) {
            int opcoes = scanner.nextInt(); 

    
            for (int i = 0; i < opcoes; i++) {
                prefs[i] = scanner.nextInt();
            }

            
            for (int k = 0; k < opcoes; k++) {
                if (tipos[prefs[k] - 1] > 0) { 
                    sentados++;
                    tipos[prefs[k] - 1]--;
                    break;  
                }
            }
        }

        int dePe = nHabitantes - sentados; 
        int livres = totalCadeiras - sentados; 

    
        System.out.println(dePe);
        System.out.println(livres);
    }
}
