import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("branches.txt"))) {
            int n = scanner.nextInt();
            int[] branches = new int[n];
            for (int i = 0; i < n; i++) {
                branches[i] = scanner.nextInt();
            }

            int m = scanner.nextInt();
            int[] targets = new int[m];
            for (int i = 0; i < m; i++) {
                targets[i] = scanner.nextInt();
            }

            insertionSortThreePasses(branches);

            for (int i = 0; i < branches.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(branches[i]);
            }
            System.out.println();

            for (int target : targets) {
                int index = linearSearch(branches, target);
                if (index >= 0) {
                    System.out.println(target + ": FOUND at index " + index);
                } else {
                    System.out.println(target + ": NOT FOUND");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File branches.txt tidak ditemukan.");
        }
    }

    private static void insertionSortThreePasses(int[] branches) {
        for (int i = 1; i <= 3; i++) {
            int key = branches[i];
            int j = i - 1;
            while (j >= 0 && branches[j] < key) {
                branches[j + 1] = branches[j];
                j--;
            }
            branches[j + 1] = key;
        }
    }

    private static int linearSearch(int[] branches, int target) {
        for (int i = 0; i < branches.length; i++) {
            if (branches[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
