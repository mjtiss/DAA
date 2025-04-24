import java.util.Scanner;

public class disse {

    
    public static void writeGroup(int[] x, int start, int ng) {
        System.out.print(ng);
        int next = start;
        do {
            System.out.print(" " + next);
            next = x[next];
        } while (next != start);
        System.out.println();
    }

    
    public static void removeGroup(int[] x, int curr) {
        int next;
        do {
            next = x[curr];
            x[curr] = 0;
            curr = next;
        } while (x[curr] != 0);
    }


    public static void solve(int[] x, int n) {
        int i = 1;
        int outside = 0;
        int visited = 0;
        while (visited < n) {
            while (x[i] == 0) i++;
            int imax = i;
            int next = x[i];
            int lenGroup = 1;
            while (next != i) {
                lenGroup++;
                if (next > imax)
                    imax = next;
                next = x[next];
            }

            visited += lenGroup;
            if (lenGroup >= 3)
                writeGroup(x, imax, lenGroup);
            else
                outside += lenGroup;
            removeGroup(x, imax);
            i++;
        }
        System.out.println(outside);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        int n_pessoas = scanner.nextInt();

    
        int[] a = new int[n_pessoas + 1];

        
        for (int i = 1; i <= n_pessoas; i++) {
            a[i] = scanner.nextInt();
        }

        
        solve(a, n_pessoas);

        
        scanner.close();
    }
}
