package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) System.out.println("Please call the program with path to the input file.");
        else try (Scanner scanner = new Scanner(new File(args[0]))) {
            String text = getFileText(scanner);
            printTextInformation(text);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid filepath: " + args[0]);
        }
    }

    /** Calculates the ARI.
     * @param chars The amount of characters in the text, not counting whitespace.
     * @param words The amount of words in the text.
     * @param sents The amount of sentences in the text.
     * @return The automated readability index of the text.
     */
    private static double automatedReadabilityIndex(int chars, int words, int sents) {
        return 4.71 * chars / words + 0.5 * words / sents - 21.43;
    }

    /** Gets and prints relevant information about input text.
     * @param text Input text string.
     */
    private static void printTextInformation(String text) {
        // Split to find all the sentences in the text.
        String[] sentences = text.split("[.!?]");

        // Counters for sentences and words
        int sents = sentences.length;
        int words = wordCount(sentences);
        int chars = charCount(sentences);

        double ari = automatedReadabilityIndex(chars, words, sents);

        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();

        System.out.printf("Words: %d\n", words);
        System.out.printf("Sentences: %d\n", sents);
        System.out.printf("Characters: %d\n", chars);

        System.out.printf("The score is: %f.2\n", ari);
        printReadingLevelARI();
    }

    private static void printReadingLevelARI() {
        // TODO: implement me
    }

    private static int wordCount(String[] sentences) {
        // TODO: implement me
        return 0;
    }

    private static int charCount(String[] sentences) {
        // TODO: implement me
        return 0;
    }

    /** Gets all of the text from a scanner.
     * @param scanner Input scanner.
     * @return A string containing all of the text.
     */
    private static String getFileText(Scanner scanner) {
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append('\n');
        }

        return sb.toString();
    }
}
