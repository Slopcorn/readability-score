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

    /** Calculates the ARI.
     * @param chars The amount of characters in the text, not counting whitespace.
     * @param words The amount of words in the text.
     * @param sents The amount of sentences in the text.
     * @return The automated readability index of the text.
     */
    private double automatedReadabilityIndex(int chars, int words, int sents) {
        return 4.71 * chars / words + 0.5 * words / sents - 21.43;
    }
}
