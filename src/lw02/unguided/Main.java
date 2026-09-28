import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {


    private static final int MAX_BORROW = 2;

    public static void main(String[] args) {

        LinkedList<String[]> requests = new LinkedList<>();


        LinkedList<String[]> books = new LinkedList<>();
        books.add(new String[] { "Kalkulus", "2" });
        books.add(new String[] { "Fisika", "1" });
        books.add(new String[] { "Statistika", "2" });

        LinkedList<String[]> members = new LinkedList<>();

        
        try (Scanner scanner = new Scanner(resolveBorrowingFile())) {
            while (scanner.hasNext()) {
                String name = scanner.next();       
                String bookTitle = scanner.next();  
                requests.add(new String[] { name, bookTitle });

                
                findOrAddMember(members, name);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File borrowing.txt tidak ditemukan.");
            return;
        }

        
        Queue<String[]> queue = new LinkedList<>();
        while (!requests.isEmpty()) {
            queue.add(requests.removeFirst());
        }

        
        LinkedList<String[]> successfulRequests = new LinkedList<>();

        Stack<String[]> failedRequests = new Stack<>();

        while (!queue.isEmpty()) {
            String[] request = queue.poll();  
            String name = request[0];
            String bookTitle = request[1];

            String[] book = findBook(books, bookTitle);
            String[] member = findMember(members, name);

            if (book == null) {
                failedRequests.push(request);
                continue;
            }

            int stock = Integer.parseInt(book[1]);
            int borrowed = Integer.parseInt(member[1]);

            if (stock > 0 && borrowed < MAX_BORROW) {
                successfulRequests.add(request);
                book[1] = String.valueOf(stock - 1);       
                member[1] = String.valueOf(borrowed + 1);  
            } else {
                failedRequests.push(request);
            }
        }

    

        System.out.println("=== Successfully Processed Requests ===");
        for (String[] req : successfulRequests) {
            System.out.println(req[0] + " " + req[1]);
        }

        System.out.println("=== Remaining Book Stock ===");
        for (String[] b : books) {
            System.out.println(b[0] + " : " + b[1]);
        }

        System.out.println("=== Failed Requests ===");

        while (!failedRequests.isEmpty()) {
            String[] failed = failedRequests.pop();
            System.out.println(failed[0] + " " + failed[1]);
        }
    }


    private static String[] findOrAddMember(LinkedList<String[]> members, String name) {
        for (String[] m : members) {
            if (name.equals(m[0])) {
                return m;
            }
        }
        String[] created = new String[] { name, "0" };
        members.add(created);
        return created;
    }


    private static String[] findMember(LinkedList<String[]> members, String name) {
        for (String[] m : members) {
            if (name.equals(m[0])) {
                return m;
            }
        }
        return null;
    }


    private static String[] findBook(LinkedList<String[]> books, String title) {
        for (String[] b : books) {
            if (title.equals(b[0])) {
                return b;
            }
        }
        return null;
    }

    private static File resolveBorrowingFile() {
        List<File> candidates = new ArrayList<>();
        candidates.add(new File("borrowing.txt"));
        candidates.add(new File("src/lw02/unguided/borrowing.txt"));
        candidates.add(new File("dsa-5026231208/src/lw02/unguided/borrowing.txt"));
        candidates.add(new File("../unguided/borrowing.txt"));

        try {
            File codeLocation = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (codeLocation.isFile()) {
                codeLocation = codeLocation.getParentFile();
            }
            if (codeLocation != null) {
                candidates.add(new File(codeLocation, "borrowing.txt"));
                File parent = codeLocation.getParentFile();
                if (parent != null) {
                    candidates.add(new File(parent, "borrowing.txt"));
                }
            }
        } catch (Exception ignored) {

        }

        for (File file : candidates) {
            if (file.isFile()) {
                return file;
            }
        }
        return new File("borrowing.txt");
    }
}
