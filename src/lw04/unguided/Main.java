import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("cards.txt"))) {
            int n = scanner.nextInt();
            int[] cards = new int[n];
            for (int i = 0; i < n; i++) {
                cards[i] = scanner.nextInt();
            }

            
            int m = scanner.nextInt();
            int[] targets = new int[m];
            for (int i = 0; i < m; i++) {
                targets[i] = scanner.nextInt();
            }

            
            sortCards(cards, 2);

            for (int i = 0; i < cards.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(cards[i]);
            }
            System.out.println();

            for (int target : targets) {
                int index = findCard(cards, target);
                if (index >= 0) {
                    System.out.println(target + ": FOUND at index " + index);
                } else {
                    System.out.println(target + ": NOT FOUND");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File cards.txt tidak ditemukan.");
        }
    }

    
    private static void sortCards(int[] arr, int passes) {
        for (int i = 0; i < passes; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    private static int findCard(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
