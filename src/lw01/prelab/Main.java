import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<PrintJob> jobs = new ArrayList<>();

        try (Scanner scanner = new Scanner(resolveJobsFile())) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                String id = scanner.next();
                int pages = scanner.nextInt();

                PrintJob job;
                if ("MONO".equals(type)) {
                    job = new MonoPrint(id, pages);
                } else if ("COLOUR".equals(type)) {
                    job = new ColourPrint(id, pages);
                } else {
                    throw new IllegalArgumentException("Unknown job type: " + type);
                }
                jobs.add(job);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File jobs.txt tidak ditemukan.");
            return;
        }

        for (PrintJob job : jobs) {
            System.out.println(job.summary());
        }
    }

    private static File resolveJobsFile() {
        File[] candidates = {
            new File("jobs.txt"),
            new File("src/lw01/prelab/jobs.txt"),
            new File("dsa-5026231208/src/lw01/prelab/jobs.txt")
        };
        for (File file : candidates) {
            if (file.isFile()) {
                return file;
            }
        }
        return new File("jobs.txt");
    }
}
