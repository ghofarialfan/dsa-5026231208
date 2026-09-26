import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();
        Queue<String[]> queue = new LinkedList<>();
        Stack<String[]> failedWithdrawals = new Stack<>();

        try (Scanner scanner = new Scanner(resolveTransactionsFile())) {
            while (scanner.hasNext()) {
                String name = scanner.next();
                String type = scanner.next();
                String amount = scanner.next();
                transactions.add(new String[] { name, type, amount });
            }
        } catch (FileNotFoundException e) {
            System.err.println("File transactions.txt tidak ditemukan.");
            return;
        }

        while (!transactions.isEmpty()) {
            queue.add(transactions.removeFirst());
        }

        while (!queue.isEmpty()) {
            String[] transaction = queue.poll();
            String name = transaction[0];
            String type = transaction[1];
            int amount = Integer.parseInt(transaction[2]);
            String[] customer = findOrAddCustomer(customers, name);
            int balance = Integer.parseInt(customer[1]);

            if ("DEPOSIT".equals(type)) {
                customer[1] = String.valueOf(balance + amount);
            } else if ("WITHDRAW".equals(type)) {
                if (amount > balance) {
                    failedWithdrawals.push(transaction);
                } else {
                    customer[1] = String.valueOf(balance - amount);
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + " : " + customer[1]);
        }

        System.out.println("=== Failed Transactions ===");
        while (!failedWithdrawals.isEmpty()) {
            String[] failed = failedWithdrawals.pop();
            System.out.println(failed[0] + " WITHDRAW " + failed[2]);
        }
    }

    private static String[] findOrAddCustomer(LinkedList<String[]> customers, String name) {
        for (String[] customer : customers) {
            if (name.equals(customer[0])) {
                return customer;
            }
        }
        String[] created = new String[] { name, "0" };
        customers.add(created);
        return created;
    }

    private static File resolveTransactionsFile() {
        List<File> candidates = new ArrayList<>();
        candidates.add(new File("transactions.txt"));
        candidates.add(new File("src/lw02/prelab/transactions.txt"));
        candidates.add(new File("dsa-5026231208/src/lw02/prelab/transactions.txt"));
        candidates.add(new File("../lw02/prelab/transactions.txt"));

        try {
            File codeLocation = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (codeLocation.isFile()) {
                codeLocation = codeLocation.getParentFile();
            }
            if (codeLocation != null) {
                candidates.add(new File(codeLocation, "transactions.txt"));
                File parent = codeLocation.getParentFile();
                if (parent != null) {
                    candidates.add(new File(parent, "transactions.txt"));
                }
            }
        } catch (Exception ignored) {
            // Fall back to the relative paths above.
        }

        for (File file : candidates) {
            if (file.isFile()) {
                return file;
            }
        }
        return new File("transactions.txt");
    }
}
