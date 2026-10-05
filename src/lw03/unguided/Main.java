import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> enrollments = new LinkedHashMap<String, Integer>();
        List<String> checkResults = new ArrayList<String>();
        int rejectedOperations = 0;

        try {
            Scanner scanner = new Scanner(new File("enrollment.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ");
                if (parts.length < 2) {
                    continue;
                }
                String command = parts[0];

                if ("REGISTER".equals(command) && parts.length >= 3) {
                    String course = parts[1];
                    int count = Integer.parseInt(parts[2]);
                    if (count <= 0) {
                        rejectedOperations++;
                    } else {
                        if (enrollments.containsKey(course)) {
                            int current = enrollments.get(course);
                            enrollments.put(course, current + count);
                        } else {
                            enrollments.put(course, count);
                        }
                    }
                } else if ("WITHDRAW".equals(command) && parts.length >= 3) {
                    String course = parts[1];
                    int count = Integer.parseInt(parts[2]);
                    if (count <= 0) {
                        rejectedOperations++;
                    } else {
                        if (enrollments.containsKey(course)) {
                            int current = enrollments.get(course);
                            if (current >= count) {
                                enrollments.put(course, current - count);
                            } else {
                                rejectedOperations++;
                            }
                        } else {
                            rejectedOperations++;
                        }
                    }
                } else if ("CHECK".equals(command)) {
                    String course = parts[1];
                    if (enrollments.containsKey(course)) {
                        checkResults.add(course + ": " + enrollments.get(course) + " students");
                    } else {
                        checkResults.add(course + ": Not found");
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File enrollment.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Enrollment Checks =====");
        for (int i = 0; i < checkResults.size(); i++) {
            System.out.println(checkResults.get(i));
        }
        System.out.println("===== Final Enrollment =====");
        for (Map.Entry<String, Integer> entry : enrollments.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " students");
        }
        System.out.println("Rejected operations: " + rejectedOperations);
    }
}
