import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class barbearia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        
        int perdeu = 0;
        Queue<Integer> serviceTimes = new LinkedList<>();
        int barberFreeTime = 0; 
        
        for (int i = 0; i < k; i++) {
            int h = scanner.nextInt();
            int m = scanner.nextInt();
            int d = scanner.nextInt();
            
            int arrivalTime = h * 60 + m;
            
            // ver se a loja esta aberta (9:00-12:00 or 15:00-19:00)
            if ((arrivalTime >= 9 * 60 && arrivalTime <= 12 * 60) || 
                (arrivalTime >= 15 * 60 && arrivalTime <= 19 * 60)) {
                
                // clientes a irem do lounge para a cadeira
                while (!serviceTimes.isEmpty() && barberFreeTime <= arrivalTime) {
                    barberFreeTime += serviceTimes.poll(); // proximo cliente!
                }
                
                //barbeiro livre, sucesso :D
                if (barberFreeTime <= arrivalTime) {
                    barberFreeTime = arrivalTime + d;
                }
                // a esperar no lounge (max 3)
                else if (serviceTimes.size() < 3) {
                    serviceTimes.add(d);
                }
                // sem lugar
                else {
                    perdeu++;
                }
            }
            // loja fechada
            else {
                perdeu++;
            }
        }
        
        System.out.println("Perdeu = " + perdeu);
        scanner.close();
    }
}
