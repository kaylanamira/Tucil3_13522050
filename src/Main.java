package src;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter startword : ");
            String startWord = scanner.nextLine();
            System.out.print("Enter endword(length must be identical to startword's length) : ");
            String endWord = scanner.nextLine();
            scanner.close();

            int len = startWord.length();
            if (len != endWord.length()){
                System.out.println("Startword and endword are not in the same length!\n");
                System.exit(0);
            }

            Dictionary d = new Dictionary(len);
            long startTime = System.nanoTime();
            Ucs s3 = new Ucs(startWord,endWord,d);
            s3.solve();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            long seconds = (duration / 1000) % 60;
            s3.printResult();
            String formatedSeconds = String.format("(0.%d seconds)", seconds);
            System.out.println("total runtime for GBFS 3 = "+ formatedSeconds);
            // System.out.println(seconds);

            // startTime = System.nanoTime();
            // Ucs u = new Ucs(startWord,endWord,d);
            // u.solve();
            // endTime = System.nanoTime();
            // duration = (endTime - startTime);
            // seconds = (duration / 1000) % 60;
            // u.printResult();
            // formatedSeconds = String.format("(0.%d seconds)", seconds);
            // // System.out.println(seconds);
            // System.out.printf("total runtime for UCS = "+ formatedSeconds);

            // startTime = System.nanoTime();
            // GBFS3 s3 = new GBFS3(startWord,endWord,d);
            // s3.solve();
            // endTime = System.nanoTime();
            // duration = (endTime - startTime);
            // seconds = (duration / 1000) % 60;
            // s3.printResult();
            // formatedSeconds = String.format("(0.%d seconds)", seconds);
            // System.out.println("total runtime for GBFS 3 = "+ formatedSeconds);
        } catch (FileNotFoundException e) {
            System.err.println("No dictionary file found");
        }
    }
}
