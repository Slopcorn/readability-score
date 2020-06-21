package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        // Split to find all the sentences in the text.
        String[] sentences = text.split("[.!?]");

        // Counters for sentences and words
        int sents = sentences.length;
        int words = 0;

        // Find wordcount
        for (String sentence : sentences) {
            words += sentence.split(" ").length;
        }

        System.out.println(text);
        System.out.println(words / sents > 10 ? "HARD" : "EASY");
    }
}
