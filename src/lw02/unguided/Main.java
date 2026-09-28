import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    
    // Batas maksimal pinjam buku
    private static final int MAX_BORROW = 2;

    public static void main(String[] args) {

        // LinkedList untuk nyimpen semua request awal
        LinkedList<String[]> requests = new LinkedList<>();

        // Setup data buku dan stok awal
        LinkedList<String[]> books = new LinkedList<>();
        books.add(new String[] { "Kalkulus", "2" });
        books.add(new String[] { "Fisika", "1" });
        books.add(new String[] { "Statistika", "2" });

        // LinkedList untuk nyimpen data member dan jumlah pinjamannya
        LinkedList<String[]> members = new LinkedList<>();

        // 1. Baca data dari borrowing.txt
        try {
            // Path ini disesuaikan asumsi program di-run dari root folder dsa-5026231208
            File file = new File("src/lw02/unguided/borrowing.txt");
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNext()) {
                String name = scanner.next();       
                String bookTitle = scanner.next();  
                requests.add(new String[] { name, bookTitle });

                // Daftarin member kalau belum ada
                findOrAddMember(members, name);
            }
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File borrowing.txt tidak ditemukan. Cek letak foldernya.");
            return;
        }

        // 2. Pindah semua request ke Queue (antrean)
        Queue<String[]> queue = new LinkedList<>();
        while (!requests.isEmpty()) {
            queue.add(requests.removeFirst());
        }

        LinkedList<String[]> successfulRequests = new LinkedList<>();
        Stack<String[]> failedRequests = new Stack<>();

        // 3. Proses antrean satu per satu (FIFO)
        while (!queue.isEmpty()) {
            String[] request = queue.poll();  
            String name = request[0];
            String bookTitle = request[1];

            String[] book = findBook(books, bookTitle);
            String[] member = findMember(members, name);

            // Kalau judul buku nggak ada di daftar
            if (book == null) {
                failedRequests.push(request);
                continue;
            }

            int stock = Integer.parseInt(book[1]);
            int borrowed = Integer.parseInt(member[1]);

            // Cek syarat sukses: stok ada DAN member belum limit
            if (stock > 0 && borrowed < MAX_BORROW) {
                successfulRequests.add(request);
                book[1] = String.valueOf(stock - 1);       
                member[1] = String.valueOf(borrowed + 1);  
            } else {
                // Kalau gagal, masukin ke Stack
                failedRequests.push(request);
            }
        }

        // 4. Print hasil sesuai contoh output
        System.out.println("=== Successfully Processed Requests ===");
        for (String[] req : successfulRequests) {
            System.out.println(req[0] + " " + req[1]);
        }

        System.out.println("=== Remaining Book Stock ===");
        for (String[] b : books) {
            System.out.println(b[0] + " : " + b[1]);
        }

        System.out.println("=== Failed Requests ===");
        // Pop semua failed request biar keluarnya LIFO (terbalik)
        while (!failedRequests.isEmpty()) {
            String[] failed = failedRequests.pop();
            System.out.println(failed[0] + " " + failed[1]);
        }
    }

    // Helper method buat nyari atau nambah member baru
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

    // Helper method buat nyari member
    private static String[] findMember(LinkedList<String[]> members, String name) {
        for (String[] m : members) {
            if (name.equals(m[0])) {
                return m;
            }
        }
        return null;
    }

    // Helper method buat nyari buku
    private static String[] findBook(LinkedList<String[]> books, String title) {
        for (String[] b : books) {
            if (title.equals(b[0])) {
                return b;
            }
        }
        return null;
    }
}
