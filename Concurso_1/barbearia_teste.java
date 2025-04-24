import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class barbearia_teste {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        
        int perdeu = 0;
        Queue<Integer> serviceTimes = new LinkedList<>(); // Queue of waiting customers' service times
        int barberFreeTime = 0; // Time when barber becomes free
        
        for (int i = 0; i < k; i++) {
            int h = scanner.nextInt();
            int m = scanner.nextInt();
            int d = scanner.nextInt();
            
            int arrivalTime = h * 60 + m;
            
            // Check if shop is open (9:00-12:00 or 15:00-19:00, inclusive)
            if ((arrivalTime >= 9 * 60 && arrivalTime <= 12 * 60) || 
                (arrivalTime >= 15 * 60 && arrivalTime <= 19 * 60)) {
                
                // Process any customers who would have been served by arrival time
                // This simulates customers moving from waiting area to barber
                while (!serviceTimes.isEmpty() && barberFreeTime <= arrivalTime) {
                    barberFreeTime += serviceTimes.poll(); // Next customer is served
                }
                
                // If barber is free, customer goes directly to service
                if (barberFreeTime <= arrivalTime) {
                    barberFreeTime = arrivalTime + d;
                }
                // If there's space in waiting area (max 3)
                else if (serviceTimes.size() < 3) {
                    serviceTimes.add(d);
                }
                // No space, customer is lost
                else {
                    perdeu++;
                }
            }
            // Shop closed, customer is lost
            else {
                perdeu++;
            }
        }
        
        System.out.println("Perdeu = " + perdeu);
        scanner.close();
    }
}