import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        solveProblem1();
        solveProblem2();
        solveProblem3();
    }

    private static void solveProblem1() {
        List<String> playlist = new ArrayList<String>();

        try {
            // Menyesuaikan path file jika error (misal: "src/lw03/prelab/playlist.txt")
            Scanner scanner = new Scanner(new File("playlist.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ", 2);
                String command = parts[0];

                if ("ADD".equals(command)) {
                    if (parts.length > 1) {
                        playlist.add(parts[1]);
                    }
                } else if ("INSERT".equals(command)) {
                    String[] rest = parts[1].split(" ", 2);
                    int index = Integer.parseInt(rest[0]);
                    String song = rest[1];
                    if (index >= 0 && index <= playlist.size()) {
                        playlist.add(index, song);
                    }
                } else if ("REMOVE".equals(command)) {
                    if (parts.length > 1) {
                        playlist.remove(parts[1]);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File playlist.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Problem 1 =====");
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }
    }

    private static void solveProblem2() {
        Set<String> participants = new LinkedHashSet<String>();
        int duplicates = 0;

        try {
            // Mesuaikan path file jika error (misal: "src/lw03/prelab/participants.txt")
            Scanner scanner = new Scanner(new File("participants.txt"));
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    continue;
                }
                boolean added = participants.add(name);
                if (!added) {
                    duplicates++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File participants.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Problem 2 =====");
        System.out.println("Unique participants: " + participants.size());
        int counter = 1;
        for (String name : participants) {
            System.out.println(counter + ". " + name);
            counter++;
        }
        System.out.println("Duplicate registrations: " + duplicates);
    }

    private static void solveProblem3() {
        Map<String, Integer> inventory = new LinkedHashMap<String, Integer>();
        int failedSales = 0;

        try {
            // Mesuaikan path file jika error (misal: "src/lw03/prelab/inventory.txt")
            Scanner scanner = new Scanner(new File("inventory.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ");
                if (parts.length < 3) {
                    continue;
                }
                String type = parts[0];
                String product = parts[1];
                int quantity = Integer.parseInt(parts[2]);

                if ("ADD".equals(type)) {
                    if (inventory.containsKey(product)) {
                        int current = inventory.get(product);
                        inventory.put(product, current + quantity);
                    } else {
                        inventory.put(product, quantity);
                    }
                } else if ("SELL".equals(type)) {
                    if (inventory.containsKey(product)) {
                        int stock = inventory.get(product);
                        if (stock >= quantity) {
                            inventory.put(product, stock - quantity);
                        } else {
                            failedSales++;
                        }
                    } else {
                        failedSales++;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File inventory.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Problem 3 =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Failed sales: " + failedSales);
    }
}