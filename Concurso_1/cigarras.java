import java.util.*;

public class cigarras {

    public static void cigarrasC() {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> path = new HashMap<>();
        int start = scanner.nextInt();
        int curr = start;

        
        while (curr != 0) {
            int next = scanner.nextInt();
            path.put(curr, next); 
            curr = next;
        }

        
        writePath(path, start);
        scanner.close();
    }

    
    public static void writePath(Map<Integer, Integer> path, int start) {
        while (start != 0) {
            System.out.println(start); 
            start = path.get(start); 
        }
    }

    
    public static void main(String[] args) {
        cigarrasC(); 
    }
}
